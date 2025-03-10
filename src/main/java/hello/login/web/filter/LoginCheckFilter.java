package hello.login.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.PatternMatchUtils;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {

	private static final String[] whiteList = {"/","/member/add","/login","/logout","/css/*"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws
		IOException,
		ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String requestURI= httpRequest.getRequestURI();

		HttpServletResponse httpResponse = (HttpServletResponse)response;

		try{
			log.info("인증 체크 필터 시작{}",requestURI);
			if(isLoginCheckPath(requestURI)){
				log.info("인증 체크 로직 실행{}",requestURI);
				HttpSession session = httpRequest.getSession();
				if(session==null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
					log.info("미인증 사용자 요청{}",requestURI);

					httpResponse.sendRedirect("/login?redirectURL="+requestURI);
					return;
				}
			}
			filter.doFilter(request,response);
		}catch (Exception e){
			throw e;
		} finally {
			log.info("인증 체크 필터 종료{}",requestURI);
		}

	}
	private boolean isLoginCheckPath(String requestURI){
		return !PatternMatchUtils.simpleMatch(whiteList,requestURI);
	}

}
