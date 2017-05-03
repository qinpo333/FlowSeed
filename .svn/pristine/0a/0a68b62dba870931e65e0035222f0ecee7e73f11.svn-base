/* 
* @Author: anchen
* @Date:   2015-07-20 14:12:50
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-07 13:34:54
*/

(function(){
    'use strict';
    $.fn.modal = function(options){
        var args = arguments;
        var Modal = function (element, options) {
            this._ele = typeof element == 'string' ? $("#"+element) : element;
            $.extend(this, options || {});

            if(!$(".modal-backdrop").length){
                $("body").append('<div class="modal-backdrop fade"></div>');
            }

            this.backdrop = this._ele.data("backdrop");

            this._listeners();
        };

        Modal.prototype = {
            show: function(){
                $('body').addClass("modal-open");
                $('.modal-backdrop').show();
                $('.modal-backdrop').attr("data-target", this._ele.attr("id"));
                var scope = this;
                window.setTimeout(function(){
                    scope._ele.show();
                    scope._ele.addClass("in");
                    $('.modal-backdrop').addClass("in");
                }, 50);
            },
            hide: function(){
                $('body').removeClass("modal-open");
                var scope = this;
                scope._ele.removeClass("in");
                $('.modal-backdrop').removeClass("in");
                window.setTimeout(function(){
                    scope._ele.hide();
                    if($('.modal-backdrop').attr("data-target") == scope._ele.attr("id")) {
                        $('.modal-backdrop').hide();
                    }
                }, 400);
            },

            _listeners: function(){
                var scope = this;
                $('a[data-dismiss="modal"]', this._ele).on("tap", function(){
                    scope.hide();
                });
                this._ele.on("tap", function(e){
                    if(scope.backdrop && (scope.backdrop == "true" || scope.backdrop === true)){
                        if(!$(e.target).parents(".modal-content").length && !$(e.target).hasClass("modal-content")){
                            scope.hide();
                        }
                    }
                });
            }
        };

        return this.each(function(){
            if(this.ins && typeof options == 'string'){
                var method = Array.prototype.splice.apply(args,[0,1])[0];
                if(typeof this.ins[method] == 'function'){
                    this.ins[method].apply(this.ins, args);
                }
            }else{
                this.ins = new Modal($(this), options);
            }
        });
    };

    $(".modal").modal();
})();