package com.ztzh.ui.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UiInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(UiInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o) throws Exception {
		String userId = httpServletRequest.getParameter("userId");
		logger.info(httpServletRequest.getRequestURL()+"?userId:" + userId);
		if (userId != null) {
			return true;
		} else {
			httpServletResponse.sendRedirect(httpServletRequest
					.getContextPath() + "/userpage/toLogin");
			return false;
		}

	}

}
