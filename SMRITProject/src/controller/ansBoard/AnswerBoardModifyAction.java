package controller.ansBoard;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnswerBoardModifyAction {
	public String execute(HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath("/answerBoard/upload");
		int limitSize = 1024 * 1024 * 5;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("logId");
		AnsBoardDTO dto = new AnsBoardDTO();
		AnsBoardDAO dao = new AnsBoardDAO();
		MultipartRequest multi = null;

		try {
			multi = new MultipartRequest(request, realPath, limitSize, "utf-8", new DefaultFileRenamePolicy());
			dto.setBoardContent(multi.getParameter("boardContent"));
			dto.setBoardNum(Long.parseLong(multi.getParameter("num")));
			dto.setBoardPass(multi.getParameter("boardPass"));
			dto.setBoardSubject(multi.getParameter("boardSubject"));
			dto.setUserId(userId);
			dao.ansContentUpdate(dto);
			// 내용 업데이트 완료

			if (multi.getFile("fileUp") == null) { // 변경할 파일이 없을때
				if (!multi.getParameter("fileDel").trim().equals("")) { // 삭제할 파일이 있으면
					dto.setOriginalFileName("");
					dto.setStoreFileName("");
					dto.setFileSize(0L);
					int i = dao.fileUpdate(dto);
					if (i >= 1) {
						String fl = multi.getParameter("fileDel");
						File file = new File(realPath + "/" + fl);
						if (file.exists()) {
							file.delete();
						}
					}

				}
			} else { // 변경할 파일이 있을때
				dto.setOriginalFileName(multi.getOriginalFileName("fileUp"));
				dto.setStoreFileName(multi.getFilesystemName("fileUp"));
				File file = multi.getFile("fileUp");
				dto.setFileSize(file.length());
				int i = dao.fileUpdate(dto);
				if (i >= 1) {
					String fl = multi.getParameter("fileDel");
					file = new File(realPath + "/" + fl);
					if (file.exists()) {
						file.delete();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return multi.getParameter("num");

	}

}
