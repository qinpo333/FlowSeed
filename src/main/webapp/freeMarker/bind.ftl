<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>手机绑定</title>
    <link rel="stylesheet" href="../assets/css/seeds.min.css"/>
    <link rel="stylesheet" href="../assets/css/weui_toast.min.css"/>
    <style type="text/css">
        .bg-img{  position: absolute;  top: 0;  left: 0; z-index: 0;}
        body{ background-color: #b1e5e4;  font-family: "Microsoft YaHei";}
        .form-control:focus {
            border-color: #ccc;
            outline: 0;
        }
        .input-box-group{margin: 30px 0 20px 0;}
        #tel-box,#code-box{margin-bottom: 10px;}
        .form{position: relative; background: url(../assets/imgs/form-bg.png); background-size: 100% 100%; margin: 0 auto; width: 90%; margin-top: 2rem; z-index: 10;
            padding: 1.5rem; font-size: 1.4rem; color: #5a320b;}
        .input-group-addon{background-color: white; color: #000; border: 1px solid #ccc; font-size: 12px; }
        .input-group-addon:last-child{border-left: 1px solid #aaa;}
        /*.separator{display: inline-block; float: left; height: 30px; border-left: 1px solid #aaa; line-height: 30px; padding-left: 5px; }*/
    </style>
</head>
<body>
<img class="bg-img" data-src="warehouse.png">
<div class="form">
    <div class="input-box-group">
        <div id="tel-box">
            <input class="form-control" id="tel-input" type="tel" maxlength="13" placeholder="请输入手机号码" autocomplete="on">
            <span id="tel-from"></span>
            <span class="del-input hide"></span>
            <label id="tel-tip"></label>
        </div>
        <div id="code-box">
            <div class="input-group">
                <input class="form-control" id="code-input" type="tel" maxlength="6" placeholder="请输入验证码" aria-describedby="basic-addon2">
                    <span class="input-group-addon get-code" id="basic-addon2" data-isClick="false">
                        获取验证码
                    </span>
            </div>
            <label id="code-tip"></label>
        </div>
    </div>
</div>

<div style=" position: relative;" class="mt-20 text-center" id="submit-btn">
    <img src="../assets/imgs/btn-bg.png" class="btn-bg">
    <div class="btn-text" id="confirm-btn">绑 定</div>
</div>

<!-- 已经绑定弹框 -->
<div id="isUsed" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body text-center">
                <p class="text-center">该手机号已经绑定！</p>
            </div>
            <div class="modal-footer" id="clear">
                <p class="footer-color"><a style="display: block;" data-dismiss="modal">确 定</a></p>
            </div>
        </div>
    </div>
</div><!-- 已经绑定弹框 end -->

<!-- 绑定结果弹框 -->
<div id="result-alert" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content body-success hide">
            <div class="modal-body">
                <p>绑定成功！</p>
            </div>
            <div class="modal-footer" id="success-btn">
                <p class="footer-color"><a style="display: block;" data-dismiss="modal">确 定</a></p>
            </div>
        </div>
        <div class="modal-content body-failure hide">
            <div class="modal-body">
                <p>绑定失败，请重新获取验证码！</p>
            </div>
            <div class="modal-footer" id="fail-btn">
                <p class="footer-color"><a style="display: block;" data-dismiss="modal">再试一次</a></p>
            </div>
        </div>
        <div class="modal-content body-busy hide">
            <div class="modal-body">
                <p>网络繁忙</p>
            </div>
            <div class="modal-footer">
                <p class="footer-color"><a style="display: block;" data-dismiss="modal">再试一次</a></p>
            </div>
        </div>
    </div>
</div><!-- 绑定结果弹框 end -->


<!-- loading -->
<div id="loadingToast" class="weui_loading_toast">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <div class="weui_loading">
            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
        </div>
        <p class="weui_toast_content">数据加载中</p>
    </div>
</div><!-- loading end -->

<script>
//    var ctx = 'http://mockapi.ngrok.cc/mock/seeds';
    var ctx = "${rc.contextPath}/seed/farm";
    var baseUrl = "../assets/imgs/";

    var DIALOG_TEXT = {
        SUCCESS: "绑定成功！",
        ERROR: "绑定失败，请重新获取验证码！",
        NET_ERROR: "网络繁忙!",
        USED: "该手机号已经绑定！"
    };

    var DIALOG_ADS = {
        "SUCCESS": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "ERROR": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "NET_ERROR": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "USED": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        }
    };
</script>
<script src="../assets/js/zepto.min.js"></script>
<script src="../assets/js/base.min.js"></script>
<script src="../assets/js/bindTel.min.js"></script>
<#--添加微信js文件-->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    wx.config({
        debug : false,
        appId : '${wxconfig.appid!}',
        timestamp : ${wxconfig.timestamp!},
        nonceStr : '${wxconfig.nonceStr!}',
        signature : '${wxconfig.signature!}',
        jsApiList : [ 'onMenuShareAppMessage',
            'onMenuShareTimeline']
        // 功能列表，我们要使用JS-SDK的什么功能
    });

    function initCommon() {
        // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title : '快来流量农场种流量、收流量啦', // 分享标题
            desc : "我刚刚在中国移动10086微信号流量农场收获了流量，你也快来试试吧！", // 分享描述
        <#--link : "http://wx.10086.cn/lottery/seed/farm/login?state=${openid!}",-->
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link', // 分享类型,music、video或link，不填默认为link
//            trigger: function (res) {
//                alert('用户点击发送给朋友');
//            },
            success: function (res) {
                $.ajax({
                    url: ctx + "/share/0",
                    type: "GET",
                    dataType: "json",
                    success: function () {

                    }
                });
            }
//            cancel: function (res) {
//                alert('已取消');
//            }
        });
        wx.onMenuShareTimeline({
            title : '快来流量农场种流量、收流量啦', // 分享标题
            desc : "我刚刚在中国移动10086微信号流量农场收获了流量，你也快来试试吧！", // 分享描述
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
        <#--link : "http://wx.10086.cn/lottery/seed/farm/login?state=${openid!}",-->
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link',// 分享类型,music、video或link，不填默认为link
//            trigger: function (res) {
//                alert('用户点击发送给朋友');
//            },

            success: function (res) {
                $.ajax({
                    url: ctx + "/share/1",
                    type: "GET",
                    dataType: "json",
                    success: function () {

                    }
                });
            }

//            cancel: function (res) {
//                alert('已取消');
//            }
        });
    }

    function initWater(flow,matureDays) {
        var title = "${title!}";

        if(flow && matureDays) {
            title = "我正在种植" + flow +
                    "M流量，还有" + matureDays + "天就成熟啦，快来帮我浇水吧！"
        }
        // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title : title, // 分享标题
            desc : "中国移动10086微信全新推出流量农场，种植免费流量哦，快来试试吧！", // 分享描述
        <#--link : "http://wx.10086.cn/lottery/seed/farm/login?state=${openid!}",-->
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link', // 分享类型,music、video或link，不填默认为link
//            trigger: function (res) {
//                alert('用户点击发送给朋友');
//            },
            success: function (res) {
                $.ajax({
                    url: ctx + "/share/0",
                    type: "GET",
                    dataType: "json",
                    success: function () {

                    }
                });
            }
//            cancel: function (res) {
//                alert('已取消');
//            }
        });
        wx.onMenuShareTimeline({
            title : title, // 分享标题
            desc : "中国移动10086微信全新推出流量农场，种植免费流量哦，快来试试吧！", // 分享描述
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
        <#--link : "http://wx.10086.cn/lottery/seed/farm/login?state=${openid!}",-->
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link',// 分享类型,music、video或link，不填默认为link
//            trigger: function (res) {
//                alert('用户点击发送给朋友');
//            },

            success: function (res) {
                $.ajax({
                    url: ctx + "/share/1",
                    type: "GET",
                    dataType: "json",
                    success: function () {

                    }
                });
            }

//            cancel: function (res) {
//                alert('已取消');
//            }
        });
    }

    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在 页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready 函数中。
    wx.ready(function() {
        if("${hasPlant?c}" == 'true'){
            initWater();
        } else {
            initCommon();
        }
//        init();
    });
</script>
</body>
</html>