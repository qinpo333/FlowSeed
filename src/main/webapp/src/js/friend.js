/**
 * Created by cqb32_000 on 2016-01-07.
 */
(function(){
    var friendName = null;
    var images = {
        harvest: baseUrl+"icon-hand.png",
        water: baseUrl+"icon-water.png",
        eradicate: baseUrl+"icon-eradicate.png"
    };

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
        showToast();
        //获取用户信息
        getUserInfo();
        getSelfInfo();
        //获取用户的植物
        getPlants();

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

        ajaxData(url, data, function(plant){
            if(plant){
                PlantsMap[plant.id] = plant;
                renderPlant(plant);
            }
        });
    }

    /**
     * 获取到所有植物
     */
    function getPlants(){
        var url = ctx+"/getPlants?_="+new Date().getTime();

        ajaxData(url, {friendOpenId: friendOpenId}, function(plants){
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
            //    status: "-1",
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
        ajaxData(url, {friendOpenId: friendOpenId}, function(userInfo){
            $("#user_achievements").html(userInfo.achievements);
            $("#user_flow").html(userInfo.flow);

            friendName = userInfo.name;
            $(".userName").html(userInfo.name);
            $(".userPhoto").attr("src", userInfo.photo);

            if(callback) {
                callback(userInfo);
            }
        },null,"GET");
    }

    /**
     * 获取用户信息
     */
    function getSelfInfo(){
        var url = ctx+"/getUserInfo?_="+new Date().getTime();
        ajaxData(url, {}, function(userInfo){

            if(userInfo.isNewUser){
                $("#tool-join").show();
            }else{
                $("#tool-home").show();
            }
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
                //checkWaterAvailable(function(){
                    waterPlant();
                //});
            }
        });

        //偷果实
        $("#tool-rob").on("tap", function(){
            if(!$(this).hasClass("active")) {
                $(this).addClass("active");
                //checkStealAvailable(function(){
                    stealPlant();
                //});
            }
        });

        //仓库
        $("#tool-home,#tool-join").on("tap", function(){
            window.location.href = "./index.html";
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
        }
    }

    /**
     * 验证当前是否可以进行浇水
     */
    function checkWaterAvailable(callback){
        var url = ctx + "/checkWater";
        ajaxData(url, {friendOpenId: friendOpenId}, function(ret){
            if(ret){
                //允许浇水
                if(ret.available){
                    if(callback){
                        callback();
                    }
                }else{
                    //植物都已经浇过水了,没有可以进行浇水的植物
                    if(ret.msg == "none"){
                        updateDialogText(dialog, DIALOG_TEXT.WATER_ALL_WATERED, DIALOG_ADS.WATER_ALL_WATERED);
                    }else if(ret.msg == "over"){//超过了浇水次数,已经为该好友浇过了
                        updateDialogText(dialog, DIALOG_TEXT.WATER_OVER, DIALOG_ADS.WATER_OVER);
                    }
                }

                $("#tool-water").removeClass("active");
            }
        },null,"GET");
    }

    /**
     * 验证是否可以偷果实
     */
    function checkStealAvailable(callback){
        var url = ctx + "/checkSteal";
        ajaxData(url, {friendOpenId: friendOpenId}, function(ret){
            if(ret){
                //允许偷果实
                if(ret.available){
                    if(callback){
                        callback();
                    }
                }else{
                    //没有可以被偷的植物
                    if(ret.msg == "none"){
                        updateDialogText(dialog, DIALOG_TEXT.STEAL_NONE, DIALOG_ADS.STEAL_NONE);
                    }else if(ret.msg == "stolen"){//已经偷过该好友
                        updateDialogText(dialog, DIALOG_TEXT.STOLEN, DIALOG_ADS.STOLEN);
                    }else if(ret.msg == "over"){//今天已经达到上限
                        updateDialogText(dialog, DIALOG_TEXT.STEAL_OVER, DIALOG_ADS.STEAL_OVER);
                    }
                }

                $("#tool-rob").removeClass("active");
            }
        },null,"GET");
    }

    /**
     * 偷果实
     */
    function stealPlant(){

        var allDead = true;
        var hasPlants = false;
        //是否全都死亡
        for(var i in PlantsMap){
            if(PlantsMap[i].growthStatus != 5){
                allDead = false;
            }
            if(PlantsMap[i].growthStatus != -1){
                hasPlants = true;
            }
        }

        if(!hasPlants){
            $("#tool-rob").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.STEAL_NO_PLANTS, DIALOG_ADS.WATER_DEAD);
            return;
        }

        if(Object.keys(PlantsMap).length === 0){
            $("#tool-rob").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.STEAL_NO_PLANTS, DIALOG_ADS.WATER_DEAD);
            return;
        }

        if(allDead){
            $("#tool-rob").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.STEAL_DEAD, DIALOG_ADS.WATER_DEAD);
            return;
        }

        var url = ctx + "/steal";
        showToast();
        ajaxData(url, {friendOpenId: friendOpenId}, function(ret){
            hideToast();
            $("#tool-rob").removeClass("active");
            if(ret && ret.success){
                var msg = DIALOG_TEXT.STEAL.replace("$$COUNT$$", ret.flow);
                updateDialogText(dialog, msg, DIALOG_ADS.STEAL);
            }else{
                //没有可以被偷的植物
                if(ret.msg == "none"){
                    updateDialogText(dialog, DIALOG_TEXT.STEAL_NONE, DIALOG_ADS.STEAL_NONE);
                }else if(ret.msg == "stolen"){//已经偷过该好友
                    updateDialogText(dialog, DIALOG_TEXT.STOLEN, DIALOG_ADS.STOLEN);
                }else if(ret.msg == "over"){//今天已经达到上限
                    updateDialogText(dialog, DIALOG_TEXT.STEAL_OVER, DIALOG_ADS.STEAL_OVER);
                }else if(ret.msg == "fail"){//偷取概率失败
                    updateDialogText(dialog, DIALOG_TEXT.STEAL_FAIL, DIALOG_ADS.STEAL_FAIL);
                }else{
                    updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                }
            }
        });
    }

    /**
     * 随机对植物浇水
     */
    function waterPlant(){

        var allDead = true;
        var hasPlants = false;
        //是否全都死亡
        for(var i in PlantsMap){
            if(PlantsMap[i].growthStatus != 5){
                allDead = false;
            }
            if(PlantsMap[i].growthStatus != -1){
                hasPlants = true;
            }
        }

        if(!hasPlants){
            $("#tool-water").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.WATER_NO_PLANTS, DIALOG_ADS.WATER_DEAD);
            return;
        }

        if(Object.keys(PlantsMap).length === 0){
            $("#tool-water").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.WATER_NO_PLANTS, DIALOG_ADS.WATER_DEAD);
            return;
        }

        if(allDead){
            $("#tool-water").removeClass("active");
            updateDialogText(dialog, DIALOG_TEXT.WATER_DEAD, DIALOG_ADS.WATER_DEAD);
            return;
        }

        showToast();
        $.ajax({
            url: ctx + "/waterFriend?_="+new Date().getTime(),
            type: "get",
            dataType: "json",
            data: {friendOpenId: friendOpenId},
            success: function(ret){
                if(ret.success){
                    var msg = DIALOG_TEXT.WATER_SUCCESS.replace("$$NAME$$", friendName);
                    updateDialogText(dialog, msg, DIALOG_ADS.WATER_SUCCESS);
                }else{
                    //植物都已经浇过水了,没有可以进行浇水的植物
                    if(ret.msg == "none"){
                        updateDialogText(dialog, DIALOG_TEXT.WATER_ALL_WATERED, DIALOG_ADS.WATER_ALL_WATERED);
                    }else if(ret.msg == "over"){//超过了浇水次数,已经为该好友浇过了
                        updateDialogText(dialog, DIALOG_TEXT.WATER_OVER, DIALOG_ADS.WATER_OVER);
                    }else{
                        updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
                    }
                }
                hideToast();
                $("#tool-water").removeClass("active");
            },
            error: function(){
                hideToast();
                updateDialogText(dialog, DIALOG_TEXT.FAIL, DIALOG_ADS.FAIL);
            }
        });
    }

    /**
     * 更新我的流量
     */
    function updateFlow(){
        var url = ctx + "/getUserFlow?_="+new Date().getTime();
        ajaxData(url, null, function(ret){
            $("#user_flow").html(ret.flow);
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
        var ele = $('<div class="modal fade text-center" data-backdrop="true">'+
            '<div class="modal-dialog">'+
            '<div class="modal-content">'+
            '<div class="modal-body">'+
            '</div>'+
            '<div class="modal-text">'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>');

        if(cback){
            cback(ele);
        }

        ele.attr("id", "share_dialog");

        var screenW = document.documentElement.clientWidth;
        var dialogW = screenW * 0.85;
        var dialogH = dialogW * 0.8;

        $(".modal-dialog", ele).height(dialogH);

        ele.modal();

        $("body").append(ele);

        return ele;
    }

    /**
     * 创建帮助文案窗口
     */
    function buildHelpDialog(){
        var dialog = buildDialog(function(ele){
            $(".modal-body", ele).hide();
            $(".modal-text", ele).append('恭喜你获得了一个流量种子！每天为种子浇水就可以在14天后收获更多流量，浇水越多惊喜越多！');
            $(".modal-text", ele).css({padding: "20px", "text-align": "left", "line-height": "1.7", "text-indent": "4rem"});
        });

        dialog.modal();
        dialog.modal("show");

        //点击确定之后进行种植
        $(".btn", dialog).on("tap", function(){
            plant();
            dialog.modal("hide");
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
                    '<a class="btn btn-ok">获得更多流量</a>' +
                    '<a class="btn btn-cancel">以后再说</a>' +
                    '</div>');
            }
        });

        dialog.modal();

        $(".btn", dialog).on("tap", function(){
            dialog.modal("hide");
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

})();