package controller;

import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CookieAction {
	public void execute(HttpServletRequest request) {
		Cookie [] cookies=request.getCookies();	//쿠키를 여러개 받을수 있게 하기위해서 배열로
		HttpSession session=request.getSession();// 로그인상태 유지하기 위한
		if(cookies!=null && cookies.length>0) {
			for(Cookie c:cookies) {
				if(c.getName().startsWith("id")){ //id로 시작하는 쿠키가 있는지
					request.setAttribute("isId", c.getValue()); // c가 가지고 있는 쿠키를 request로 보냄
					
				}
				if(c.getName().equals("autologin")) {
					session.setAttribute("logId", c.getValue());
				}
				
			}
		}

	}
}
