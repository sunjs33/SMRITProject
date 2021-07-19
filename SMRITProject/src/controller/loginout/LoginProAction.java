package controller.loginout;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.MemberDAO;

public class LoginProAction {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		MemberDAO dao = new MemberDAO();
		Integer i = dao.memberLoginCk(userId, userPw);
		
		HttpSession session = request.getSession();
		
		if (i >= 1) {
			session.setAttribute("logId", userId);
			//자동로그인 쿠키 생성
			String autologin=request.getParameter("autologin");
			if(autologin!=null && autologin.equals("auto")) {
				Cookie cookie=new Cookie("autologin", userId);
				cookie.setPath("/");
				cookie.setMaxAge(60*60*24*30); // 쿠키 유효기간 초단위
				response.addCookie(cookie); // 쿠키를 클라이언트로
			}
			//
			
			//아이디 저장 체크했을때
			String idStore=request.getParameter("idStore");
			if(idStore!=null && idStore.equals("store")) {
				Cookie cookie=new Cookie("idStore", userId);
				cookie.setPath("/");
				cookie.setMaxAge(60*60*24*30); // 쿠키 유효기간 초단위
				response.addCookie(cookie); // 쿠키를 클라이언트로 보내기
			}
			//
			// 아이디저장 체크 해제 했을때	
			else {
				Cookie cookie=new Cookie("idStore", userId);
				cookie.setPath("/");
				cookie.setMaxAge(0); // 생존 시간이 0초여서 쿠키가 날라가면서 삭제됨
				response.addCookie(cookie); // 쿠키를 클라이언트로 보내기
			}
			try {
				response.sendRedirect("../main.main");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print("<script>");
				if(i==0) {
					out.print("alert('비밀번호가 틀렸습니다.');");
					
				}else if(i==-1) {
					out.print("alert('아이디가 틀렸습니다.');");
				}
				out.print("location.href='../main.main'");
				out.print("</script>");
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
