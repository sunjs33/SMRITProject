package controller.goods;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import model.DAO.GoodsDAO;
import model.DTO.GoodsDTO;

public class GoodsDelAction {
	public void execute(HttpServletRequest request) {
		String goodsNum=request.getParameter("goodsNum");
		String goodsImage=request.getParameter("image");
		GoodsDAO dao=new GoodsDAO();
		int i =dao.goodsDel(goodsNum);
		
		File file =null;
		if(i>=1) {
			String path="/goodsView/upload";
			String realPath=request.getServletContext().getRealPath(path);
			file=new File(realPath+"/"+goodsImage);
			if(file.exists()) {
				file.delete();
			}
		}
		
		
		
		
	}

}
