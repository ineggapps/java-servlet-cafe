$(document).ready(function () {
  $(".cards").slick({
    nextArrow: "",
    prevArrow: "",
    centerMode: true,
    arrows: true,
    focusOnSelect: true,
    centerPadding: "60px",
    slidesToShow: 1,
    variableWidth: true,
    infinite: true,
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

  $(".cards").on("beforeChange", function (event, slick, currentSlide, nextSlide) {
    var $next = $(this).find("li[data-slick-index=" + nextSlide + "]");
    var cardNum = $next.attr("data-card-num");
    $("#cardNum").attr("value", cardNum);
  });

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
    console.log(cardName + cardThumbnail);
    //충전 후 잔액 수정
    const priceChk = $("input[name=price]:checked");
    let price = 0;
    if(priceChk.length>0){
    	price = new Number(priceChk.eq(0).attr("value"));
    }
    const balance = cardBalance + price;
    $("#after_balance").text(new Intl.NumberFormat().format(balance));
    //카드 정보 접근
    $("#controller input[name=cardNum]").attr("value", cardNum);
  });
});
