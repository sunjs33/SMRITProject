package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DTO.AnsBoardDTO;

public class AnsBoardDAO extends DataBaseInfo {
	final String COLUMNS = "BOARD_NUM,USER_ID,BOARD_NAME,BOARD_PASS,"
			+ "BOARD_SUBJECT,BOARD_CONTENT,BOARD_DATE,IP_ADDR,READ_COUNT,"
			+ "ORIGINAL_FILE_NAME,STORE_FILE_NAME,FILE_SIZE," + "BOARD_RE_REF,BOARD_RE_LEV,BOARD_RE_SEQ";
	
	public void answerSeqUpdate(AnsBoardDTO dto) {
		conn=getConnection();
		sql="update answerboard "
				+ "set BOARD_RE_SEQ=BOARD_RE_SEQ+1 where BOARD_RE_SEQ > ?"
				+ "and BOARD_RE_REF=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, dto.getBoardReSeq());
			pstmt.setLong(2, dto.getBoardReRef());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public void answerReplyInsert(AnsBoardDTO dto) {
		Long lev=dto.getBoardReLev()+1;
		Long seq=dto.getBoardReSeq()+1;
		conn=getConnection();
		sql = "insert into answerboard(" + COLUMNS + ") values(NUM_SEQ.nextval,?,?,?,?,?,sysdate,?,0,null,null,0,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBoardName());
			pstmt.setString(3, dto.getBoardPass());
			pstmt.setString(4, dto.getBoardSubject());
			pstmt.setString(5, dto.getBoardContent());
			pstmt.setString(6, dto.getIpAddr());
			pstmt.setLong(7, dto.getBoardReRef());
			pstmt.setLong(8, lev);
			pstmt.setLong(9, seq);
			int i = pstmt.executeUpdate();        
			System.out.println(i + "행이 저장되었습니다.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	
	public Integer fileUpdate(AnsBoardDTO dto) {
		Integer i=0;
		conn=getConnection();
		sql="update answerboard "
		  + "set original_file_name=?, "
		  + "    store_file_name=?, file_size=? "
		  + "where board_pass=? and user_id=? and board_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getOriginalFileName());
			pstmt.setString(2, dto.getStoreFileName());
			pstmt.setLong(3, dto.getFileSize());
			pstmt.setString(4, dto.getBoardPass());
			pstmt.setString(5, dto.getUserId());
			pstmt.setLong(6, dto.getBoardNum());
			i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return i;
	}
	
	
	public void ansContentUpdate(AnsBoardDTO dto) {
		conn=getConnection();
		sql="update answerboard "
		   + "set board_subject=?, "
		   + "    board_content=? "
		   + "where board_num=? "
		   + "and board_pass=? "
		   + "and user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardSubject());
			pstmt.setString(2, dto.getBoardContent());
			pstmt.setLong(3, dto.getBoardNum());
			pstmt.setString(4, dto.getBoardPass());
			pstmt.setString(5, dto.getUserId());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정 되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public Integer ansBoardDelete(String boardNum,String userId,String boardPass) {
		Integer i=0;
		conn=getConnection();
		sql="delete from answerboard where board_num=? and user_id=? and board_pass=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, boardNum);
			pstmt.setString(2, userId);
			pstmt.setString(3, boardPass);
			i=pstmt.executeUpdate();
			System.out.println(i+"개가 삭제 되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return i;
		
	}

	public int ansCount() {
		int i = 0;
		conn = getConnection();
		sql = "select count(*) from answerboard";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close();
		}

		return i;
	}

	public List<AnsBoardDTO> ansSelectAll(String num, int page, int limit) {
		List<AnsBoardDTO> list = new ArrayList<AnsBoardDTO>();
		int startRow = (page - 1) * limit + 1;
		int endRow = startRow + limit - 1;

		String condition="";
		if(num!=null) {
			condition ="and board_num='"+num+"'";
		}
		
		conn = getConnection();
		sql = "select * "
				+ "from(select rownum rn, "+COLUMNS
				+"		from (select " + COLUMNS 
							+ " from answerboard "
							+ " where 1=1" +condition+ 
							   "order by board_re_ref desc, board_re_seq asc)) "
				+ "where rn between ? and ? " ;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				AnsBoardDTO dto = new AnsBoardDTO();
				dto.setBoardNum(rs.getLong("BOARD_NUM"));
				dto.setUserId(rs.getString("USER_ID"));
				dto.setBoardName(rs.getString("BOARD_NAME"));
				dto.setBoardPass(rs.getString("BOARD_PASS"));
				dto.setBoardSubject(rs.getString("board_subject"));
				dto.setBoardContent(rs.getString("board_content"));
				dto.setBoardDate(rs.getTimestamp("BOARD_DATE"));
				dto.setIpAddr(rs.getString("ip_addr"));
				dto.setReadCount(rs.getLong("read_count"));
				dto.setOriginalFileName(rs.getString("original_file_name"));
				dto.setStoreFileName(rs.getString("store_file_name"));
				dto.setFileSize(rs.getLong("file_size"));
				dto.setBoardReRef(rs.getLong("BOARD_RE_REF"));
				dto.setBoardReLev(rs.getLong("BOARD_RE_LEV"));
				dto.setBoardReSeq(rs.getLong("BOARD_RE_SEQ"));
				list.add(dto);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	public void ansInsert(AnsBoardDTO dto) {
		conn = getConnection();
		sql = "insert into answerboard(" + COLUMNS + ") values(NUM_SEQ.nextval,?,?,?,?,?,sysdate,?,0,?,?,?,NUM_SEQ.currval,0,0)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBoardName());
			pstmt.setString(3, dto.getBoardPass());
			pstmt.setString(4, dto.getBoardSubject());
			pstmt.setString(5, dto.getBoardContent());
			pstmt.setString(6, dto.getIpAddr());
			pstmt.setString(7, dto.getOriginalFileName());
			pstmt.setString(8, dto.getStoreFileName());
			pstmt.setLong(9, dto.getFileSize());
			int i = pstmt.executeUpdate();        
			System.out.println(i + "행이 저장되었습니다.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

	}

}
