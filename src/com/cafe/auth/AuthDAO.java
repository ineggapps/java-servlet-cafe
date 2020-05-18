package com.cafe.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBCPConn;

public class AuthDAO {

	public int insertMember(AuthDTO dto) {
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
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				if (!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}

		return result;
	}

	public AuthDTO readMember(String userId) {
		AuthDTO dto = null; 
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT userNum, email, userId, userPwd, userName, nickname, "
				+ "TO_CHAR(created_date,'YYYY-MM-DD') created_date, TO_CHAR(updated_date,'YYYY-MM-DD') "
				+ "updated_date, phone, enabled FROM member WHERE enabled=1 and userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int userNum = rs.getInt("userNum");
				String email = rs.getString("email");
				String userPwd = rs.getString("userPwd");
				String userName = rs.getString("userName");
				String nickname = rs.getString("nickname");
				String created_date = rs.getString("created_date");
				String updated_date = rs.getString("updated_date");
				String phone = rs.getString("phone");
				int enabled = rs.getInt("enabled");
				dto = new AuthDTO(userNum, email, userId, userPwd, userName, nickname, created_date, updated_date, phone, enabled);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
}
