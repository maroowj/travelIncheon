




/*셀렉트 박스*/
let select_title = $(".selectBox > button"),
    select_arrow = $(".historyList > span.icoArrow");

select_title.click(function () {
    //    $(this).parent('.selectBox').toggleClass("active");
    if ($('.selectBox').hasClass('active')) {
        $('.selectBox').removeClass('active');
    } else {
        $(this).parent('.selectBox').addClass("active");
    }
});

select_arrow.click(function () {
    $(this).parent('.selectBox').toggleClass("active");
});


$(".optionList > li").on('click', function () {
    let li_value = $(this).text();
    $(this).parent(".optionList").siblings('.label').text(li_value);
    $(this).parents('.selectBox').removeClass("active");
});
$("body").click(function (e) {
    if ($(".selectBox").hasClass("active")) {
        if (!$(".selectBox").has(e.target).length) {
            $(".selectBox").removeClass("active");
        };
    }
});






/*모달*/
$('.close-btn, .modal-bg').click(function () {
    $('.modal').css('display', 'none');
    event.preventDefault();
});


/*header 2depth*/
$('.headerWrap').mouseenter(function(){
    $(this).addClass('hover');

});

$('.headerWrap').mouseleave(function(){
    $(this).removeClass('hover');

});


/*모바일 메뉴*/
$('.m-menuWrap .menuList > li > a.hasDep2').click(function(){
    $(this).toggleClass('actived');
    $(this).siblings('.depth2').slideToggle();
});

//$('.m-headerWrap .menuBtn').click(function(){
//   $('.m-menuWrap').css('display','block');
//});
//
//$('.m-menuWrap .close-btn').click(function(){
//   $('.m-menuWrap').css('display','none');
//});

$('.m-headerWrap .menuBtn').click(function(){
    $('.m-menuWrap').addClass('actived');
});

$('.m-menuWrap .close-btn').click(function(){
    $('.m-menuWrap').removeClass('actived');
});


/*tab*/
$('.tab > li > a').click(function () {
    let contId = $(this).attr('href');
    $('.tab > li > a').removeClass('actived');
    $(this).addClass('actived');
    $('.tabCont').removeClass('actived');

    $(contId).addClass('actived');

    event.preventDefault();
});



/*푸터 모달*/
$('.footerModal1-btn').click(function(){
    $('.footerModal1').css('display','block');
    event.preventDefault();
});
$('.footerModal2-btn').click(function(){
    $('.footerModal2').css('display','block');
    event.preventDefault();
});


/*제작의뢰 업종선택*/
$('.textSelect li').click(function () {
    if ($(this).text() == '기타') {
        $('.inputText').css('display','block');
    } else {
        $('.inputText').css('display','none');
    }

});


/*여행자 보험 insurance 국내외 선택 22-11-25 우람 수정*/
$('.radioWrap input').click(function(){
    if ($(this).is(":checked")) {
        if($(this).hasClass("domestic")) {
            $('.insuranceA').css('display','flex');
            $('.tb-insA').css('display','revert');
            $('.insuranceB').css('display','none');
            $('.tb-insB').css('display','none');
            $(".tb-insA").removeClass("d-none");
            $(".tb-insB").addClass("d-none");
        }else {
            $('.insuranceB').css('display','flex');
            $('.tb-insB').css('display','revert');
            $('.insuranceA').css('display','none');
            $('.tb-insA').css('display','none');
            $(".tb-insB").removeClass("d-none");
            $(".tb-insA").addClass("d-none");
        }
    }
});


/*datepicker*/
$("#datepicker, #datepicker1, #datepicker2, #datepicker3, #datepicker4").datepicker({
    language: 'ko'
});

datePickerSet($("#datepickerStr"), $("#datepickerFin"), true);
//다중은 시작하는 달력 먼저, 끝달력 2번째

/*
    * 달력 생성기
    * @param sDate 파라미터만 넣으면 1개짜리 달력 생성
    * @example   datePickerSet($("#datepicker"));
    *
    *
    * @param sDate,
    * @param eDate 2개 넣으면 연결달력 생성되어 서로의 날짜를 넘어가지 않음
    * @example   datePickerSet($("#datepickerStr"), $("#datepickerFin"));
    */
function datePickerSet(sDate, eDate, flag) {

    //시작 ~ 종료 2개 짜리 달력 datepicker
    if (!isValidStr(sDate) && !isValidStr(eDate) && sDate.length > 0 && eDate.length > 0) {
        var sDay = sDate.val();
        var eDay = eDate.val();

        if (flag && !isValidStr(sDay) && !isValidStr(eDay)) { //처음 입력 날짜 설정, update...
            var sdp = sDate.datepicker().data("datepicker");
            sdp.selectDate(new Date(sDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요

            var edp = eDate.datepicker().data("datepicker");
            edp.selectDate(new Date(eDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
        }

        //시작일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
        if (!isValidStr(eDay)) {
            sDate.datepicker({
                maxDate: new Date(eDay.replace(/-/g, "/"))
            });
        }
        sDate.datepicker({
            language: 'ko',
            autoClose: true,
            onSelect: function () {
                datePickerSet(sDate, eDate);
            }
        });

        //종료일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
        if (!isValidStr(sDay)) {
            eDate.datepicker({
                minDate: new Date(sDay.replace(/-/g, "/"))
            });
        }
        eDate.datepicker({
            language: 'ko',
            autoClose: true,
            onSelect: function () {
                datePickerSet(sDate, eDate);
            }
        });

        //한개짜리 달력 datepicker
    } else if (!isValidStr(sDate)) {
        var sDay = sDate.val();
        if (flag && !isValidStr(sDay)) { //처음 입력 날짜 설정, update...
            var sdp = sDate.datepicker().data("datepicker");
            sdp.selectDate(new Date(sDay.replace(/-/g, "/"))); //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
        }

        sDate.datepicker({
            language: 'ko',
            autoClose: true
        });
    }


    function isValidStr(str) {
        if (str == null || str == undefined || str == "")
            return true;
        else
            return false;
    }
}


function toastModal(txt, sec) {
    let secs = 1000;
    if(sec == undefined || sec == null) secs = 1000;
    else secs = sec;

    const newDiv = document.createElement('div');
    const newText = document.createTextNode(txt);

    newDiv.appendChild(newText);

    $(newDiv).css({"position":"fixed","top":"49%","left":"50%","transform":"translateX(-50%)","background-color":"#276cad","padding":"12px 30px","border-radius":"20px","color":"#fff","z-index":9999});

    document.body.appendChild(newDiv);

    setTimeout(function () {
        $(newDiv).animate({
            opacity: "hide"
        });
    }, secs);
}







