package controller.ansBoard;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ansBoardController extends HttpServlet implements Servlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		if (command.equals("/ans/ansList.ans")) {
			AnsBoardListAction action = new AnsBoardListAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/answerBoard/ans_board_list.jsp");
			dispatcher.forward(request, response);
		} else if (command.equals("/ans/ansBoardWrite.ans")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/answerBoard/ans_board_write.jsp");
			dispatcher.forward(request, response);

		} else if (command.equals("/ans/ansBoardWritePro.ans")) {
			AnsBoardWriteAction action = new AnsBoardWriteAction();
			action.execute(request);
			response.sendRedirect("ansList.ans");
		} else if (command.equals("/ans/ansBoardDetail.ans")) {
			String path = "/answerBoard/ans_board_view.jsp";
			AnsBoardDetailAction action = new AnsBoardDetailAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

		}else if(command.equals("/ans/ansBoardDelete.ans")) {
			request.setAttribute("num", request.getParameter("num"));//익스프레션 랭귀지를 사용하기 위해서
			RequestDispatcher dispatcher=request.getRequestDispatcher("/answerBoard/ans_board_delete.jsp");
			dispatcher.forward(request, response);
			
		}else if(command.equals("/ans/answerBoardDelPro.ans")) {
			AnsBoardDelAction action=new AnsBoardDelAction();
			action.execute(request);
			response.sendRedirect("ansList.ans");
			
		}else if(command.equals("/ans/answerBoardModify.ans")) {
			AnsBoardDetailAction action = new AnsBoardDetailAction();
			action.execute(request);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/answerBoard/ans_board_modify.jsp");
			dispatcher.forward(request, response);
			
		}else if(command.equals("/ans/answerBoardModifyPro.ans")) {
			AnswerBoardModifyAction action =new AnswerBoardModifyAction();
			String num=action.execute(request);
			response.sendRedirect("ansBoardDetail.ans?num="+num);
		}else if(command.equals("/ans/answerBoardReply.ans")) {
			AnsBoardDetailAction action = new AnsBoardDetailAction();
			action.execute(request);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/answerBoard/ans_board_reply.jsp");
			dispatcher.forward(request, response);
			
		}else if(command.equals("/ans/answerBoardReplyPro.ans")) {
			AnsBoardReplyAction action =new AnsBoardReplyAction();
			action.execute(request);
			response.sendRedirect("ansList.ans");
			
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}
