jQuery(document).ready(function($) {

    $("input[type=text], textarea").attr("spellcheck", false);

    /*모바일 메뉴*/
    $('.menuBar').click(function(){
        $('.m-nav-wrap').addClass('active');
        $('.bg-cover').addClass('active');
    });

    $('.close-btn, .bg-cover, .close, .modal-bg-cover').click(function(){
        $('.m-nav-wrap').removeClass('active');
        $('.bg-cover').removeClass('active');
        $('.modal').css('display','none');
    });

    /*견학신청 modal*/
    $('.detail-btn .reserve-btn').click(function(){
        if (matchMedia("screen and (max-width: 768px)").matches) {
            $('.m-modal').css('display','block');
        } else {
            $('.pc-modal').css('display','block');
        }
    });

    /*예약가능 modal*/
    //$('.sch-type2').click(function(){
    $("#dates").on("click", ".sch-type2", function(){
        if (matchMedia("screen and (max-width: 768px)").matches) {
            $('.m-reserve-modal').css('display','block');
        } else {
            $('.pc-reserve-modal').css('display','block');
        }
    });

    /*예약확인 modal*/
    //$('.sch-type3').click(function(){
    $("#dates").on("click", ".sch-type3", function(){
        $('.confirm-modal').css('display','block');
        $('#confirm-modal').attr("seq", $(this).attr("reservationseq"));
    });

    /*예약정보 modal*/
    /*$('.confirm-btn').click(function(){
       $('.confirm-modal').css('display','none');
        if (matchMedia("screen and (max-width: 768px)").matches) {
            $('.m-info-modal').css('display','block');
        } else {
            $('.pc-info-modal').css('display','block');
        }
    });*/
    /*예약수정 modal*/
    $('.update-btn').click(function(){
        $('.info-modal').css('display','none');
    });

    /*달력*/
    /*$("#datepicker").datepicker({
        language: 'ko'
    });*/


    /*관리자페이지*/
    /*좌측메뉴 연결*/
    // $('.leftMenu-inner ul li a').click(function(){
    //     $('.leftMenu-inner ul li a').removeClass('on');
    //     $(this).addClass('on');
    // });

    /*모달창*/
    // $('.company-table table tbody tr td.modal-open').click(function(){
    //    $('.course-modal').css('display','block');
    // });
    //
    // $('.modal-open-table table tbody tr').click(function(){
    //     $('.course-modal').css('display','block');
    // });

    $("#reservationList").on("click", ".info", function() {
        // $('.modal-open-table table tbody tr td.modal-open').click(function() {
        $('.admin-info-modal').css('display', 'block');
    });

    $("#companyCourseList").on("click", ".info", function() {
    // $('.modal-open-table table tbody tr td.modal-open').click(function() {
        $('.course-modal').css('display', 'block');
    });


    $('.company-add-btn').click(function(){
       $('.company-add-modal').css('display','block');
    });

    /*$('.company-update-btn').click(function(){
        $('.company-update-modal').css('display','block');
    });*/

    /*링크연결*/
    // $(".view-update").click(function(){
    //     $(location).attr("href", "../admin2/bannerUpdate")
    // });

    //$(".goReservation").click(function(){
    $("#header").on("click", ".goReservation", function(){
        $('.pc-modal').css('display','block');
    });

    $(".footerInfo").click(function(){
        //$('.footerModal').css('display','block');
        $(".footerModal").find(".title").text($(this).text());
    });

    // 전체선택
    $('.all-check').click(function(){
        if($('.all-check').prop('checked')){
            $('.checked').prop('checked', true);
        }else{
            $('.checked').prop('checked', false);
        }
    });

    // 개별선택
    $('.main-content').on("click", ".checked", function() {
        if ($('.checked').length == $('.checked:checked').length) {
            $('.all-check').prop('checked', true);
        } else {
            $('.all-check').prop('checked', false);
        }
    });

    //관리자 페이지-문의 답변하기
    let qnaTxt = $('.modal textarea').val();
    let link = document.location.href;
    if(link.includes('/admin/qna')){
        $('.admin-modal .modal-bottom .modal-btn').on('click', function (){
            // 답변내용을 작성하지 않은 경우
            if(qnaTxt == ''){
                $('.modal textarea').focus();
                modalScrollBottom();
                function modalScrollBottom(){
                    let modalContArea = document.querySelector('#course-modal .modal-text-wrap');
                    modalContArea.scrollTop = modalContArea.scrollHeight;
                    setInterval(updateScroll, 0);

                    var scrolled = false;
                    function updateScroll(){
                        if(!scrolled){
                            modalContArea.scrollTop = modalContArea.scrollHeight;
                        }
                    }
                    $(modalContArea).on('scroll', function(){
                        scrolled=true;
                    });
                }
            }
        });
    }

});





























