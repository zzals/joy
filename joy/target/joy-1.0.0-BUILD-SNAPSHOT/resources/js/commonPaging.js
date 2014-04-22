(function($) {

    $.fn.commonPaging = function(options) {
        var opts = $.extend({},$.fn.commonPaging.defaults,options);
           
        var original = this;

        var action = {

            init : function() {
                var totalPage = Math.ceil(opts.totalSize/opts.pageSize);
                var totalPageList = Math.ceil(totalPage/opts.pageListSize);
                var pageList = Math.ceil(opts.pageNo/opts.pageListSize);
                if (pageList < 1) pageList = 1;
                if (pageList > totalPageList) pageList = totalPageList;
                var startPageList = (pageList - 1) * opts.pageListSize + 1;
                var endPageList = startPageList + opts.pageListSize - 1;

                if (startPageList < 1) startPageList = 1;
                if (endPageList > totalPage) endPageList = totalPage;
                if (endPageList < 1) endPageList = 1;

                var innerHTML = action.getPageItemLink(1, 'FIRST', (opts.pageNo > 1), 'PREV');
                
                innerHTML += action.getPageItemLink((startPageList - 1), 'PREV', (startPageList > 1), 'PREV');
                
                for (var i = startPageList; i <= endPageList; i++) {

                    innerHTML += action.getNumberLink(i, null, (opts.pageNo != i), ((opts.pageNo == i)? 'selected': ''));
                    if (i < endPageList) {
                    	//innerHTML += '<span>|</span>';
                        //innerHTML += ' | ';       // separate action
                    }
                    innerHTML += '</li>\n';
                }
                innerHTML += action.getPageItemLink((endPageList + 1),'NEXT',(endPageList < totalPage), 'NEXT');
                innerHTML += action.getPageItemLink(totalPage, 'LAST', (opts.pageNo < totalPage), 'NEXT');
                $(original).html(innerHTML);
            },

            getNumberLink : function(pageNo, text, useLink, className) {
            	var numHTML = '';
                if (useLink) {
                	numHTML = '<li><a href="javascript:'+opts.pageClickFunctionName+'(' + pageNo + ')"><span>' + ((text != null && text != '')? text: pageNo) + '</span></a>';
                }
                else {
                	numHTML = '<li ' + ((className != null && className != '')? ' class="' + className + '"': '') +'>' +
                            '<a href="javascript:'+opts.pageClickFunctionName+'(' + pageNo + ')"><span>'+((text != null && text != '')? text: pageNo) + '</span></a>';
                }
                return numHTML;
            },

            getPageItemLink : function(pageNo, text, useLink, className) {
            	if(useLink) {
                    return '<li' + ((className != null && className != '')? ' class="' + className + '"': '') + '>' +
                            '<a href="javascript:'+opts.pageClickFunctionName+'(' + pageNo + ')">' +
                            ((text != null && text != '')? text: pageNo) +
                            '</a></li>\n';
                }
                else {
                    if(opts.showUnlinkedSymbols) {
                        return '<li>' +
                                    ((text != null && text != '')? text: pageNo) +
                                '</li>\n';
                    } else {
                        return '\n';
                    }
                }
            }

        };
        action.init();
    };

    $.fn.commonPaging.defaults = {
        totalSize : 0,
        pageNo : 1,
        pageSize : 10,  // 글 목록 갯수
        pageListSize : 5,  //페이지 갯수
        pageClickFunctionName : 'page_click',
        showUnlinkedSymbols : true
    };

})(jQuery);