package controller.ansBoard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import controller.PageAction;
import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnsBoardListAction {
	public void execute(HttpServletRequest request) {
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int limit = 10;
		int limitPage = 10;
		
		
		String num=null;
		AnsBoardDAO dao=new AnsBoardDAO();
		List<AnsBoardDTO> list=dao.ansSelectAll(num, page, limit);
		Integer count=dao.ansCount();
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		PageAction pageAction = new PageAction();
		pageAction.page(request, count, limit, limitPage, page, "ansList.ans?");
	}

}
