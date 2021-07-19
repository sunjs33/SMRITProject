package controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.MemberDAO;

public class MemberController extends HttpServlet implements Servlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		if (command.equals("/mem/memberList.mem")) {
			MemberListAction action = new MemberListAction();
			action.execute(request);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/memberList.jsp");
			dispatcher.forward(request, response);

		} else if (command.equals("/mem/memberRegist.mem")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/memberForm.html");
			dispatcher.forward(request, response);

		} else if (command.equals("/mem/memberOk.mem")) {
			MemberOKAction action = new MemberOKAction();
			action.execute(request);
			HttpSession session = request.getSession();
			if (session.getAttribute("logId") != null) {
				response.sendRedirect("memberList.mem");
			} else {
				response.sendRedirect("../main.main");

			}

		} else if (command.equals("/mem/memberInfo.mem")) {
			MemberInfoAction action = new MemberInfoAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher(("/member/memberInfo.jsp"));
			dispatcher.forward(request, response);

		} else if (command.equals("/mem/memberInfoPw.mem")) {
			MemberInfoAction action = new MemberInfoAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher(("/member/memberInfoPro.jsp"));
			dispatcher.forward(request, response);

		// 회원정보
		} else if (command.equals("/mem/memberInfoModifyPro.mem")) {
			MemberInfoModifyAction action = new MemberInfoModifyAction();
			action.execute(request);
			if (request.getParameter("memChk") != null && request.getParameter("memChk").equals("true")) { // 사용자가 접근했을때
				response.sendRedirect("memberDetail.mem");
			} else {
				response.sendRedirect("memberInfo.mem?userId=" + request.getParameter("userId")); // 관리자가 접근했을때
			}

		} else if (command.equals("/mem/memberDel.mem")) {
			MemberDelAction action = new MemberDelAction();
			action.execute(request);
			response.sendRedirect("memberList.mem");

		} else if (command.equals("/mem/memberAgree.mem")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/agree.html");
			dispatcher.forward(request, response);

		} else if (command.equals("/mem/userConfirm.mem")) {
			// 아이디 중복체크
			MemberConfirmAction action = new MemberConfirmAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/userConfirm.jsp");
			dispatcher.forward(request, response);

		} else if (command.equals("/mem/memberJoinOk.mem")) {
			MemberJoinOkAction action = new MemberJoinOkAction();
			String path = action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);

			// 내정보 확인
		} else if (command.equals("/mem/memberDetail.mem")) {
			MemberDetailAction action = new MemberDetailAction();
			action.execute(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/memberDetail.jsp");
			dispatcher.forward(request, response);
		}
		// 내정보 수정 비밀번호 확인
		else if (command.equals("/mem/memberPw.mem")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/memberInfoPw.jsp");
			dispatcher.forward(request, response);

		}
		// 내정보 수정
		else if (command.equals("/mem/memberInfoCng.mem")) {
			MemberInfoCngAction action = new MemberInfoCngAction();
			Integer i = action.execute(request);
			if (i == 1) {
				MemberDetailAction action1 = new MemberDetailAction();
				action1.execute(request);
				request.setAttribute("memChk", "true");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/member/memberInfoPro.jsp");
				dispatcher.forward(request, response);

			} else if (i == 0) {
				response.sendRedirect("memberPw.mem");
			}

		}
		// 사용자 비밀번호 수정
		else if(command.equals("/mem/memberPwForm.mem")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/member/pwModify.jsp");
			dispatcher.forward(request, response);
		}
		// 사용자 비밀번호 수정	
		else if(command.equals("/mem/pwModify1.mem")) {
			PwModifyAction action=new PwModifyAction();
			Integer i=action.execute(request);
			if(i==1) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("/member/pwModify_1.jsp");
				dispatcher.forward(request, response);
			}else if(i==0) {
				response.sendRedirect("memberPwForm.mem");
			}
		}
		// 사용자 비밀번호 수정
		else if(command.equals("/mem/pwModifyPro.mem")) {
			PwModifyProAction action=new PwModifyProAction();
			Integer i=action.execute(request);
			if(i==1) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("/member/pwModifyOk.jsp");
				dispatcher.forward(request, response);
			}else if(i==0) {
				response.sendRedirect("memberPwForm.mem");
			}
			
			// 사용자 탈퇴
		}else if(command.equals("/mem/memberUserDel.mem")) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/member/userDeletePw.html");
			dispatcher.forward(request, response);
			
			
		}else if(command.equals("/mem/memberUserDelPro.mem")) {
			MemberUserDelAction action=new MemberUserDelAction();
			action.execute(request);
			response.sendRedirect("../login/logOutPro.main");
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
