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
});
