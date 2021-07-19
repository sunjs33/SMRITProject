package controller.comment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentController extends HttpServlet implements Servlet{
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());
		
		if(command.equals("/cb/commentList.cb")) {
			CommentListAction action=new CommentListAction();
			action.execute(request);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/comment/comment_list.jsp");
			dispatcher.forward(request, response);
		}else if(command.equals("/cb/commentForm.cb")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/comment/commentForm.html");
			dispatcher.forward(request, response);
		}else if(command.equals("/cb/commentWrite.cb")) {
			CommentWriteAction action=new CommentWriteAction();
			action.execute(request);
			response.sendRedirect("commentList.cb");
		}else if(command.equals("/cb/commentDetail.cb")) {
			CommentDetailAction action=new CommentDetailAction();
			action.execute(request);
			String path="/comment/commentCollection.jsp";
			RequestDispatcher dispatcher=request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else if(command.equals("/cb/commentModify.cb")) {
			CommentDetailAction action=new CommentDetailAction();
			action.execute(request);
			String path="/comment/commentModify.jsp";
			RequestDispatcher dispatcher=request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else if(command.equals("/cb/commentModifyPro.cb")) {
			CommentModifyAction action=new CommentModifyAction();
			action.execute(request);
			response.sendRedirect("commentDetail.cb?commentNo="+request.getParameter("commentNo"));
		}else if(command.equals("/cb/commentDelete.cb")) {
			CommentDelAction action=new CommentDelAction();
			action.execute(request);
			response.sendRedirect("commentList.cb");
		}else if(command.equals("/cb/replyInsert.cb")) {
			ReplyInsertAction action=new ReplyInsertAction();
			action.execute(request);
			response.sendRedirect("commentDetail.cb?commentNo="+request.getParameter("commentNo"));
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request,response);
	}
	

}
