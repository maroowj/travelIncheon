jQuery(document).ready(function($) {

	$( '.prevent' ).click(function(){
		if(!jQuery.browser.msie){
			event.preventDefault();
		}else{
			event.returnValue = false;
		}
	});
	function prevent(){
		if(!jQuery.browser.msie){
			event.preventDefault();
		}else{
			event.returnValue = false;
		}
	}

	/*IE*/
	var version = detectIE();
	if (version === false) {
	  $('body').addClass('no-ie');
	} else if (version >= 12) {
	  $('body').addClass('ie-' + version);
	} else {
	  $('body').addClass('ie-' + version);
	}
	function detectIE() {
		var ua = window.navigator.userAgent;
		var msie = ua.indexOf('MSIE ');
		if (msie > 0) {
			return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
		}
		var trident = ua.indexOf('Trident/');
		if (trident > 0) {
			var rv = ua.indexOf('rv:');
			return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
		}
		var edge = ua.indexOf('Edge/');
		if (edge > 0) {
			return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
		}
		return false;
	}
	/*IE*/

	/* ip */
	//public_ip //프론트에서 설정함

	/*popup cookie*/
	//.popup-close 을 클릭하면 바깥쪽 div의 id를 가져와 cookie를 생성
	//사용예시는 http://www.fun25.co.kr/blog/jquery-cookie-simple-sample 사이트를 참고
	var cookie_name;
	$('.popup-close').click( function(){
		var popupDIv = $(this).closest('.popup-div');
		var checkBox = $(this).siblings('label').find('input');
		$(popupDIv).addClass('hide');
		cookie_name = $(popupDIv).attr('id');
		if( $(checkBox).is(':checked') ){
			$.cookie('madeshot-' + cookie_name, 'no', { expires: 1 }); 
		}
	});
	//cookie가 있으면 해당 popup에 hide를 추가
	//사용하려는 popup list
	var cookieNames = ['header-popup', 'main-popup'];
	$.each( cookieNames, function( index, cookieName ) {
		var madeshotCookie = 'madeshot-' + cookieName;
		if ( $.cookie( madeshotCookie ) ) {
			$('#' + cookieName).addClass('hide');
		}
	});
	/*cookie*/

    

	/* fadein */
	$('.fadein').each(function(){
		$(this).addClass('ready');
	});

	/* 각종 메뉴 open close */
	function removeShow(e){
		$('body').find('.show').each(function(){
			$(this).removeClass('show');
		});
	}

	$('body').click( function( e ){	

		//console.log($(e.target));
		if( $(e.target).hasClass('close') || $(e.target).hasClass('menu-close') ){
			//console.log('aaa');
			removeShow();
		}else if( $(e.target).hasClass('open-popup') ){
			//console.log('bbb');
			/*popup control*/
			// open-popup이 있는 경우 해당 id를 가져와서 해당id+target을 찾아 show를 추가 target에는 open-popup-target클라스 필수
			removeShow();
			var eventId = '';
			eventId = $(e.target).attr('id');
			eventId = eventId + '-popup';
			$('#footer-overlay').addClass('show');	
			$( '#'+ eventId ).addClass('show');
		}else if( $(e.target).hasClass('open-popup-target') || $(e.target).parents('.open-popup-target').length ){
			//console.log('ccc');
			var openPopupTarget;
			if( !$(e.target).parents('.open-popup-target').length ){
				openPopupTarget = $(e.target);
			}else{
				openPopupTarget = $(e.target).parents('.open-popup-target')
			}
			removeShow();
			$('#footer-overlay').addClass('show');
			$(openPopupTarget).addClass('show');

		}else if ( $(e.target).hasClass('open') && !$(e.target).siblings('.open-target').hasClass('show')  ) {
			//console.log('ddd');
			removeShow();

			$(e.target).siblings('.open-target').addClass('show');
			$(e.target).addClass('show');


		}else if( $(e.target).closest('a').hasClass('footer-popup') || $(e.target).parents('div#footer-popup').length ){
			//console.log('eee');
			removeShow();
			$('#footer-overlay').addClass('show');
			$('#footer-popup').addClass('show');		
		}else if( $(e.target).parents('.open-target').hasClass('show') || $(e.target).hasClass('show') ){
			//console.log('fff');
			if( $(e.target).hasClass('menu-close') || $(e.target).siblings('.open-target').hasClass('quick-menu') ){
				removeShow();
			}
		}else{
			removeShow();
		}








		/*sms예약 선택의 경우 datepicker, timepicker 콘트롤*/
		if( $('input#send_reserve').is(':checked') ){
			$('.datepicker-div').addClass('show');
		}else{
			$('.datepicker-div').removeClass('show');
			//$('.datepicker').val('');
			//$('.timepicker').val('');
		}
		if( $('input#send_reserve_1').is(':checked') ){
			$('.datepicker-div-1').addClass('show');
		}else{
			$('.datepicker-div-1').removeClass('show');
			//$('.datepicker').val('');
			//$('.timepicker').val('');
		}

		/*광고용 선택의 경우 하단 div open*/
		if( $('input#send_for_ad').is(':checked') ){
			$('.send_for_ad_target').addClass('show');
		}else{
			$('.send_for_ad_target').removeClass('show');
			$('#ad_agree').prop('checked' , false);
		}
		if( $('input#send_for_ad_1').is(':checked') ){
			$('.send_for_ad_target_1').addClass('show');
		}else{
			$('.send_for_ad_target_1').removeClass('show');
			$('#ad_agree_1').prop('checked' , false);
		}



		/**/
		if( $('input#confirm_by_fax').is(':checked') ){
			$('.confirm_by_fax_target').addClass('show');
		}else{
			$('.confirm_by_fax_target').removeClass('show');
		}
		if( $('input#confirm_by_paper').is(':checked') ){
			$('.confirm_by_paper_target').addClass('show');
		}else{
			$('.confirm_by_paper_target').removeClass('show');
		}



		/*test sms 오른쪽 block*/
		if( $('input#test-sms').is(':checked') ){
			$('.disabled').addClass('block');
		}else{
			$('.disabled').removeClass('block');
		}
		$('input.send-option').each( function(){
			if ( $(this).is(':checked') ){
				$(this).closest('label').siblings('div.open-target').addClass('show');
			}
		});

		/*basic-fax-cover 콘트롤*/
		if( $('input#basic-fax-cover').is(':checked') ){
			$('.basic-fax-cover-target').addClass('show');
		}else{
			$('.basic-fax-cover-target').removeClass('show');
		}

		/*첨부파일*/
		$( 'input[type="file"]' ).change( function(){
			var strOriginal = $(this).val();
			var str = $(this).val();
			var n = str.lastIndexOf("\\");
			str = str.substring( n + 1 )
			var fileSize;
			if( $(this).hasClass('file-size') ){
				fileSize = this.files[0].size / 1024;
				fileSize = ' ['+ Math.ceil(fileSize) + ' KB]';
				//console.log(fileSize);
			}
			$(this).siblings('.input-val').text( str + fileSize );
			$(this).siblings('.input-val').attr('data', strOriginal);
		});





		$( '.send_freq_type' ).change( function(){
			var addNewClass = $(this).val();
			$(this).closest('div').removeClass('daily business_daily weekly monthly');
			$(this).closest('div').addClass(addNewClass);
		});



		/*동적계산*/
		$('.js100pro').each( function(){
			$(this).css({'height': $(this).width()+'px'});
		});
		$('.js60pro').each( function(){
			$(this).css({'height': $(this).width()*.6+'px'});
		});
	});	

	/*전체메뉴 클론*/
	$('.paste-here').find('.open-target > div.all-menu').append( $('.copy-this').clone() );


	/*cs-fax*/
	var csFax = $('a#csfax').html();
	$('#cs-fax').text( csFax );
	var csEmail = $('a#csemail').html();
	$('#cs-email').text( csEmail );
	$('#cs-email').attr( 'href', 'mailto:'+ csEmail );
	//console.log(csFax);



	/*휴대폰 목업에서 문자보내기 전화번호 입력란 추가 & 제거*/
	$('.cell-phone-inner2').on('click', 'a.more-input', function(e){
		var closestDiv = $(e.target).parent.closest('div');
		var n =  $(closestDiv).siblings('div').length;
		if ( $(closestDiv).is('div:first-child') ){
			if( n <= 3 ){
				var clonedDiv = $(closestDiv).clone();
				//console.log(clonedDiv);
				$('.cell-phone-inner2').append( $(closestDiv).clone().find('input[type="number"]').val('').end() );		
			}
		}else{
			$(closestDiv).remove();
		}
	});
    
		
	/*대량 문자보내기 전화번호 입력란 추가 & 제거 등*/
	function countNumbers(){
		var n = $('select.sms_num_list > option').length;
		$('.count_number').text(n);
	}
	function addOption(){
		var newNumber = $('input#sms_num').val();
		var numberLength = newNumber.toString().length;
		if( numberLength >= 11  && numberLength <= 11){
			$('select.sms_num_list').append('<option value="'+ newNumber +'">'+ newNumber +'</option>');
			$('input#sms_num').val('');
		}
	}
	$('select.sms_num_list').on('click', 'option',function(e){
        
        var result = confirm("선택한 번호를 삭제하시겠습니까?");
        if(result) { 
              $(e.target).remove();
		      countNumbers();
         } else { 
                //no 
         }

    
	});
	$('input.sms_num').on('keypress', function(e){
		if( e.keyCode == 13 ){
			addOption();
			countNumbers();
			return false;
		}
	});
	$('a.add-sms-number').on('click', function(){
		addOption();
		countNumbers();
	});

	/*fax보내기 문서 추가 & 제거 등*/
	function countNumbers1(){
		var n = $('select[name="fax_docs"] > option').length;
		$('.count_number1').text(n);
	}
	function addOption1(){
		var newNumber = $('span.input-val').text();
		var newNumberData = $('span.input-val').attr('data');
		var numberLength = newNumber.toString().length;
		if( numberLength > 1 ){
			$('select[name="fax_docs"]').append('<option value="'+ newNumberData +'">'+ newNumber +'</option>');
			$('input[name="fax_doc"]').val('');
			$('span.input-val').text('');
		}
	}
	$('select[name="fax_docs"]').on('click', 'option',function(e){
		$(e.target).remove();
		countNumbers1();
	});
	$('a.add_to_fax_doc').on('click', function(){
		addOption1();
		countNumbers1();
	});










	/*SMS byte 계산*/
	$('body').on('keyup keypress click', function(e) {

		if( $('.cell-phone-inner').length ){
			var textVal = $('.cell-phone-inner textarea#sms').val();
			var textSize = $('.cell-phone-inner textarea#sms').val().length;
			//console.log(textSize);
			$('.count-length').text(textSize);
			if( textSize >= 0 && textSize <= 90 ){
				$('.max-byte').text('90');
				$('.ms-type').text('SMS');
				$('.cell-phone-inner textarea#sms').removeClass('over90');
				$('.cell-phone-inner textarea#sms').removeClass('over2000');
			}else if( textSize > 90 && textSize <= 2000 ){
				$('.max-byte').text('2000');
				$('.ms-type').text('LMS');
				$('.cell-phone-inner textarea#sms').addClass('over90');
				$('.cell-phone-inner textarea#sms').removeClass('over2000');
			}else{
				$('.max-byte').text('2000');
				$('.ms-type').text('LMS');
				$('.cell-phone-inner textarea#sms').addClass('over2000');
				var textVal2 = textVal.substring(0, 1999)
				$('.cell-phone-inner textarea#sms').val(textVal2);
			}
		}

		/*문자보관함 추가3개*/
		if( $('.cell-phone-inner').length ){
			var textVal = $('.cell-phone-inner textarea#sms1').val();
			var textSize = $('.cell-phone-inner textarea#sms1').val().length;
			//console.log(textSize);
			$('.count-length1').text(textSize);
			if( textSize >= 0 && textSize <= 90 ){
				$('.max-byte1').text('90');
				$('.ms-type1').text('SMS');
				$('.cell-phone-inner textarea#sms1').removeClass('over90');
				$('.cell-phone-inner textarea#sms1').removeClass('over2000');
			}else if( textSize > 90 && textSize <= 2000 ){
				$('.max-byte1').text('2000');
				$('.ms-type1').text('LMS');
				$('.cell-phone-inner textarea#sms1').addClass('over90');
				$('.cell-phone-inner textarea#sms1').removeClass('over2000');
			}else{
				$('.max-byte1').text('2000');
				$('.ms-type1').text('LMS');
				$('.cell-phone-inner textarea#sms1').addClass('over2000');
				var textVal2 = textVal.substring(0, 1999)
				$('.cell-phone-inner textarea#sms1').val(textVal2);
			}
		}
		if( $('.cell-phone-inner').length ){
			var textVal = $('.cell-phone-inner textarea#sms2').val();
			var textSize = $('.cell-phone-inner textarea#sms2').val().length;
			//console.log(textSize);
			$('.count-length2').text(textSize);
			if( textSize >= 0 && textSize <= 90 ){
				$('.max-byte2').text('90');
				$('.ms-type2').text('SMS');
				$('.cell-phone-inner textarea#sms2').removeClass('over90');
				$('.cell-phone-inner textarea#sms2').removeClass('over2000');
			}else if( textSize > 90 && textSize <= 2000 ){
				$('.max-byte2').text('2000');
				$('.ms-type2').text('LMS');
				$('.cell-phone-inner textarea#sms2').addClass('over90');
				$('.cell-phone-inner textarea#sms2').removeClass('over2000');
			}else{
				$('.max-byte2').text('2000');
				$('.ms-type2').text('LMS');
				$('.cell-phone-inner textarea#sms2').addClass('over2000');
				var textVal2 = textVal.substring(0, 1999)
				$('.cell-phone-inner textarea#sms2').val(textVal2);
			}
		}
		if( $('.cell-phone-inner').length ){
			var textVal = $('.cell-phone-inner textarea#sms3').val();
			var textSize = $('.cell-phone-inner textarea#sms3').val().length;
			//console.log(textSize);
			$('.count-length3').text(textSize);
			if( textSize >= 0 && textSize <= 90 ){
				$('.max-byte3').text('90');
				$('.ms-type3').text('SMS');
				$('.cell-phone-inner textarea#sms3').removeClass('over90');
				$('.cell-phone-inner textarea#sms3').removeClass('over2000');
			}else if( textSize > 90 && textSize <= 2000 ){
				$('.max-byte3').text('2000');
				$('.ms-type3').text('LMS');
				$('.cell-phone-inner textarea#sms3').addClass('over90');
				$('.cell-phone-inner textarea#sms3').removeClass('over2000');
			}else{
				$('.max-byte3').text('2000');
				$('.ms-type3').text('LMS');
				$('.cell-phone-inner textarea#sms3').addClass('over2000');
				var textVal2 = textVal.substring(0, 1999)
				$('.cell-phone-inner textarea#sms3').val(textVal2);
			}
		}







	});


	/*자주보내는 번호, 주소록 불러오기 등 콘트롤*/
	$('.tap-title > li').click( function(e){
		var nth = $(this).index();	//0123
		$(this).closest('.tap-title').find('li').each( function(){
			$(this).removeClass('active');
		});
		$(this).addClass('active');
		/*tap-title > li 클릭 후 해야하는 것 여기에서...*/


	});
	/*초기화*/
	$('.w62pro').on('click', '.clear-all', function(){
		$('.w62pro').find('input').each( function(){
			var inputType	= $(this).attr('type');
			var thisId		= $(this).attr('id');
			if( inputType == 'checkbox' || inputType == 'radio' ){
				if( thisId == 'send_now' || thisId == 'send_for_business' || thisId == 'send_option_03' ){
					//console.log(thisId);
					$(this).prop('checked' , true);
				}else{
					$(this).prop('checked' , false);
				}

			}else if(  inputType == 'text' || inputType == 'number'  ){
				$(this).val('');
			}
		});
		$('.w62pro').find('select.sms_num_list > option').each( function(){
            $(this).remove();
		});
		$('.datepicker-div').removeClass('show');
		$('.cell-phone-inner textarea#sms').val('');
		$('.cell-phone-inner2').find('input').each( function(){
			var inputType = $(this).attr('type');
			if(  inputType == 'text' || inputType == 'number'  ){
				var outerDiv	= $(this).closest('div');
				var outerDivNth	= $(this).closest('div').index();
				if( outerDivNth != 0 ){
					$(outerDiv).remove();
				}else{
					$(this).val('');
				}
			}
		});
		$('#test-sms').attr('checked' , false);
		$('span.count_number').text('0');
		$('.tap-title').find('> li').each( function(){
			var nth = $(this).index();
			if( nth == 0 ){
				$(this).addClass('active');
			}else{
				$(this).removeClass('active');
			}
		});
		/*초가화 하려는 내용을 여기에 추가*/

		/*초가화 하려는 내용을 여기에 추가*/
	});
	/*datepicker*/
	$('.datepicker').each( function(){
		$(this).datepicker({
			monthNames	: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNamesMin	: ['일','월','화','수','목','금','토'],
			dateFormat	: "yy-mm-dd",
		});
	});
	/*timepicker*/
	$('.timepicker').each( function(){
		$(this).timepicker({
			timeFormat	: 'HH:mm',
			scrollbar	: true
		});
	});
	/*미리 설정된 문자 tap*/
	$('.sms-tap').on('click','li', function(){
		unCheckAll();
		var nth = $(this).index();
		var thisUl = $(this).closest('.sms-tap');
		$(thisUl).find('li').each(function(){
			$(this).removeClass('active');
		});
		$(this).addClass('active');
		$(thisUl).siblings('.sms-target').find('> li').each(function(){
			//console.log(this);
			var nth2 = $(this).index();
			if( nth == nth2 ){
				$(this).addClass('active');
				if($(this).hasClass('saved-photo-sms')){

					$('#saved-sms-edit').removeClass('show1');
					$('#saved-photo-sms-edit').addClass('show1');

				}else{

					$('#saved-sms-edit').addClass('show1');
					$('#saved-photo-sms-edit').removeClass('show1');

				}
			}else{
				$(this).removeClass('active');
			}
		});
	});
	/*미리 설정된 문자 tag 추가*/
	$('ul.sms-target > li > ul > li').find('> a').each( function(){
		$(this).wrap( "<div></div>" );
	});
	/*미리 설정된 문자 휴대폰에 넣기*/
	$('ul.sms-target > li > ul > li a').on('click', function(){
		if( $(this).find('img') ){
			var imgSrc = $(this).find('img').attr('src');
			//console.log(imgSrc);
			$('a#img-upload > img').attr('src', imgSrc);
		}else{
			$('.cell-phone-inner textarea#sms').val( $(this).html().replace(/<br>/gi,"\n") );
		}
		var msg = $(this).html();
	})	

	/*문자보관함*/
	function unCheckAll(){
		$('.saved-sms input[type="checkbox"]').each(function(){
			$(this).prop('checked', false);
			$('.po-a.cover').removeClass('none');
		});
	}
	$('.saved-sms').on('click','input[type="checkbox"]', function(){
		unCheckAll();		
		$(this).prop('checked', true);
		$('.po-a.cover').addClass('none');
		var existCheck = $('.saved-sms input[type="checkbox"]:checked').length;
		if( existCheck == 0 ){
			$('.po-a.cover').removeClass('none');
		}	
	});
		





	
	




	/*동적계산*/
	$('.js100pro').each( function(){
		$(this).css({'height': $(this).width()+'px'});
	});
	$('.js60pro').each( function(){
		$(this).css({'height': $(this).width()*.6+'px'});
	});



});