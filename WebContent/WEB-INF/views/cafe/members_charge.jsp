<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			document.getElementById('after_balance').innerText =	new Intl.NumberFormat().format(after)
			
		}
		
		function submit(){
			const f = document.chargeForm;
			<c:if test="${mode=='register'}">
			f.action = "<%=cp%>/members/register.do";
			</c:if>
			<c:if test="${mode=='charge'}">
			f.action = "<%=cp%>/members/charge_ok.do";
			</c:if>
			f.submit();
		}
	</script>    
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
                  <h3>카드 ${mode=="register"?"등록":"충전"}하기</h3>
                </div>
                <div class="card_container card_container_full">
                  <ul>
                    <li>
                      <figure>
                      	<c:if test="${mode=='register'}">
                        <img src="<%=cp%>${modelDTO.thumbnail}" alt="card" />
                      	</c:if>
						<c:if test="${mode=='charge' }">
                        <img src="<%=cp%>${cardDTO.thumbnail}" alt="card" />
						</c:if>
                      </figure>
                      <div class="detail">
                        <c:if test="${mode=='charge'}">
                        <p class="card_title detail">
                          <strong>${cardDTO.cardName}</strong><a href="#" class="modify">수정</a>
                        </p>
                        <p class="card_id">${cardDTO.cardIdentity}</p>
                        <p class="card_remain">잔액:&nbsp;<strong><fmt:formatNumber value="${cardDTO.balance}"/></strong>원</p>
                      	</c:if>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="row">
                <table id="card_charge">
                  <thead>
                    <tr>
                      <td>항목</td>
                      <td>입력</td>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:if test="${mode=='register'}">
                  	<tr>
                  		<td class="col_charge_category">카드 이름</td>
                  		<td class="col_charge_data"><input class="text_input" type="text" placeholder="카드 이름" name="cardName" /></td>
                  	</tr>
                  	</c:if>
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
                  </tbody>
                </table>
              </div>
            <div class="row buttons">
            <c:if test="${mode=='register'}">
            	<input type="hidden" name="register_step" value="3" />
            	<input type="hidden" name="modelNum" value ="${modelDTO.modelNum}"/>
              <a href="<%=cp %>/members/register.do" class="list_button">목록</a>
            </c:if>
            <c:if test="${mode=='charge'}">
            	<input type="hidden" name="cardNum" value="${cardDTO.cardNum}" />
              <a href="<%=cp %>/members/list.do" class="list_button">목록</a>
            </c:if>
              <a href="#" class="list_button submit" onclick="submit()">${mode=="register"?"등록":"충전"}하기</a>
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
