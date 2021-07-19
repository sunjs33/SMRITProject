package controller.goods;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.DAO.GoodsDAO;
import model.DTO.GoodsDTO;

public class GoodsModifyAction {
	public String execute(HttpServletRequest request) {
		String realPath=request.getServletContext().getRealPath("/goodsView/upload");
		int limitSize=1024*1024*10;
		GoodsDTO dto=new GoodsDTO();
		
		HttpSession session=request.getSession();
		String userId=(String) session.getAttribute("logId");
		GoodsDAO dao=new GoodsDAO();
		MultipartRequest multi=null;
		try {
			multi=new MultipartRequest(request, realPath, limitSize, "utf-8", new DefaultFileRenamePolicy());
			dto.setGoodsContent(multi.getParameter("goodsContent"));
			dto.setGoodsPrice(Long.parseLong(multi.getParameter("goodsPrice")));
			dto.setGoodsNum(multi.getParameter("goodsNum"));
			dto.setUserId(userId);
			dao.goodsContentUpdate(dto);
			if (multi.getFile("goodsImage") != null) {
				dto.setGoodsImage(multi.getFilesystemName("goodsImage"));
				int i=dao.fileUpdate(dto);
				File file = null;
				if(i>=1) {
					String fl=multi.getParameter("fileDel");
					file=new File(realPath+"/"+fl);
					if(file.exists()) {
						file.delete();
						
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return multi.getParameter("goodsNum");
		
	}

}
