package controller.comment;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.CommentDAO;
import model.DTO.CommentDTO;

public class CommentModifyAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommentDTO dto=new CommentDTO();
		dto.setCommentContent(request.getParameter("commentContent"));
		dto.setCommentNo(Long.parseLong(request.getParameter("commentNo")));
		dto.setCommentSubject(request.getParameter("commentSubject"));
		HttpSession session=request.getSession();
		dto.setCuserId((String)session.getAttribute("logId"));
		CommentDAO dao=new CommentDAO();
		dao.commentUpdate(dto);
		
		
		
		
	}

}
