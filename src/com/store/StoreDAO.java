package com.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class StoreDAO {
	public int storeCount() {
		int count = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(COUNT(storeNum),0) FROM store WHERE visible=1";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public List<StoreDTO> listStore(int offset, int rows) {
		List<StoreDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT storeNum, storeName, tel, storeAddress, visible FROM store WHERE visible=1 "
				+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int storeNum = rs.getInt("storeNum");
				String storeName = rs.getString("storeName");
				String tel = rs.getString("tel");
				String storeAddress = rs.getString("storeAddress");
				boolean isVisible = rs.getInt("visible") == 1 ? true : false;
				list.add(new StoreDTO(storeNum, storeName, tel, storeAddress, isVisible));
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

	public int storeCount(String keyword) {
		int count = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(COUNT(storeNum),0) FROM store "
				+ "WHERE visible=1 AND INSTR(storeName, ?) > 0 OR INSTR(storeAddress, ?) > 0 ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public List<StoreDTO> listStore(String keyword, int offset, int rows) {
		List<StoreDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT storeNum, storeName, tel, storeAddress, visible " + "FROM store "
				+ "WHERE visible=1 AND INSTR(storeName, ?) > 0 OR INSTR(storeAddress, ?) > 0 "
				+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, rows);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int storeNum = rs.getInt("storeNum");
				String storeName = rs.getString("storeName");
				String tel = rs.getString("tel");
				String storeAddress = rs.getString("storeAddress");
				boolean isVisible = rs.getInt("visible") == 1 ? true : false;
				list.add(new StoreDTO(storeNum, storeName, tel, storeAddress, isVisible));
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

}
