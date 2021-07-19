package controller.ansBoard;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.DAO.AnsBoardDAO;
import model.DTO.AnsBoardDTO;

public class AnsBoardWriteAction {
	public void execute(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String filePath = "/answerBoard/upload";
		String realPath = request.getServletContext().getRealPath(filePath);
		int fileSize = 1024 * 1024 * 5;
		String ipAddr = request.getRemoteAddr();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("logId");

		AnsBoardDTO dto = new AnsBoardDTO();
		try {
			MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "utf-8",
					new DefaultFileRenamePolicy());
			dto.setBoardName(multi.getParameter("boardName"));
			dto.setBoardSubject(multi.getParameter("boardSubject"));
			dto.setBoardContent(multi.getParameter("boardContent"));
			dto.setBoardPass(multi.getParameter("boardPass"));
			dto.setUserId(userId);
			dto.setIpAddr(ipAddr);
			dto.setOriginalFileName(multi.getOriginalFileName("fileUp"));
			dto.setStoreFileName(multi.getFilesystemName("fileUp"));
			File file = multi.getFile("fileUp");
			dto.setFileSize(file.length());

		} catch (Exception e) {
			e.printStackTrace();
		}
		AnsBoardDAO dao = new AnsBoardDAO();
		dao.ansInsert(dto);

	}

}
