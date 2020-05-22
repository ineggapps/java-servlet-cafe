package com.cafe.admin.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cafe.auth.AuthDTO;
import com.util.DBCPConn;

public class AdminAuthDAO {
	public AuthDTO readAdmin(String userId) { // 아이디로 회원정보 조회
		AuthDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT admin.userNum, email, userId, userPwd, userName, nickname, TO_CHAR(created_date,'YYYY-MM-DD') created_date, TO_CHAR(updated_date,'YYYY-MM-DD') updated_date, phone, enabled, admin.userNum isAdmin FROM member_admin admin JOIN member m ON m.userNum = admin.userNum WHERE enabled=1 and userId= ?";
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
			try {
				if (!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		return dto;
	}
}
