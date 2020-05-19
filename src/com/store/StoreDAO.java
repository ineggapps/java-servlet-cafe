package com.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class StoreDAO {
	public List<StoreDTO> listStore() {
		List<StoreDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT storeNum, storeName, tel, storeAddress, visible FROM store WHERE visible=1";
		;
		try {
			pstmt = conn.prepareStatement(sql);
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
