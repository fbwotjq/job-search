$(document).ready(function() {

    var plzInsertKeywordText = '검색어를 입력해주세요';
    var EMPTY_TEXT = '';

    function doSearch() {
        var query = $('#topQuery').val();
        if(query != undefined && query.replace(/^\s+|\s+$/gm, '') !== '' && $.trim(query) !== '검색어를 입력해주세요' && $.trim(query) !== '') {
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
        console.log($.trim(query) === plzInsertKeywordText, $.trim(query), plzInsertKeywordText)
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

    console.log($.cookie('my_keyword'));

});