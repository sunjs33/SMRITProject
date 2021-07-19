package model.DAO;

import java.util.ArrayList;
import java.util.List;

import model.DTO.CartDTO;
import model.DTO.GoodsDTO;

public class GoodsDAO extends DataBaseInfo {
	final String COLUMNS="GOODS_NUM, USER_ID, GOODS_NAME, "
			+ "GOODS_CONTENT, GOODS_IMAGE, read_count, "
			+ "IP_ADDR, GOODS_PRICE, GOODS_REGISTER";
	final String CART_COLUMNS="CART_NUM, GOODS_NUM, USER_ID, "
			+ "GOODS_NAME, GOODS_PRICE, GOODS_IMAGE, "
			+ "QTY, TOTAL_PRICE";
	
	public void cartRemove(String [] cartNums) {
		conn=getConnection();
		sql="delete from cart where cart_num=?";
		try {
			int j=0;
			pstmt=conn.prepareStatement(sql);
			for (String cartNum : cartNums) {
				pstmt.setString(1, cartNum);
				int i=pstmt.executeUpdate();
				if(i>0) {
					j++;
				}
				System.out.println(sql);
				
			}
			System.out.println(j+"개가 삭제되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public void cartQtyDown(String goodsNum, String userId) {
		conn=getConnection();
		sql="update cart set qty=qty-1, "
				+ "			total_price=(qty-1)*goods_price "
				+ "where goods_Num=? and user_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, goodsNum);
			pstmt.setString(2, userId);
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public List<CartDTO> cartAllSelect(String userId) {
		List<CartDTO> list=new ArrayList<CartDTO>();
		conn=getConnection();
		sql="select "+CART_COLUMNS+", "
				+ "sum(total_price) over(partition by user_id) as sum_total_price "
				+ "from cart where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				CartDTO dto=new CartDTO();
				dto.setCartNum(rs.getInt("cart_NUM"));
				dto.setGoodsNum(rs.getString("GOODS_NUM"));
				dto.setUserId(rs.getString("USER_ID"));
				dto.setGoodsName(rs.getString("GOODS_NAME"));
				dto.setGoodsPrice(rs.getInt("GOODS_PRICE"));
				dto.setGoodsImage(rs.getString("GOODS_IMAGE"));
				dto.setQty(rs.getInt("qty"));
				dto.setTotalPrice(rs.getInt("TOTAL_PRICE"));
				dto.setSumTotalPrice(rs.getInt("SUM_TOTAL_PRICE"));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
		
	}
	
	public void goodsCartAdd(GoodsDTO dto, String userId) {
		conn=getConnection();
		sql="merge into cart c "
				+ "using (select goods_num from goods where goods_num = ? ) g "
				+ "on (c.goods_num=g.goods_num and c.user_id = ?) "
				+ " when MATCHED then update set QTY=QTY+1, total_price=(qty+1)*goods_price "
				+ " when not matched then insert (c.cart_num, c.goods_num, c.user_id, c.goods_name, c.goods_price, c.goods_image, c.qty, c.total_price) "
				+ " values (num_seq.nextval,?,?,?,?,?,1,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGoodsNum());
			pstmt.setString(2, userId);
			pstmt.setString(3, dto.getGoodsNum());
			pstmt.setString(4, userId);
			pstmt.setString(5, dto.getGoodsName());
			pstmt.setLong(6, dto.getGoodsPrice());
			pstmt.setString(7, dto.getGoodsImage());
			pstmt.setLong(8, dto.getGoodsPrice());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 변경되었습니다.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public Integer fileUpdate(GoodsDTO dto) {
		Integer i=0;
		conn=getConnection();
		sql="update goods set goods_image=? "
				+ "where user_id=? and goods_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGoodsImage());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getGoodsNum());
			i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
		
	}
	
	public void goodsContentUpdate(GoodsDTO dto) {
		conn=getConnection();
		sql="update goods set goods_content=?, goods_price=? "
				+ "where user_id=? and goods_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGoodsContent());
			pstmt.setLong(2, dto.getGoodsPrice());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getGoodsNum());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	
	public Integer goodsDel(String goodsNum) {
		Integer i=0;
		conn=getConnection();
		sql="delete from goods where goods_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, goodsNum);
			i=pstmt.executeUpdate();
			System.out.println(sql);
			System.out.println(i+"개가 삭제 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
		
	}
	
	public void updateReadCount(String num) {
		conn=getConnection();
		sql="update goods set read_count=read_count+1 where goods_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, num);
			int i = pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public int goodsCount() {
		int i=0;
		conn=getConnection();
		sql="select count(*) from Goods";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				i=rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
		
	}
	
	public List<GoodsDTO> goodsSelect(int page, int limit, String num) {
		int startRow = (page - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		
		String condition="";
		if(num!=null) {
			condition="and goods_num='" + num + "'";
		}
		List<GoodsDTO> list= new ArrayList<GoodsDTO>();
		conn=getConnection();
		sql = "select * "
				+ "from(select rownum rn, "+COLUMNS
				+"		from(select "+ COLUMNS +" from goods "
						+ "	where 1=1 "+condition+" order by goods_num desc)) "
				+ "where rn between ? and ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				GoodsDTO dto =new GoodsDTO();
				dto.setGoodsContent(rs.getString("GOODS_CONTENT"));
				dto.setGoodsImage(rs.getString("GOODS_IMAGE"));
				dto.setGoodsName(rs.getString("GOODS_NAME"));
				dto.setGoodsNum(rs.getString("GOODS_NUM"));
				dto.setGoodsPrice(rs.getLong("GOODS_PRICE"));
				dto.setGoodsRegister(rs.getTimestamp("GOODS_REGISTER"));
				dto.setGoodsVisit(rs.getLong("read_count"));
				dto.setIpAddr(rs.getString("IP_addr"));
				dto.setUserId(rs.getString("user_id"));
				list.add(dto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
	}
	
	public void goodsInsert(GoodsDTO dto) {
		conn=getConnection();
		sql="insert into goods("+COLUMNS+") values(?,?,?,?,?,0,?,?,sysdate)";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGoodsNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getGoodsName());
			pstmt.setString(4, dto.getGoodsContent());
			pstmt.setString(5, dto.getGoodsImage());
			pstmt.setString(6, dto.getIpAddr());
			pstmt.setLong(7, dto.getGoodsPrice());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 저장되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}

}
