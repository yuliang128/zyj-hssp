package com.hand.hap.security.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.components.CaptchaConfig;
import com.hand.hap.core.components.SysConfigManager;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.core.util.TimeZoneUtil;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.hr.service.IEmployeeAssignService;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.message.websocket.CommandMessage;
import com.hand.hap.message.websocket.WebSocketSessionManager;
import com.hand.hap.security.IAuthenticationSuccessListener;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.dto.SysPreferences;
import com.hand.hap.system.service.ISysPreferencesService;

/**
 * @author shengyang.zhou@hand-china.com
 * @author njq.niu@hand-china.com
 */
@Component
public class DefaultAuthenticationSuccessListener implements IAuthenticationSuccessListener {

    @Autowired
    private IUserService userService;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private ISysPreferencesService preferencesService;

    @Autowired
    private IExpEmployeeAssignService employeeAssignService;

    @Autowired
    private IFndCompanyService companyService;

    @Autowired
    private CaptchaConfig captchaConfig;

    @Autowired
    private SysConfigManager sysConfigManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IMessagePublisher iMessagePublisher;

    @Value("${sys.user.security.generate.accesstoken:false}")
    private boolean generateAccessToken;

    @Value("${db.type}")
    private String dataSourceType;

    private static final String HAP_LOGIN_USER = BaseConstants.HAP_CACHE + "login:";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Locale locale = RequestContextUtils.getLocale(request);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.convertToUser(userDetails);
        HttpSession session = request.getSession(true);
        processRepeatLogin(user, session);
        session.setAttribute(User.FIELD_USER_ID, user.getUserId());
        session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
        session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
        //查询首选项
        SysPreferences preference = new SysPreferences();
        preference.setUserId(user.getUserId());
        List<SysPreferences> preferences = preferencesService.querySysPreferences(RequestHelper.newEmptyRequest(), preference);
        setTimeZoneFromPreference(request, getPreferences(preferences, BaseConstants.PREFERENCE_TIME_ZONE));
        setNavFromPreference(session, getPreferences(preferences, BaseConstants.PREFERENCE_NAV));
        setLocalePreference(request, getPreferences(preferences, BaseConstants.PREFERENCE_LOCALE));
        setProcessUser(user, session);
        generateSecurityKey(session);
        fetchSystemImageVersion(request);
        setJdbcType(session);
        captchaConfig.resetLoginFailureInfo(request, response);
    }

    private void setJdbcType(HttpSession session) {
        session.setAttribute(IRequest.FILED_DB_TYPE,dataSourceType);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private void fetchSystemImageVersion(HttpServletRequest request) {
        WebUtils.setSessionAttribute(request, SysConfigManager.SYS_LOGO_VERSION, sysConfigManager.getSystemLogoVersion());
        WebUtils.setSessionAttribute(request, SysConfigManager.SYS_FAVICON_VERSION, sysConfigManager.getSystemFaviconVersion());
    }

    private void processRepeatLogin(User user, HttpSession session) {
        String existSessionId = redisTemplate.opsForValue().get(HAP_LOGIN_USER + user.getUserName());
        redisTemplate.opsForValue().set(HAP_LOGIN_USER + user.getUserName(), session.getId(), session.getMaxInactiveInterval(), TimeUnit.SECONDS);
        if (existSessionId != null && !existSessionId.equals(session.getId()) && sysConfigManager.isProhibitRepeatLogin()) {
            CommandMessage commandMessage = new CommandMessage();
            commandMessage.setSessionId(session.getId());
            commandMessage.setUserName(user.getUserName());
            commandMessage.setAction("SYS_REPEAT_LOGIN");
            iMessagePublisher.publish(WebSocketSessionManager.CHANNEL_WEB_SOCKET, commandMessage);
        }
    }

    private void setProcessUser(User user, HttpSession session) {
        if (user.getEmployeeId() != null) {
            session.setAttribute(User.FIELD_EMPLOYEE_ID, user.getEmployeeId());
            session.setAttribute(User.FIELD_EMPLOYEE_CODE, user.getEmployeeCode());
            session.setAttribute(User.FIELD_EMPLOYEE_NAME, user.getEmployeeName());
        }
    }

    private void setTimeZoneFromPreference(HttpServletRequest request, SysPreferences pref) {
        String tz = pref == null ? null : pref.getPreferencesValue();
        if (StringUtils.isBlank(tz)) {
            final String appServletContextKey = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet";
            WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext(), appServletContextKey);
            TimeZone timeZone = appContext.getBean(SessionLocaleResolver.class).getDefaultTimeZone();
            tz = TimeZoneUtil.toGMTFormat(timeZone == null ? TimeZone.getDefault() : timeZone);
        }
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME,
                org.springframework.util.StringUtils.parseTimeZoneString(tz));
        request.getSession(true).setAttribute(BaseConstants.PREFERENCE_TIME_ZONE, tz);
    }

    private void setNavFromPreference(HttpSession session, SysPreferences pref) {
        String nav = pref == null ? null : pref.getPreferencesValue();
        session.setAttribute(BaseConstants.PREFERENCE_NAV, nav);
    }

    private void setLocalePreference(HttpServletRequest request, SysPreferences pref) {
        if (pref != null) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                    org.springframework.util.StringUtils.parseLocaleString(pref.getPreferencesValue()));
        }
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    private SysPreferences getPreferences(List<SysPreferences> preferencesList, String preferenceName) {
        for (SysPreferences pref : preferencesList) {
            if (preferenceName.equals(pref.getPreferences())) {
                return pref;
            }
        }
        return null;
    }


}
