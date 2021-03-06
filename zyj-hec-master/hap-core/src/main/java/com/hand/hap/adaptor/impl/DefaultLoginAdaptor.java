/*
 * #{copyright}#
 */
package com.hand.hap.adaptor.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import com.google.common.base.Throwables;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.WebUtils;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.adaptor.ILoginAdaptor;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.components.CaptchaConfig;
import com.hand.hap.core.components.SysConfigManager;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.core.util.TimeZoneUtil;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.security.IUserSecurityStrategy;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.security.captcha.ICaptchaManager;
import com.hand.hap.security.service.impl.UserSecurityStrategyManager;
import com.hand.hap.system.dto.ResponseData;

/**
 * 默认登陆代理类.
 *
 * @author njq.niu@hand-china.com
 * @author xiawang.liu@hand-china.com 2016年1月19日 TODO:URL和页面分开
 */
public class DefaultLoginAdaptor implements ILoginAdaptor {

    private static final boolean VALIDATE_CAPTCHA = true;

    /**
     * 校验码
     */
    private static final String KEY_VERIFICODE = "verifiCode";

    /**
     * 默认主页
     */
    private static final String VIEW_INDEX = "/";

    /**
     * 默认的登录页
     */
    private static final String VIEW_LOGIN = "/login";

    /**
     * 默认角色选择路径
     */
    private static final String VIEW_ROLE_SELECT = "/role";


    @Autowired
    private ICaptchaManager captchaManager;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private CaptchaConfig captchaConfig;

    @Autowired
    private IFndCompanyService companyService;

    @Autowired
    private SysConfigManager sysConfigManager;

    @Autowired
    UserSecurityStrategyManager userSecurityStrategyManager;


    public ModelAndView doLogin(User user, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView view = new ModelAndView();
        Locale locale = RequestContextUtils.getLocale(request);
        view.setViewName(getLoginView(request));
        try {
            beforeLogin(view, user, request, response);
            checkCaptcha(view, user, request, response);
            user = userService.login(user);
            HttpSession session = request.getSession(true);
            session.setAttribute(User.FIELD_USER_ID, user.getUserId());
            session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
            session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
            setTimeZoneFromPreference(session, user.getUserId());
            generateSecurityKey(session);
            afterLogin(view, user, request, response);
        } catch (UserException e) {
            view.addObject("msg", messageSource.getMessage(e.getCode(), e.getParameters(), locale));
            view.addObject("code", e.getCode());
            processLoginException(view, user, e, request, response);
        }
        return view;
    }

