<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>兑换</title>

    <link rel="stylesheet" href="../assets/css/seeds.min.css"/>
    <link rel="stylesheet" href="../assets/css/weui_toast.min.css"/>

    <style>
        .bg-img{  position: absolute;  top: 0;  left: 0; z-index: 0;}
        body{ background-color: #b1e5e4;  font-family: "Microsoft YaHei";}
        .form{position: relative; background: url(../assets/imgs/form-bg.png); background-size: 100% 100%; margin: 0 auto; width: 90%; margin-top: 2rem; z-index: 10;
            padding: 2rem; font-size: 1.4rem; color: #5a320b;}
        .form-row{margin: 1rem 0; position: relative;}
        .form-row label{font-weight: 400; }
        .tip-color{color: #996c42; font-size: 1rem;}
        #tel-input{height: 3rem; font-size: 1.4rem; color: #6a3906; background: white; border: none; border-radius: 4px; text-indent: 10px; outline: none;  }
        .package-size{float: left; display: inline-block; width: 27%; margin: 0 7px; background-color: white; border-radius: 3px; text-align: center; padding: 8px;
            font-weight: 700; }
        .package-size.active{background-image: url(../assets/imgs/selected.png); background-position: right top; background-size: 24px 24px; background-repeat: no-repeat;}
        .package-size.disabled{background-color: #fef6d9; color: #c9aa79;}
        .tel-tip{  position: absolute;  font-size: 1rem;  left: 6rem; top: 3.2rem;}
        .buttons-wrap{
            position: absolute; width: 80%; bottom: 22%; left: 10%; overflow: hidden;
            background: url(../assets/imgs/buttons-bg.png); background-size: 100% 100%;  padding: 3px 1px;
        }
        .buttons-wrap .btn{width: 48%; padding: 8px 0; height: auto; line-height: normal; margin: 0 1%;}
        .btn-ok{background: url(../assets/imgs/btn-bg-left.png); background-size: 100% 100%;}
        .btn-cancel{background: url(../assets/imgs/btn-bg-cancel.png); background-size: 100% 100%;}
        .modal-body{min-height: 120px;}
    </style>
</head>
<body>
    <img class="bg-img" data-src="warehouse.png">

    <div class="form">
        <div class="form-row" style="margin-bottom: 1.8rem; height: 3rem;">
            <label>手机号码:</label>
            <input id="tel-input" type="tel" maxlength="13" autocomplete="on">
            <label class="tel-tip"></label>
        </div>
        <div class="form-row">
            <label>充值额度:</label>
            <span class="tip-color">(你可以兑换<span id="flow">--</span>MB流量)</span>
        </div>
        <div class="form-row" id="flow-package">
            <ul style="overflow: hidden; padding: 0;">
                <#--<li class="package-size" data-value="10">10M</li>-->
                <li class="package-size" data-value="30">30M</li>
                <li class="package-size" data-value="500">500M</li>
            </ul>
        </div>
    </div>

    <div style=" position: relative;" class="mt-20 text-center">
        <img src="../assets/imgs/btn-bg.png" class="btn-bg">
        <div class="btn-text" id="confirm-btn">确定兑换</div>
    </div>

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


    <div id="tipToast" class="weui_loading_toast">
        <div class="weui_mask_transparent"></div>
        <div class="tip_weui_toast">
            <p id="tip_toast_content"></p>
        </div>
    </div>

    <script>
        var baseUrl = "../assets/imgs/";
//        var ctx = "http://mockapi.ngrok.cc/mock/seeds";
        var ctx = "${rc.contextPath}/seed/farm";

        var DIALOG_TEXT = {
            CONFIRM: "确认兑换$$FLOW$$MB<br>到手机号：$$PHONE$$",
            SUCCESS: "兑换成功！具体到账以短信为准。",
            ERROR: "兑换失败!",
            NET_ERROR: "网络异常!"
        };

        var DIALOG_ADS = {
            "CONFIRM": {
                img: baseUrl + "dialog_ad.jpg",
                link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
            },
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
            }
        };
    </script>

    <script src="../assets/js/zepto.min.js"></script>
    <script src="../assets/js/base.min.js"></script>
    <script src="../assets/js/exchange.min.js"></script>
    <#--<script src="../src/js/exchange.js"></script>-->
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