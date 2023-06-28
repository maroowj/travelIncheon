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

function fConvertDateFrom(string) {
    let year = string.substr(0, 4);
    let month = string.substr(5, 2);

    let day = string.substr(8, 2);

    let returnDate = new Date(year, parseInt(month) - 1, day);
    returnDate.setHours(returnDate.getHours() + 9);

    let returnStr = returnDate.toISOString().replace("T", " ").replace(/\..*/, '');
    return returnStr;
}

function fConvertDateTo(string) {
    let year = string.substr(0, 4);
    let month = string.substr(5, 2);

    let day = (parseInt(string.substr(8, 2)) + 1) + '';

    let returnDate = new Date(year, parseInt(month) - 1, day);
    returnDate.setHours(returnDate.getHours() + 9);

    let returnStr = returnDate.toISOString().replace("T", " ").replace(/\..*/, '');
    return returnStr;
}

function fPagination(data, page) {
    let num = "";
    let reNum = parseInt(data.number / 5);
    let pre = "";
    let next = "";

    // 이전버튼 (5페이지씩 이동, 처음으로)
    if (reNum >= 1 && page > 5) {
        pre = "<li><a class='cupoint' gopage='" + (reNum * 5) + "'><i class='fa-solid fa-chevron-left'></i></a></li>";
    }

    // 다음버튼 (5페이지씩 이동, 마지막으로)
    if (parseInt((data.totalPages - 1) / 5) != reNum && data.totalPages > 5) {
        next = "<li><a class='cupoint' gopage='" + ( reNum * 5 + 6 ) + "'><i class='fa-solid fa-chevron-right'></i></a></li>";
    }

    for ( let idx = 1; idx <= data.totalPages; idx++ ) {
        if ( idx <= reNum * 5 + 5 && idx > reNum * 5 ) {
            if (idx == page) {
                num += "<li class='on'><a class='cupoint' gopage=" + idx + ">" + idx + "</a></li>";
            } else {
                num += "<li><a class='cupoint' gopage=" + idx + ">" + idx + "</a></li>";
            }
        }
    }
    $("#pagination").html("<ul class='paging'>" + pre + num + next + "</ul>");
}

function fPagination2(data, page) {
    let num = "";
    let reNum = parseInt(data.number / 10);
    let first = "";
    let pre = "";
    let next = "";
    let last = "";

    // 이전버튼 (10페이지씩 이동, 처음으로)
    if (reNum >= 1 && page > 10) {
        first = '<a class="cupoint page-arrow1 page-arrow mr05" gopage="1">처음 게시물 목록</a>';
        pre = '<a class="cupoint page-arrow2 page-arrow mr05" gopage="'+(reNum * 10) +'">이전 게시물 목록</a>';
    }

    // 다음버튼 (10페이지씩 이동, 마지막으로)
    if (parseInt((data.totalPages - 1) / 10) != reNum && data.totalPages > 10) {
        last = '<a class="cupoint page-arrow4 page-arrow" gopage="'+data.totalPages+'">마지막 게시물 목록</a>';
        next = '<a class="cupoint page-arrow3 page-arrow mr05 ml05" gopage="'+( reNum * 10 + 11 )+'">다음 게시물 목록</a>';
    }

    num += '<div class="page-num">'
    for ( let idx = 1; idx <= data.totalPages; idx++ ) {
        if ( idx <= reNum * 10 + 10 && idx > reNum * 10 ) {
            if (idx == page) {
                num += "<a class='cupoint actived' gopage=" + idx + ">" + idx + "</a>";
            } else {
                num += "<a class='cupoint' gopage=" + idx + ">" + idx + "</a>";
            }
        }
    }
    num += '</div>';

    $(".pagination").html(first + pre + num + next + last);
}

// 천단위 콤마
function fcomma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

// 천단위 콤마 제거
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

function fConvertHour(second) {
    let hour = parseInt((second % 3600) / 60) < 10 ? '0' + parseInt((second % 3600) / 60) : parseInt((second % 3600) / 60);

    return hour;
}
function fConvertMin(second) {
    let min = parseInt(second % 60) < 10 ? '0' + parseInt(second % 60) : parseInt(second % 60);

    return min;
}

//cost 비용 불러오기
function getAllCost() {
    let busFare;
    let guideFee;
    let insuranceFee;

    $.ajax({
        type: "GET",
        url: "/api/user/unit",
        async: false,
        success: function (data) {
            // console.log(data);

            busFare=data.busFare;
            guideFee=data.guideFee;
            insuranceFee=data.insuranceFee;
        }
    });
    return [busFare, guideFee, insuranceFee];
}

//이메일 정규식 체크
function femailCheck(email) {
    let reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

    return reg.test(email);
    //return reg.test(email);
}