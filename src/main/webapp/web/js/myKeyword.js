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