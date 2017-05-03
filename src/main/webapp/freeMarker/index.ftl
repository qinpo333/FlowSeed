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
                    <div class="user-bar">
                        <div class="bar-part llgs-left"></div>
                        <div class="">流量：<span class="user-flow-wrap"><span id="user_flow"></span>MB</span></div>
                    </div>
                    <div class="user-bar">
                        <div class="bar-part exp-left"></div>
                        <div class="">经验值：<span class="user-exp-wrap"><span id="user_exp"></span></span></div>
                    </div>
                </div>
            </div>
            <a href="${rc.contextPath}/seed/farm/help"><div class="icon-help"></div></a>
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
        <div class="tool-item" id="tool-friends">
            <img src="../assets/imgs/friends.png">
                <span>
                    <img src="../assets/imgs/friends-text.png">
                </span>
        </div>
        <div class="tool-item" id="tool-water">
            <img src="../assets/imgs/water.png">
                <span>
                    <img src="../assets/imgs/water-text.png">
                </span>
        </div>
        <div class="tool-item" id="tool-hand">
            <img src="../assets/imgs/hand.png">
                <span>
                    <img src="../assets/imgs/hand-text.png">
                </span>
        </div>
        <div class="tool-item" id="tool-home">
            <img src="../assets/imgs/home.png">
                <span>
                    <img src="../assets/imgs/home-text.png">
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
    <#--var ctx = "${rc.contextPath}/seed/farm";-->
    var ctx = "${rc.contextPath}/seed/farm";
    //用户是否绑定
    var isBind = true;

    var DIALOG_TEXT = {
        "WATER_SUCCESS": "恭喜你浇水成功！<br>再坚持$$DAYS$$就可以<br>收获$$AMOUNT$$MB流量了！",
        "WATER_ALL_WATERED": "今天已经浇过水了！<br>明天继续！",
        "WATER_HAS_MATURED": "成熟的植物不需要浇水!",
        "WATER_SHARE": "赶紧邀请好友浇水<br/>赢取经验值吧",
        "WATER_DEAD": "不能给已经枯死的流量种子浇水！",
        "HARVEST_SUCCESS": "恭喜您收获了<br>$$COUNT$$MB流量果实",
        "HARVEST_MATURE": "您的流量果实还需要$$DAYS$$<br>成熟,请继续努力！",
        "HARVEST_DEAD": "流量种子已经枯萎，下次记得常浇水哦！",
        "FAIL": "抱歉！网络繁忙~",
        "ERADICATE_SUCCESS": "已经铲除枯萎的植物重新种下了流量种子，记得勤浇水哦！",
        "NO_SEEDS": "种子已经领完请下次再来！",
        "GET_SEED": "恭喜你领取到了$$COUNT$$MB的流量种子,坚持浇水$$DAYS$$就可以收获流量哦！",
        "NO_PLANTS": "您还没有流量种子<br>下次早点去领取哦！"
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
        "WATER_HAS_MATURED": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "WATER_SHARE": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "HARVEST_SUCCESS": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "HARVEST_MATURE": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "FAIL": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "ERADICATE_SUCCESS": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        },
        "NO_SEEDS": {
            img: baseUrl + "dialog_ad.jpg",
            link: "http://wx.10086.cn/gfms/app/apk/html/4GDownload.html"
        }
    };

    /**
     * 关闭浇水分享回调函数
     */
    function afterCloseShareDialog(){
        init();
    }
</script>

<script src="../assets/js/zepto.min.js"></script>
<script src="../assets/js/base.min.js"></script>
<script>
    $(document).on("touchmove", function(e){
        e.preventDefault();
        e.stopPropagation();
    });
</script>
<script src="../assets/js/main.min.js"></script>
<#--<script src="../src/js/main.js"></script>-->
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

    function afterWater1(){
        $.ajax({
            url: ctx + "/afterWaterInit",
            type: "GET",
            dataType: "json",
            success: function () {
                afterWaterInit();
            }
        });
    }

    function afterWaterInit(){
        // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title : '${title!}', // 分享标题
            desc : "中国移动10086微信全新推出流量农场，种植免费流量哦，快来试试吧！", // 分享描述
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link', // 分享类型,music、video或link，不填默认为link
            //            trigger: function (res) {
            //                alert('用户点击发送给朋友');
            //            },
            success: function (res) {
                hideGuideDialog();
                $.ajax({
                    url: ctx + "/afterShareWater/0",
                    type: "GET",
                    dataType: "json",
                    success: function () {
                        var url = ctx + "/getUserFlow?_="+new Date().getTime();
                        ajaxData(url, null, function(ret){
                            $("#user_flow").html(ret.flow);
                            $("#user_exp").html(ret.exp);
                        },null,"GET");
                    }
                });
            }
        });
        wx.onMenuShareTimeline({
            title : '${title!}', // 分享标题
            desc : "中国移动10086微信全新推出流量农场，种植免费流量哦，快来试试吧！", // 分享描述
            link : "http://wx.10086.cn/lottery/seed/farm/friend?friendOpenId=${openId!}",
            imgUrl: "http://wx.10086.cn/lottery/seed/assets/imgs/logo.png",
            type : 'link',// 分享类型,music、video或link，不填默认为link
            //            trigger: function (res) {
            //                alert('用户点击发送给朋友');
            //            },

            success: function (res) {
                hideGuideDialog();
                $.ajax({
                    url: ctx + "/afterShareWater/1",
                    type: "GET",
                    dataType: "json",
                    success: function () {
                        var url = ctx + "/getUserFlow?_="+new Date().getTime();
                        ajaxData(url, null, function(ret){
                            $("#user_flow").html(ret.flow);
                        },null,"GET");
                    }
                });
            }

            //            cancel: function (res) {
            //                alert('已取消');
            //            }
        });
    }

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
        var nickname = $(".userName").text();
        if(flow && matureDays) {
            title = nickname + "正在种植" + flow +
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
                    url: ctx + "/afterShareWater/0",
                    type: "GET",
                    dataType: "json",
                    success: function () {
                        var url = ctx + "/getUserFlow?_="+new Date().getTime();
                        ajaxData(url, null, function(ret){
                            $("#user_flow").html(ret.flow);
                            $("#user_exp").html(ret.exp);
                        },null,"GET");
                    }
                });
            }
//            cancel: function (res) {
//                alert('已取消');
//            }
        });
        wx.onMenuShareTimeline({
            title : '${title!}', // 分享标题
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
                    url: ctx + "/afterShareWater/1",
                    type: "GET",
                    dataType: "json",
                    success: function () {
                        var url = ctx + "/getUserFlow?_="+new Date().getTime();
                        ajaxData(url, null, function(ret){
                            $("#user_flow").html(ret.flow);
                            $("#user_exp").html(ret.exp);
                        },null,"GET");
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