<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>流量农场</title>

    <link rel="stylesheet" href="../assets/css/seeds.min.css"/>
    <link rel="stylesheet" href="../assets/css/weui_toast.min.css"/>

    <style>
        .bg-img{  position: absolute;  top: 0;  left: 0; z-index: 0;}
        body{ background-color: #b1e5e4;  font-family: "mini";}
        .form{position: relative; min-height: 200px; line-height: 30px; background: url(../assets/imgs/form-bg.png); background-size: 100% 100%; margin: 0 auto; width: 90%; margin-top: 2rem; z-index: 10;
            padding: 2rem; font-size: 1.4rem; color: #5a320b; padding-top: 4rem;}
        .form-row{margin: 1.5rem 0; position: relative;}
        .form-row label{font-weight: 400; }
    </style>
</head>
<body>
    <img class="bg-img" data-src="warehouse.png">

    <div class="form">
        <div class="form-row text-center">
            <#--恭喜你，你的好友XXX<br>送给你XXXMB流量<br>-->
            <#--该链接已经失效<br>-->
            <#--流量已经被其他人抢走啦~-->
            ${msg!"流量已经被其他人抢走啦~"}
        </div>
    </div>

    <div style=" position: relative;" class="mt-20 text-center">
        <a href="./index.html">
        <img src="../assets/imgs/btn-bg.png" class="btn-bg">
        <div class="btn-text" id="confirm-btn">确 定</div>
        </a>
    </div>

    <script>
        var baseUrl = "../assets/imgs/";
        var ctx = "http://mockapi.ngrok.cc/mock/seeds";
    </script>

    <script src="../assets/js/zepto.min.js"></script>
    <script src="../assets/js/base.min.js"></script>
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