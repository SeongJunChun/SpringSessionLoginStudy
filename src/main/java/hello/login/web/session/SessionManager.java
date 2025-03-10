package hello.login.web.session;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	public static final String SESSION_COOKIE_NAME = "mySessionId";
	private final Map<String,Object> sessionSore = new ConcurrentHashMap<>();

	public void createSession(Object value, HttpServletResponse response){

		String sessionId = UUID.randomUUID().toString();
		sessionSore.put(sessionId,value);
		Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(mySessionCookie);
	}

	public Object getSession(HttpServletRequest request){
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if(sessionCookie==null){
			return null;
		}
		return sessionSore.get(sessionCookie.getValue());
	}

	public void expire(HttpServletRequest request){
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if(sessionCookie!=null){
			sessionSore.remove(sessionCookie.getValue());
		}
	}
	public Cookie findCookie(HttpServletRequest request, String cookieName){
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			return null;
		}
		return Arrays.stream(cookies)
			.filter(cookie->cookie.getName().equals(cookieName))
			.findFirst()
			.orElse(null);
	}
}
