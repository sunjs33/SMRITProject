package controller.qnaBoard;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.QnaBoardDAO;
import model.DTO.QnaBoardDTO;

public class QnaWriteProAction {

	public void execute(HttpServletRequest request) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String boardName = request.getParameter("boardName");
		String boardPass = request.getParameter("boardPass");
		String boardSubject = request.getParameter("boardSubject");
		String boardContent = request.getParameter("boardContent");
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("logId");
		String ipAddr = request.getRemoteAddr();

		QnaBoardDTO dto = new QnaBoardDTO();
		dto.setUserId(userId);
		dto.setBoardContent(boardContent);
		dto.setBoardName(boardName);
		dto.setBoardPass(boardPass);
		dto.setBoardSubject(boardSubject);
		dto.setIpAddr(ipAddr);
		
		
		QnaBoardDAO dao =new QnaBoardDAO();
		dao.qnaInsert(dto);

	}

}
