package com.cafe.admin.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBCPConn;

public class AdminOrderDAO {
	public static final int STATUS_PAYMENT_COUNT = 1;
	public static final int STATUS_BEFORE_MAKING_COUNT = 2;
	public static final int STATUS_MAKING_COUNT = 3;
	public static final int STATUS_DONE_COUNT = 4;
	public static final int STATUS[] = { STATUS_PAYMENT_COUNT, STATUS_BEFORE_MAKING_COUNT, STATUS_MAKING_COUNT,
			STATUS_DONE_COUNT };

	public DashBoardStatusDTO getTodayDashBoardStatus() {
		DashBoardStatusDTO dto = new DashBoardStatusDTO();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String subSql = "SELECT COUNT(orderNum) FROM order_history WHERE statusNum=? AND TO_CHAR(order_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD')";
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		for (int i = 1; i <= STATUS.length; i++) {
			sql.append("(" + subSql + ") status" + i);
			if (i != STATUS.length) {
				sql.append(",");
			}
		}
		sql.append(" FROM dual");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			for (int i = 1; i <= STATUS.length; i++) {
				pstmt.setInt(i, STATUS[i - 1]);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setPaymentCount(rs.getInt(1));
				dto.setBeforeMakingCount(rs.getInt(2));
				dto.setMakingCount(rs.getInt(3));
				dto.setDoneCount(rs.getInt(4));
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

	public TodayStatusDTO getTodayStatus() {
		TodayStatusDTO dto = new TodayStatusDTO();

		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from( SELECT * FROM( SELECT rownum rnum, menuName todayMenuName, quantity, thumbnail FROM ( SELECT menuName, od.menuNum, thumbnail, SUM(quantity) quantity from order_detail od JOIN order_history oh ON od.orderNum = oh.orderNum JOIN menu mn ON od.menuNum = mn.menuNum WHERE TO_CHAR(order_date,'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD') group by (od.menuNum, menuName, thumbnail) ORDER BY quantity DESC)) WHERE rnum=1 ), (SELECT SUM(unitPrice*quantity) todayTotalSales FROM order_detail od JOIN order_history oh ON od.orderNum = oh.orderNum WHERE TO_CHAR(order_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD'))";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String todayMenuName = rs.getString("todayMenuName");
				if (todayMenuName == null || todayMenuName.length() == 0) {
					todayMenuName = "-";
				}
				int todayTotalSales = rs.getInt("todayTotalSales");
				dto.setTodayMenuName(todayMenuName);
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setTodayTotalSales(todayTotalSales);
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
