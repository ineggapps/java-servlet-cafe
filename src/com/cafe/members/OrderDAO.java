package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cafe.menu.MenuDTO;
import com.util.DBCPConn;

public class OrderDAO {
	//OrderHistoryDTO (주문내역서) 먼저 추가해야 함.
	//OrderDetailDTO (주문세부사항)
	
	public int addOrderHistory(SessionCart cart, int userNum, int cardNum) throws OrderException{
		/*
		 결제 과정
		 1. 선택한 결제 카드에 잔액이 결제금액 이상인지 확인
		 2. 선택한 결제 카드가 실제 소유주 것이 맞는지 확인 (security check)
		 * */
		int result = 0;
		int totalPaymentAmount = cart.getTotalPaymentAmount();
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			conn.setAutoCommit(false);

			//1. 결제카드 잔액 결제금액 이상인지 확인하기
			sql = "SELECT cardNum FROM cards WHERE cardNum = ? AND balance >= ? AND userNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, totalPaymentAmount);
			pstmt.setInt(3, userNum); //게다가 카드 소유주까지 조회하기
			rs = pstmt.executeQuery();
			if(rs.next()==false) {//결제금액이 잔액보다 크다면 쿼리 값이 반환되지 않을 것임
				throw new OrderException("잔액이 부족합니다.");
				//그러나 소유주 체크 AND userNum = ? 까지 했으므로 남의 카드번호로 요청한 경우에도 이곳에서 걸린다.
				//다만 특수한 상황이므로 오류를 세분화하여 나누지 않았음 .(DB요청 최소화)
			}
			returnDBResources(pstmt, rs);
			
			//2. 주문내역(order_history)에 먼저 등록하기
			//2-1. 주문내역 orderNum 일련번호 미리 가져오기
			sql = "SELECT order_history_seq.NEXTVAL FROM DUAL";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()==false) {
				throw new OrderException("시퀀스 다음 번호 가져오기 실패 (order_history_seq.NEXTVAL)");
			}
			
			int orderNum = rs.getInt(1);
			returnDBResources(pstmt, rs);
			
			//2-2.
			//★ 주의.. 먼저 order_status에 1값이 먼저 등록되어 있어야 함!!!
			final int statusNum = 1;//결제 완료
			
			sql = "INSERT INTO order_history(orderNum, totalPaymentAmount, statusNum, userNum, cardNum) "
					+ " VALUES(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderNum);
			pstmt.setInt(2, totalPaymentAmount);
			pstmt.setInt(3, statusNum);
			pstmt.setInt(4, userNum);
			pstmt.setInt(5, cardNum);
			result = pstmt.executeUpdate();
			
