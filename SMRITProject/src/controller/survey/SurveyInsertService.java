package controller.survey;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.QuestionDAO;
import model.DTO.OptionDTO;
import model.DTO.QuestionDTO;

public class SurveyInsertService {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String question=request.getParameter("question");
		String option=request.getParameter("options");
		String [] options=option.split(",");
		HttpSession session=request.getSession();
		QuestionDTO qdto= new QuestionDTO();
		String userId=(String)session.getAttribute("logId");
		qdto.setUserId(userId);
		qdto.setQuestionTitle(question);
		
		QuestionDAO dao=new QuestionDAO();
		int questionNum=dao.questionInsert(qdto);
		
		int optionsNum=1;
		if(option!=null && !option.trim().equals("")) {
			for (String optionsName : options) {
				OptionDTO odto=new OptionDTO();
				odto.setQuestionNum(questionNum);
				odto.setOptionsNum(optionsNum++);
				odto.setOptionsName(optionsName);
				odto.setUserId(userId);
				dao.optionInsert(odto);
				
				
			}
			
		}
		
		
	}

}
