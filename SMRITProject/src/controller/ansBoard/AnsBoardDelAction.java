 package controller.ansBoard;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnsBoardDelAction {
	public void execute(HttpServletRequest request) {
		String boardNum=request.getParameter("num");
		String boardPass=request.getParameter("boardPass");
		HttpSession session=request.getSession();
		String userId=(String) session.getAttribute("logId");
		
		AnsBoardDAO dao=new AnsBoardDAO();
		List<AnsBoardDTO> list=dao.ansSelectAll(boardNum, 1, 1);
		int i=dao.ansBoardDelete(boardNum,userId,boardPass);
		if(i>=1) {
			String path=request.getServletContext().getRealPath("/answerBoard/upload");
			File file=new File(path+"/"+list.get(0).getStoreFileName());
			if(file.exists()) {
				file.delete();
			}
			
		}
				
		
	}

	
}
