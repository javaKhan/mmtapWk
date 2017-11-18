/**
 * 统计报表管理初始化
 */
var Report = {
    id: "ReportTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Report.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Report.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Report.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加统计报表
 */
Report.openAddReport = function () {
    var index = layer.open({
        type: 2,
        title: '添加统计报表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/report/report_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看统计报表详情
 */
Report.openReportDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '统计报表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/report/report_update/' + Report.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除统计报表
 */
Report.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/report/delete", function (data) {
            Feng.success("删除成功!");
            Report.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("reportId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询统计报表列表
 */
Report.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Report.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Report.initColumn();
    var table = new BSTable(Report.id, "/report/list", defaultColunms);
    table.setPaginationType("client");
    Report.table = table.init();
});
