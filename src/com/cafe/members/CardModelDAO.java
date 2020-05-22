package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class CardModelDAO {
	
	public int count() {
		int count = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(COUNT(modelNum), 0) FROM card_model";
		try {
			pstmt = conn.prepareStatement(sql);
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
				DBCPConn.close(conn);
			} catch (Exception e2) {
			}
		}
		return count;
	}
	
	public List<CardModelDTO> listCardModel(int offset, int rows) {
		List<CardModelDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT modelNum, modelname, text, thumbnail FROM card_model " + " ORDER BY modelNum DESC "
				+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int modelNum = rs.getInt("modelNum");
				String modelName = rs.getString("modelName");
				String text = rs.getString("text");
				String thumbnail = rs.getString("thumbnail");
				list.add(new CardModelDTO(modelNum, modelName, text, thumbnail));
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

	public List<CardModelDTO> listCardModel() {
		List<CardModelDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT modelNum, modelname, text, thumbnail FROM card_model " + " ORDER BY modelNum DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int modelNum = rs.getInt("modelNum");
				String modelName = rs.getString("modelName");
				String text = rs.getString("text");
				String thumbnail = rs.getString("thumbnail");
				list.add(new CardModelDTO(modelNum, modelName, text, thumbnail));
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

	public CardModelDTO readCardModel(int modelNum) {// 모델번호로 카드 모델 읽기
		CardModelDTO dto = null;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT modelNum, modelName, text, thumbnail FROM card_model WHERE modelNum = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, modelNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String modelName = rs.getString("modelName");
				String text = rs.getString("text");
				String thumbnail = rs.getString("thumbnail");
				dto = new CardModelDTO(modelNum, modelName, text, thumbnail);
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
