package com.ztzh.ui.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ztzh.ui.bo.LoginInfoForRedisBo;
import com.ztzh.ui.service.LoginInfoRecordService;
import com.ztzh.ui.utils.IpOperateUtil;

@Component
public class UiInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(UiInterceptor.class);
	@Autowired
	LoginInfoRecordService loginInfoRecordService;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o) throws Exception {
		String userId = httpServletRequest.getParameter("userId");
		logger.info(httpServletRequest.getRequestURL()+"?userId:" + userId);
		if (userId != null) {
			LoginInfoForRedisBo loginInfoForRedisBo = loginInfoRecordService.get(userId);
			if(loginInfoForRedisBo==null) {
				toLoginPage(httpServletRequest,httpServletResponse);
				return false;
			}
			String ipAddress = IpOperateUtil.getIpAddress(httpServletRequest);
			String realIpAddress = loginInfoForRedisBo.getIpAddress();
			if(!ipAddress.equals(realIpAddress)) {
				toLoginPage(httpServletRequest,httpServletResponse);
				return false;
			}
			//登录成功,重置redis里时间
			loginInfoForRedisBo.setLoginTime(new Date());
			loginInfoRecordService.set(userId, loginInfoForRedisBo);
			return true;
		} else {
			toLoginPage(httpServletRequest,httpServletResponse);
			return false;
		}

	}
	
	private void toLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
		httpServletResponse.sendRedirect(httpServletRequest
				.getContextPath() + "/userpage/toLogin");
	}
}
