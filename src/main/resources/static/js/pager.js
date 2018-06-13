/**
 * jquery.pager.js v0.1.0
 * MIT License
 * for more info pls visit: https://github.com/LuoPQ/jquery.pager.js
 */
; (function ($, window, document, undefined) {
    "use strict";
    var defaults = {
        pageIndex: 0,
        pageSize: 6,
        itemCount: 50,
        maxButtonCount: 7,
        prevText: "��һҳ",
        nextText: "��һҳ",
        buildPageUrl: null,
        onPageChanged: null
    };

    function Pager($ele, options) {
        this.$ele = $ele;
        this.options = options = $.extend(defaults, options || {});
        this.init();
    }
    Pager.prototype = {
        constructor: Pager,
        init: function () {
            this.renderHtml();
            this.bindEvent();
        },
        renderHtml: function () {
            var options = this.options;

            options.pageCount = Math.ceil(options.itemCount / options.pageSize);
            var html = [];

            //������һҳ�İ�ť
            if (options.pageIndex > 0) {
                html.push('<a page="' + (options.pageIndex - 1) + '" href="' + this.buildPageUrl(options.pageIndex + 1) + '" class="flip">' + options.prevText + '</a>');
            } else {
                html.push('<span class="flip noPage">' + options.prevText + '</span>');
            }

            //�����ǹؼ�
            //��ʱ����ʼҳ���м�ҳ�룬��ҳ������������ʾ�����ť��ʱʹ��
            var tempStartIndex = options.pageIndex - Math.floor(options.maxButtonCount / 2) + 1;

            //������ֹҳ�룬ͨ��max����һ�Ű�ť�еĵ�һ����ť��ҳ�룬Ȼ������ҳ����
            var endIndex = Math.min(options.pageCount, Math.max(0, tempStartIndex) + options.maxButtonCount) - 1;
            var startIndex = Math.max(0, endIndex - options.maxButtonCount + 1);

            // ��һҳ
            if (startIndex > 0) {
                html.push("<a href='" + this.buildPageUrl(0) + "' page='" + 0 + "'>1</a> ");
                html.push("<span>...</span>");
            }

            //����ҳ�밴ť
            for (var i = startIndex; i <= endIndex; i++) {
                if (options.pageIndex == i) {
                    html.push('<span class="curPage">' + (i + 1) + '</span>');
                } else {
                    html.push('<a page="' + i + '" href="' + this.buildPageUrl(options.pageIndex + 1) + '">' + (i + 1) + '</a>');
                }
            }

            // ���һҳ
            if (endIndex < options.pageCount - 1) {
                html.push("<span>...</span> ");
                html.push("<a href='" + this.buildPageUrl(options.pageCount - 1) + "' page='" + (options.pageCount - 1) + "'>" + options.pageCount + "</a> ");
            }

            //������һҳ�İ�ť
            if (options.pageIndex < options.pageCount - 1) {
                html.push('<a page="' + (options.pageIndex + 1) + '" href="' + this.buildPageUrl(options.pageIndex + 1) + '" class="flip">' + options.nextText + '</a>');
            } else {
                html.push('<span class="flip noPage">' + options.nextText + '</span>');
            }

            this.$ele.html(html.join(""));
        },
        bindEvent: function () {
            var that = this;
            that.$ele.on("click", "a", function () {
                that.options.pageIndex = parseInt($(this).attr("page"), 10);
                that.renderHtml();
                that.options.onPageChanged && that.options.onPageChanged(that.options.pageIndex);
            })
        },
        buildPageUrl: function () {
            if ($.isFunction(this.options.buildPageUrl)) {
                return this.options.buildPageUrl(pageIndex);
            }
            return "javascript:;";
        },
        getPageIndex: function () {
            return this.options.pageIndex;
        },
        setPageIndex: function (pageIndex) {
            this.options.pageIndex = pageIndex;
            this.renderHtml();
        },
        setItemCount: function (itemCount) {
            this.options.pageIndex = 0;
            this.options.itemCount = itemCount;
            this.renderHtml();
        }
    };


    $.fn.pager = function (options) {
        options = $.extend(defaults, options || {});

        return new Pager($(this), options);
    }

})(jQuery, window, document);