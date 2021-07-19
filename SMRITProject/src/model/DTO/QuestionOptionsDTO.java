package model.DTO;

import java.util.List;

public class QuestionOptionsDTO {
	QuestionDTO question;
	List<OptionDTO> options;
	
	public QuestionDTO getQuestion() {
		return question;
	}
	public void setQuestion(QuestionDTO question) {
		this.question = question;
	}
	public List<OptionDTO> getOptions() {
		return options;
	}
	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	
	

}
