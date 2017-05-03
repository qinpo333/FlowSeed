<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>明细</title>

    <link rel="stylesheet" href="../assets/css/seeds.min.css"/>
    <link rel="stylesheet" href="../assets/css/weui_toast.min.css"/>

    <style>
        html, body{height: 100%;}
        .bg-img{  position: absolute;  top: 0;  left: 0; z-index: 0;}
        body{ background-color: #b1e5e4; }
        .main-container{position: relative; z-index: 2; width: 90%; margin: 0 auto; height: 100%; padding: 1rem 0 1rem;}

        .nav-tabs{border-bottom: 0; margin: 0; padding: 0;}
        ul.nav {height: 38px; position: relative; z-index: 4;}
        .nav li.active a,.nav li.active a:visited,.nav li.active a:focus,.nav li.active a:hover ,.nav li.active a:active{
            border:0 none; color: #A26828; border-radius: 4px 4px 0 0; background: transparent;  padding: 5px 0;

        }
        .nav li a,.nav li a:visited,.nav li a:focus,.nav li a:hover ,.nav li a:active{
            border:0 none; color: #A26828; padding: 5px 0 7px 0; display: inline-block; width: 100%; border-radius: 4px 4px 0 0;
            background: url(../assets/imgs/nav-bg.png) no-repeat;background-size: 100% 100%;
        }
        span.icon-outgo{background: url(../assets/imgs/icon-outgo.png); background-size: 100% 100%; display: inline-block; width: 20px; height: 20px; position: relative; top: 4px; left: -10px;}
        span.icon-income{background: url(../assets/imgs/icon-income.png); background-size: 100% 100%; display: inline-block; width: 20px; height: 20px; position: relative; top: 4px; left: -10px;}
        ul.nav li{margin: 0; height: 100%; padding: 0; border: 4px solid #fff6d8; border-bottom: 0; border-radius: 5px 5px 0 0; border-bottom: transparent; overflow: hidden; }
        .tab-content{border: 4px solid #fff6d8; border-top-color: transparent; border-radius: 8px; margin-top: -37px; position: relative; z-index: 2; padding: 0; height: 100%;
            background: #fff6d8;
        }
        #outgo{
            background: url(../assets/imgs/panel-active-bg.png) no-repeat,
            url(../assets/imgs/panel_bg.png);
            background-size: 100% auto;
            border-radius: 8px;  padding-top: 42px; padding-bottom: 10px;
        }
        #income{
            background: url(../assets/imgs/panel-active-income.png) no-repeat,
            url(../assets/imgs/panel_bg.png);
            background-size: 100% auto;
            border-radius: 8px; padding-top: 42px; padding-bottom: 10px;
        }
        .tab-pane{height: 100%; }
        .list-group{margin: 0; padding: 0 20px; font-size: 1.6rem; height: 100%; overflow: auto;}
        .list-group li {margin: 0; border: 0;padding: 10px 0; line-height: 25px; border-bottom: 1px solid #9F5F1F; color: #5B310B;}
        .row-right{font-size: 1.8rem; margin-top: 5px; -webkit-text-stroke: 1px rgba(255,0,0,0.5); color: white; font-weight: 700; font-family: "微软雅黑";}
        #income .row-right{-webkit-text-stroke: 1px rgba(238,162,27, 0.8);}
    </style>
</head>
<body>

    <img class="bg-img" data-src="warehouse.png">

    <div class="main-container">
        <ul class="nav nav-tabs text-center" role="tablist">
            <li role="presentation" class="col-xs-6"><a href="#outgo" role="tab" data-toggle="tab"><span class="icon-outgo"></span>支出记录</a></li>
            <li role="presentation" class="col-xs-6"><a href="#income" role="tab" data-toggle="tab"><span class="icon-income"></span>收入记录</a></li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in list_wrap" id="outgo">
                <ul id="flow-outgo-list" class="list-group">
                    <#if (outFlowList?size>0)>
                        <#list outFlowList as outFlow>
                            <li class="row">
                                <div class="pull-left">
                                    <div class="row-up">
                                        <span class="mr-10">${outFlow.opType}</span>
                                        <#if outFlow.phone!="">
                                            <span style="font-size: 1.3rem;">手机号: ${outFlow.phone}</span>
                                        <#else>
                                            <span style="font-size: 1.3rem;"></span>
                                        </#if>

                                    </div>
                                    <div class="row-down" style="font-size: 1.3rem;">时间: ${outFlow.opTime}</div>
                                </div>
                                <div class="pull-right row-right">${outFlow.flowChange}MB</div>
                            </li>
                        </#list>
                    <#else>
                        <li class="row text-center">
                            暂无支出记录
                        </li>
                    </#if>
                    <#--<li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">赠送</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">兑换</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">赠送</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">兑换</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">赠送</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">兑换</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">赠送</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">兑换</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">赠送</span>
                                <span style="font-size: 1.3rem;">手机号: 18867125896</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>-->
                </ul>
            </div>
            <div role="tabpanel" class="tab-pane fade in list_wrap" id="income">
                <ul id="flow-income-list" class="list-group">
                    <#if (inFlowList?size>0)>
                        <#list inFlowList as inFlow>
                            <li class="row">
                                <div class="pull-left">
                                    <div class="row-up">
                                        <span class="mr-10">${inFlow.opType}</span>
                                        <span style="font-size: 1.3rem;"></span>
                                    </div>
                                    <div class="row-down" style="font-size: 1.3rem;">时间: ${inFlow.opTime}</div>
                                </div>
                                <div class="pull-right row-right">${inFlow.flowChange}MB</div>
                            </li>
                        </#list>
                    <#else>
                        <li class="row text-center">
                            暂无收入记录
                        </li>
                    </#if>
                    <#--<li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">偷果实</span>
                                <span style="font-size: 1.3rem;">昵称: xxxxx</span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>
                    <li class="row">
                        <div class="pull-left">
                            <div class="row-up">
                                <span class="mr-10">收获</span>
                                <span style="font-size: 1.3rem;"></span>
                            </div>
                            <div class="row-down" style="font-size: 1.3rem;">时间: 2016-01-01 10:00:00</div>
                        </div>
                        <div class="pull-right row-right">10MB</div>
                    </li>-->
                </ul>
            </div>
        </div>
    </div>

    <script>
        var baseUrl = "../assets/imgs/";
        var ctx = "http://mockapi.ngrok.cc/mock/seeds";
    </script>
    <script src="../assets/js/zepto.min.js"></script>
    <script src="../assets/js/base.min.js"></script>
    <script>
        $('.nav a').on('click',function(){
            selectedTab($(this));
            return false;
        }).eq(0).click();

        /**
         * 导航切换
         * @param ele
         */
        function selectedTab(ele){
            if(!ele.data("inited")){
                ele.data("inited", true);
            }
            var divId = ele.attr('href');
            if (!ele.parent().hasClass('active')) {
                ele.parent().siblings().removeClass('active');
                ele.parent().addClass('active');
                $(divId).siblings().removeClass('in active');
                $(divId).addClass('in active');
            }
        }
    </script>
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