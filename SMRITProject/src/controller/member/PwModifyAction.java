package controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.MemberDAO;

public class PwModifyAction {
	public Integer execute(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("logId");
		String userPw=request.getParameter("userPw");
		MemberDAO dao=new MemberDAO();
		Integer i=dao.memberLoginCk(userId, userPw);
		return i;
				
	}

}
