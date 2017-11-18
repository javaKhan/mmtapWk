/**
 * 初始化统计报表详情对话框
 */
var ReportInfoDlg = {
    reportInfoData : {}
};

/**
 * 清除数据
 */
ReportInfoDlg.clearData = function() {
    this.reportInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportInfoDlg.set = function(key, val) {
    this.reportInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ReportInfoDlg.close = function() {
    parent.layer.close(window.parent.Report.layerIndex);
}

/**
 * 收集数据
 */
ReportInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
ReportInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/report/add", function(data){
        Feng.success("添加成功!");
        window.parent.Report.table.refresh();
        ReportInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ReportInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/report/update", function(data){
        Feng.success("修改成功!");
        window.parent.Report.table.refresh();
        ReportInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportInfoData);
    ajax.start();
}

$(function() {

});
