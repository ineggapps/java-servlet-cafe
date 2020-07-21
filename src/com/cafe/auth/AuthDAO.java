package com.cafe.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.DBCPConn;

public class AuthDAO {

	public int insertMember(AuthDTO dto) throws Exception { // 회원가입
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO member(userNum, email, userId, userPwd, userName, nickname, phone) VALUES(MEMBER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getUserPwd());
			pstmt.setString(4, dto.getUserName());
			pstmt.setString(5, dto.getNickname());
			pstmt.setString(6, dto.getPhone());
			result=pstmt.executeUpdate();
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

	public AuthDTO readMember(String userId) { // 아이디로 회원정보 조회
		AuthDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		synchronized (this) {

			/*
			 * SELECT member.userNum, email, userId, userPwd, userName, nickname,
			 * TO_CHAR(created_date,'YYYY-MM-DD') created_date,
			 * TO_CHAR(updated_date,'YYYY-MM-DD') updated_date, phone, enabled,
			 * admin.userNum isAdmin FROM member LEFT OUTER JOIN member_admin admin ON
			 * member.userNum = admin.userNum WHERE enabled=1 and member.userNum = 5;
			 */
			String sql = "SELECT m.userNum, email, userId, userPwd, userName, nickname, "
					+ "TO_CHAR(created_date,'YYYY-MM-DD') created_date, TO_CHAR(updated_date,'YYYY-MM-DD') "
					+ "updated_date, phone, enabled, admin.userNum isAdmin FROM member m "
					+ "LEFT OUTER JOIN member_admin admin ON m.userNum = admin.userNum "
					+ "WHERE enabled=1 and userId = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					int userNum = rs.getInt("userNum");
					String email = rs.getString("email");
					String userPwd = rs.getString("userPwd");
					String userName = rs.getString("userName");
					String nickname = rs.getString("nickname");
					String created_date = rs.getString("created_date");
					String updated_date = rs.getString("updated_date");
					String phone = rs.getString("phone");
					int enabled = rs.getInt("enabled");
					boolean isAdmin = rs.getInt("isAdmin") > 0 ? true : false; // null이면 0으로 반환됨.
					dto = new AuthDTO(userNum, email, userId, userPwd, userName, nickname, created_date, updated_date,
							phone, enabled, isAdmin);
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
		}
		return dto;
	}

	public int updateMember(AuthDTO dto) throws Exception { // 회원정보 수정

		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		Connection conn = DBCPConn.getConnection();

		try {
			sql = "UPDATE member SET userPwd=?, nickname=?, phone=? WHERE userNum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getNickname());
			pstmt.setString(3, dto.getPhone());
			pstmt.setInt(4, dto.getUserNum());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
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

	// 아이디 찾기 (이름, 핸드폰번호)
	protected String findId(String userName, String phone) throws ServletException, IOException {
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String userId = null;
		String sql = "SELECT userId FROM member WHERE userName=? and phone=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, phone);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				userId = rs.getString("userId");
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
		return userId;

	}
	
	protected int findPwdBefore(String userId, String phone) throws ServletException, IOException {
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(count(userId),0) FROM member WHERE userId=? and phone=?";
		int result=0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, phone);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result=rs.getInt(1);
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

	// 비밀번호 찾기 (아이디, 핸드폰번호)
	public int findPwd(String userPwd, String userId, String phone) {
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE member SET userPwd=? WHERE userId=? and phone=?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userPwd);
			pstmt.setString(2, userId);
			pstmt.setString(3, phone);

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

	// 아이디 중복체크
	protected boolean memberIdCheck(String userId) {
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean x = false;

		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT userId FROM member WHERE userId=?");

			conn = DBCPConn.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next())
				x = true;

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
		return x;
	}

	public int deleteMember(int userNum) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE member SET enabled=0 WHERE userNum = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNum);
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

	public int deleteMember(int userNum, String userPwd) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "UPDATE member SET enabled=0 WHERE userNum=? and userPwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNum);
			pstmt.setString(2, userPwd);
			pstmt.executeUpdate();
			result = pstmt.executeUpdate();

//			System.out.println(userNum + "," + userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPConn.close(conn);

		}
		return result;
	}

	
}
/*
 * protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp)
 * throws ServletException, IOException { String path =
 * "/WEB-INF/views/cafe/auth_mypage2.jsp"; SessionAuthInfo info =
 * getSessionAuthInfo(req);
 * 
 * try { AuthDAO dao = new AuthDAO(); String email1 =
 * req.getParameter(PARAM_EMAIL1); String email2 =
 * req.getParameter(PARAM_EMAIL2); String userPwd =
 * req.getParameter(PARAM_USER_PWD); String nickname =
 * req.getParameter(PARAM_NICKNAME); String phone =
 * req.getParameter(PARAM_PHONE); int userNum =
 * Integer.parseInt(req.getParameter(PARAM_USER_NUM));
 * 
 * AuthDTO dto = new AuthDTO(); dto.setEmail(email1 + "@" + email2);
 * dto.setUserPwd(userPwd); dto.setNickname(nickname); dto.setPhone(phone);
 * dto.setUserNum(userNum); System.out.println(dto);
 * 
 * dao.updateMember(dto); forward(req, resp, path);
 * 
 * 
 * } catch (Exception e) { e.printStackTrace(); resp.sendRedirect(apiPath +
 * "/update.do"); return; }
 * 
 * }
 */
