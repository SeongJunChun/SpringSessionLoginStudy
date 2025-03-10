package hello.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String requestURI = request.getRequestURI();

		log.info("인증 체크 인터셉터 실행 {}",requestURI);

		HttpSession session = request.getSession();

		if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
			log.info("미인증 사용자 요철");
			response.sendRedirect("/login?redirectURL="+requestURI);
			return false;
		}
		return true;
	}
}
