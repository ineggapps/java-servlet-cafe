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
	public static final String STATUS_NAME[] = { "결제 완료", "제조 대기", "제조 중", "제조 완료" };// 불필요한 DB접속 방지 위해서.. (어차피 고정된 값으로
																						// 쓸 거니까)

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
//		String subSql = "SELECT () status1, (SELECT Count(ordernum) FROM order_history WHERE statusnum = ? AND To_char(order_date, 'YYYY-MM-DD') = To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL) status2, (SELECT Count(ordernum) FROM order_history WHERE statusnum = ? AND To_char(order_date, 'YYYY-MM-DD') = To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL) status3, (SELECT Count(ordernum) FROM order_history WHERE statusnum = ? AND To_char(order_date, 'YYYY-MM-DD') = To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL) status4 FROM dual";
		String subSql = "SELECT Count(ordernum) FROM order_history WHERE statusnum = ? AND To_char(order_date, 'YYYY-MM-DD') = To_char(sysdate, 'YYYY-MM-DD') AND cancelNum IS NULL";
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
//			System.out.println(sql.toString());
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
			DBCPConn.close(conn);
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

		String sql = "select * from( SELECT * FROM( SELECT rownum rnum, menuName todayMenuName, quantity, thumbnail FROM ( SELECT menuName, od.menuNum, thumbnail, SUM(quantity) quantity from order_detail od JOIN order_history oh ON od.orderNum = oh.orderNum JOIN menu mn ON od.menuNum = mn.menuNum WHERE TO_CHAR(order_date,'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND cancelNum IS NULL group by (od.menuNum, menuName, thumbnail) ORDER BY quantity DESC)) WHERE rnum=1 ), (SELECT SUM(unitPrice*quantity) todayTotalSales FROM order_detail od JOIN order_history oh ON od.orderNum = oh.orderNum WHERE TO_CHAR(order_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND cancelNum IS NULL)";
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
			DBCPConn.close(conn);
		}

		return dto;
	}

	/***
	 * 
	 * @param statusNum
	 * @return 상태번호별(취소건 제외) 데이터 목록 개수 반환
	 */
	public int countOrderHistory(int statusNum) {
		int count = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT NVL(COUNT(orderNum),0) FROM order_history WHERE statusNum = ? AND cancelNum IS NULL ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, statusNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			returnDBResources(pstmt, rs);
			DBCPConn.close(conn);
		}
		return count;
	}
	
	
	/**
	 * 단계별 현황
	 * 
	 * @param statusNum (1: 결제완료, 2: 제조 대기, 3: 제조 중, 4: 제조 완료)
	 * @return
	 */

	public List<OrderHistoryDTO> listOrderHistory(int statusNum, int offset, int rows) {
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
			// 일반 구매내역
			sql = "SELECT orderNum, totalPaymentAmount, storeNum, oh.statusNum, statusName, oh.userNum, nickname, cardNum, "
					+ "TO_CHAR(order_date,'YYYY-MM-DD HH24:MI:SS') order_date, cancelNum " + " FROM order_history oh "
					+ " JOIN  order_status os ON oh.statusNum = os.statusNum "
					+ " JOIN member m ON oh.userNum = m.userNum " + " WHERE oh.statusNum = ? AND cancelNum IS NULL "
					+ " ORDER BY orderNum DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, statusNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
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
			DBCPConn.close(conn);
		}

		return list;
	}
	
	public List<OrderHistoryDTO> listCancelOrderHistory() {
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
			// 일반 구매내역
			sql = "SELECT orderNum, totalPaymentAmount, storeNum, oh.statusNum, statusName, oh.userNum, nickname, cardNum, "
					+ "TO_CHAR(order_date,'YYYY-MM-DD HH24:MI:SS') order_date, cancelNum " + " FROM order_history oh "
					+ " JOIN  order_status os ON oh.statusNum = os.statusNum "
					+ " JOIN member m ON oh.userNum = m.userNum "
					+ " WHERE cancelNum IS NOT NULL "
					+ " ORDER BY orderNum DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderHistoryDTO historyDTO = new OrderHistoryDTO();
				int orderNum = rs.getInt("orderNum");
				historyDTO.setOrderNum(orderNum);
				historyDTO.setTotalPaymentAmount(rs.getInt("totalPaymentAmount"));
				historyDTO.setStoreNum(rs.getInt("storeNum"));
				historyDTO.setStatusNum(rs.getInt("statusNum"));//결제 취소됐으니까 상태번호는 그다지 중요하지 않음.
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
			DBCPConn.close(conn);
		}
		
		return list;
	}

	// 단계를 올림..
	public int stepUpOrderStatus(int orderNum) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE order_history SET statusNum = statusNum + 1 "
				+ "WHERE orderNum=? AND statusNum < ? AND cancelNum IS NULL";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			pstmt.setInt(2, STATUS.length);
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
			DBCPConn.close(conn);

		}
		return result;
	}

	// 주문취소하기
	public int insertCancelOrder(int orderNum) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cardNum = 0;
		int totalPaymentAmount = 0;
		String sql;

		try {
			conn.setAutoCommit(false);
			// #1. 기존 주문내역에서 cardNum 찾기
			sql = "SELECT cardNum, totalPaymentAmount FROM order_history WHERE orderNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cardNum = rs.getInt(1);
				totalPaymentAmount = rs.getInt(2);
			} else {
				throw new Exception("결제 내역이 없습니다.");
			}
			try {
				returnDBResources(pstmt, rs);
			} catch (Exception e) {
			}
			// #2. 취소내역 번호 가져오기
			int cancelNum = 0;
			sql = "SELECT order_cancel_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cancelNum = rs.getInt(1);
			}
			returnDBResources(pstmt, rs);

			// #3. 취소내역 추가하기
			sql = "INSERT INTO order_cancel(cancelNum, orderNum, cardNum, paymentAmount) " + "VALUES (" + cancelNum
					+ ", ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			pstmt.setInt(2, cardNum);
			pstmt.setInt(3, totalPaymentAmount);// 부분취소할 수도 있으므로. 일단은 전체 취소만 가능하도록 설정
			result = pstmt.executeUpdate();

			returnDBResources(pstmt, rs);
			// #4. 기존 order_history에 취소번호 추가하기
			sql = "UPDATE order_history SET cancelNum=?, statusNum=? WHERE orderNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cancelNum);
			pstmt.setInt(2, -1);
			pstmt.setInt(3, orderNum);
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
			returnDBResources(pstmt, rs);
			DBCPConn.close(conn);
		}
		return result;
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