			if(result==0) {
				throw new OrderException("주문내역 생성에 실패하였습니다.");
			}
			//자원 반납
			returnDBResources(pstmt, rs);
			
			
			//3. 주문 상세 만들기
			sql = "INSERT INTO order_detail(detailNum, orderNum, menuNum, unitPrice, quantity, paymentAmount) "
					+ " VALUES(order_detail_seq.NEXTVAL, ?, ?, ?, ?, ?)";
			Map<Integer, MenuDTO> items = cart.getItems();
			for(int menuNum: items.keySet()) {
				MenuDTO item = items.get(menuNum);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, orderNum);
				pstmt.setInt(2, item.getMenuNum());
				pstmt.setInt(3, item.getPrice());
				pstmt.setInt(4, item.getQuantity()); //일단 1개로 구현
				pstmt.setInt(5, item.getPrice()*item.getQuantity());//단가*수량(1로 고정)
				pstmt.executeUpdate();
				returnPrepResource(pstmt);
			}
			
			conn.commit();
		}catch(OrderException e) {
			try {
				if(conn!=null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
			}
			throw new OrderException(e);
		}
		catch (Exception e) {
			try {
				if(conn!=null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null && !conn.isClosed()) {
					conn.setAutoCommit(true);
				}
			} catch (Exception e2) {
			}
			returnDBResources(pstmt, rs);
			//연결 닫기
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return result;
	}
	
	//카드번호로 내역 조회하기
	public List<OrderHistoryDTO> listOrderHistoryByCardNum(int cardNum, int userNum) {
		List<OrderHistoryDTO> list = new ArrayList<>();
		List<OrderDetailDTO> items;
		Connection conn = DBCPConn.getConnection();
		//OrderHistory 조작하는 pstmt, rs
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//OrderDetail 조작하는 pstmt, rs
		PreparedStatement pstmtSub = null;
		ResultSet rsSub = null;
		String sql;
		try {
			sql = "SELECT orderNum, totalPaymentAmount, storeNum, oh.statusNum, statusName, oh.userNum, userName, cardNum, "
					+ "TO_CHAR(order_date,'YYYY-MM-DD HH24:MI:SS') order_date, cancelNum "
					+ " FROM order_history oh "
					+ " JOIN  order_status os ON oh.statusNum = os.statusNum "
					+ "JOIN member m ON oh.userNum = m.userNum"
					+ "WHERE cardNum = ? AND userNum = ? "
					+ "ORDER BY orderNum DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, userNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderHistoryDTO historyDTO = new OrderHistoryDTO();
				int orderNum = rs.getInt("orderNum");
				historyDTO.setOrderNum(orderNum);
				historyDTO.setTotalPaymentAmount(rs.getInt("totalPaymentAmount"));
				historyDTO.setStoreNum(rs.getInt("storeNum"));
				historyDTO.setStatusNum(rs.getInt("statusNum"));
				historyDTO.setStatusName(rs.getString("statusName"));
				historyDTO.setUserNum(userNum);
				historyDTO.setNickname(rs.getString("nickname"));
				historyDTO.setCardNum(cardNum);
				historyDTO.setOrderDate(rs.getString("order_date"));
				historyDTO.setCancelNum(rs.getInt("cancelNum"));
				sql = "SELECT detailNum, orderNum, od.menuNum, menuName, unitPrice, quantity, paymentAmount "
						+ " FROM order_detail od "
						+ " JOIN menu mn ON od.menuNum = mn.menuNum "
						+ " WHERE orderNum = ?"
						+ "ORDER BY detailNum, orderNum DESC";
				pstmtSub = conn.prepareStatement(sql);
				pstmtSub.setInt(1, orderNum);
				rsSub = pstmtSub.executeQuery();
				items = new ArrayList<>();
				while(rsSub.next()) {
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
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
			}
		}
		
		return list;
	}
	
	//회원번호로 내역 조회하기
	public List<OrderHistoryDTO> listOrderHistoryByUserNum(int userNum) {
		List<OrderHistoryDTO> list = new ArrayList<>();
		List<OrderDetailDTO> items;
		Connection conn = DBCPConn.getConnection();
		//OrderHistory 조작하는 pstmt, rs
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//OrderDetail 조작하는 pstmt, rs
		PreparedStatement pstmtSub = null;
		ResultSet rsSub = null;
		String sql;
		try {
			sql = "SELECT orderNum, totalPaymentAmount, storeNum, oh.statusNum, statusName, userNum, cardNum, "
					+ "TO_CHAR(order_date,'YYYY-MM-DD HH24:MI:SS') order_date, cancelNum "
					+ " FROM order_history oh "
					+ " JOIN  order_status os ON oh.statusNum = os.statusNum "
					+ " WHERE userNum = ?"
					+ " ORDER BY orderNum DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderHistoryDTO historyDTO = new OrderHistoryDTO();
				int orderNum = rs.getInt("orderNum");
				historyDTO.setOrderNum(orderNum);
				historyDTO.setTotalPaymentAmount(rs.getInt("totalPaymentAmount"));
				historyDTO.setStoreNum(rs.getInt("storeNum"));
				historyDTO.setStatusNum(rs.getInt("statusNum"));
				historyDTO.setStatusName(rs.getString("statusName"));
				historyDTO.setUserNum(userNum);
				historyDTO.setCardNum(rs.getInt("cardNum"));
				historyDTO.setOrderDate(rs.getString("order_date"));
				historyDTO.setCancelNum(rs.getInt("cancelNum"));
				sql = "SELECT detailNum, orderNum, od.menuNum, menuName, unitPrice, quantity, paymentAmount "
						+ " FROM order_detail od "
						+ " JOIN menu mn ON od.menuNum = mn.menuNum "
						+ " WHERE orderNum = ?";
				pstmtSub = conn.prepareStatement(sql);
				pstmtSub.setInt(1, orderNum);
				rsSub = pstmtSub.executeQuery();
				items = new ArrayList<>();
				while(rsSub.next()) {
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
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
			}
		}
		
		return list;
	}
	
	//////////////////////////////////////////////자원반납 대신하기..
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
