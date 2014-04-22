$(window).ready(function(){

	//사이트맵 버튼을 클릭했을때
	$( '.btn_sitemap' ).click( function(){
		makePopBg();                                                                                                                                                                                                       
		layPopupSetting( $( '.site_map_pop' ) );
	});

	//콘텍트 버튼을 클릭했을때
	$( '.btn_contact' ).click( function(){
		makePopBg();
		layPopupSetting( $( '.contact_pop' ) );
	});
	
	$( '.btn_close' ).click( function(){
		removePopup();
	});

	//리사이즈 이벤트
	$( window ).resize( function(){
/*		if( popState == false ) return;
		layPopupSetting( nowPop );*/
	});
	
	//스크롤 이벤트
	$( window ).scroll( function(){
	/*	if( popState == false ) return;
		layPopupSetting( nowPop );*/
	});
	
	$('.select_year').setYear();
	$('.select_month').setMonth();
});

//팝업닫기
function removePopup()
{
	popState = false;
	$( '.layer_pop_bg' ).remove();
	$( nowPop ).css({ display:'none' });
}

//팝업 배경 만들어주기
function makePopBg()
{
	var popupBg = '<div class="layer_pop_bg"></div>';
	$( '#wrap' ).append( popupBg );

	popupBgWidth = ( $( window ).width() < $( document ).width() ) ? $( document ).width() : $( window ).width();
	popupBgHeight = ( $( window ).height() < $( document ).height() ) ? $( document ).height() : $( window ).height();
	$( '.layer_pop_bg' ).css({ position:'absolute' , top:'0' , left:'0' , width:popupBgWidth , height:popupBgHeight , backgroundColor:'black' , opacity:'0.4' });
}

//팝업 가운데로 셋팅하기
function layPopupSetting( target )
{
	popState = true;
	nowPop = target;
	popupTop = ( $( window ).height() / 2 ) - ( $( target ).height()/2 ) + $(document).scrollTop();
	popupLeft = ( $( window ).width() / 2 ) - ( $( target ).width()/2 );
	$( target ).css({ display:'block' , position:'absolute' , top:popupTop , left:popupLeft , zIndex:'2' });
	$( '.layer_pop_bg' ).css({width:popupBgWidth , height:popupBgHeight});
}


function Validation(){
	var validator = {
		field : '',
		types : {},
		messages : [],
		customMsg : "",
		config : {},
		validate : function(data){
			var i, msg, type, checker, result_ok;
			this.messages = [];
			for(i in data){
				if(data.hasOwnProperty(i)){
					type = this.config[i];
					// 여러가지 타입이 있을경우.
					for(var j = 0; j < type.length; j += 1){
						checker = this.types[type[j]];	
						if(!type[j]){
							continue;
						}
						if(!checker){
							throw{
								name : "ValidationError",
								message : type + "값을 처리할 유효성 검사기가 존재하지 않습니다."
							};
						}
						result_ok = checker.validate(data[i].value);
						if(!result_ok){
							msg = "\'" + i + "\' 값이 유효하지 않습니다. " + checker.instructions;
							this.messages.push(msg);
							this.field = i;
							break;
						}
					}
					if(this.messages.length !== 0){
						break;
					}
				}
			}
			//return  this.hasErrors();
			if(this.hasErrors()){
			//	console.log(this.messages.length);
				alert(this.messages[0]);
				if(type != "isChecked"){					
					data[this.field].focus();
				}
				return false;
			}
			return true;
		},
		hasErrors : function(){
			return this.messages.length !== 0;
		}
	};
	
	validator.types.isNonEmpty = {
		validate : function(value){
			return value !== "";
		},
		instructions : "이 값은 필수입니다."
	};
	
	validator.types.isOnlyEng = {
		validate : function(value){
			var pattern = /^[a-zA-Z\s]+$/;				
			return pattern.test(value);
		},
		instructions : "영문만 가능합니다."			
	};
	validator.types.isOnlyEngNumber = {
		validate : function(value){
			var pattern = /^[a-zA-Z]+[a-zA-Z0-9_]+$/;
			return pattern.test(value);
		},
		instructions : "영문과 숫자만 가능합니다."			
	};
	validator.types.isTelNumber = {
		validate : function(value){
			var pattern = /\(?\+?\d{2,3}\)?[ -]?\d{3,4}[ -]?\d{4}$/;
			return pattern.test(value);
		},
		instructions : "전화번호만 가능합니다."			
	};
	validator.types.isMail = {
		validate : function(value){
			var pattern = /\w+[\w.]*@[\w.]+(\.\w{2,3})?\.\w{2,3}/;
			return pattern.test(value);
		},
		instructions : "이메일만 가능합니다."			
	};
	validator.types.isChecked = {
		validate : function(value){
			return value;
		},
		instructions : ""			
	};
	return validator;
}


$.fn.setYear = function() {
	var date = new Date();
	var year = date.getFullYear();
	var $selectBox = $(this);
	for(var i=0; i<25; i++){
		var opt = $('<option>').attr("value",year-i).text(year-i);
		$selectBox.append(opt);
	}
};

$.fn.setMonth = function() {
	var date = new Date();
	var month = date.getMonth() + 1;
	var $selectBox = $(this);
	for(var i=1; i<13; i++){
		var opt = $('<option>').attr("value",i).text(i);
		if(i == month){
			opt.attr("selected","selected");
		}
		$selectBox.append(opt);
	}
	$selectBox.change(function(){
		var val = $selectBox.val();
		$('.select_day').children().remove();
		$('.select_day').setDay(val);
	});
	
	$('.select_day').setDay(month);
	
};

$.fn.setDay = function(num) {
	var date = new Date();
	date.setMonth(num);
	var year = date.getFullYear();
	var month = date.getMonth()
	var day = date.getDate();
	var lastDay = (new Date(year,month,0)).getDate();
	var $selectBox = $(this);
	for(var i=1; i<=lastDay; i++){
		var opt = $('<option>').attr("value",i).text(i);
		if(i == day){
			opt.attr("selected","selected");
		}
		$selectBox.append(opt);
	}
};
