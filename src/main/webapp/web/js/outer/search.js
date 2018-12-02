function otherKeywordClickEventBinding() {
	
	$('.otherKeyword').click(function(event) {
		event.preventDefault();
		event.stopPropagation();
		var query = $(this).attr('href');
		$('#topQuery').val(query);
		$('#searchForm').submit();
	});
	
}

function doPaging(paging) {
	
	event.preventDefault();
	event.stopPropagation();
	$('#paging').val(paging);
	$('#searchForm').submit();
	
}

function setMyKeyword(keyword) {
	
	if(keyword != undefined && keyword != '' && keyword.replace(/^\s+|\s+$/gm, '') != '') {
		
		var myKeyword = $.cookie('my_keyword');
	    if(myKeyword != undefined && myKeyword != '' && myKeyword.replace(/^\s+|\s+$/gm, '') != '') {
	    	var array = myKeyword.split(",");
	    	var hasKeyword = false;
	    	for (i = 0; i < array.length ; i++) {
	    		if(array[i] == keyword) {
	    			hasKeyword = true;
	    		}
	    	}
	    	if(hasKeyword == false) {
	    		array.push(keyword);
	    		if(array.length > 5) {
	    			array.shift();
	    		}
	    		$.cookie('my_keyword', array.toString());
	    	}
	    } else {
	    	$.cookie('my_keyword', keyword);
	    }
	    
	}
    
}

var doubleSubmitFlag = false;
function doubleSubmitCheck(){
    if(doubleSubmitFlag){
        return doubleSubmitFlag;
    }else{
        doubleSubmitFlag = true;
        return false;
    }
}

$(document).ready(function() {
    
	$('#topSearch').click(function(event) {
		
		event.preventDefault();
		var query = $('#topQuery').val();
		if(query != undefined && query.replace(/^\s+|\s+$/gm, '') != '') {
			$('#searchForm').submit();
		} else {
			alert('검색어를 입력하여 주세요.');
		}
		
	});
	
	$("input[name=query]").keydown(function (event) {
		
		var query = $('#topQuery').val();
        if(event.keyCode == 13) {
        	event.preventDefault();
        	if(query != undefined && query.replace(/^\s+|\s+$/gm, '') != '') {
        		$('#searchForm').submit();
        	} else {
    			alert('검색어를 입력하여 주세요.');
    		}
        }
 
    });
		
	$('#searchForm').submit(function(event) {
		
		event.preventDefault();
		var query = $('#topQuery').val();
		setMyKeyword(query);
	    this.submit();	
	    
	});
	
	$('.totalAreaUlLi').click(function(event) {
		
		event.preventDefault();
		event.stopPropagation();
		
		var query = $('#topQuery').val();
		
		if(query == undefined || query.replace(/^\s+|\s+$/gm, '') == '') {

			var hiddenQuery = $('#hiddenQuery').val();
			$('#topQuery').val(hiddenQuery);
			
		}
		
		var collectionName = $(this).find('a').attr('href');
		$('#collection').val(collectionName);
		$('#searchForm').submit();
		
	});
	
	$('.totalAreaUlLiA').click(function(event) {
		
		event.preventDefault();
		event.stopPropagation();
		
		var query = $('#topQuery').val();
		
		if(query == undefined || query.replace(/^\s+|\s+$/gm, '') == '') {

			var hiddenQuery = $('#hiddenQuery').val();
			$('#topQuery').val(hiddenQuery);
			
		}
		
		var collectionName = $(this).attr('href');
		$('#collection').val(collectionName);
		$('#searchForm').submit();
		
	});

	$('#myKeywordAreaH2').click(function(event) {

        event.preventDefault();
        event.stopPropagation();
        $('#myKeywordArea').toggle();

    });

    $('#popKeywordAreaH2').click(function(event) {

        event.preventDefault();
        event.stopPropagation();
        $('#popKeywordAreaDiv').toggle();

    });
	
	var myKeyword = $.cookie('my_keyword');
    if(myKeyword != undefined && myKeyword != '' && myKeyword.replace(/^\s+|\s+$/gm,'') != '') {
    	$('#myKeywordArea').empty();
    	var array = myKeyword.split(",");
    	for (i = 0; i < array.length ; i++) {
    		$('#myKeywordArea').append("<li><span class='num'>" + (i + 1) + "</span><a href='" + array[i] + "' class='otherKeyword'>" + array[i] + "</a></li>");
    	}
    } else {
    	$('#myKeywordAreaDiv').hide();
    }

    otherKeywordClickEventBinding();
    
});