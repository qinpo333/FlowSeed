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
    <link rel="stylesheet" href="../assets/css/index.min.css"/>

    <style>
        .tool-item{width: 33.33%; text-align: center;}
        .tool-item>img{width: 70%;}
        .tool-item>span>img {  width: 42%;  }
    </style>
</head>
<body>
<div class="ground">
    <div class="ground-back">
        <img class="noTouch" data-src="bg-part1.png">
        <div class="client">
            <div class="userInfo">
                <div class="user-photo-wrap">
                    <img src="" class="userPhoto">
                    <div class="userName"></div>
                </div>
                <div class="bars">
                    <div class="user-bar">
                        <div class="bar-part cj-left"></div>
                        <div class="">成就：<span id="user_achievements"></span></div>
                    </div>
                    <#--<div class="user-bar">-->
                        <#--<div class="bar-part exp-left"></div>-->
                        <#--<div class="">经验值：<span class="user-exp-wrap"><span id="user_exp"></span></span></div>-->
                    <#--</div>-->
                </div>
            </div>
            <a href="help.html"><div class="icon-help"></div></a>
        </div>
        <img class="noTouch flag-img" data-src="flag.png">
    </div>
    <div class="ground-bottom">
        <img class="noTouch" data-src="bg-part2.png">
        <div class="ground-grid">
            <img data-src="ground.png">
            <div class="trees">
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
                <div class="tree">
                    <img class="plant" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==">
                </div>
            </div>
        </div>
    </div>

    <div class="tools">
        <div class="tool-item" id="tool-water">
            <img src="../assets/imgs/water.png">
                <span>
                    <img src="../assets/imgs/water-text.png">
                </span>
        </div>
        <div class="tool-item" id="tool-rob">
            <img src="../assets/imgs/rob.png">
                <span>
                    <img src="../assets/imgs/rob-text.png" style="width: 60%;margin-top: -8%;">
                </span>
        </div>
        <div class="tool-item" id="tool-home" style="display: none">
            <img src="../assets/imgs/gohome.png">
                <span>
                    <img src="../assets/imgs/gohome-text.png">
                </span>
        </div>
        <div class="tool-item" id="tool-join" style="display: none">
            <img src="../assets/imgs/join.png">
                <span>
                    <img src="../assets/imgs/join-text.png" style="width: 83%;margin-top: 4px;">
                </span>
        </div>
    </div>
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

<script>
    var baseUrl = "../assets/imgs/";
//    var ctx = "http://mockapi.ngrok.cc/mock/seeds";
    var ctx = "${rc.contextPath}/seed/farm";
    var friendOpenId = "${friendOpenId}";

    var DIALOG_TEXT = {
        "WATER_SUCCESS": "多谢你为$$NAME$$的流量种子浇水！",
        "WATER_ALL_WATERED": "今天已经浇过水了！<br>请明天再来！",
        "WATER_OVER": "已达到浇水上限，请明天再来吧!",
        "WATER_SHARE": "浇水越多就能收获越多流量!",
        "WATER_DEAD": "不能给已经枯死的流量种子浇水！",
        "STEAL_DEAD": "不能偷已经枯死的流量种子！",
        "STEAL": "恭喜您成功偷到了<br>$$COUNT$$MB流量果实",
        "STEAL_NONE": "今天运气不好<br>请明天再来！",
        "STOLEN": "该好友已被偷过<br>请明天再来！",
        "STEAL_FAIL": "今天运气不好<br>请明天再来！",
        "STEAL_OVER": "今天偷流量次数达到上限<br>请明天再来！",
        "FAIL": "抱歉！网络繁忙~",
        "WATER_NO_PLANTS": "该好友还没有流量种子，不能浇水！",
        "STEAL_NO_PLANTS": "该好友还没有流量种子，不能偷流量！"
    };

    var DIALOG_ADS = {
        "WATER_SUCCESS": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "WATER_ALL_WATERED": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "WATER_OVER": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "WATER_SHARE": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "WATER_DEAD": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "STEAL": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "STEAL_NONE": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "STOLEN": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "STEAL_OVER": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "STEAL_FAIL": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "FAIL": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        }
    };
</script>

<script src="../assets/js/zepto.min.js"></script>
<script src="../assets/js/base.min.js"></script>
<script>
    $(document).on("touchmove", function(e){
        e.preventDefault();
        e.stopPropagation();
    });
</script>
<#--<script src="../src/js/friend.js"></script>-->
<script src="../assets/js/friend.min.js"></script>
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