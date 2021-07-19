package controller.comment;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.CommentDAO;
import model.DTO.CommentDTO;

public class CommentWriteAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommentDTO dto =new CommentDTO();
		dto.setCommentContent(request.getParameter("commentContent"));
		dto.setCommentSubject(request.getParameter("commentSubject"));
		HttpSession session=request.getSession();
		String cuserId=(String) session.getAttribute("logId");
		dto.setCuserId(cuserId);
		CommentDAO dao=new CommentDAO();
		dao.commentInsert(dto);
		
		
	}

}
