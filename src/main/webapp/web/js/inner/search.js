$(document).ready(function() {

    $("#topQuery").keydown(function (event) {

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

});