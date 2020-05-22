<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0" /> -->
    <title>COFFEE</title>
	<link rel="stylesheet" href="<%=cp%>/resource/css/reset.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" />
    <link rel="stylesheet" href="<%=cp%>/resource/css/members.css" />  
  	<script>
  		function addCart(menuNum){
  			alert("주문한 상품이 담겼습니다.");
  			const url = "<%=cp%>/members/order.do?menuNum="+menuNum;
  			location.href = url;
  		}
  		function goToCart(menuNum){
  			alert("주문 내역으로 이동합니다.");
  			const url = "<%=cp%>/members/buy.do?menuNum="+menuNum;
  			location.href = url;
  		}
  		function goToError(){
  			const err = document.getElementById("error");
  			if(err!=null){
  				location.href = "#error";
  			}
  		}
  		function submit(){
  		  const f = document.buy;
          const cards = document.getElementsByName("cardNum");
          for(let i = 0;i<cards.length;i++){
              if(cards[i].checked){
                  f.submit();
                  return;
              }
          }
          alert("결제 수단이 선택되지 않았습니다. 카드를 선택해 주세요");
  		}
  
  	</script>
  </head>
  <body onload="goToError()">
    <div id="wrap">
      <header id="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
           <!-- Content영역 -->
           <div class="banner_visual">
             <h2><span>쿠앤크 멤버스</span></h2>
             <div class="visual_text">
               <span
                 >쉽고 빠른 주문, 편리한 결제와 적립은 기본<br />친구에게 선물까지, 쿠앤크멤버스를
                 경험해보세요.</span
               >
             </div>
		  <jsp:include page="/WEB-INF/views/layout/members_lnb.jsp"/>
           </div>
           <c:if test="${not empty errorMessage }">
		<div class="row_full alert" id="error">
             <div class="row">
             	<div class="box">
             		<figure class="img">
             			<img src="<%=cp %>/resource/images/assets/alert-240.png" alt="alert" />
             		</figure>
             		<div class="msg_content">
					<div class="title">${errorMessage.title}</div>              		
				  	<p class="message">${errorMessage.content}</p>
             		</div>
             	</div>
		  </div>
           </div>
           </c:if>
             <div class="row">
               <div class="row_title">
                 <h3>쿠앤크 오더</h3>
               </div>
             <div class="row">
                 <ul class="tab order">
                   <li ${api=="/order.do"?"class=\"on\"":""}><a href="<%=cp%>/members/order.do">주문하기</a></li>
                   <li ${api=="/buy.do"?"class=\"on\"":""}><a href="<%=cp%>/members/buy.do">결제하기</a></li>
                   <li ${api=="/orderedList.do"?"class=\"on\"":""}><a href="<%=cp%>/members/orderedList.do">주문내역 조회</a></li>
                 </ul>
               </div>
             </div>
             <c:if test="${api=='/order.do'}">
             <div class="row">
               <ul class="shop">
               	<c:forEach var="dto" items="${list}"> 
               		<li>
               			<div class="menu_item" id="item_${dto.menuNum}">
               				<figure class="menu_figure">
               					<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.menuName}" />
               				</figure>
               		 		<div class="menu_content">
               		 			<p>${dto.menuName}</p>
               		 			<div class="menu_item_controller">
					              <a href="#" class="item_button" onclick="addCart(${dto.menuNum})">담기</a>
					              <a href="#" class="item_button submit" onclick="goToCart(${dto.menuNum})">주문</a>
					            </div>
               		 		</div>
               			</div>
               		</li>
               	</c:forEach>
               </ul>
             </div>
             </c:if>
             <c:if test="${api=='/buy.do' and fn:length(cart.items)==0}">
             <div class="row border_box"> 
				<p class="title">아직 주문하지 않았습니다.</p>
             	<a href="<%=cp%>/members/order.do" class="single_button">주문하러 가기</a>
             </div>
             </c:if>
             <c:if test="${api=='/buy.do' and fn:length(cart.items)>0}">
             <div class="row">
               <table class="table" id="buy">
               <thead>
                 <tr class="on">
                   <th class="col_category">상품구분</th>
                   <th class="col_menuName">상품명</th>
                   <th class="col_price">금액</th>
                 </tr>
                 <tr>
               </thead>
               <tbody>
               	<c:forEach var="dto" items="${cart.items}">
               	<tr>
               		<td class="col_category">${dto.value.categoryNum}</td>
               		<td class="col_menuName">
               			<div class="buy_item">
               				<div class="item_image">
               					<img src="<%=cp %>/${dto.value.thumbnail}" alt="${dto.value.menuName}" />
               				</div>
               		 		<div class="item_label">
               		 			<span>${dto.value.menuName} X ${dto.value.quantity}개</span>
               		 		</div>
               			</div>
               		</td>
               		<td class="col_price"><fmt:formatNumber value="${dto.value.price * dto.value.quantity}"/></td>
               	</tr>
               	</c:forEach>
               </tbody>
             </table>
             <div class="total">
              <h3>총 주문금액</h3>
              <span><fmt:formatNumber value="${cart.totalPaymentAmount}"/></span>
             </div>
             <div class="row" id="cart_size">
             	<h4>총 ${fn:length(cart.items)}개의 종류가 담겼습니다.</h4>
             </div>
             <form action="<%=cp%>/members/buy_ok.do" method="post" name="buy">
             <div class="row">
             	<ul class="buy_method" id="cards">
				<c:forEach var="dto" items="${cards}">
				<li>
					<div class="item_card">
						<div>
							<label for="card_${dto.cardNum}">
								<input name="cardNum" id="card_${dto.cardNum}" class="radio_cards" type="radio" value="${dto.cardNum}"/>
								<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.cardNum}" />
							</label>
							<ul>
								<li><span>${dto.cardName}</span></li>								
								<li><span>${dto.balance}</span></li>								
							</ul>
						</div>
					</div>
				</li>
				</c:forEach>
             	</ul>
             </div>
             <div class="row">
              	<a href="#" class="list_button submit" onclick="submit()">결제하기</a>
             </div>
             </form>
             </div>
             </c:if>
             <c:if test="${api=='/orderedList.do'}">
             <div class="row">
             	<p class="title">오늘의 주문 현황</p>
             	<ul class="order_process">
             		<li>
             			<div class="circle">
             				<dl>
             					<dt>결제 완료</dt>
             					<dd>${dashBoardStatusDTO.paymentCount}</dd>
             				</dl>
             			</div>
             		</li>
             		<li>
             			<div class="circle">
             				<dl>
             					<dt>제조 대기</dt>
             					<dd>${dashBoardStatusDTO.beforeMakingCount}</dd>
             				</dl>
             			</div>
             		</li>
             		<li>
             			<div class="circle">
             				<dl>
             					<dt>제조 중</dt>
             					<dd>${dashBoardStatusDTO.makingCount}</dd>
             				</dl>
             			</div>
             		</li>
             		<li>
             			<div class="circle">
             				<dl>
             					<dt>제조 완료</dt> 
             					<dd>${dashBoardStatusDTO.doneCount}</dd>
             				</dl>
             			</div>
             		</li>
             	</ul>
             </div>
             <div class="row">
                <p class="title">주문 내역 조회</p>
                <table class="table" id="ordered_history">
                <thead>
                  <tr>
                    <td class="col_no">No</td>
                    <td class="col_date">날짜</td>
                    <td class="col_content">내역</td>
                    <td class="col_amount">금액</td>
                    <td class="col_status">상태</td>
                  </tr>
                </thead>
                <tbody>
                <c:forEach var="history" items="${orderHistory}" varStatus="status">
                  <tr>
                    <td class="col_no">${status.count}</td>
                    <td class="col_date">${history.orderDate}</td>
                    <td class="col_content">
						<dl id="ordered_detail">
						<c:forEach var="item" items="${history.items}" varStatus="st">
							<dt>${item.menuName}</dt>
							<dd>${item.unitPrice}원 (${item.quantity}개)</dd>
						</c:forEach>
						</dl>
					</td>
                    <td class="col_amount"><fmt:formatNumber value="${history.totalPaymentAmount}"/></td>
                    <td class="col_status">${history.statusName}</td>
                  </tr>
                </c:forEach>
				</tbody>
				</table>             
             </div>
             </c:if>
             <div class="row">
				<p:pager pages="${pages}" data_count="${dataCount}" total_page="${totalPage}" uri="${uri}" query="${query}" current_page="${currentPage}"/>
             </div>
           <!-- Content 영역 끝 -->
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>
