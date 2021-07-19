package controller.qnaBoard;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import model.DAO.QnaBoardDAO;
import model.DTO.QnaBoardDTO;

public class QnaBoardModifyAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userId="sunjs";
		String boardNum=request.getParameter("boardNum");
		String boardSubject=request.getParameter("boardSubject");
		String boardContent=request.getParameter("boardContent");
		String boardPass=request.getParameter("boardPass");
		
		QnaBoardDTO dto =new QnaBoardDTO();
		dto.setBoardContent(boardContent);
		dto.setBoardNum(Long.parseLong(boardNum));
		dto.setBoardPass(boardPass);
		dto.setBoardSubject(boardSubject);
		dto.setUserId(userId);
		
		QnaBoardDAO dao =new QnaBoardDAO();
		dao.qnaUpdate(dto);
	}

}
