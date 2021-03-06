<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cd" tagdir="/WEB-INF/tags" %>
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
		const balance = ${cardDTO.balance};
		function senario(chk){
			const price = chk.value;
			const after = Number(balance) + Number(price);
			if(after>550000){
				alert("카드 충전금액은 550,000원을 넘길 수 없습니다.");
				chk.checked = false;
				return;
			}
			printBalance(after);
		}
		
		function printBalance(balance){
			if(!balance){
				balance = ${cardDTO.balance};
			}
			document.getElementById('after_balance').innerText =	new Intl.NumberFormat().format(balance);
		}
		
		function submit(){
			
			
			const f = document.chargeForm;
	 		<c:if test="${mode=='register'}">
			if(!confirm("등록하시겠습니까?")){
				return;
			}
			f.action = "<%=cp%>/members/register.do";
			f.submit();
			</c:if>
			<c:if test="${mode=='charge'}">
			f.action = "<%=cp%>/members/charge_ok.do";
			if(!confirm("충전하시겠습니까?")){
				return;
			}
			for(let i=0;i<f.length;i++){
				let type = f[i].getAttribute("type");
				if(type=="radio" && f[i].checked){
					f.submit();
					return;
				}
			}
			
			alert("충전 금액을 선택하세요.");
			</c:if>
			<c:if test="${mode=='close'}">
			if(!confirm("정말 해지하시겠습니까?")){
				return;
			}
			f.action = "<%=cp%>/members/close_ok.do";
			f.submit();
			</c:if>
			
		}
		
    window.onload = function(){
  	  	printBalance();
    }
	</script>    
   
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
  <script type="text/javascript" src="<%=cp %>/resource/js/jquery-3.5.1.min.js"></script>
  <script type="text/javascript" src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
  <script type="text/javascript" src="https:///cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
  <c:if test="${mode=='charge'}">
  <script>
  $(document).ready(function () {
		//충전모드
	  $(".charge-cards").slick({
	    nextArrow: "",
	    prevArrow: "",
	    centerMode: true,
	    arrows: true,
	    focusOnSelect: true,
	    centerPadding: "60px",
	    slidesToShow: 1,
	    variableWidth: true,
	    infinite: true,
	    initialSlide: ${cardChargeIndex},
	    responsive: [
	      {
	        breakpoint: 768,
	        settings: {
	          arrows: false,
	          centerMode: true,
	          centerPadding: "40px",
	          slidesToShow: 3,
	        },
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          arrows: false,
	          centerMode: true,
	          centerPadding: "40px",
	          slidesToShow: 1,
	        },
	      },
	    ],	
	  });
	
	  $(".charge-cards").on("beforeChange", function (event, slick, currentSlide, nextSlide) {
	    const $next = $(this).find("li[data-slick-index=" + nextSlide + "]");
	    const cardNum = $next.attr("data-card-num");
	    const cardName = $next.attr("data-card-name");
	    const cardIdentity = $next.attr("data-card-identity");
	    const cardThumbnail = $next.attr("data-card-thumbnail");
	    const cardBalance = new Number($next.attr("data-card-balance"));
	    //카드 프로필 고치기
	    const $card = $("#card_profile");
	    $card.find(".card_title strong").text(cardName); //카드이름
	    $card.find(".card_id").text(cardIdentity); //카드이름
	    $card.find("img").attr("src", cardThumbnail); //썸네일
	    $card.find(".card_remain strong").text(cardBalance); //잔액
	    //충전 후 잔액 수정
	    const priceChk = $("input[name=price]:checked");
	    let price = 0;
	    if (priceChk.length > 0) {
	      price = new Number(priceChk.eq(0).attr("value"));
	    }
	    const balance = cardBalance + price;
	    $("#after_balance").text(new Intl.NumberFormat().format(balance));
	    //카드 정보 접근
	    $("#controller input[name=cardNum]").attr("value", cardNum);
	  });
  });
  </script>
  </c:if>
  <script type="text/javascript" src="<%=cp %>/resource/js/slick-cards.js"></script>
