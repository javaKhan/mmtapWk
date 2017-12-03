/**
 * 初始化工作详情对话框
 */
var WorkInfoDlg = {
    workInfoData : {}
};

/**
 * 清除数据
 */
WorkInfoDlg.clearData = function() {
    this.workInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkInfoDlg.set = function(key, val) {
    this.workInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkInfoDlg.close = function() {
    parent.layer.close(window.parent.Work.layerIndex);
}

/**
 * 收集数据
 */
WorkInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
WorkInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/work/add", function(data){
        Feng.success("添加成功!");
        window.parent.Work.table.refresh();
        WorkInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workInfoData);
    ajax.start();
}

/**
 * 进入业务下一流程
 */
WorkInfoDlg.doNext = function() {
    // var data =document.getElementById("wid").value; //$("#wid").val();
    var data =$("#wid").val();
    $.post('/work/next/'+data);
}

/**
 * 保存业务数据
 */
WorkInfoDlg.saveInfo = function () {
    var data = $("#propForm").serialize();
    $.post('/info/add',data,function (res) {
        if(200==res.code){
            Feng.success("保存成功!");
        }else {
            Feng.error("保存失败!" + res.message + "!");
        }
    });
}

/**
 * 保存业务的备注信息
 * @returns {boolean}
 */
WorkInfoDlg.saveComment = function () {

    var wo = $("#wc-old").text();
    var wn = $("#wc-new").val();
    var text = wo+"&lt;br&gt;"+wn;
    if(null==wn||''==wn){
        Feng.info("没有内容不需要保存！");
        return false;
    }else {
        var wid =$("#wid").val();
        $.post("/work/workcom",{"wid":wid,"workcom":text},function (res) {
            if(200==res.code){
                Feng.success("保存成功!");
            }else {
                Feng.error("保存失败!" + res.message + "!");
            }
        });
    }
}

WorkInfoDlg.editPrice = function () {
    $("#price").removeAttr("readonly");
    $("#priceBtn").removeClass("btn-warning").addClass("btn-info").attr("onclick","WorkInfoDlg.savePrice()").text("点击保存");
}
WorkInfoDlg.savePrice = function () {
    var price = $("#price").attr("readonly","readonly").val();
    var wid =$("#wid").val();
    $("#priceBtn").removeClass("btn-info").addClass("btn-warning").attr("onclick","WorkInfoDlg.editPrice()").text("点击修改");
    console.info(price);
    if(null==price||""==price){
        Feng.info("价格不能为空!");
        return false;
    }else {
        $.post('/work/newprice',{'wid':wid,'price':price},function (res) {
            if(200==res.code){
                Feng.success("价格修改成功!");
            }else {
                Feng.error("价格修改失败!" + res.message + "!");
            }
        });
    }
}



$(function() {

});
