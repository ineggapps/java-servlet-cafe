package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	
	public int dataCount(int cardNum, int userNum) {
		int count = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(COUNT(chargeNum),0) FROM card_charge cc "
				+ " JOIN cards c ON c.cardNum = cc.cardNum "
				+ " WHERE cc.cardNum = ? AND userNum = ? AND isClosed=0 ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, userNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
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
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		return count;
	}

	public List<CardChargeDTO> listCardCharge(int cardNum, int userNum, int offset, int rows) {
		List<CardChargeDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT chargeNum, cc.cardNum, chargeAmount, TO_CHAR(charge_date, 'YYYY-MM-DD HH24:MI:SS') charge_date "
				+ " FROM card_charge cc" + " JOIN cards cds ON cc.cardNum = cds.cardNum"
				+ " WHERE cc.cardNum = ? AND userNum = ? "
				+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, userNum);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int chargeNum = rs.getInt("chargeNum");
				int chargeAmount = rs.getInt("chargeAmount");
				String chargeDate = rs.getString("charge_date");
				list.add(new CardChargeDTO(chargeNum, cardNum, chargeAmount, chargeDate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
				}
			}
			try {
				if (!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e) {
			}
		}
		return list;
	}
}
