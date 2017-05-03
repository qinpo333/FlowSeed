/**
 * Created by cqb32_000 on 2016-02-16.
 */
(function(){
    var total = 0, dialog_index = 0;
    var tipDialog = null;

    //事件监听
    initListeners();
    //获取用户流量
    getUserFlow();

    /**
     * 事件监听
     */
    function initListeners(){
        $("#amount").on("input", function(e){
            validInput(this);
        });

        $("#confirm-btn").on("tap", function(){
            doGive();
        });
    }

    /**
     * 验证赠送的额度
     */
    function validInput(ele){
        var val = $(ele).val();

        val = val.replace(/[^0-9]/g, "");
        $(ele).val(val);
    }

    /**
     * 获取用户流量
     * @param callback
     */
    function getUserFlow(callback){
        $.ajax({
            url: ctx + '/getUserFlow?_=' + new Date(),
            type: 'get',
            dataType: 'json',
            success: function(data) {
                total = data.flow;
                $('#flow').text(data.flow || 0);
            }
        });
    }

    /**
     * 开始赠送
     */
    function doGive(){
        var amount = parseInt($("#amount").val());
        if(isNaN(amount)){
            $("#error-tip").html("请填写赠送额度");
        }else if(amount === 0){
            $("#error-tip").html("请填写赠送额度");
        } else if(amount > total){
            $("#error-tip").html("额度超出范围");
        }else{
            $("#error-tip").html("");
            if(!tipDialog){
                tipDialog = buildTipDialog();
            }
            var msg = DIALOG_TEXT.GUIDE.replace("$$amount$$", amount);
            updateDialogText(tipDialog, msg, DIALOG_ADS.GUIDE);

            if(window.afterGive) {
                afterGive();
            }
        }
    }

    /**
     * 更新弹框文案
     * @param dialog
     * @param msg
     * @param ads
     */
    function updateDialogText(dialog, msg, ads){
        $(".modal-text", dialog).html(msg);
        var src = $(".modal-body img", dialog).attr("src");
        if(src != ads.img) {
            $(".modal-body", dialog).empty();
            $(".modal-body", dialog).append('<a href="'+ads.link+'" target="_blank"><img src="' + ads.img + '"></a>');
        }
        dialog.modal("show");
    }

    /**
     * 创建dialog
     * @param cback
     * @returns {*|jQuery|HTMLElement}
     */
    function buildDialog(cback){
        var ele = $('<div class="modal fade text-center" data-backdrop="static">'+
            '<div class="modal-dialog">'+
            '<div class="modal-content">'+
            '<div class="modal-body">'+
            '</div>'+
            '<div class="modal-text">'+
            '</div>'+
            '<div class="modal-tools">'+
                '<a class="btn btn-img">关 闭</a>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>');

        if(cback){
            cback(ele);
        }

        dialog_index++;
        ele.attr("id", "share_dialog");

        var screenW = document.documentElement.clientWidth;
        var dialogW = screenW * 0.85;
        var dialogH = dialogW ;

        $(".modal-dialog", ele).height(dialogH);

        $("body").append(ele);

        return ele;
    }

    /**
     * 创建确认兑换弹框
     */
    function buildTipDialog(msg){
        var dialog = buildDialog(function(ele){
            $(".modal-text", ele).html(msg);
        });

        dialog.modal();

        $(".btn", dialog).on("tap", function(){
            if(window.afterCloseShareDialog) {
                window.afterCloseShareDialog();
            }
            dialog.modal("hide");
        });

        return dialog;
    }

    /**
     * 重置
     */
    window.reset = function(){
        if(tipDialog){
            tipDialog.modal("hide");
        }

        $("#amount").val("");
        //获取用户流量
        //getUserFlow();
    };
})();