package controller.ansBoard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnsBoardDetailAction {
	public void execute(HttpServletRequest request) {
		String num=request.getParameter("num");
		AnsBoardDAO dao=new AnsBoardDAO();
		dao.updateReadCount(num, "answerboard");
		List<AnsBoardDTO> list=dao.ansSelectAll(num,1,1);
		request.setAttribute("dto", list.get(0));
		
		// 같은 구문
//		AnsBoardDTO dto=dao.ansSelectAll(num, 1, 1).get(0);
//		request.setAttribute("dto", dto);
		
	}

}
