package com.hand.hap.system.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.adaptor.ILoginAdaptor;
import com.hand.hap.adaptor.impl.DefaultLoginAdaptor;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.core.exception.IBaseException;
import com.hand.hap.security.service.impl.DefaultUserSecurityStrategy;
import com.hand.hap.system.dto.ResponseData;

/**
 * 用户登录控制层.
 *
 * @author wuyichu
 * @author njq.niu@hand-china.com
 */
@Controller
public class LoginController extends BaseController implements InitializingBean {

    @Autowired(required = false)
    private ILoginAdaptor loginAdaptor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/login.html", "/login"})
    @Timed
    public ModelAndView loginView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().loginView(request, response);
    }

    @GetMapping(value = {"/role.html", "/role"})
    public ModelAndView roleView(final HttpServletRequest request, final HttpServletResponse response)
            throws BaseException {
        return getLoginAdaptor().roleView(request, response);
    }


    @GetMapping(value = {"/", "/index.html"})
    public ModelAndView indexView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().indexView(request, response);
    }

    @PostMapping(value = "/role")
    public ModelAndView selectRole(final UserRole userRole, final HttpServletRequest request,
                                   final HttpServletResponse response) throws RoleException {
        return getLoginAdaptor().doSelectRole(userRole, request, response);
    }

    @RequestMapping(value = "/sys/role/change")
    public ModelAndView changeRole(HttpServletRequest request, HttpServletResponse response,Long companyId,Long roleId) throws RoleException {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setCompanyId(companyId);
        return getLoginAdaptor().doSelectRole(userRole, request, response);
    }

    @RequestMapping(value = "/sys/company/change")
    public ModelAndView changeCompany(HttpServletRequest request, HttpServletResponse response,Long companyId) throws RoleException {
        UserRole userRole = new UserRole();
        userRole.setCompanyId(companyId);
        HttpSession session = request.getSession(false);
        if (session != null) {
            userRole.setUserId((Long) session.getAttribute(IRequest.FIELD_USER_ID));
        }
        return getLoginAdaptor().doSelectRole(userRole, request, response);
    }

    @GetMapping(value = "/getRolesByCompanyId")
    public List<IRole> getRolesByCompanyId(final HttpServletRequest request, Long companyId){
        return getLoginAdaptor().getRolesByCompanyId(request,companyId);
    }

    @PostMapping(value = "/sessionExpiredLogin")
    public ResponseData sessionExpiredLogin(final User account, final HttpServletRequest request,
                                            final HttpServletResponse response) throws BaseException {
        return getLoginAdaptor().sessionExpiredLogin(account, request, response);
    }

    @GetMapping(value = "/password/reset")
    public ModelAndView resetPassword(final HttpServletRequest request) {
        // 首次登录 密码过期跳转修改密码页面
        ModelAndView view = new ModelAndView("update_password");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String reason = (String) session.getAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
            Locale locale = RequestContextUtils.getLocale(request);
            view.addObject("update_password_title", updatePasswordTitle(reason, locale));
        }
        return view;
    }

    @PostMapping(value = "/password/reset")
    public ModelAndView resetPassword(HttpServletRequest request, String newPwd, String newPwdAgain) throws UserException {
        // 首次登录修改密码提交，密码过期提交
        ModelAndView view = new ModelAndView("update_password");
        IRequest iRequest = createRequestContext(request);
        HttpSession session = request.getSession(false);
        String reason = (String) session.getAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
        try {
            if (reason != null) {
                request.getSession(false).removeAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
                userService.firstAndExpiredLoginUpdatePassword(iRequest, newPwd, newPwdAgain);
                return new ModelAndView(BaseConstants.VIEW_REDIRECT + "/");
            }
        } catch (Exception e) {
            if (e instanceof IBaseException) {
                IBaseException be = (IBaseException) e;
                Locale locale = RequestContextUtils.getLocale(request);
                String messageKey = be.getDescriptionKey();
                String message = getMessageSource().getMessage(messageKey, be.getParameters(), messageKey, locale);
                view.addObject("update_password_title", updatePasswordTitle(reason, locale));
                view.addObject("message", message);
            } else {
                throw e;
            }
        }
        return view;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (loginAdaptor == null) {
            loginAdaptor = new DefaultLoginAdaptor();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(loginAdaptor);
        }
    }

    @GetMapping(value = "/casLoginFailure")
    public ModelAndView casLoginError(HttpServletRequest request, HttpServletResponse response) throws UserException {
        return getLoginAdaptor().casLoginFailure(request, response);
    }

    private ILoginAdaptor getLoginAdaptor() {
        return loginAdaptor;
    }

    private String updatePasswordTitle(String reason, Locale locale) {
        String reasonTitle = "";
        switch (reason) {
            case DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON_EXPIRED:
                reasonTitle = getMessageSource().getMessage("error.user.update_password", null, locale);
                break;
            case DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON_RESET:
                reasonTitle = getMessageSource().getMessage("sys.config.resetpassword", null, locale);
                break;
            default:
                break;
        }
        return reasonTitle;
    }
}