    private void setTimeZoneFromPreference(HttpSession session, Long accountId) {
        String tz = "GMT+0800";
        if (StringUtils.isBlank(tz)) {
            tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
        }
        session.setAttribute(BaseConstants.PREFERENCE_TIME_ZONE, tz);
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    /**
     * 登陆前逻辑.
     *
     * @param view     视图
     * @param account  账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    protected void beforeLogin(ModelAndView view, User account, HttpServletRequest request,
                               HttpServletResponse response) throws UserException {

    }

    /**
     * 处理登陆异常.
     *
     * @param view
     * @param account
     * @param e
     * @param request
     * @param response
     */
    protected void processLoginException(ModelAndView view, User account, UserException e, HttpServletRequest request,
                                         HttpServletResponse response) {

    }

    /**
     * 校验验证码是否正确.
     *
     * @param view     视图
     * @param user     账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    private void checkCaptcha(ModelAndView view, User user, HttpServletRequest request, HttpServletResponse response)
            throws UserException {
        if (VALIDATE_CAPTCHA) {
            Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
            String captchaCode = request.getParameter(KEY_VERIFICODE);
            if (cookie == null || StringUtils.isEmpty(captchaCode)
                    || !captchaManager.checkCaptcha(cookie.getValue(), captchaCode)) {
                throw new UserException(UserException.ERROR_INVALID_CAPTCHA, UserException.ERROR_INVALID_CAPTCHA, null);
            }
        }
    }

    /**
     * 账号登陆成功后处理逻辑.
     *
     * @param view     视图
     * @param user     账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    protected void afterLogin(ModelAndView view, User user, HttpServletRequest request, HttpServletResponse response)
            throws UserException {
        view.setViewName(BaseConstants.VIEW_REDIRECT + getRoleView(request));
        Cookie cookie = new Cookie(User.FIELD_USER_NAME, user.getUserName());
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        Locale locale = RequestContextUtils.getLocale(request);
        addCookie("LANG", locale.getLanguage(), request, response);
    }

    @Override
    public ModelAndView doSelectRole(UserRole userRole, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ModelAndView result = new ModelAndView();
        // 选择角色
        HttpSession session = request.getSession(false);
        if (session != null && userRole != null && userRole.getCompanyId() != null) {
            userRole.setUserId((Long) session.getAttribute(User.FIELD_USER_ID));
            if (sysConfigManager.getRoleMergeFlag()) {
                List<IRole> roles = roleService.selectActiveRolesByUserAndCompany(RequestHelper.createServiceRequest(request), userRole);
                List<Long> roleIds = new ArrayList<>();
                for (IRole role : roles) {
                    roleIds.add(role.getRoleId());
                }
                Long[] ids = roleIds.toArray(new Long[0]);
                session.setAttribute(IRequest.FIELD_ALL_ROLE_ID, ids);
                session.setAttribute(IRequest.FIELD_ROLE_ID, roles.get(0).getRoleId());
            } else {
                roleService.checkUserRoleExists(userRole.getUserId(), userRole.getRoleId(), userRole.getCompanyId());
                Long[] ids = new Long[1];
                ids[0] = userRole.getRoleId();
                session.setAttribute(IRequest.FIELD_ALL_ROLE_ID, ids);
                session.setAttribute(IRequest.FIELD_ROLE_ID, ids[0]);
            }

            FndCompany company = new FndCompany();
            company.setCompanyId(userRole.getCompanyId());
            company = companyService.selectByPrimaryKey(RequestHelper.createServiceRequest(request), company);
            session.setAttribute(IRequest.FIELD_COMPANY_ID, company.getCompanyId());
            session.setAttribute(IRequest.FIELD_MAG_ORG_ID, company.getMagOrgId());
            session.setAttribute(FndCompany.FIELD_COMPANY_SHORT_NAME, company.getCompanyShortName());
            session.setAttribute(FndCompany.FIELD_COMPANY_CODE, company.getCompanyCode());
            String targetUrl = (String) session.getAttribute("targetUrl");
            if (targetUrl != null) {
                result.setViewName(BaseConstants.VIEW_REDIRECT + targetUrl);
            } else {
                result.setViewName(BaseConstants.VIEW_REDIRECT + getIndexView(request));
            }
            session.removeAttribute("targetUrl");
        } else {
            result.setViewName(BaseConstants.VIEW_REDIRECT + getLoginView(request));
        }
        return result;
    }


    /**
     * 获取主界面.
     *
     * @param request
     * @return 视图
     */
    protected String getIndexView(HttpServletRequest request) {
        return VIEW_INDEX;
    }

    /**
     * 获取登陆界面.
     *
     * @param request
     * @return 视图
     */
    protected String getLoginView(HttpServletRequest request) {
        return VIEW_LOGIN;
    }


    /**
     * 获取角色选择界面.
     *
     * @param request
     * @return 视图
     */
    protected String getRoleView(HttpServletRequest request) {
        return VIEW_ROLE_SELECT;
    }

    /**
     * 集成类中可扩展此方法实现不同的userService.
     *
     * @return IUserService
     */
    public IUserService getUserService() {
        return userService;
    }

    @Override
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        //跳转选择公司&角色页面
        ModelAndView mav = indexModelAndView(request, response);
        String userName;
        if (session != null) {
            // 获取user
            userName = (String) session.getAttribute(User.FIELD_USER_NAME);
            if (userName != null) {
                if (session.getAttribute(User.LOGIN_CHANGE_INDEX) != null) {
                    User user = userService.selectByUserName(userName);
                    List<IUserSecurityStrategy> userSecurityStrategies = userSecurityStrategyManager.getUserSecurityStrategyList();
                    for (IUserSecurityStrategy userSecurityStrategy : userSecurityStrategies) {
                        ModelAndView mv = userSecurityStrategy.loginVerifyStrategy(user, request);
                        if (mv != null) {
                            return mv;
                        }
                    }
                    session.removeAttribute(User.LOGIN_CHANGE_INDEX);
                }
            } else {
                return new ModelAndView(BaseConstants.VIEW_REDIRECT + getLoginView(request));
            }
        }

        Long companyId = (Long) session.getAttribute(IRequest.FIELD_COMPANY_ID);
        Long roleId = (Long) session.getAttribute(IRequest.FIELD_ROLE_ID);
        if (companyId == null || roleId == null) {
            return new ModelAndView(BaseConstants.VIEW_REDIRECT + getRoleView(request));
        }
        UserRole userRole = new UserRole();
        userRole.setCompanyId(companyId);
        userRole.setUserId((Long) session.getAttribute(User.FIELD_USER_ID));
        userRole.setRoleId(roleId);

        List<UserRole> activeCompanies = roleService
                .selectActiveCompaniesWithRoles(RequestHelper.createServiceRequest(request), userRole
                        , sysConfigManager.getRoleMergeFlag());
        mav.addObject("SYS_USER_COMPANIES", activeCompanies);
        mav.addObject("CURRENT_USER_COMPANY", companyId);
        mav.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));

        if (!sysConfigManager.getRoleMergeFlag()) {
            mav.addObject("CURRENT_USER_ROLE", companyId.toString() + roleId.toString());
        }

        return mav;
    }

    /**
     * 默认登陆页面.
     *
     * @param request
     * @param response
     * @return 视图
     */
    public ModelAndView indexModelAndView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @Override
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView view = new ModelAndView(getLoginView(request));
        // 配置3次以后开启验证码
        Cookie cookie = WebUtils.getCookie(request, CaptchaConfig.LOGIN_KEY);
        if (captchaConfig.getWrongTimes() > 0) {
            if (cookie == null) {
                String uuid = UUID.randomUUID().toString();
                cookie = new Cookie(CaptchaConfig.LOGIN_KEY, uuid);
                cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
                cookie.setMaxAge(captchaConfig.getExpire());
                cookie.setHttpOnly(true);
                if (SysConfigManager.useHttps) {
                    cookie.setSecure(true);
                }
                response.addCookie(cookie);
                captchaConfig.updateLoginFailureInfo(cookie);
            }
        }

        // 向前端传递是否开启验证码
        view.addObject("ENABLE_CAPTCHA", captchaConfig.isEnableCaptcha(cookie));

        view.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));

        Boolean error = (Boolean) request.getAttribute("error");
        Throwable exception = (Exception) request.getAttribute("exception");
        String code = UserException.ERROR_USER_PASSWORD;
        if (exception != null && !(exception instanceof BadCredentialsException)) {
            exception = Throwables.getRootCause(exception);
            code = exception.getMessage();
        }
        if (error != null && error) {
            String msg;
            Locale locale = RequestContextUtils.getLocale(request);
            msg = messageSource.getMessage(code, null, locale);
            view.addObject("msg", msg);
        }

        return view;
    }

    @Override
    public ModelAndView roleView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView(getRoleView(request));
        HttpSession session = request.getSession(false);
        mv.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));

        if (session != null) {
            // 获取user
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            if (userId != null) {
                UserRole user = new UserRole();
                user.setUserId(userId);
                session.setAttribute(User.FIELD_USER_ID, userId);
                addCookie(User.FIELD_USER_ID, userId.toString(), request, response);
                List<UserRole> activeCompanies = roleService.selectActiveCompanies(RequestHelper.createServiceRequest(request), user);
                mv.addObject("companies", activeCompanies);
                if (!sysConfigManager.getRoleMergeFlag()) {
                    mv.addObject("roles", true);
                }
            }
        }
        return mv;
    }

    @Override
    public ModelAndView casLoginFailure(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("cas_login_failure");
        Throwable exception = (Exception) request.getAttribute("exception");
        String code = UserException.ERROR_USER_PASSWORD;
        if (exception != null) {
            exception = Throwables.getRootCause(exception);
            code = exception.getMessage();
        }
        Locale locale = RequestContextUtils.getLocale(request);
        String errorMessage = messageSource.getMessage(code, null, locale);
        view.addObject("errorMessage", errorMessage);
        return view;
    }

    protected void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
                             HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public ResponseData sessionExpiredLogin(User account, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ResponseData data = new ResponseData();
        ModelAndView view = this.doLogin(account, request, response);
        ModelMap mm = view.getModelMap();
        if (mm.containsAttribute("code")) {
            data.setSuccess(false);
            data.setCode((String) mm.get("code"));
            data.setMessage((String) mm.get("msg"));
        } else {
            Object userIdObj = request.getParameter(User.FIELD_USER_ID);
            Object roleIdObj = request.getParameter(IRequest.FIELD_ROLE_ID);
            Object companyIdObj = request.getParameter(IRequest.FIELD_COMPANY_ID);
            if (userIdObj != null && roleIdObj != null && companyIdObj != null) {
                Long userId = Long.valueOf(userIdObj.toString()),
                        roleId = Long.valueOf(roleIdObj.toString()),
                        companyId = Long.valueOf(companyIdObj.toString());
                roleService.checkUserRoleExists(userId, roleId, companyId);
                HttpSession session = request.getSession();
                session.setAttribute(User.FIELD_USER_ID, userId);
                session.setAttribute(IRequest.FIELD_ROLE_ID, roleId);
            }
        }
        return data;
    }

    @Override
    public List<IRole> getRolesByCompanyId(HttpServletRequest request, Long companyId) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 获取user
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            UserRole user = new UserRole();
            user.setUserId(userId);
            user.setUserName((String) session.getAttribute(User.FIELD_USER_NAME));
            user.setCompanyId(companyId);
            return roleService.selectActiveRolesByUserAndCompany(RequestHelper.createServiceRequest(request), user);
        }
        return Collections.emptyList();
    }
}
