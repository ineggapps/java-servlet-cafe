package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class CardDAO {

	// 카드 일련번호 규칙
	// System.currentTimeMillis() => 13자리 중 10자리, 3자리는 랜덤 => 총 16자리

	// 카드 만들기
	public int insertCard(CardDTO dto) throws Exception{
		int cardNum = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cards_seq.NEXTVAL FROM DUAL";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cardNum = rs.getInt(1);
			}
			pstmt.close();
			rs.close();
			pstmt = null;
			rs = null;
			
			sql = "INSERT INTO cards(cardNum, cardName, userNum, modelNum, cardIdentity) "
					+ "VALUES ("+cardNum+", ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCardName());
			pstmt.setInt(2, dto.getUserNum());
			pstmt.setInt(3, dto.getModelNum());
			pstmt.setString(4, new CardIdentityMaker().getCardIdentity());
			pstmt.executeUpdate();
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
			try {
				if (!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}

		return cardNum;
	}

	// 카드 목록 불러오기
	public List<CardDTO> listCard(int userNum) {
		List<CardDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cardNum, cardName, userNum, cards.modelNum, cardIdentity, balance, thumbnail "
				+ "FROM cards "
				+ "JOIN card_model ON cards.modelNum = card_model.modelNum "
				+ "WHERE userNum = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int cardNum = rs.getInt("cardNum");
				String cardName = rs.getString("cardName");
				int modelNum = rs.getInt("modelNum");
				String cardIdentity = rs.getString("cardIdentity");
				int balance = rs.getInt("balance");
				String thumbnail = rs.getString("thumbnail");
				list.add(new CardDTO(cardNum, cardName, userNum, modelNum, cardIdentity, balance, thumbnail));
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

		return list;
	}
	
	public CardDTO readCard(int cardNum, int userNum) throws Exception{
		CardDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/*
		 SELECT cardNum, cardName, cards.userNum, cards.modelNum, cardIdentity, balance, thumbnail 
		FROM cards JOIN card_model ON cards.modelNum = card_model.modelNum 
		JOIN member m ON cards.userNum = m.userNum 
		WHERE cardNum = 7 AND cards.userNum = 7;
		 */
		String sql = "SELECT cardNum, cardName, cards.userNum, cards.modelNum, cardIdentity, balance, thumbnail "
				+ "FROM cards "
				+ "JOIN card_model ON cards.modelNum = card_model.modelNum "
				+ "JOIN member m ON cards.userNum = m.userNum "
				+ "WHERE cardNum = ? AND cards.userNum = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, userNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String cardName = rs.getString("cardName");
				int modelNum = rs.getInt("modelNum");
				String cardIdentity = rs.getString("cardIdentity");
				int balance = rs.getInt("balance");
				String thumbnail = rs.getString("thumbnail");
				dto = new CardDTO(cardNum, cardName, userNum, modelNum, cardIdentity, balance, thumbnail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return dto;
	}

}
