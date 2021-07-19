package controller.qnaBoard;

import javax.servlet.http.HttpServletRequest;

import model.DAO.QnaBoardDAO;
import model.DTO.QnaBoardDTO;

public class QnaBoardDeleteAction {
	public void execute(HttpServletRequest request) {
		String boardNum =request.getParameter("boardNum");
		String userId="sunjs";
		String boardPass=request.getParameter("boardPass");
		
		QnaBoardDTO dto =new QnaBoardDTO();
		dto.setBoardNum(Long.parseLong(boardNum));
		dto.setBoardPass(boardPass);
		dto.setUserId(userId);
		
		QnaBoardDAO dao =new QnaBoardDAO();
		dao.qnaDelete(dto);
				
		
				
	}

}
