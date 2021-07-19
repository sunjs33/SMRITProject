package controller.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controller.PageAction;
import model.DAO.MemberDAO;
import model.DTO.MemberDTO;

public class MemberListAction {
	public void execute(HttpServletRequest request) {
		// 페이징
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int limit = 10;
		int limitPage = 10;
		//

		String userId = null;
		MemberDAO dao = new MemberDAO();
		List<MemberDTO> list = dao.memSelectAll(page, limit, userId);
		Integer count = dao.memCount();
		request.setAttribute("list", list);
		request.setAttribute("count", count);

		PageAction pageAction = new PageAction();
		pageAction.page(request, count, limit, limitPage, page, "memberList.mem?");
		
	}

}
