/**
 * Created by lele on 2015/4/15.
 */
(function(){
    var devide = "ldp";
    var width = document.documentElement.clientWidth;
    var ratio = window.devicePixelRatio;
    if(ratio&&ratio<1.5){
        devide = "ldp";
    }
    if(ratio&&ratio>=1.5&&ratio<2.5){
        devide = "mdp";
    }
    if(ratio&&ratio>=2.5||width>640){
        devide = "hdp";
    }
    $("img[data-src]").each(function(){
        var src = $(this).data("src");
        $(this).attr("src", baseUrl + devide + "/" + src);
    });
    function is_weixin(){
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i)=="micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    if(is_weixin()){
        $('#flow-nav').hide();
        $('body').css('padding-top','0');
    }


    $('.btn[data-ajaxloading="true"]').on("click", function(){
        $('.btn[data-ajaxloading="true"].ajaxStarting').removeClass("ajaxStarting");
        $(this).addClass("ajaxStarting");
    });

    $(document).on("ajaxComplete",function(xhr, options){
        removeBtnLoadingText();
    }).on("ajaxError", function(xhr, options, error){
        updateBtnText(false);
    }).on("ajaxSuccess", function(evt, xhr, options, data){
        if(data && data.success) {
            updateBtnText(true);
        }else{
            updateBtnText(false);
        }
    }).on("ajaxBeforeSend", function(){
        var btn = $('.btn.ajaxStarting');
        btnTriggerAjax(btn);
    });

    /**
     * 触发ajax请求
     * @param ele
     */
    function btnTriggerAjax(ele){
        ele.addClass("ajaxLoading");
        var orig = ele.html();
        ele.data("orig", orig);
        ele.html(ele.data("loadingtext") || "加载中...");
    }

    /**
     * ajax结束后删除loading
     */
    function removeBtnLoadingText(){
        var btn = $(".btn.ajaxLoading");
        btn.removeClass("ajaxLoading");
    }

    /**
     * 更新按钮文字
     * @param flag
     */
    function updateBtnText(flag){
        var btn = $(".btn.ajaxLoading");
        if(flag) {
            btn.html(btn.data("successtext") || btn.data("orig"));
        }else{
            btn.html(btn.data("orig"));
        }
    }

    window.ajaxData = function(url, data, success, error, type){
        type = type || "POST";
        $.ajax({
            url: url,
            type: type,
            data: data,
            dataType: "json",
            success: function(ret){
                if(success) {
                    success(ret);
                }
            },
            error: function(e){
                if(error) {
                    error(e);
                }
            }
        });
    };
})();


//IOS页面双击上移问题
(function() {
    var agent = navigator.userAgent.toLowerCase();        //检测是否是ios
    var iLastTouch = null;                                //缓存上一次tap的时间
    if (agent.indexOf('iphone') >= 0 || agent.indexOf('ipad') >= 0)
    {
        document.body.addEventListener('touchend', function(event)
        {
            var iNow = new Date().getTime();
            iLastTouch = iLastTouch || iNow + 1 /** 第一次时将iLastTouch设为当前时间+1 */ ;
            var delta = iNow - iLastTouch;
            if (delta < 500 && delta > 0){
                event.preventDefault();
                return false;
            }
            iLastTouch = iNow;
        }, false);
    }
})();

/**
 * 显示
 */
function showToast(){
    $("#loadingToast").show();
}

/**
 * 隐藏
 */
function hideToast(){
    $("#loadingToast").hide();
}

