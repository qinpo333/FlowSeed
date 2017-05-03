/**
 * Created by cqb32_000 on 2016-01-07.
 */
(function(){
    var images = {
        harvest: baseUrl+"icon-hand.png",
        water: baseUrl+"icon-water.png",
        eradicate: baseUrl+"icon-eradicate.png"
    };
    var GETING_PLANT = false;
    var isShared = false;

    var PLANTIMGS = ["plant1.png","plant2.png","plant3.png","plant4.png","plant5.png","plant6.png"];
    var PLANT_STATUS = {
        "NEED_WATER": "0",
        "NEED_HARVEST": "1",
        "NEED_ERADICATE": "2",
        "NONE": "-1"
    };
    var PlantsMap = {};
    var dialog, waterDialog,guideDialog;

    $(function(){
        if(!isBind){
            window.location.href = "./bind";
            return false;
        }

        //获取用户信息
        getUserInfo(function(userInfo){
            //新用户
            if(userInfo.isNewUser){
                if(!sessionStorage.getItem("receivePlant")) {
                    buildHelpDialog();
                }
                hideToast();
            }else{
                getPlants();
            }
            isShared = userInfo.shared;
        });

        //工具栏事件
        toolbarListeners();
    });

    /**
     * 种植种子
     */
    function plant(plantId){
        var url = ctx+"/plant?_="+new Date().getTime();

        var data = null;
        if(plantId) {
            data = {plantId: plantId};
        }

        if(dialog){
            delete dialog.for;
            delete dialog.plantId;
        }

        ajaxData(url, data, function(plant){
            sessionStorage.setItem("receivePlant", true);
            if(plant){
                var msg = DIALOG_TEXT.GET_SEED.replace("$$COUNT$$", plant.flow);
                if(plant.matureDays){
                    msg = msg.replace("$$DAYS$$", plant.matureDays+ "天");
                }
                if(plant.matureHours){
                    msg = msg.replace("$$DAYS$$", plant.matureHours+ "小时");
                }
                updateDialogText(dialog, msg, DIALOG_ADS.NO_SEEDS);
                PlantsMap[plant.id] = plant;
                renderPlant(plant);
                if(window.initWater) {
                    initWater(plant.flow, plant.matureDays);
                }
            }else{
                updateDialogText(dialog, DIALOG_TEXT.NO_SEEDS, DIALOG_ADS.NO_SEEDS);
            }

            GETING_PLANT = false;
        });
    }

    /**
     * 获取到所有植物
     */
    function getPlants(){
        var url = ctx+"/getPlants?_="+new Date().getTime();

        ajaxData(url, null, function(plants){
            hideToast();
            //plants = [];
            //for(var i = 0; i < 9; i++){
            //    plants.push({
            //        id: i,
            //        ground: i,
            //        status: "1",
            //        growthStatus: 4
            //    });
            //}
            //plants.push({
            //    id: "1",
            //    ground: 0,
            //    status: "3",
            //    growthStatus: 5
            //});
            //plants.push({
            //    id: "2",
            //    ground: 4,
            //    status: "1",
            //    growthStatus: 1
            //});

            for(var j in plants){
                PlantsMap[plants[j].id] = plants[j];
            }
            renderPlants(plants);
        },null,"GET");
    }

    /**
     * 获取用户信息
     */
    function getUserInfo(callback){
        var url = ctx+"/getUserInfo?_="+new Date().getTime();
        showToast();
        ajaxData(url, null, function(userInfo){
            $("#user_achievements").html(userInfo.achievements);
            $("#user_flow").html(userInfo.flow);
            $("#user_exp").html(userInfo.exp);

            $(".userName").html(userInfo.name);
            $(".userPhoto").attr("src", userInfo.photo);

            callback(userInfo);
        },null,"GET");
    }

    /**
     * 渲染所有植物
     * @param plants
     */
    function renderPlants(plants){
        if(plants){
            plants.forEach(function(plant){
                renderPlant(plant);
            });
        }
    }

    /**
     * 工具栏事件监听
     */
    function toolbarListeners(){
        //浇水
        $("#tool-water").on("tap", function(){
            if(!$(this).hasClass("active")) {
                $(this).addClass("active");
                waterAll();
            }
        });

        //收获
        $("#tool-hand").on("tap", function(){
            if(!$(this).hasClass("active")) {
                $(this).addClass("active");
                harvestAll();
            }
        });

        //仓库
        $("#tool-home").on("click", function(){
            window.location.href = "warehouse";
        });

        //好友
        $("#tool-friends").on("tap", function(){
            window.location.href = "friends";
        });
    }

    /**
     * 渲染植物
     * @param plant
     */
    function renderPlant(plant){
        var imgSrc = getPlantImageByStatus(plant);
        var ground = $(".trees .tree").eq(plant.ground);

        if(ground.length){
            var plantEle = ground.children("img.plant");
            plantEle.removeAttr("class");
            plantEle.addClass("plant");
            //plantEle.removeClass("plant5");//删除成熟阶段的css，否则收获后重新种植的发芽阶段样式不正确
            if (plant.growthStatus != -1) {
                plantEle.addClass("plant" + (plant.growthStatus + 1)).attr("src", imgSrc);
            }
            plant.ele = plantEle;

            plantEle.off("singleTap");
            plantEle.on("singleTap", function(){
                if(plant.growthStatus < 5) {
                    showMatureTime(ground, $(this), plant);
                }
            });
        }

        renderStatusIcon(ground, plant);
    }

    /**
     * 根据植物的状态绘制浇水或收获或铲除图标
     * @param ground
     * @param plant
     */
    function renderStatusIcon(ground, plant){
        var offset = ground.position();

        var statusIcon,imgSrc;
        if(plant.status == PLANT_STATUS.NEED_WATER){
            statusIcon = $('<div class="status-icon"></div>');
            imgSrc = images.water;
            statusIcon.data("target", "water_"+plant.id);
        }else if(plant.status == PLANT_STATUS.NEED_HARVEST){
            imgSrc = images.harvest;
            statusIcon = $('<div class="status-icon"></div>');
            statusIcon.data("target", "harvest_"+plant.id);
        }else if(plant.status == PLANT_STATUS.NEED_ERADICATE){
            imgSrc = images.eradicate;
            statusIcon = $('<div class="status-icon"></div>');
            statusIcon.data("target", "eradicate_"+plant.id);
        }

        if(statusIcon) {
            var img = new Image();
            img.src = imgSrc;
            img.onload = function(){
                var w = statusIcon.width();
                var h = statusIcon.height();
                statusIcon.css({
                    left: offset.left + ground.width() / 2 - w / 2,
                    top: offset.top - h
                });

                var timer = null;
                var gw = ground.children(".plant").width();
                if(gw === 0 || w === 0){
                    timer = window.setInterval(function(){
                        gw = ground.children(".plant").width();
                        w = statusIcon.width();
                        h = statusIcon.height();
                        if(gw !== 0 && w !== 0){
                            window.clearInterval(timer);
                            offset = ground.position();
                            statusIcon.css({
                                left: offset.left + ground.width() / 2 - w / 2,
                                top: offset.top - h
                            });
                        }
                    }, 10);
                }
            };

            statusIcon.append(img);
            $(".ground-grid").append(statusIcon);

            statusIcon.on("click", function () {
                if (plant.status == PLANT_STATUS.NEED_WATER) {
                    doWater($(this), plant);
                }

                if (plant.status == PLANT_STATUS.NEED_HARVEST) {
                    doHarvest($(this), plant);
                }

                if (plant.status == PLANT_STATUS.NEED_ERADICATE) {
                    doEradicate($(this), plant);
                }
            });
        }
    }

    /**
     *
     * @param ground
     * @param plantEle
     * @param plant
     */
    function showMatureTime(ground, plantEle, plant){
        if($(".matureStatus", ground).length){
            $(".matureStatus", ground).remove();
        }else{
            var url = ctx + "/getMatureTime/" + plant.id+"?_"+new Date().getTime();
            ajaxData(url, {plantId: plant.id}, function(ret){
                var txt = "剩余";
                if(ret.matureDays){
                    txt += ret.matureDays + "天";
                }
                if(ret.matureHours){
                    txt += ret.matureHours + "小时";
                }

                var h = plantEle.height();
                var gh = ground.height();
                var mature = $('<span class="matureStatus">'+txt+'</span>');
                mature.css("top", -h + gh);
                ground.append(mature);
            },null,"GET");
        }
    }

    /**
     * 对所有的植物浇水
     */
    function waterAll(){

        var hasPlants = false;
        for(var k in PlantsMap){
            if(PlantsMap[k].growthStatus != -1){
                hasPlants = true;
            }
        }
        if(!hasPlants){
            $("#tool-water").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.NO_PLANTS, DIALOG_ADS.FAIL);
            return;
        }

        if(Object.keys(PlantsMap).length === 0){
            $("#tool-water").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.NO_PLANTS, DIALOG_ADS.FAIL);
            return;
        }

        showToast();
        var plants = [], hasMaturedPlants = false, hasWitherPlants = false;
        for(var i in PlantsMap){
            var plant = PlantsMap[i];
            if(plant.status == PLANT_STATUS.NEED_WATER){
                plants.push(plant);
            }else{
                //存在成熟的植物
                if(plant.status == PLANT_STATUS.NEED_HARVEST){
                    hasMaturedPlants = true;
                }
                //存在枯萎的植物
                if(plant.status == PLANT_STATUS.NEED_ERADICATE){
                    hasWitherPlants = true;
                }
            }
        }

        var finishednum = 0;

        function finishedWater(ret) {
            finishednum++;
            if (finishednum == plants.length) {
                hideToast();
                //if(!waterDialog){
                //    waterDialog = buildWaterDialog("", true);
                //}
                //var msg = DIALOG_TEXT.WATER_SUCCESS;
                //updateDialogText(waterDialog, msg, DIALOG_ADS.WATER_SUCCESS);
                ////更新经验值
                //updateFlow();
                $("#tool-water").removeClass("active");
            }
        }

        if(plants.length === 0){
            hideToast();
            //存在成熟的植物
            if(hasMaturedPlants){
                updateDialogText(dialog, DIALOG_TEXT.WATER_HAS_MATURED, DIALOG_ADS.WATER_HAS_MATURED);
            }else if(hasWitherPlants){//枯死了
                updateDialogText(dialog, DIALOG_TEXT.WATER_DEAD, DIALOG_ADS.FAIL);
            }else {
                showWaterDialog();
            }
            $("#tool-water").removeClass("active");
        }else {
            for (var j in plants) {
                var aplant = plants[j];
                var ele = $(".status-icon[data-target='water_" + aplant.id + "']");
                doWater(ele, aplant, false, finishedWater);
            }
        }
    }

    /**
     * 没有可浇的植物
     */
    function showWaterDialog(){
        var theDialog = null;
        if(isShared){
            if(!dialog){
                dialog = buildWaterDialog("");
            }
            theDialog = dialog;
        }else{
            if(!waterDialog){
                waterDialog = buildWaterDialog("", true);
            }
            theDialog = waterDialog;
        }

        if(theDialog){
            updateDialogText(theDialog, DIALOG_TEXT.WATER_ALL_WATERED, DIALOG_ADS.WATER_ALL_WATERED);
        }
    }

    /**
     * 收获所有的植物
     */
    function harvestAll(){
        var hasPlants = false;
        for(var k in PlantsMap){
            if(PlantsMap[k].growthStatus != -1){
                hasPlants = true;
            }
        }
        if(!hasPlants){
            $("#tool-hand").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.NO_PLANTS, DIALOG_ADS.FAIL);
            return;
        }

        if(Object.keys(PlantsMap).length === 0){
            $("#tool-hand").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.NO_PLANTS, DIALOG_ADS.FAIL);
            return;
        }
        showToast();
        var plants = [];
        var needShowMatureTime = false, hasWitherPlants = false;
        for(var i in PlantsMap){
            var plant = PlantsMap[i];
            if(plant.status == PLANT_STATUS.NEED_HARVEST){
                plants.push(plant);
            }else{
                //if(plant.growthStatus < 4) {
                needShowMatureTime = true;
                //}

                //存在枯萎的植物
                if(plant.status == PLANT_STATUS.NEED_ERADICATE){
                    hasWitherPlants = true;
                }
            }
        }

        var finishednum = 0;
        var amountAll = 0;
        function finished(amount) {
            finishednum++;
            amountAll += amount;
            if (finishednum == plants.length) {
                hideToast();
                var msg = DIALOG_TEXT.HARVEST_SUCCESS.replace("$$COUNT$$", amountAll);
                updateDialogText(dialog, msg, DIALOG_ADS.HARVEST_SUCCESS);
                $("#tool-hand").removeClass("active");
            }
        }

        if(plants.length === 0){
            if(hasWitherPlants){
                updateDialogText(dialog, DIALOG_TEXT.HARVEST_DEAD, DIALOG_ADS.FAIL);
                hideToast();
            }else if(needShowMatureTime) {
                var msg;

                //弹框显示剩余成熟时间
                getMinMatureTime(function (ret) {
                    hideToast();
                    if(ret.matureDays){
                        msg = DIALOG_TEXT.HARVEST_MATURE.replace("$$DAYS$$", ret.matureDays+"天");
                        updateDialogText(dialog, msg, DIALOG_ADS.HARVEST_MATURE);
                    }
                    //未成熟，提示剩余成熟时间
                    if(ret.matureHours && ret.matureHours > 0){
                        msg = DIALOG_TEXT.HARVEST_MATURE.replace("$$DAYS$$", ret.matureHours+"小时");
                        updateDialogText(dialog, msg, DIALOG_ADS.HARVEST_MATURE);
                    } else {//成熟了，页面没刷新，还是成长阶段，则刷新作物
                        getPlants();
                    }
                });
            }else{
                hideToast();
            }
            $("#tool-hand").removeClass("active");
        }else {
            for (var j in plants) {
                var aplant = plants[j];
                var ele = $(".status-icon[data-target='harvest_" + aplant.id + "']");
                doHarvest(ele, aplant, true, finished);
            }
        }
    }

    /**
     * 对植物浇水
     * @param ele
     * @param plant
     * @param silent 静默浇水
     * @param cback 回调函数
     */
    function doWater(ele, plant, silent, cback){
        //防止重复点击
        if(ele.hasClass("clicked")){
            return false;
        }
        $(ele).addClass("clicked");
        showToast();

        if(!waterDialog){
            waterDialog = buildWaterDialog("", true);
        }

        $.ajax({
            url: ctx + "/water/" + plant.id+"?_="+new Date().getTime(),
            type: "get",
            dataType: "json",
            data: {plantId: plant.id},
            success: function(ret){
                if(ret.success){
                    if(!silent) {
                        var msg = DIALOG_TEXT.WATER_SUCCESS;
                        msg = msg.replace("$$AMOUNT$$", ret.flow);
                        if(ret.matureDays){
                            msg = msg.replace("$$DAYS$$", "浇水"+ret.matureDays+ "天");
                        }
                        if(ret.matureHours){
                            msg = msg.replace("$$DAYS$$", ret.matureHours+ "小时");
                        }
                        updateDialogText(waterDialog, msg, DIALOG_ADS.WATER_SUCCESS);
                    }
                    plant.status = PLANT_STATUS.NONE;
                    //更新经验值
                    updateFlow();
                    ele.remove();
                    if(cback){cback(ret);}
                }else{
                    if(!silent) {
                        updateDialogText(waterDialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                    }
                    if(cback){cback();}
                }
                ele.removeClass("clicked");
                hideToast();
            },
            error: function(){
                hideToast();
                ele.removeClass("clicked");
                if(!silent) {
                    updateDialogText(waterDialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                }
                if(cback){cback();}
            }
        });
    }

    /**
     * 收获
     * @param ele
     * @param thePlant
     * @param silent 静默收获
     * @param cback 回调函数
     */
    function doHarvest(ele, thePlant, silent, cback){
        if(ele.hasClass("clicked")){
            return false;
        }
        $(ele).addClass("clicked");
        showToast();

        if(!dialog){
            dialog = buildWaterDialog("");
        }
        $.ajax({
            url: ctx + "/harvest/" + thePlant.id+"?_="+new Date().getTime(),
            type: "get",
            dataType: "json",
            data: {plantId: thePlant.id},
            success: function(ret){
                if(ret.success){
                    if(!silent) {
                        var msg = DIALOG_TEXT.HARVEST_SUCCESS.replace("$$COUNT$$", ret.amount);
                        updateDialogText(dialog, msg, DIALOG_ADS.HARVEST_SUCCESS);
                    }
                    dialog.for = "steal";
                    dialog.plantId = thePlant.id;

                    thePlant.status = PLANT_STATUS.NONE;
                    if(thePlant.ele) {
                        thePlant.ele.attr("src", "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==");
                    }
                    ele.remove();
                    //更新用户的流量
                    updateFlow();
                    //收获后立即从新种植
                    //plant(thePlant.id);
                    //getPlants();
                    if(cback){cback(ret.amount);}

                    delete PlantsMap[thePlant.id];
                }else{
                    if(!silent) {
                        updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                    }
                    if(cback){cback(0);}
                }
                ele.removeClass("clicked");
                hideToast();
            },
            error: function(){
                hideToast();
                ele.removeClass("clicked");
                if(!silent) {
                    updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                }
                if(cback){cback(0);}
            }
        });
    }

    /**
     * 对植物进行铲除
     * @param ele
     * @param thePlant
     */
    function doEradicate(ele, thePlant){
        if(ele.hasClass("clicked")){
            return false;
        }
        $(ele).addClass("clicked");
        showToast();

        if(!dialog){
            dialog = buildWaterDialog("");
        }

        $.ajax({
            url: ctx + "/eradicate/" + thePlant.id+"?_="+new Date().getTime(),
            type: "get",
            dataType: "json",
            data: {plantId: thePlant.id},
            success: function(ret){
                //铲除成功
                if(ret && ret.success){
                    updateDialogText(dialog, DIALOG_TEXT.ERADICATE_SUCCESS, DIALOG_ADS.ERADICATE_SUCCESS);
                    thePlant.status = PLANT_STATUS.NONE;
                    dialog.for = "eradicate";
                    dialog.plantId = thePlant.id;

                    //删除铲除图标
                    ele.remove();
                    if(thePlant.ele) {
                        thePlant.ele.attr("src", "data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==");
                    }
                    //除铲后立即重新种植
                    //plant(thePlant.id);
                    delete PlantsMap[thePlant.id];
                }else{
                    updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                }

                ele.removeClass("clicked");
                hideToast();
            },
            error: function(){
                hideToast();
                ele.removeClass("clicked");
            }
        });
    }

    /**
     * 获取所有植物剩余成熟时间最小值
     */
    function getMinMatureTime(callback){
        var url = ctx + "/getMinMatureTime?_="+new Date().getTime();
        ajaxData(url, null, function(ret){
            callback(ret);
        },null,"GET");
    }

    /**
     * 更新我的流量
     */
    function updateFlow(){
        var url = ctx + "/getUserFlow?_="+new Date().getTime();
        ajaxData(url, null, function(ret){
            $("#user_flow").html(ret.flow);
            $("#user_exp").html(ret.exp);
        },null,"GET");
    }

    /**
     * 获取当前植物的图片
     * @param plant
     */
    function getPlantImageByStatus(plant){
        //铲除状态是枯萎
        if(plant.status == PLANT_STATUS.NEED_ERADICATE){
            return baseUrl + PLANTIMGS[5];
        }else {
            return baseUrl + PLANTIMGS[plant.growthStatus];
        }
    }

    var dialog_index = 0;

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
                     '<a class="btn btn-img">确 定</a>'+
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
     * 创建dialog
     * @param cback
     * @returns {*|jQuery|HTMLElement}
     */
    function buildShareGuideDialog(cback){
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

        ele.attr("id", "share_dialog-water");

        var screenW = document.documentElement.clientWidth;
        var dialogW = screenW * 0.85;
        var dialogH = dialogW;

        $(".modal-dialog", ele).height(dialogH);

        ele.modal();

        $(".btn", ele).on("tap", function(){
            if(window.afterCloseShareDialog) {
                try {
                    window.afterCloseShareDialog();
                }catch(e){
                    console.log(e);
                }
            }
            ele.modal("hide");
        });

        $("body").append(ele);

        return ele;
    }

    /**
     * 创建帮助文案窗口
     */
    function buildHelpDialog(){
        var dialog = buildDialog(function(ele){
            $(".modal-body", ele).hide();
            $(".modal-text", ele).append('欢迎您来到流量农场！每天不定时发放流量种子，快试试您的运气吧！');
            $(".modal-text", ele).css({padding: "20px", "text-align": "left", "line-height": "1.7", "text-indent": "4rem"});
        });

        dialog.modal();
        dialog.modal("show");

        //点击确定之后进行种植
        $(".btn", dialog).on("tap", function(){
            if(!GETING_PLANT) {
                GETING_PLANT = true;
                dialog.modal("hide");
                plant();
            }
        });
    }

    /**
     * 创建浇水成功窗口
     */
    function buildWaterDialog(msg, mtButs){
        var dialog = buildDialog(function(ele){
            $(".modal-text", ele).append(msg);

            if(mtButs) {
                $(".modal-tools", ele).empty();
                $(".modal-tools", ele).append('<div class="buttons-wrap">' +
                    '<a class="btn btn-ok">邀请好友</a>' +
                    '<a class="btn btn-cancel">以后再说</a>' +
                    '</div>');
            }
        });

        dialog.modal();

        $(".btn", dialog).on("tap", function(){
            dialog.modal("hide");
            if($(this).hasClass("btn-img")){
                //收获或除铲后立即重新种植
                if(dialog.for === "steal" || dialog.for === "eradicate"){
                    window.setTimeout(function(){
                        plant(dialog.plantId);
                    }, 600);
                }
            }
        });

        $(".btn-ok", dialog).on("tap", function(){
            showGuideDialog();
        });
        return dialog;
    }

    /**
     *
     */
    function showGuideDialog(){
        if(!guideDialog){
            guideDialog = buildShareGuideDialog();
        }
        updateDialogText(guideDialog, DIALOG_TEXT.WATER_SHARE, DIALOG_ADS.WATER_SHARE);

        if(window.afterWater) {
            try {
                afterWater();
            }catch(e){
                console.log(e);
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
        if(!dialog){
            dialog = buildWaterDialog("");
        }
        var src = $(".modal-body img", dialog).attr("src");
        //不同的图片才替换
        if(src != ads.img) {
            $(".modal-body", dialog).empty();
            $(".modal-body", dialog).append('<a href="'+ads.link+'" target="_blank"><img src="' + ads.img + '"></a>');
        }
        $(".modal-text", dialog).html(msg);
        dialog.modal("show");
    }


    /**
     * 关闭分享引导
     */
    window.hideGuideDialog = function(){
        guideDialog.modal("hide");
    };
})();