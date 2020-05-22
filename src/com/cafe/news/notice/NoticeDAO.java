package com.cafe.news.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class NoticeDAO {
	public int insertNotice(NoticeDTO dto) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "INSERT INTO notice(num, userNum, subject, content) VALUES(notice_seq.NEXTVAL, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getUserNum());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			DBCPConn.close(conn);
		}

		return result;
	}

	// 전체 데이터 개수 구하기
	public int dataCount() {
		int result = 0;

		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			DBCPConn.close(conn);
		}

		return result;
	}
	
	// 글 목록
	public List<NoticeDTO> listNotice(int start, int end) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT * FROM (");
			sb.append(" SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("   SELECT num, subject, views, ");
			sb.append("     TO_CHAR(updated_date, 'YYYY-MM-DD') updated_date  ");
			sb.append("	  FROM notice ");
			sb.append("   ORDER BY num DESC ");
			sb.append(" )  tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setViews(rs.getInt("views"));
				dto.setUpdated_date(rs.getString("updated_date"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			DBCPConn.close(conn);
		}

		return list;
	}

	// 검색 모드에서 전체의 개수 구하기
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = DBCPConn.getConnection();
		String sql;

		try {
			if (condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql = "SELECT NVL(COUNT(*), 0) FROM notice WHERE TO_CHAR(updated_date, 'YYYYMMDD') = ?  "; // 날짜(등록일)로 검색
			} else {
				sql = "SELECT NVL(COUNT(*), 0) FROM notice WHERE INSTR(" + condition + ", ? ) >= 1 "; // 제목,내용으로 검색 : 포함하는 내용을 다 검색
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);

			rs = pstmt.executeQuery();

			if (rs.next()) result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			DBCPConn.close(conn);
		}

		return result;
	}
	
	// 검색에서 리스트(목록)
	public List<NoticeDTO> listNotice(int start, int end, String condition, String keyword){
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb = new StringBuilder();
        Connection conn = DBCPConn.getConnection();

        try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("        SELECT num, subject, views ");
			sb.append("            ,TO_CHAR(updated_date, 'YYYY-MM-DD') updated_date");
			sb.append("         FROM notice");
			if(condition.equalsIgnoreCase("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("      WHERE TO_CHAR(updated_date, 'YYYYMMDD') = ? ");
			} else {
				sb.append("      WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("	       ORDER BY num DESC");
			sb.append("    ) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, keyword);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
            	NoticeDTO dto = new NoticeDTO();
                dto.setNum(rs.getInt("num"));
                dto.setSubject(rs.getString("subject"));
                dto.setViews(rs.getInt("views"));
                dto.setUpdated_date(rs.getString("updated_date"));
            
                list.add(dto);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try	{
					rs.close();
				}catch (Exception e2){
				}
			}
			if(pstmt!=null) {
				try	{
					pstmt.close();
				}catch (Exception e2){
				}
			}
			DBCPConn.close(conn);
		}
		
		return list;
	}
	
	// 글보기
	public NoticeDTO readNotice(int num) {
		NoticeDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		Connection conn = DBCPConn.getConnection();
		
		try {
			sql = "SELECT num, subject, content, views, TO_CHAR(updated_date, 'YYYY-MM-DD') updated_date FROM notice WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new NoticeDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setViews(rs.getInt("views"));
				dto.setUpdated_date(rs.getString("updated_date"));
				dto.setContent(rs.getString("content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			DBCPConn.close(conn);
		}
		return dto;
	}
	
	// 조회수
	public int updateViews(int num) {
		int result = 0;
		
		PreparedStatement pstmt=null;
		String sql;
		Connection conn = DBCPConn.getConnection();

		try {
			sql = "UPDATE notice SET views = views+1 WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			DBCPConn.close(conn);
		}
		
		return result;
	}
	
	// 이전글
	public NoticeDTO preReadNotice(int num, String condition, String keyword) {
		NoticeDTO dto = null;
		
		PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb = new StringBuilder();
        Connection conn = DBCPConn.getConnection();
        
        try {
			
        	if(keyword!=null && keyword.length() != 0) {
                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT num, subject FROM notice ");
                if(condition.equalsIgnoreCase("created")) {
                	keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
                	sb.append("     WHERE (TO_CHAR(updated_date, 'YYYYMMDD') = ?) ");
                } else {
    				sb.append("     WHERE (INSTR(" + condition + ", ?) >= 1) ");
                }
                sb.append("             AND (num > ? ) ");
                sb.append("         ORDER BY num ASC ");	
                sb.append(" ) tb WHERE ROWNUM=1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, num);
			} else {
                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
                sb.append("    SELECT num, subject FROM notice ");
                sb.append("    WHERE num > ? ");		
                sb.append("    ORDER BY num ASC ");		
                sb.append(" ) tb WHERE ROWNUM=1 ");		

                
                
                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
			}
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		dto = new NoticeDTO();
        		dto.setNum(rs.getInt("num"));
        		dto.setSubject(rs.getString("subject"));
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try	{
					rs.close();
				}catch (Exception e2){
				}
			}
			if(pstmt!=null) {
				try	{
					pstmt.close();
				}catch (Exception e2){
				}
			}
			DBCPConn.close(conn);
		}
        
		return dto;
	}
	
	// 다음글
		public NoticeDTO nextReadNotice(int num, String condition, String keyword) {
			NoticeDTO dto = null;
			
			PreparedStatement pstmt=null;
	        ResultSet rs=null;
	        StringBuilder sb = new StringBuilder();
	        Connection conn = DBCPConn.getConnection();
	        
	        try {
				
	        	if(keyword!=null && keyword.length() != 0) {
	                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM notice ");
	                if(condition.equalsIgnoreCase("created")) {
	                	keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
	                	sb.append("     WHERE (TO_CHAR(updated_date, 'YYYYMMDD') = ?) ");
	                } else {
	    				sb.append("     WHERE (INSTR(" + condition + ", ?) >= 1) ");
	                }
	                sb.append("             AND (num < ? ) ");
	                sb.append("         ORDER BY num DESC ");	
	                sb.append(" ) tb WHERE ROWNUM=1 ");

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setString(1, keyword);
	                pstmt.setInt(2, num);
				} else {
	                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("    SELECT num, subject FROM notice ");
	                sb.append("    WHERE num < ? ");		
	                sb.append("    ORDER BY num DESC ");		
	                sb.append(" ) tb WHERE ROWNUM=1 ");		

	                
	                
	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
				}
	        	
	        	rs = pstmt.executeQuery();
	        	
	        	if(rs.next()) {
	        		dto = new NoticeDTO();
	        		dto.setNum(rs.getInt("num"));
	        		dto.setSubject(rs.getString("subject"));
	        	}
	        	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) {
					try	{
						rs.close();
					}catch (Exception e2){
					}
				}
				if(pstmt!=null) {
					try	{
						pstmt.close();
					}catch (Exception e2){
					}
				}
				DBCPConn.close(conn);
			}
	        
			return dto;
		}
		
		
		
		// 글 수정
		public int updateNotice(NoticeDTO dto) {
			int result = 0;
			
			PreparedStatement pstmt=null;
			String sql;
			Connection conn = DBCPConn.getConnection();

			try {
				sql = "UPDATE notice SET subject = ?, content = ?, updated_date = SYSDATE WHERE num = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContent());
				pstmt.setInt(3, dto.getNum());
				
				result = pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				DBCPConn.close(conn);
			}
			
			return result;
		}
		
		
		// 글 삭제
		public int deleteNotice(int num) {
			int result = 0;
			
			PreparedStatement pstmt=null;
			String sql;
			Connection conn = DBCPConn.getConnection();

			try {
				sql = "DELETE FROM notice WHERE num = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				result = pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				DBCPConn.close(conn);
			}
			
			return result;
		}
}
