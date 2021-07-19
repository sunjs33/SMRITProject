package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DTO.LibBoardDTO;

public class LibBoardDAO extends DataBaseInfo {
	final String COLUMNS = "BOARD_NUM,USER_ID,BOARD_NAME,BOARD_PASS," + "BOARD_SUBJECT,BOARD_CONTENT,BOARD_DATE,"
			+ "IP_ADDR,READ_COUNT,ORIGINAL_FILE_NAME," + "STORE_FILE_NAME,FILE_SIZE";

	public Integer fileUpdate(LibBoardDTO dto) {
		Integer i=0;
		conn=getConnection();
		sql="update libraryboard set ORIGINAL_FILE_NAME=?, STORE_FILE_NAME=?, FILE_SIZE=? where board_num=? and BOARD_PASS=? and user_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getOriginalFileName());
			pstmt.setString(2, dto.getStoreFileName());
			pstmt.setLong(3, dto.getFileSize());
			pstmt.setLong(4, dto.getBoardNum());
			pstmt.setString(5, dto.getBoardPass());
			pstmt.setString(6, dto.getUserId());
			
			i=pstmt.executeUpdate();
			System.out.println(i+"개가 수정되었습니다.");
			
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
		}
		
		return i;
		
	}
	
	public void libContentUpdate(LibBoardDTO dto) {
		conn=getConnection();
		sql="update libraryboard set board_subject=?, board_content=? where board_num=? and board_pass=? and user_id=?";
	try {
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, dto.getBoardSubject());
		pstmt.setString(2, dto.getBoardContent());
		pstmt.setLong(3, dto.getBoardNum());
		pstmt.setString(4, dto.getBoardPass());
		pstmt.setString(5, dto.getUserId());
		int i=pstmt.executeUpdate();
		System.out.println(i+"개가 수정되었습니다.");
		
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}finally {
		close();
	}
	
	}
	
	public Integer libBoardDel(String boardNum) {
		Integer i=0;
		conn=getConnection();
		sql="delete from libraryboard where BOARD_NUM=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, boardNum);
			i=pstmt.executeUpdate();
			System.out.println(i+"개가 삭제 되었습니다.");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
		}
		
		return i;
		
	}
	
	public int libCount() {
		int i = 0;
		conn = getConnection();
		sql = "select count(*) from libraryboard";
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

	public List<LibBoardDTO> libSelectAll(String num, int page, int limit) {
		
		int startRow = (page - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		
		String condition="";
		if(num!=null) {
			condition="and board_num='"+num+"'";
		}
		List<LibBoardDTO> list = new ArrayList<LibBoardDTO>();
		conn = getConnection();
		sql = "select * "
				+ "from(select rownum rn, "+COLUMNS
				+"		from(select "+ COLUMNS +" from libraryboard "
						+ "	where 1=1 "+condition+" order by board_num desc)) "
				+ "where rn between ? and ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LibBoardDTO dto = new LibBoardDTO();
				dto.setBoardContent(rs.getString("BOARD_CONTENT"));
				dto.setBoardDate(rs.getTimestamp("BOARD_DATE"));
				dto.setBoardName(rs.getString("BOARD_NAME"));
				dto.setBoardNum(rs.getLong("BOARD_NUM"));
				dto.setBoardSubject(rs.getString("BOARD_SUBJECT"));
				dto.setBoardPass(rs.getString("BOARD_PASS"));
				dto.setIpAddr(rs.getString("IP_ADDR"));
				dto.setUserId(rs.getString("USER_ID"));
				dto.setReadCount(rs.getLong("READ_COUNT"));
				dto.setOriginalFileName(rs.getString("ORIGINAL_FILE_NAME"));
				dto.setStoreFileName(rs.getString("STORE_FILE_NAME"));
				dto.setFileSize(rs.getLong("FILE_SIZE"));
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

	public void libInsert(LibBoardDTO dto) {
		conn = getConnection();
		sql = "insert into LIBRARYBOARD(" + COLUMNS + ") " + "values(NUM_SEQ.nextval,?,?,?,?,?,sysdate,?,0,?,?,?)";
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
