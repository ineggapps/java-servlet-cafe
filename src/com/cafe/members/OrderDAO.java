package com.cafe.members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
			sql = "SELECT cardNum FROM cards WHERE cardNum = ? AND balance >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNum);
			pstmt.setInt(2, totalPaymentAmount);
			rs = pstmt.executeQuery();
			if(rs.next()==false) {//결제금액이 잔액보다 크다면 쿼리 값이 반환되지 않을 것임
				throw new OrderException("잔액이 부족합니다");
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
			sql = "INSERT INTO order_detail(detailNum, orderNum, unitPrice, quantity, paymentAmount) "
					+ " VALUES(order_detail_seq.NEXTVAL, ?, ?, ?, ?)";
			List<MenuDTO> items = cart.getItems();
			for(MenuDTO item: items) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, orderNum);
				pstmt.setInt(2, item.getPrice());
				pstmt.setInt(3, 1); //일단 1개로 구현
				pstmt.setInt(4, item.getPrice()*1);//단가*수량(1로 고정)
				pstmt.executeUpdate();
				returnPrepResource(pstmt);
			}
			
			conn.commit();
		}catch(OrderException e) {
			throw new OrderException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
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
	
	
	//자원반납 대신하기..
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
