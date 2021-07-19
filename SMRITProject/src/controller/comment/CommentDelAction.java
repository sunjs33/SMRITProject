package controller.comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.CommentDAO;
import model.DTO.CommentDTO;

public class CommentDelAction {
	public void execute(HttpServletRequest request) {
		String commentNo=request.getParameter("commentNo");
		HttpSession session=request.getSession();
		String cuserId=(String) session.getAttribute("logId");
		
		CommentDAO dao= new CommentDAO();
		dao.commentDelete(commentNo, cuserId);
		
		
	}

}
