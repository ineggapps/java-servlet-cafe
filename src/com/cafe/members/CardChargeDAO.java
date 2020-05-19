package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBCPConn;

public class CardChargeDAO {
	public int insertCardCharge(CardChargeDTO dto) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO card_charge(chargeNum, cardNum, chargeAmount) "
				+ "VALUES(card_charge_seq.NEXTVAL, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getCardNum());
			pstmt.setInt(2, dto.getChargeAmount());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
