/**
 * 业务状态管理初始化
 */
var FlowState = {
    id: "FlowStateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    busId:null
};

/**
 * 初始化表格的列
 */
FlowState.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '状态状态ID', field: 'fid', visible: false, align: 'center', valign: 'middle'},
        {title: '业务状态', field: 'flowname', visible: true, align: 'center', valign: 'middle'},
        {title: '业务状态排序', field: 'floworder', visible: true, align: 'center', valign: 'middle'},
        {title: '状态完成角色', field: 'roleName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建者', field: 'createrName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
FlowState.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FlowState.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加业务状态
 */
FlowState.openAddFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加业务状态',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/flow/flow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看业务状态详情
 */
FlowState.openFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '业务状态详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/flow/flow_update/' + FlowState.seItem.fid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除业务状态
 */
FlowState.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/flow/delete", function (data) {
            Feng.success("删除成功!");
            FlowState.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("fid",this.seItem.fid);
        ajax.start();
    }
};

/**
 * 查询业务状态列表
 */
FlowState.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    FlowState.table.refresh({query: queryData});
};
FlowState.listParam = function () {
    var queryData = {};
    queryData['bid'] = FlowState.busId;
    return queryData;
}


$(function () {
    FlowState.busId = $("#bid").val();
    var defaultColunms = FlowState.initColumn();
    var table = new BSTable(FlowState.id, "/flow/list", defaultColunms);
    table.setQueryParams(FlowState.listParam());
    table.setPaginationType("client");
    FlowState.table = table.init();
});
