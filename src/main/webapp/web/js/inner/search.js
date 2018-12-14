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
        if($.trim(query) === plzInsertKeywordText) {
            query = EMPTY_TEXT;
            $('#topQuery').val(EMPTY_TEXT);
        } else {
            setMyKeyword(query);
        }
        this.submit();
    });

    // 좌측 메뉴 클릭
    $('#lmb ul li a').click(function (event) {
        var query = $('#topQuery').val();

        event.preventDefault();
        event.stopPropagation();

        var collectionName = $(this).attr('href');
        $('#collection').val(collectionName);
        $('#group').val('');
        $('#searchForm').submit();

    });

    $('.collectionMore').click(function (event) {

        event.preventDefault();
        event.stopPropagation();
        var collectionName = $(this).attr('href');
        $('#collection').val(collectionName);
        $('#group').val('');
        $('#searchForm').submit();

    });

    $('.groupMore').click(function (event) {

        event.preventDefault();
        event.stopPropagation();

        var groupName = $(this).attr('href');

        $('#group').val(groupName);
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

    $('.popkeywordtab').click(function (event) {

        event.preventDefault();
        event.stopPropagation();

        $('#popkeywordtabarea .menu li').removeClass("on");
        $('#popkeywordtabarea .list').hide();

        var $atag = $(this);
        var type = $atag.attr('href');
        $('#' + type + 'Popkeywords').show();
        $atag.parent().addClass("on");

    });

    otherKeywordClickEventBinding();

});