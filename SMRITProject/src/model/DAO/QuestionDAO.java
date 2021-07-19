package model.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DTO.OptionDTO;
import model.DTO.QuestionDTO;
import model.DTO.QuestionOptionsDTO;

public class QuestionDAO extends DataBaseInfo {
	
	public List<QuestionOptionsDTO> surveySelect(String userId) {
		List<QuestionOptionsDTO> list=new ArrayList<QuestionOptionsDTO>();
		conn=getConnection();
		List<Integer> nums=new ArrayList<Integer>();
		try {
			sql="select question_num from question where user_id=? order by q.question_num";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				nums.add(rs.getInt(1));
			}
//			int qNum=0;
//			if(rs.next()) {
//				qNum=rs.getInt(1);
//			}
			rs.close();
			pstmt.close();
			sql="select q.question_num, question_title, options_num, options_name "
					+ "from question q, options o "
					+ "where q.question_num = o.question_num(+) "
					+ "and q.user_id=? and q.question_num=? order by q.question_num";
			pstmt=conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			for (int i:nums) {
				pstmt.setString(1, userId);
				pstmt.setInt(2, i);
				rs=pstmt.executeQuery();
				QuestionDTO qdto = new QuestionDTO();
				List<OptionDTO> options = new ArrayList<OptionDTO>();
				QuestionOptionsDTO qoDTO =new QuestionOptionsDTO();
				if(rs.next()) {
					qdto.setQuestionNum(rs.getInt("QUESTION_NUM"));
					qdto.setQuestionTitle(rs.getString("QUESTION_TITLE"));
				}rs.beforeFirst();
				while(rs.next()) {
					OptionDTO odto = new OptionDTO();
					odto.setOptionsName(rs.getString("OPTIONS_NAME"));
					odto.setOptionsNum(rs.getInt("OPTIONS_NUm"));
					options.add(odto);
				}
				qoDTO.setQuestion(qdto);
				qoDTO.setOptions(options);
				list.add(qoDTO);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return list;
	}
	
	public void optionInsert(OptionDTO odto) {
		conn=getConnection();
		sql="insert into options (user_id, QUESTION_NUM, OPTIONS_NUM, OPTIONS_NAME) values (?,?,?,?)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, odto.getUserId());
			pstmt.setInt(2, odto.getQuestionNum());
			pstmt.setInt(3, odto.getOptionsNum());
			pstmt.setString(4, odto.getOptionsName());
			int i=pstmt.executeUpdate();
			System.out.println(i+"개가 입력되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
	
	public int questionInsert(QuestionDTO qdto) {
		int i=0;
		conn=getConnection();
				
		try {
			sql="select nvl(max(question_num),0)+1 from question where user_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, qdto.getUserId());
			rs=pstmt.executeQuery();
			rs.next();
			i=rs.getInt(1);
			rs.close();
			pstmt.close();
			
			sql="insert into question (question_num, question_title, user_id) values (?,?,? )";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			pstmt.setString(2, qdto.getQuestionTitle());
			pstmt.setString(3, qdto.getUserId());
			int j=pstmt.executeUpdate();
			System.out.println(j+"개가 입력되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return i;
		
		
	}

}
