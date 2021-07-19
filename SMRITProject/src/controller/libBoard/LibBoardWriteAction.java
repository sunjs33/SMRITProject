package controller.libBoard;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.DAO.LibBoardDAO;
import model.DTO.LibBoardDTO;

public class LibBoardWriteAction {
	public void execute(HttpServletRequest request) {

		String filePath = "/lib_Board/upload";
		String realPath = request.getServletContext().getRealPath(filePath);
		int fileSize = 1024 * 1024 * 5;
		LibBoardDTO dto = new LibBoardDTO();
		HttpSession session=request.getSession();
		String userId=(String)session.getAttribute("logId");
		String ipAddr = request.getRemoteAddr();

		try {
			MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "utf-8",
					new DefaultFileRenamePolicy());
			dto.setBoardContent(multi.getParameter("boardContent"));
			dto.setBoardSubject(multi.getParameter("boardSubject"));
			dto.setBoardPass(multi.getParameter("boardPass"));
			dto.setBoardName(multi.getParameter("boardName"));
			dto.setUserId(userId);
			dto.setIpAddr(ipAddr);
			dto.setOriginalFileName(multi.getOriginalFileName("fileUp"));
			dto.setStoreFileName(multi.getFilesystemName("fileUp"));
			File file = multi.getFile("fileUp");
			dto.setFileSize(file.length());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LibBoardDAO dao = new LibBoardDAO();
		dao.libInsert(dto);

	}
}
