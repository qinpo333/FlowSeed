/**
 * Created by cqb32_000 on 2016-01-19.
 */
(function(){

    var total = 0;
    var dialog_index = 0;
    var confirmDialog = null;
    var tipDialog = null;

    mobileListener();

    packagesListener();

    submitListener();

    /**
     * 提交
     */
    function submitListener(){
        $("#confirm-btn").on("click", function(){
            exchangeValid();
        });
    }

    /**
     * 兑换验证
     */
    function exchangeValid(){
        var telEle = $('#tel-input');
        validateTel(telEle);
        var valid = telEle.data("validate");
        if(!valid){
            return false;
        }
        var flow = $('.package-size.active').data("value");
        if(!flow){
            $("#tip_toast_content").html("请选择流量包");
            $("#tipToast").show();
            window.setTimeout(function(){
                $("#tipToast").hide();
            }, 2000);
            return false;
        }

        //验证通过
        var tel = trimAll(telEle.val());

        var msg = DIALOG_TEXT.CONFIRM.replace("$$FLOW$$", flow).replace("$$PHONE$$", tel);
        if(!confirmDialog){
            confirmDialog = buildConfirmDialog("");
        }

        updateDialogText(confirmDialog, msg, DIALOG_ADS.CONFIRM);
        confirmDialog.tel = tel;
        confirmDialog.flow = flow;
    }

    /**
     * 请求进行兑换
     */
    function doExchange(){
        var tel = confirmDialog.tel;
        var flow = confirmDialog.flow;
        showToast();

        if(!tipDialog){
            tipDialog = buildTipDialog();
        }
        $.ajax({
            url: ctx + '/exchange',
            type: 'POST',
            dataType: 'json',
            data: {tel: tel, flow: flow},
            success: function(data) {
                hideToast();
                if (data.success) {
                    updateDialogText(tipDialog, DIALOG_TEXT.SUCCESS, DIALOG_ADS.SUCCESS);
                } else {
                    updateDialogText(tipDialog, DIALOG_TEXT.ERROR, DIALOG_ADS.ERROR);
                }
            },
            error: function() {
                hideToast();
                updateDialogText(tipDialog, DIALOG_TEXT.NET_ERROR, DIALOG_ADS.NET_ERROR);
            }
        });
    }

    /**
     * 流量包选中事件
     */
    function packagesListener(){
        $("#flow-package>ul").on("tap", ".package-size", function(){
            if($(this).hasClass("disabled") || $(this).hasClass("active")){
                return false;
            }else{
                $(".package-size.active").removeClass("active");
                $(this).addClass("active");
            }
        });
    }

    //获取用户流量
    getUserFlow(function(){
        filterPackage();
    });

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
                $('#flow').text(total || 0);
                if(callback){
                    callback();
                }
            }
        });
    }

    /**
     * 过滤充值包
     */
    function filterPackage(){
        $(".package-size").each(function(){
            var size = parseInt($(this).data("value"));
            if(size > total){
                $(this).addClass("disabled");
            }
        });
    }

    /**
     * 手机号码框
     */
    function mobileListener(){
        $('#tel-input').on('input',function(){

            var val = $(this).val();
            val = val.replace(/[^0-9-]+/, "");
            $(this).val(val);

            var scope = this;
            window.setTimeout(function(){
                fillTelSpace($(scope));
            },1);
            inputMaxBlur($(this), parseInt($(this).attr("maxlength")));
        }).on('blur',function(){
            validateTel($(this));
        });
    }

    /**
     * 手机号中加入空格
     * @param ele
     */
    function fillTelSpace(ele) {
        var tel = trimAll(ele[0].value);
        if (tel !== '') {
            var telLength = tel.length;
            if (telLength <= 3) {
                ele[0].value = tel;
            } else if (telLength <= 7) {
                ele[0].value = tel.substring(0, 3) + '-' + tel.substring(3, telLength);
            } else {
                ele[0].value = tel.substring(0, 3) + '-' + tel.substring(3, 7) + '-' + tel.substring(7, telLength);
            }
        }
    }

    /**
     * 输入字符数达到限制自动失去焦点
     * @param ele
     * @param num
     */
    function inputMaxBlur(ele, num) {
        if (ele.val().length === num) {
            window.setTimeout(function(){
                ele.blur();
            },1);
        }
    }

    /**
     * 验证手机号码
     * @param ele
     */
    function validateTel(ele) {
        var tel = trimAll(ele[0].value);
        var validate = false;

        var yd = [134,135,136,137,138,139,147,150,151,152,157,158,159,182,183,184,187,188];/*移动号段*/
        var telPref = parseInt(tel.substr(0,3));
        if (!tel) {
            $('.tel-tip').css('color','red').html('请输入手机号码！');
        } else if($.inArray(telPref, yd) == -1){
            $('.tel-tip').css('color','red').html('请输入移动手机号码！');
        }else if (/^1[3|4|5|7|8]\d{9}$/.test(tel)) {
            $('.tel-tip').css('color','#aaa').html('');
            validate = true;
        } else {
            $('.tel-tip').css('color','red').html('请输入正确的手机号码！');
        }
        ele.data('validate', validate);
    }

    /**
     * 去除字符串中的所有空格
     * @param str
     * @returns {*|{by}|XML|void|string}
     */
    function trimAll(str) {
        return str.replace(/[-]/g,'');
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
                '<div class="buttons-wrap">'+
                    '<a class="btn btn-ok">确 定</a>'+
                    '<a class="btn btn-cancel">取 消</a>'+
                '</div>'+
            '</div>'+
            '</div>'+
            '</div>'+
        '</div>');

        if(cback){
            cback(ele);
        }

        dialog_index++;
        ele.attr("id", "dialog_"+dialog_index);

        var screenW = document.documentElement.clientWidth;
        var dialogW = screenW * 0.85;
        var dialogH = dialogW * 1.0945;

        $(".modal-dialog", ele).height(dialogH);

        $("body").append(ele);

        return ele;
    }

    /**
     * 创建确认兑换弹框
     */
    function buildConfirmDialog(msg){
        var dialog = buildDialog(function(ele){
            $(".modal-text", ele).html(msg);
        });

        dialog.modal();
        dialog.modal("show");

        $(".btn-ok", dialog).on("click", function(){
            doExchange();
            dialog.modal("hide");
        });
        $(".btn-cancel", dialog).on("click", function(){
            dialog.modal("hide");
        });
        return dialog;
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
     * 创建确认兑换弹框
     */
    function buildTipDialog(msg){
        var dialog = buildDialog(function(ele){
            $(".modal-tools", ele).empty();
            $(".modal-tools", ele).append('<a class="btn btn-img">确 定</a>');
            $(".modal-text", ele).html(msg);
        });

        dialog.modal();

        $(".btn-img", dialog).on("click", function(){
            if($(".modal-text", dialog).html() == DIALOG_TEXT.SUCCESS){
                history.go(-1);
            }
            dialog.modal("hide");
            //getUserFlow(function(){
            //    filterPackage();
            //});
        });
        return dialog;
    }
})();