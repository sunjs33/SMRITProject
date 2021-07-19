package controller.ansBoard;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnsBoardReplyAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		AnsBoardDTO dto=new AnsBoardDTO();
		dto.setBoardSubject(request.getParameter("boardSubject"));
		dto.setBoardContent(request.getParameter("boardContent"));
		dto.setBoardName(request.getParameter("boardName"));
		dto.setBoardPass(request.getParameter("boardPass"));
		dto.setBoardReLev(Long.parseLong(request.getParameter("boardReLev")));
		dto.setBoardReRef(Long.parseLong(request.getParameter("boardReRef")));
		dto.setBoardReSeq(Long.parseLong(request.getParameter("boardReSeq")));
		dto.setIpAddr(request.getRemoteAddr());
		HttpSession session=request.getSession();
		String userId=(String) session.getAttribute("logId");
		dto.setUserId(userId);
		AnsBoardDAO dao=new AnsBoardDAO();
		dao.answerSeqUpdate(dto);
		dao.answerReplyInsert(dto);
		
				
		
	}

}
