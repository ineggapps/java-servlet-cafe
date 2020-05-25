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
    <link rel="stylesheet" href="<%=cp%>/resource/css/authentication.css" />
    <script type="text/javascript" src="<%=cp %>/resource/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript">
    	
		function checkSubmit(){
			const f = document.joinForm;
			
			if(!f.agreement.checked){
				alert("약관에 동의해야 가입이 가능합니다.");
				f.agreement.focus();
				return false;
			}
			
			if(!f.email1.value || !f.email2.value){
				alert("이메일 주소를 입력하세요.")
				f.email1.focus();
				return false;
			}
			
			if(!f.userId.value){
				alert("아이디를 입력하세요");
				f.userId.focus();
				return false;
			}
			
			if(!f.userName.value){
				alert("이름을 입력하세요");
				f.userName.focus();
				return false;
			}
			
			if(!f.nickname.value){
				alert("별명을 입력하세요.");
				f.nickname.focus();
				return false;
			}
			
			if(!f.phone.value){
				alert("휴대폰 번호를 입력하세요.");
				f.phone.focus();
				return false;
			}
			
			
			if(!f.userPwd.value){
				alert("비밀번호를 입력하세요.");
				f.userPwd.focus();
				return false;
			}
			
			
			if(!f.userPwdConfirm.value){
				alert("비밀번호 확인란을 입력하세요.");
				f.userPwdConfirm.focus();
				return false;
			}
			
			if(f.userPwd.value != f.userPwdConfirm.value){
				alert("비밀번호 항목이 일치하지 않습니다.");
				f.userPwd.focus();
				return false;
			}
			
    		return true;
    	}
    	
		function changeDomain(){
			const f = document.joinForm;
			f.email2.value = f.selectEmail.value;
		}
		
	    function autoFormatPhone(){
			const f = document.joinForm;
			f.phone.value = f.phone.value.replace(/\-/gi,"");
		}
	    
    	//이하 jQuery
		$(function(){
			$(".join_form input[type=text]").focus(function(){
				$(this).next("p").removeClass("hidden");
			});
			
			$(".join_form input[type=text]").blur(function(){
				$(this).next("p").addClass("hidden");
			});
			
		});
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
            <div class="banner_visual join">
              <div class="row join">
                <h2><span>회원가입</span></h2>
                <p class="welcome"><span>Join us, Cookie&amp;Cream Coffee</span></p>
                <p><span>쿠앤크 멤버스 가입을 위한 약관 동의 및 본인 인증단계입니다.</span></p>
              </div>
            </div>
            <div class="row">
              <form action="<%=cp%>/auth/${mode }_ok.do" method="post" onsubmit="return checkSubmit()" name="joinForm">
                <div class="joinbox">
                  <div class="email_wrap component_wrap">
                    <h3>이메일을 입력해주세요.</h3>
                    <p class="join_text">이메일은 멤버스 로그인 시 아이디로 사용됩니다.</p>

                    <ul class="email">
                      <li><input type="text" class="email id" name="email1"/></li>
                      <li>@</li>
                      <li>
                      	<input type="text" class="email domain" name="email2"/>
                      </li>
                      <li>
                        <select name="selectEmail" onchange="changeDomain()">
                          <option value="">직접입력</option>
                          <option value="naver.com">naver.com</option>
                          <option value="daum.net">daum.net</option>
                          <option value="nate.com">nate.com</option>
                          <option value="hanmail.net">hanmail.net</option>
                          <option value="hotmail.com">hotmail.com</option>
                          <option value="yahoo.co.kr">yahoo.co.kr</option>
                          <option value="korea.com">korea.com</option>
                          <option value="gmail.com">gmail.com</option>
                          <option value="empal.com">empal.com</option>
                          <option value="freechal.com">freechal.com</option>
                          <option value="dreamwiz.com">dreamwiz.com</option>
                          <option value="netian.com">netian.com</option>
                          <option value="chol.net">chol.net</option>
                        </select>
                      </li>
                    </ul>
                  </div>
                  <div class="component_wrap">
                    <h3>서비스 이용약관</h3>
                    <div class="agreement_box">
                      <div class="join_item_title">
                        <h4>
                          쿠앤크 멤버스 서비스 이용약관 동의 <span class="color">(필수)</span>
                        </h4>
                        <input type="checkbox" name="agreement" id="agreement"/>
                      </div>
                      <div class="agreement_content_box">
                        <p>
                          제1조(목적) 이 약관은 OO 회사(전자상거래 사업자)가 운영하는 OO 사이버
                          몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라
                          한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을
                          규정함을 목적으로 합니다. ※「PC통신, 무선 등을 이용하는 전자상거래에
                          대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
                        </p>
                      </div>
                    </div>
                    <%--div class="agreement_box">
                      <div class="join_item_title">
                        <h4>개인정보 수집 및 이용 동의 <span class="color">(필수)</span></h4>
                        <input type="checkbox" />
                      </div>
                      <div class="agreement_content_box">
                        <p>
                          제1조(목적) 이 약관은 OO 회사(전자상거래 사업자)가 운영하는 OO 사이버
                          몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라
                          한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을
                          규정함을 목적으로 합니다. ※「PC통신, 무선 등을 이용하는 전자상거래에
                          대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
                        </p>
                      </div>
                    </div>
                    <div class="agreement_box">
                      <div class="join_item_title">
                        <h4>위치기반서비스 이용약관 동의 <span class="color">(필수)</span></h4>
                        <input type="checkbox" />
                      </div>
                      <div class="agreement_content_box">
                        <p>
                          제1조(목적) 이 약관은 OO 회사(전자상거래 사업자)가 운영하는 OO 사이버
                          몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라
                          한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을
                          규정함을 목적으로 합니다. ※「PC통신, 무선 등을 이용하는 전자상거래에
                          대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
                        </p>
                      </div>
                    </div>
                    <div class="agreement_box">
                      <div class="join_item_title">
                        <h4>마케팅 활용 수신 동의 <span class="color">(필수)</span></h4>
                        <input type="checkbox" />
                      </div>
                      <div class="agreement_content_box">
                        <p>
                          제1조(목적) 이 약관은 OO 회사(전자상거래 사업자)가 운영하는 OO 사이버
                          몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라
                          한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을
                          규정함을 목적으로 합니다. ※「PC통신, 무선 등을 이용하는 전자상거래에
                          대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
                        </p>
                      </div>
                    </div>
                    <div class="agreement_box">
                      <div class="join_item_title">
                        <h4>광고성 정보 수신 동의 <span class="color">(필수)</span></h4>
                        <input type="checkbox" />
                      </div>
                      <div class="agreement_content_box">
                        <p>
                          제1조(목적) 이 약관은 OO 회사(전자상거래 사업자)가 운영하는 OO 사이버
                          몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라
                          한다)를 이용함에 있어 사이버 몰과 이용자의 권리․의무 및 책임사항을
                          규정함을 목적으로 합니다. ※「PC통신, 무선 등을 이용하는 전자상거래에
                          대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
                        </p>
                      </div>
                    </div--%>
                  </div>

                  <div class="join_form">
                    <div class="component_wrap">
                      <div class="join_item_title">
                        <h3>회원정보입력</h3>
                      </div>
                      <div class="join_item"><strong>아이디</strong> <input type="text" value="${authDTO.userId}" name="userId"/>
						<p class="desc hidden">
                         	아이디가 중복되면 안 됩니다.
                        </p>
                      </div>
                      
                      <div class="join_item"><strong>이름</strong> <input type="text"  value="${authDTO.userName }" name="userName"/>
                      	<p class="desc hidden">
                        	 이름을 입력하세요
                      	</p>
                      </div>
                      <div class="join_item">
                        <strong>별명</strong> <input type="text" name="nickname"/>
                        <p class="desc always">※ 욕설 등 부적절한 단어는 제한을 받습니다.</p>
                      </div>
                      <div class="join_item"><strong>휴대폰</strong> <input type="text" maxlength="11" name="phone" onkeyup="autoFormatPhone()" onkeydown="autoFormatPhone()"/>
                      <p class="desc hidden">
                         	휴대폰 번호를 입력하세요
                      </p>
                      </div>
                      <div class="join_item">
                        <strong>비밀번호</strong> <input type="password" name="userPwd"/>
                        <p class="desc hidden">
                          ※ 안전한 비밀번호를 위해 숫자, 문자 조합하여 10~16자 이상으로
                          입력해주세요.
                        </p>
                      </div>
                      <div class="join_item">
                        <strong>비밀번호 확인</strong> <input type="password" name="userPwdConfirm"/>
                        <p class="desc hidden">
                          비밀번호를 한 번 더 입력하세요
                        </p>
                      </div>
                    </div>
                    <div class="join_item_title submit">
                      <button type="submit" name="btn" class="navy_button">가입하기</button>
                    </div>
                  </div>
                </div>
              </form>
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