</head>
  <body>
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
            <form name="chargeForm" method="post">
              <div class="row">
                <div class="row_title">
                	<c:if test="${mode!='close'}">
                    <h3>카드 ${mode=="register"?"등록":"충전"}하기</h3>
                	</c:if>
					<c:if test="${mode=='close'}">
                    <h3>카드 해지하기</h3>
					</c:if>                    
                </div>
                <div class="card_container card_container_full" id="card_profile">
                  <ul>
                    <li>
                      <figure>
                      	<c:if test="${mode=='register'}">
                        <img src="<%=cp%>${modelDTO.thumbnail}" alt="card" />
                      	</c:if>
						<c:if test="${mode!='register' }">
                        <img src="<%=cp%>${cardDTO.thumbnail}" alt="card" />
						</c:if>
                      </figure>
                      <div class="detail">
                        <c:if test="${mode=='register'}"><%--등록모드일 경우 모델이름 --%>
                        <p class="card_title detail">
                          <strong>${modelDTO.modelName}</strong><!-- a href="#" class="modify">수정</a-->
                        </p>
                        <p class="card_remain">${modelDTO.text}</p>
                      	</c:if>
                        <c:if test="${mode!='register'}">
                        <p class="card_title detail">
                          <strong>${cardDTO.cardName}</strong><!-- a href="#" class="modify">수정</a-->
                        </p>
                        <p class="card_id"><cd:card identity="${cardDTO.cardIdentity }"/></p>
                        <p class="card_remain">잔액:&nbsp;<strong><fmt:formatNumber value="${cardDTO.balance}"/></strong>원</p>
                      	</c:if>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <c:if test="${mode=='charge'}">
              <div class="row">
				<ul class="charge-cards">
				<c:forEach var="dto" items="${list}">
				<li data-card-num="${dto.cardNum}" data-card-identity = "${dto.cardIdentity}" data-card-name="${dto.cardName}" data-card-thumbnail="<%=cp%>${dto.thumbnail}" data-card-balance="${dto.balance}"> 
					<div class="card-item">
						<div>
							<figure>
								<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.cardNum}" />
							</figure>
							<ul>
								<li><span class="title">${dto.cardName}</span></li>								
								<li><span><fmt:formatNumber value="${dto.balance}"/>원</span></li>								
							</ul>
						</div>
					</div>
				</li>
				</c:forEach>
               			<c:if test="${fn:length(list)==1}">
				<c:forEach var="dto" items="${list}">
				<li data-card-num="${dto.cardNum}"> 
					<div class="card-item">
						<div>
							<figure>
								<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.cardNum}" />
							</figure>
							<ul>
								<li><span class="title">${dto.cardName}</span></li>								
								<li><span><fmt:formatNumber value="${dto.balance}"/>원</span></li>								
							</ul>
						</div>
					</div>
				</li>
				</c:forEach>
               			</c:if>
				</ul>
              </div>
              </c:if>
              <div class="row">
                <table id="card_charge">
                  <thead>
                    <tr>
                      <td>항목</td>
                      <td>입력</td>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:if test="${mode=='close'}">
                  	<tr>
                  		<td class="col_charge_category">잔액을 이체할 카드</td>
                  		<td class="col_charge_data">
                  			<input type="hidden" name="targetCardNum" id="cardNum" value="${list[0].cardNum}"/>
                  			<c:if test="${fn:length(list)<=0}">이체할 카드가 없어 해지할 수 없습니다.</c:if>
							<ul class="cards close">
							<c:forEach var="dto" items="${list}">
							<li data-card-num="${dto.cardNum}"> 
								<div class="card-item">
									<div>
										<figure>
											<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.cardNum}" />
										</figure>
										<ul>
											<li><span class="title">${dto.cardName}</span></li>								
											<li><span><fmt:formatNumber value="${dto.balance}"/>원</span></li>								
										</ul>
									</div>
								</div>
							</li>
							</c:forEach>
                  			<c:if test="${fn:length(list)==1}">
							<c:forEach var="dto" items="${list}">
							<li data-card-num="${dto.cardNum}"> 
								<div class="card-item">
									<div>
										<figure>
											<img src="<%=cp %>/${dto.thumbnail}" alt="${dto.cardNum}" />
										</figure>
										<ul>
											<li><span class="title">${dto.cardName}</span></li>								
											<li><span><fmt:formatNumber value="${dto.balance}"/>원</span></li>								
										</ul>
									</div>
								</div>
							</li>
							</c:forEach>
                  			</c:if>
							</ul>
						</td>
                  	</tr>
                  	</c:if>
                  	<c:if test="${mode=='register'}">
                  	<tr>
                  		<td class="col_charge_category">카드 이름</td>
                  		<td class="col_charge_data"><input class="text_input" type="text" placeholder="카드 이름" name="cardName" /></td>
                  	</tr>
                  	</c:if>
                 	<c:if test="${mode!='close'}">
                    <tr>
                      <td class="col_charge_category">충전 금액 선택</td>
                      <td class="col_charge_data">
                        <p class="desc">
                          충전 후 총 카드잔액: <strong class="desc_blue" id="after_balance">${cardDTO.balance}</strong>원
                        </p>
                        <ul class="charge_option">
                          <li>
                            <label
                              ><input type="radio" value="100000" name="price" onclick="senario(this)"/><span
                                >10만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="50000" name="price"  onclick="senario(this)" /><span
                                >5만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="30000" name="price" onclick="senario(this)" /><span
                                >3만원</span
                              ></label
                            >
                          </li>
                          <li>
                            <label
                              ><input type="radio" value="10000" name="price" onclick="senario(this)" /><span
                                >1만원</span
                              ></label
                            >
                          </li>
                        </ul>
                        <p class="desc_red desc">
                          ※쿠앤크 커피 온라인 충전은 1만 원 단위로 최대 55만원까지 가능하며, 충전 후
                          합계 잔액이 55만 원을 초과할 수 없습니다.
                        </p>
                      </td>
                    </tr>
                    <tr>
                      <td class="col_charge_category">결제 시점</td>
                      <td class="col_charge_data"><strong class="desc_blue">즉시</strong> 결제</td>
                    </tr>
                    </c:if>
                  </tbody>
                </table>
              </div>
            <div class="row buttons" id="controller">
            <c:if test="${mode=='register'}">
            	<input type="hidden" name="register_step" value="3" />
            	<input type="hidden" name="modelNum" value ="${modelDTO.modelNum}"/>
              <a href="<%=cp %>/members/register.do" class="list_button">목록</a>
            </c:if>
            <c:if test="${mode!='register'}">
            	<input type="hidden" name="cardNum" value="${cardDTO.cardNum}" />
              <a href="<%=cp %>/members/list.do" class="list_button">목록</a>
            </c:if>
            <c:if test="${mode!='close'}">
              <a href="#" class="list_button submit" onclick="submit()">${mode=="register"?"등록":"충전"}하기</a>
             </c:if>
            <c:if test="${mode=='close' and fn:length(list)>0}">
              <a href="#" class="list_button submit" onclick="submit()">해지하기</a>
             </c:if>
            </div>
            </form>
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
