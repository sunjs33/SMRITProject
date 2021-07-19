package model.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DTO.CommentDTO;
import model.DTO.CommentRepliesDTO;
import model.DTO.ReplyDTO;

public class CommentDAO extends DataBaseInfo{
	final String COLUMNS="COMMENT_NO, CUSER_ID, REG_DATE,"
			+ " COMMENT_SUBJECT, COMMENT_CONTENT";
	final String SUBQUERY="select nvl(max(COMMENT_NO),0)+1 from comment1";
	final String REPLYCOLUMNS="REPLY_NO, RUSER_ID, COMMENT_NO, CUSER_ID, REG_DATE, REPLY_CONTENT ";
	final String REPLYSUBQUERY="select nvl(max(reply_NO),0)+1 from reply";
	
	public void replyInsert(ReplyDTO dto ){
		conn=getConnection();
		sql="insert into reply ("+REPLYCOLUMNS+") values(("+REPLYSUBQUERY+"),?,?,?,sysdate,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRuserId());
			pstmt.setLong(2, dto.getCommentNo());
			pstmt.setString(3, dto.getCuserId());
			pstmt.setString(4, dto.getReplyContent());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 저장되었습니다.");
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public void commentDelete(String commentNo, String cuserId) {
		conn=getConnection();
		sql="delete from comment1 where comment_no=? and cuser_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, commentNo);
			pstmt.setString(2, cuserId);
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public void commentUpdate(CommentDTO dto) {
		conn=getConnection();
		sql="update comment1 set comment_subject=?, comment_content=? where comment_no=? and cuser_id=?" ;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCommentSubject());
			pstmt.setString(2, dto.getCommentContent());
			pstmt.setLong(3, dto.getCommentNo());
			pstmt.setString(4, dto.getCuserId());
			int i = pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public CommentRepliesDTO commentCollection(String commentNo) {
		// 1인 comment 저장하기 위한 객체
		CommentDTO dto= new CommentDTO();
		// reply를 n개 저장하기 위한 list
		List<ReplyDTO> list=new ArrayList<ReplyDTO>();
		// comment와 reply 인 list를 저장하기 위한 객체
		CommentRepliesDTO crdto=new CommentRepliesDTO();
		conn=getConnection();
		
		
		sql="select c.comment_no cno, c.cuser_id cid, c.comment_subject csubject, c.comment_content ccontent," + 
				"c.reg_date cdate,  r.reply_no rno, r.ruser_id rid, r.reg_date rdate, r.reply_content rcontent " + 
				"from comment1 c left outer join reply r " + 
				"on c.comment_no= r.comment_no " + 
				"where c.comment_no = ?";
		
		try {
			pstmt=conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			System.out.println(sql);
			pstmt.setString(1, commentNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
			dto.setCommentContent(rs.getString("ccontent"));
			dto.setCommentNo(rs.getLong("cno"));
			dto.setCommentSubject(rs.getString("csubject"));
			dto.setCuserId(rs.getString("cid"));
			dto.setRegDate(rs.getTimestamp("cdate"));
			}
			rs.beforeFirst();
			while(rs.next()) {
				ReplyDTO redto =new ReplyDTO();
				redto.setRegDate(rs.getTimestamp("rdate"));
				redto.setReplyContent(rs.getString("rcontent"));
				redto.setReplyNo(rs.getLong("rno"));
				redto.setRuserId(rs.getString("rid"));
				list.add(redto);
				crdto.setCommentDTO(dto);
				crdto.setReplies(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return crdto;
		
	}
	
	public int commentCount() {
		int i=0;
		conn=getConnection();
		sql="select count(*) from comment1";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			i=rs.getInt(1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
	}
	
	public List<CommentDTO> commentSelectAll(int page, int limit) {
		int startRow = (page - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		
		List<CommentDTO> list=new ArrayList<CommentDTO>();
		conn=getConnection();
		sql="select * "
				+ "from(select rownum rn, "+COLUMNS+" "
						+ "from(select "+COLUMNS+" "
								+ "from comment1 "
								+ "order by comment_no desc)) "
				+ "where rn between ? and ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				CommentDTO dto=new CommentDTO();
				dto.setCommentContent(rs.getString("COMMENT_CONTENT"));
				dto.setCuserId(rs.getString("CUSER_ID"));
				dto.setCommentNo(rs.getLong("COMMENT_NO"));
				dto.setCommentSubject(rs.getString("COMMENT_SUBJECT"));
				dto.setRegDate(rs.getTimestamp("REG_DATE"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}
	
	public void commentInsert(CommentDTO dto) {
		conn=getConnection();
		sql="insert into comment1("+COLUMNS+") "
				+ "values(("+SUBQUERY+"), ?,sysdate,?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCuserId());
			pstmt.setString(2, dto.getCommentSubject());
			pstmt.setString(3, dto.getCommentContent());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 입력되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
	}
	

}
