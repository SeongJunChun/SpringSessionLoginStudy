package hello.login.web.interseptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor  implements HandlerInterceptor {

	public static final String LOG_ID = "logId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String requestURI = request.getRequestURI();
		String uuid = UUID.randomUUID().toString();

		request.setAttribute(LOG_ID,uuid);

		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
		}

		log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info("postHandler [{}]",modelAndView);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		String requestURI = request.getRequestURI();
		Object uuid = request.getAttribute(LOG_ID);
		log.info("REQUEST [{}][{}][{}]",uuid,requestURI,handler);

		if(ex!=null){
			log.error("afterCompletion error!!",ex);
		}

	}
}
