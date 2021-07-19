package controller.member;

import javax.servlet.http.HttpServletRequest;

import model.DAO.MemberDAO;

public class MemberJoinOkAction {
	public String execute(HttpServletRequest request) {
		String path=null;
		String email=request.getParameter("email");
		String joinOk=request.getParameter("joinOk");
		MemberDAO dao=new MemberDAO();
		Integer i =dao.joinOkUpdate(email,joinOk);
		if(i==1) {
			path="/member/success.jsp";
		}else {
			path="/member/fail.jsp";
			System.out.println(i);
		}
		
		return path;
		
	}

}
