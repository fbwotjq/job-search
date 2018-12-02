$(document).ready(function() {

    var plzInsertKeywordText = '검색어를 입력해주세요';
    var EMPTY_TEXT = '';

    function doSearch() {
        var query = $('#topQuery').val();
        if(query != undefined && query.replace(/^\s+|\s+$/gm, EMPTY_TEXT) !== EMPTY_TEXT &&
            $.trim(query) !== '검색어를 입력해주세요' && $.trim(query) !== EMPTY_TEXT) {
            $('#searchForm').submit();
        } else {
            alert(plzInsertKeywordText);
        }
    }

    $('#searchSubmit').click(function () {
        event.preventDefault();
        doSearch();
    });

    $("#topQuery").keydown(function (event) {
        if(event.keyCode == 13) {
            event.preventDefault();
            doSearch();
        }
    });

    $("#topQuery").focusout(function () {
        var query = $(this).val();
        if($.trim(query) === EMPTY_TEXT) {
            $(this).val(plzInsertKeywordText);
        }
    });

    $("#topQuery").focus(function() {
        var query = $(this).val();
        if($.trim(query) === plzInsertKeywordText) {
            $(this).val(EMPTY_TEXT);
        }
    });

    $('#searchForm').submit(function(event) {
        event.preventDefault();
        var query = $('#topQuery').val();
        setMyKeyword(query);
        this.submit();
    });

    $('#lmb ul li a').click(function () {

        event.preventDefault();
        event.stopPropagation();

        var collectionName = $(this).attr('href');
        $('#collection').val(collectionName);
        $('#searchForm').submit();

    });

    var myKeyword = $.cookie('my_keyword');
    if(myKeyword != undefined && myKeyword != '' && myKeyword.replace(/^\s+|\s+$/gm,'') != '') {
        $('#myKeywordArea').empty();
        var array = myKeyword.split(",");
        for (i = 0; i < array.length ; i++) {
            $('#myKeywordArea').append("<li><small>" + (i + 1) + "</small><a href='" + array[i] + "' class='otherKeyword'><span>" + array[i] + "</span></a></li>");
        }
    } else {
        $('#myKeywordAreaDiv').hide();
    }

    otherKeywordClickEventBinding();

});