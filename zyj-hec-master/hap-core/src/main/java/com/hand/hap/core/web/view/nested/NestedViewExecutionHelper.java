package com.hand.hap.core.web.view.nested;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * 嵌套View的工具类
 * 完成了模板嵌套的功能：在conctroller A 执行完后将 Controller B/C 的 ModoelAndView 组合到 A中
 *
 * 要点：
 * <p>
 *      （1）自定义实现HttpServletRequest 以覆盖对应的请求路径，让SpringMVC 能够正确获取到 Controller B/C
 *       (2) ModelAndView mv = ha.handle(request, httpServletResponse, mappedHandler.getHandler());
 *       (3) 递归调用实现嵌套
 * </p>
 *
 * 用法：
 * <p>
 *  @RequestMapping("/index2")
 * 	public ModelAndView list2() {
 * 		List<User> userList = userService.queryUsers();
 *
 * 		List<String>  nestedViewList = Arrays.asList("/role/index","/role/index2","/role/index3");
 * 		ModelAndView modelAndView = NestedViewExecutionHelper.proceedNestedView(nestedViewList);
 * 		modelAndView.setViewName("user");
 * 		modelAndView.addObject("userList",userList);
 *
 * 		return modelAndView;
 * 	}
 * </p>
 *
 *
 * @author shira 2019/03/11 15:02
 */

@Component
public class NestedViewExecutionHelper {

	private static Logger logger = LoggerFactory.getLogger(NestedViewExecutionHelper.class);

	private static final String SPRING_MVC_SERVLET_NAME = "appServlet";
	private static final String APPLICATION_CONTEXT_KEY = FrameworkServlet.SERVLET_CONTEXT_PREFIX +SPRING_MVC_SERVLET_NAME;
	private static final String REQUEST_MAPPING_HANDLER_ADAPTER_BEAN_NAME = "org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter";
	private static final String REQUEST_MAPPING_HANDLER_MAPPING_BEAN_NAME = "org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping";

	private static final ThreadLocal<ModelAndView> LOCAL_MODEL_AND_VIEW = new ThreadLocal<>();


	private NestedViewExecutionHelper(){

	}


	/**
	 * 根据传入的嵌套view 对应的controller方法请求路径List，递归执行对应的controller方法，并返回ModelAndView的组合结果。
	 *
	 * @param viewPathList 嵌套view 对应的controller方法请求路径List
	 * @return ModelAndView的组合结果
	 */
	public static ModelAndView proceedNestedView(List<String> viewPathList){

		if(viewPathList == null ||  viewPathList.isEmpty()){
			return null;
		}


		LinkedList<String> nestedViewLinkedList =	new LinkedList<>();
		for(String view : viewPathList){
			nestedViewLinkedList.push(view);
		}

		ModelAndView mv = doProceedNestedView( nestedViewLinkedList);
		LOCAL_MODEL_AND_VIEW.remove();

		logger.info("the nested modelAndView :  {}",mv);
		return mv;
	}

	private static ModelAndView doProceedNestedView(LinkedList<String> nestedViewLinkedList){

		ModelAndView headModelAndView = LOCAL_MODEL_AND_VIEW.get();

		if(nestedViewLinkedList.isEmpty() ){
			return  headModelAndView;
		}

		String handUrl = nestedViewLinkedList.pop();

		if(!handUrl.startsWith("/")){
			handUrl="/"+handUrl;
		}

		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		ApplicationContext applicationContext = (ApplicationContext) httpServletRequest.getSession().getServletContext().getAttribute(APPLICATION_CONTEXT_KEY);

		AuroraHttpServletRequestWrapper auroraHttpServletRequestWrapper =  new AuroraHttpServletRequestWrapper(httpServletRequest);
		String requestURI = httpServletRequest.getContextPath() + handUrl;
		auroraHttpServletRequestWrapper.setRequestURI(requestURI);
		auroraHttpServletRequestWrapper.setServletPath(handUrl);


		RequestMappingHandlerAdapter ha =(RequestMappingHandlerAdapter) applicationContext.getBean(REQUEST_MAPPING_HANDLER_ADAPTER_BEAN_NAME);
		RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean(REQUEST_MAPPING_HANDLER_MAPPING_BEAN_NAME);

		try {

			HandlerExecutionChain mappedHandler = requestMappingHandlerMapping.getHandler(auroraHttpServletRequestWrapper);

			// Actually invoke the handler.
			ModelAndView mv = ha.handle(auroraHttpServletRequestWrapper, httpServletResponse, mappedHandler.getHandler());

			if( headModelAndView ==null ){
				LOCAL_MODEL_AND_VIEW.set(mv);
				headModelAndView = LOCAL_MODEL_AND_VIEW.get();
			}

			headModelAndView.getModelMap().putAll(mv.getModel());

			return doProceedNestedView(nestedViewLinkedList);

		} catch (Exception e) {
			logger.info("exception happened when :  {}", e);
		}


		return null;
	}

}
