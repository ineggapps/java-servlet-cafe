package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBCPConn;

public class CardDAO {

	// 카드 일련번호 규칙
	// System.currentTimeMillis() => 13자리 중 10자리, 3자리는 랜덤 => 총 16자리

	// 카드 만들기
	public int insertCard(CardDTO dto) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO cards(cardNum, cardName, userNum, modelNum, cardIdentity, balance) "
				+ "VALUES (cards_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCardName());
			pstmt.setInt(2,  dto.getUserNum());
			pstmt.setInt(3, dto.getModelNum());
			pstmt.setString(4, new CardIdentityMaker().getCardIdentity());
			pstmt.setInt(5, dto.getBalance());//TODO: 지금은 직접 잔액을 입력하지만 나중에는 트리거로 계산해주어야 함.
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
			try {
				if (!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}

		return result;
	}
	

}
