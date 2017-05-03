/**
 * Created by cqb32_000 on 2016-01-06.
 */

(function(){
    var dialog_index = 0;
    var dialog = null;
    $(function(){

        mobileListener();

        $('.del-input').on('mousedown',function(){
            clearInput($('#tel-input'));
            setTimeout(function(){
                $('#tel-input').focus();
            });
        }).on('touchstart',function(){
            clearInput($('#tel-input'));
            setTimeout(function(){
                $('#tel-input').focus();
            });
        });

        verifyCodeListener();

        getVerifyCodeListener();

        submit();
    });

    /**
     * 验证码验证
     */
    function verifyCodeListener(){
        $('#code-input').on('input',function(){
            inputMaxBlur($(this), parseInt($(this).attr("maxlength")));
        }).on('blur',function(){
            validateCode($(this));
        });
    }

    /**
     * 获取验证码
     */
    function getVerifyCodeListener(){
        $('.get-code').on('click',function(){
            var that = this;
            var tel = trimAll($('#tel-input')[0].value);
            if ($(this).hasClass('btn-disabled')) {
                return false;
            } else {
                if ($('#tel-input').data('validate') === true) {
                    time($(that));
                    $('#code-tip').html('');
                    $(this).data('isClick',true);
                    $.post(ctx + '/code', {tel: tel},function(){
                    });
                } else {
                    if ($('#tel-input').val() === '') {
                        $('#tel-tip').css('color','red').html('请输入手机号码！');
                    }
                }
            }
        });
    }

    /**
     * 手机号码框
     */
    function mobileListener(){
        $('#tel-input').on('focus',function(){
            showAndHide($(this));
        }).on('input',function(){
            var val = $(this).val();
            val = val.replace(/[^0-9-]+/, "");
            $(this).val(val);

            var scope = this;
            window.setTimeout(function(){
                fillTelSpace($(scope));
            },1);
            inputMaxBlur($(this), parseInt($(this).attr("maxlength")));
            showAndHide($(this));
        }).on('blur',function(){
            $('.del-input').addClass('hide');
            validateTel($(this));
        });
    }

    /**
     * form validate
     */
    function isValidate() {
        validateTel($('#tel-input'));
        var telvalidate = $('#tel-input').data('validate') === true;
        if(!telvalidate){
            return false;
        }
        validateCode($('#code-input'));
        var codevalidate = $('#code-input').data('validate') === true;

        return telvalidate && codevalidate;
    }

    /**
     * 提交绑定
     */
    function submit(){
        $('#submit-btn').on('click',function(){
            var tel = trimAll($('#tel-input')[0].value);
            var code = $('#code-input').val();
            if(isValidate()){

                showToast();

                if(!dialog){
                    dialog = buildTipDialog("");
                }

                $.ajax({
                    url: ctx + '/bind',
                    type: 'POST',
                    dataType: 'json',
                    data: {tel: tel,code: code},
                    success: function(data) {
                        hideToast();
                        if (data.isUsed) {
                            updateDialogText(dialog, DIALOG_TEXT.USED, DIALOG_ADS.USED);
                        } else {
                            if (data.success) {
                                updateDialogText(dialog, DIALOG_TEXT.SUCCESS, DIALOG_ADS.SUCCESS);
                            } else {
                                updateDialogText(dialog, DIALOG_TEXT.ERROR, DIALOG_ADS.ERROR);
                                $('#code-input').val("");
                            }
                        }
                    },
                    error: function() {
                        hideToast();
                        updateDialogText(dialog, DIALOG_TEXT.NET_ERROR, DIALOG_ADS.NET_ERROR);
                    }
                });
            }
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
     * 结果显示弹框
     * @param clazz
     */
    function showDialogContent(clazz) {
        $('#result-alert .modal-content').addClass('hide');
        $('.body-' + clazz).removeClass('hide');
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
     * 是否输入验证码
     * @param ele
     */
    function validateCode(ele) {
        var code = ele.val();
        var validate = false;
        if (!code) {
            $('#code-tip').css('color', 'red').html('请输入验证码！');
        } else if (/^[0-9]{6}$/.test(code)) {
            $('#code-tip').html('');
            validate = true;
        } else {
            $('#code-tip').css('color', 'red').html('验证码错误！');
        }
        ele.data('validate', validate);
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
            $('#tel-tip').css('color','red').html('请输入手机号码！');
        } else if($.inArray(telPref, yd) == -1){
            $('#tel-tip').css('color','red').html('请输入移动手机号码！');
        }else if (/^1[3|4|5|7|8]\d{9}$/.test(tel)) {
            $('#tel-tip').css('color','#aaa').html('');
            validate = true;
        } else {
            $('#tel-tip').css('color','red').html('请输入正确的手机号码！');
        }
        ele.data('validate', validate);
    }

    /**
     * 清空按钮显示与隐藏
     * @param ele
     */
    function showAndHide(ele) {
        if (ele.val() === '') {
            $('.del-input').addClass('hide');
        } else {
            $('.del-input').removeClass('hide');
        }
    }

    /**
     * 去除字符串中的所有空格
     * @param str
     * @returns {*|{by}|XML|void|string}
     */
    function trimAll(str) {
        return str.replace(/[-]/g,'');
    }

    //清空输入框
    function clearInput(ele) {
        ele.val('');
        $('#tel-from').html('');
        $('.del-input').addClass('hide');
    }

    //倒计时
    var timer;
    var wait = 60;
    function time(ele) {
        if (wait === 0) {
            clearTimeout(timer);
            ele.removeClass('btn-disabled');
            ele.html('重新发送验证码');
            wait = 60;
        } else {
            ele.addClass('btn-disabled');
            ele.html('重新获取'+'(' + wait + ')');
            wait--;
            timer = setTimeout(function(){
                time(ele);
            }, 1000);
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

        if(msg == DIALOG_TEXT.ERROR || msg == DIALOG_TEXT.NET_ERROR){
            $(".modal-tools a", dialog).html('再试一次');
        }else{
            $(".modal-tools a", dialog).html('确 定');
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

        $(".btn-img", dialog).on("tap", function(){
            dialog.modal("hide");
            if($(".modal-text", dialog).html() == DIALOG_TEXT.USED){
                window.location.reload();
            }
            if($(".modal-text", dialog).html() == DIALOG_TEXT.SUCCESS){
                window.location.href = "./index.html";
            }
        });
        return dialog;
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
})();

