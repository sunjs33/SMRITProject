package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DTO.QnaBoardDTO;

public class QnaBoardDAO extends DataBaseInfo {
	final String COLUMNS = "BOARD_NUM,USER_ID,BOARD_NAME,BOARD_PASS,"
						+ "BOARD_SUBJECT,BOARD_CONTENT,BOARD_DATE,"
						+ "IP_ADDR,READ_COUNT";
	
	
	public void qnaDelete(QnaBoardDTO dto) {
		conn=getConnection();
		sql="delete from board where user_id=? and board_num=? and board_pass=?";
				
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setLong(2, dto.getBoardNum());
			pstmt.setString(3, dto.getBoardPass());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 삭제되었습니다.");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public void qnaUpdate(QnaBoardDTO dto) {
		conn=getConnection();
		sql="update board set board_subject=?, board_content=? where board_num=? and user_id=? and board_pass=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardSubject());
			pstmt.setString(2, dto.getBoardContent());
			pstmt.setLong(3, dto.getBoardNum());
			pstmt.setString(4, dto.getUserId());
			pstmt.setString(5, dto.getBoardPass());
			int i = pstmt.executeUpdate();
			System.out.println(i+"개가 수정 되었습니다.");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
			
		}
		
	}
	public QnaBoardDTO QnaOneSelect(String num) {
		QnaBoardDTO dto =new QnaBoardDTO();
		conn=getConnection();
		sql="select "+COLUMNS+" from board where board_num=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto.setBoardContent(rs.getString("BOARD_CONTENT"));
				dto.setBoardDate(rs.getTimestamp("Board_Date"));
				dto.setBoardName(rs.getString("Board_Name"));
				dto.setBoardNum(rs.getLong(1));	// 컬럼명 대신 순서 번호로 넣어도됨
				dto.setBoardPass(rs.getString("Board_Pass"));
				dto.setBoardSubject(rs.getString("Board_Subject"));
				dto.setIpAddr(rs.getString("Ip_Addr"));
				dto.setReadCount(rs.getLong("Read_Count"));
				dto.setUserId(rs.getString("user_Id"));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
		return dto;
		
	}
	public int qnaCount() {
		int i =0;
		conn=getConnection();
		sql="select count(*) from board";
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				i=rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
	}
	public List<QnaBoardDTO> qnaSelectAll(int page, int limit) {
		int startRow = (page - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		
		List<QnaBoardDTO> list =new ArrayList<QnaBoardDTO>();
		conn=getConnection();
		sql="select * "
				+ "from(select rownum rn,"+COLUMNS+" "
						+ "from(select "+COLUMNS+" "
								+ "from board order by board_num desc)) "
						+ "where rn between ? and ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				QnaBoardDTO dto =new QnaBoardDTO();
				dto.setBoardContent(rs.getString("BOARD_CONTENT"));
				dto.setBoardDate(rs.getTimestamp("Board_Date"));
				dto.setBoardName(rs.getString("Board_Name"));
				dto.setBoardNum(rs.getLong("board_num"));
				dto.setBoardPass(rs.getString("Board_Pass"));
				dto.setBoardSubject(rs.getString("Board_Subject"));
				dto.setIpAddr(rs.getString("Ip_Addr"));
				dto.setReadCount(rs.getLong("Read_Count"));
				dto.setUserId(rs.getString("user_Id"));
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
		
	}

	public void qnaInsert(QnaBoardDTO dto) {
		conn = getConnection();
		String subQuery="select nvl(max(board_num),0)+1 from board";
		sql = "insert into board(" + COLUMNS + ") " + "values(("+subQuery+"),?,?,?,?,?,sysdate,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBoardName());
			pstmt.setString(3, dto.getBoardPass());
			pstmt.setString(4, dto.getBoardSubject());
			pstmt.setString(5, dto.getBoardContent());
			pstmt.setString(6, dto.getIpAddr());
			pstmt.setLong(7, 0);
			
			int i=pstmt.executeUpdate();// 저장
			System.out.println(i+"행이 저장되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}
}
