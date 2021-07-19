package controller.loginout;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.CookieAction;

public class LoginOutController extends HttpServlet implements Servlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		if (command.equals("/main.main")) {
			CookieAction action=new CookieAction();
			action.execute(request);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/main/main.jsp");
			dispatcher.forward(request, response);

		}else if(command.equals("/login/loginPro.main")) {
			LoginProAction action=new LoginProAction();
			action.execute(request,response);
		}else if(command.equals("/login/logOutPro.main")) {
			// 자동 로그인 쿠키 삭제 
			Cookie cookie=new Cookie("autologin","");
			cookie.setPath("/");
			cookie.setMaxAge(0); 
			response.addCookie(cookie); // 
			
			HttpSession session=request.getSession();
			session.invalidate();
			response.sendRedirect("../main.main");
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
