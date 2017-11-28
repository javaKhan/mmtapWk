/**
 * 工作管理初始化
 */
var Work = {
    id: "WorkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Work.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'wid', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'oid', visible: true, align: 'center', valign: 'middle'},
        {title: '客户', field: 'customname', visible: true, align: 'center', valign: 'middle'},
        {title: '业务类型', field: 'businessname', visible: true, align: 'center', valign: 'middle'},
        {title: '业务状态', field: 'flowname', visible: true, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Work.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Work.seItem = selected[0];
        return true;
    }
};

/**
 * 客户点击受理一个业务
 * 锁定该业务别人不能处理
 */
Work.workLock =function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/work/work_lock", function (data) {
            Feng.success("受理成功!");
            Work.table.refresh();
        }, function (data) {
            Feng.error("受理失败!" + data.responseJSON.message + "!");
        });
        ajax.set("wid",this.seItem.wid);
        ajax.start();
    }
}


/**
 * 打开查看工作详情
 */
Work.openWorkDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工作详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/work/work_update/' + Work.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工作
 */
Work.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/work/delete", function (data) {
            Feng.success("删除成功!");
            Work.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询工作列表
 */
Work.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Work.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Work.initColumn();
    var table = new BSTable(Work.id, "/work/list", defaultColunms);
    table.setPaginationType("client");
    Work.table = table.init();
});
