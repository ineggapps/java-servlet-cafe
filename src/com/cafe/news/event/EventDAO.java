package com.cafe.news.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe.menu.MenuDTO;
import com.cafe.news.notice.NoticeDTO;
import com.util.DBCPConn;

public class EventDAO {	
	// 이벤트 작성
	public int insertEvent(EventDTO dto) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO event (num, userNum, subject, content, start_date, end_date) "
					+ "VALUES (event_seq.NEXTVAL, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getUserNum());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getStart_date());
			pstmt.setString(5, dto.getEnd_date());
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
	
	// 전체 데이터 개수
	public int dataCount() {
		int result=0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM event";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {		
				result=rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			DBCPConn.close(conn);
		}
		return result;
	}
	
	// 검색 모드에서 전체의 개수 구하기
		public int dataCount(String condition, String keyword) {
			int result = 0;
			Connection conn = DBCPConn.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				if (condition.equalsIgnoreCase("created")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sql = "SELECT NVL(COUNT(*), 0) FROM event WHERE TO_CHAR(created_date, 'YYYYMMDD')=?";
				} else {
					sql = "SELECT NVL(COUNT(*), 0) FROM event WHERE INSTR("+condition+", ?)>=1";
				}

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);

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
		
	// 검색에서 리스트
	public List<EventDTO> listEvent(int offset, int rows, String condition, String keyword) {
		List<EventDTO> list = new ArrayList<EventDTO>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if(keyword!=null && keyword.length()!=0) {
				sb.append("SELECT num, subject, userName, views, ");
				sb.append(" TO_CHAR(e.created_date, 'YYYY-MM-DD') created_date, ");
				sb.append(" TO_CHAR(start_date, 'YYYY-MM-DD') start_date, ");
				sb.append(" TO_CHAR(end_date, 'YYYY-MM-DD') end_date, ");
				sb.append(" FROM event e ");
				sb.append(" JOIN member m ON m.userNum = e.userNum ");
				
				if(condition.equalsIgnoreCase("created")) {
					keyword = keyword.replaceAll("-", "");
					sb.append(" WHERE TO_CHAR(created_date, 'YYYY-MM-DD') created_date = ? ");
				} else {
					sb.append("      WHERE INSTR(" + condition + ", ?) >= 1 ");
				}
				
				sb.append("  ORDER BY num DESC ");
				sb.append("  OFFSET ? BOWS FETCH FIRST ? ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EventDTO dto = new EventDTO();
					dto.setNum(rs.getInt("num"));
					dto.setSubject(rs.getString("subject"));
					dto.setUserName(rs.getString("userName"));
					dto.setViews(rs.getInt("views"));
					dto.setStart_date(rs.getString("start_date"));
					dto.setEnd_date(rs.getString("end_date"));
					
					list.add(dto);
				}
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
		return list;
	}
	
	// 글 리스트
	public List<EventDTO> listEvent(int offset, int rows) {
		List<EventDTO> list = new ArrayList<EventDTO>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT num, e.userNum, subject, views, userName, ");
			sb.append(" TO_CHAR(e.created_date, 'YYYY-MM-DD') created_date ,");
			sb.append(" TO_CHAR(start_date, 'YYYY-MM-DD') start_date, ");
			sb.append(" TO_CHAR(end_date, 'YYYY-MM-DD') end_date ");
			sb.append(" FROM event e ");
			sb.append(" JOIN member m ON m.userNum = e.userNum ");
			sb.append(" ORDER BY e.userNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EventDTO dto = new EventDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserNum(rs.getInt("userNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated_date(rs.getString("created_date"));
				dto.setStart_date(rs.getString("start_date"));
				dto.setEnd_date(rs.getString("end_date"));
				
				list.add(dto);
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
		return list;
	}
	// 글 보기
	public EventDTO readEvent(int num) {
		EventDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		
		try {
			sql="SELECT num, subject, userName, content, views, created_date FROM event WHERE num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new EventDTO();
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setViews(rs.getInt("views"));
				dto.setUserName(rs.getString("userName"));
				dto.setContent(rs.getString("content"));
				dto.setCreated_date(rs.getString("created_date"));
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

	// 조회수 증가
//	public 
	
	
	// 이전글
	public EventDTO preReadEvent(int num, String condition, String keyword) {
		EventDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb = new StringBuilder();
        
        try {
        	if(keyword!=null && keyword.length() != 0) {
                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT num, subject FROM event ");
                if(condition.equalsIgnoreCase("created")) {
                	keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
                	sb.append("     WHERE (TO_CHAR(created_date, 'YYYYMMDD') = ?) ");
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
                sb.append("    SELECT num, subject FROM event ");
                sb.append("    WHERE num > ? ");		
                sb.append("    ORDER BY num ASC ");		
                sb.append(" ) tb WHERE ROWNUM=1 ");		

                
                
                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
			}
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		dto = new EventDTO();
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
		public EventDTO nextReadEvent(int num, String condition, String keyword) {
			EventDTO dto = null;
			Connection conn = DBCPConn.getConnection();
			PreparedStatement pstmt=null;
	        ResultSet rs=null;
	        StringBuilder sb = new StringBuilder();
	     
	        try {
	        	if(keyword!=null && keyword.length() != 0) {
	                sb.append(" SELECT ROWNUM, tb.* FROM ( ");
	                sb.append("     SELECT num, subject FROM event ");
	                if(condition.equalsIgnoreCase("created")) {
	                	keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
	                	sb.append("     WHERE (TO_CHAR(created_date, 'YYYYMMDD') = ?) ");
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
	                sb.append("    SELECT num, subject FROM event ");
	                sb.append("    WHERE num < ? ");		
	                sb.append("    ORDER BY num DESC ");		
	                sb.append(" ) tb WHERE ROWNUM=1 ");		

	                pstmt=conn.prepareStatement(sb.toString());
	                pstmt.setInt(1, num);
				}
	        	
	        	rs = pstmt.executeQuery();
	        	
	        	if(rs.next()) {
	        		dto = new EventDTO();
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
	
	// 이벤트 수정
	public void Eventupdate(EventDTO dto) {
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="UPDATE event SET subject=?, content=? start_date=?, end_date=? WHERE num=?";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getStart_date());
			pstmt.setString(4, dto.getEnd_date());
			pstmt.setInt(5, dto.getNum());
			
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
	}
	
	// 이벤트 삭제
	public int Eventdelete(int num) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM event WHERE num = ?";
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