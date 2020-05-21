package com.cafe.admin.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cafe.members.OrderDetailDTO;
import com.cafe.members.OrderHistoryDTO;
import com.util.DBCPConn;

public class AdminOrderDAO {
	public static final int STATUS_PAYMENT = 1;
	public static final int STATUS_BEFORE_MAKING = 2;
	public static final int STATUS_MAKING = 3;
	public static final int STATUS_DONE = 4;
	public static final int STATUS[] = { STATUS_PAYMENT, STATUS_BEFORE_MAKING, STATUS_MAKING, STATUS_DONE };

	/**
	 * 당일 단계별 (1: 결제완료, 2: 제조 대기, 3: 제조 중, 4: 제조 완료) 건수
	 * 
	 * @return DashBoardStatusDTO 자료형
	 */
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

	/**
	 * 오늘의 판매 현황 (베스트셀러 메뉴명, 썸네일, 수량 그리고 모든 품목 매출액)
	 * 
	 * @return
	 */
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

	/**
	 * 단계별 현황
	 * 
	 * @param statusNum (1: 결제완료, 2: 제조 대기, 3: 제조 중, 4: 제조 완료)
	 * @return
	 */

	public List<OrderHistoryDTO> listOrderHistoryByUserNum(int statusNum) {
		List<OrderHistoryDTO> list = new ArrayList<>();
		List<OrderDetailDTO> items;
		Connection conn = DBCPConn.getConnection();
		// OrderHistory 조작하는 pstmt, rs
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// OrderDetail 조작하는 pstmt, rs
		PreparedStatement pstmtSub = null;
		ResultSet rsSub = null;
		String sql;
		try {
			sql = "SELECT orderNum, totalPaymentAmount, storeNum, oh.statusNum, statusName, oh.userNum, nickname, cardNum, "
					+ "TO_CHAR(order_date,'YYYY-MM-DD HH24:MI:SS') order_date, cancelNum " 
					+ " FROM order_history oh "
					+ " JOIN  order_status os ON oh.statusNum = os.statusNum "
					+ " JOIN member m ON oh.userNum = m.userNum " 
					+ " WHERE oh.statusNum = ?"
					+ " ORDER BY orderNum DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, statusNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderHistoryDTO historyDTO = new OrderHistoryDTO();
				int orderNum = rs.getInt("orderNum");
				historyDTO.setOrderNum(orderNum);
				historyDTO.setTotalPaymentAmount(rs.getInt("totalPaymentAmount"));
				historyDTO.setStoreNum(rs.getInt("storeNum"));
				historyDTO.setStatusNum(statusNum);
				historyDTO.setStatusName(rs.getString("statusName"));
				historyDTO.setUserNum(rs.getInt("userNum"));
				historyDTO.setNickname(rs.getString("nickname"));
				historyDTO.setCardNum(rs.getInt("cardNum"));
				historyDTO.setOrderDate(rs.getString("order_date"));
				historyDTO.setCancelNum(rs.getInt("cancelNum"));
				sql = "SELECT detailNum, orderNum, od.menuNum, menuName, unitPrice, quantity, paymentAmount "
						+ " FROM order_detail od " + " JOIN menu mn ON od.menuNum = mn.menuNum "
						+ " WHERE orderNum = ?";
				pstmtSub = conn.prepareStatement(sql);
				pstmtSub.setInt(1, orderNum);
				rsSub = pstmtSub.executeQuery();
				items = new ArrayList<>();
				while (rsSub.next()) {
					OrderDetailDTO detailDTO = new OrderDetailDTO();
					detailDTO.setDetailNum(rsSub.getInt("detailNum"));
					detailDTO.setOrderNum(rsSub.getInt("orderNum"));
					detailDTO.setMenuNum(rsSub.getInt("menuNum"));
					detailDTO.setMenuName(rsSub.getString("menuName"));
					detailDTO.setUnitPrice(rsSub.getInt("unitPrice"));
					detailDTO.setQuantity(rsSub.getInt("quantity"));
					detailDTO.setPaymentAmount(rsSub.getInt("paymentAmount"));
					items.add(detailDTO);
				}
				historyDTO.setItems(items);
				list.add(historyDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnDBResources(pstmt, rs);
			returnDBResources(pstmtSub, rsSub);
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
			}
		}

		return list;
	}

	////////////////////////////////////////////// 자원반납 대신하기..
	public void returnDBResources(PreparedStatement pstmt, ResultSet rs) {
		returnResultSetResource(rs);
		returnPrepResource(pstmt);
	}

	public void returnPrepResource(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (Exception e) {
		}
		pstmt = null;
	}

	public void returnResultSetResource(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
		rs = null;
	}
}
