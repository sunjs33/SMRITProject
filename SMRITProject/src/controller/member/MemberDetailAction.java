package controller.member;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.MemberDAO;
import model.DTO.MemberDTO;

public class MemberDetailAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("logId");
		MemberDAO dao=new MemberDAO();
		List<MemberDTO> list=dao.memSelectAll(1, 1,userId);
		request.setAttribute("memberDTO", list.get(0));
	}
}
