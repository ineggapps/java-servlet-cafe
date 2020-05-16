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
    <link rel="stylesheet" href="<%=cp%>/resource/css/teamenu.css" />
  </head>
  <body>
    <div id="wrap">
      <header id="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
      </header>
      <main id="content">
        <div id="main">
          <article id="main_container">
            <div class="box backgroundcoffee">
              <div class="menufont">음료</div>
              <br /><br /><br />
              <div class="menu">
                <ul>
                  <li><a href="<%=cp%>/menu/coffee.do">커피</a></li>
                  <li><a href="<%=cp%>/menu/ade.do">에이드</a></li>
                  <li><a href="<%=cp%>/menu/bakery.do">베이커리</a></li>
                </ul>
              </div>
            </div>
            <div class="centercontent">
              <div class="sidefont"><a>커피</a>&nbsp;<a>></a>&nbsp;<a>메뉴</a></div>
              <br /><br />
              <div>
                <a>커피 메뉴</a>
              </div>
              <hr />
              <br /><br />
              <div class="picture">
                <ul>
                  <li>
                    <div>
                      <a href="#"><img alt="" src="https://i.imgur.com/DZdmNgR.png" /></a>
                      <p>연유 카페라떼 HOT</p>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%; padding-top: 20px; padding-bottom: 20px;">
                            <p style="text-align: center;"><span>연유 카페라떼 HOT</span></p>
                          </li>
                          <li style="border-top: 2px solid black; padding-top: 20px;">
                            베트남풍 연유의 달콤한 막과 에스프레소가 절묘하게 어우러져 누구나 쉽게
                            즐길 수 있는 부드러운 커피음료
                          </li>
                          <li></li>
                        </ul>
                      </div>
                    </div>
                  </li>
                  <li>
                    <div>
                      <a href="#"><img alt="" src="https://i.imgur.com/WE351yY.png" /></a>
                      <p>아메리카노</p>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%;">
                            <p style="text-align: center; padding-top: 20px; padding-bottom: 20px;">
                              <span>아메리카노</span>
                            </p>
                          </li>
                          <li style="border-top: 2px solid black;">
                            블랜딩을 통해 커피의 깊은 단맛과 바디감, 균형잡힌 밸런스를 느낄 수 있는
                            시원한 아메리카노
                          </li>
                        </ul>
                      </div>
                    </div>
                  </li>
                  <li>
                    <div>
                      <a href="#"><img alt="" src="https://i.imgur.com/Y4XAOvK.png" /></a>
                      <p>크림 밀크티</p>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%; padding-top: 20px; padding-bottom: 20px;">
                            <p style="text-align: center;"><span>크림 밀크티</span></p>
                          </li>
                          <li
                            style="
                              border-top: 1px solid gray;
                              padding-top: 20px;
                              text-align: center;
                            "
                          >
                            베르가못 향과 우롱티의 벨런스를 잡아 풍부하고 깊은 맛과 크림의
                            부드러움을 함께 느끼고, 타피오카 펄의 쫄깃한 식감까지 즐길 수 있는 음료
                          </li>
                        </ul>
                      </div>
                    </div>
                  </li>
                  <li>
                    <div>
                      <a href="#"><img alt="" src="https://i.imgur.com/gZdoZnV.png" /></a>
                      <p>딸기라떼</p>
                      <div class="detail">
                        <ul>
                          <li style="width: 100%; padding-top: 20px; padding-bottom: 20px;">
                            <p style="text-align: center;"><span>딸기라떼</span></p>
                          </li>
                          <li style="border-top: 1px solid gray; padding-top: 20px;">
                            달콤하고 상큼한 국내산 딸기 본연의 자연스러운 풍미에 부드러운 우유가
                            더해진 딸기 음료
                          </li>
                        </ul>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <br />
              <hr />
              <br /><br />
            </div>
          </article>
        </div>
      </main>
      <footer id="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
      </footer>
    </div>
  </body>
</html>
