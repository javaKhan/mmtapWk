/**
 * 业务状态管理初始化
 */
var Flow = {
    id: "FlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Flow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '状态ID', field: 'bid', visible: false, align: 'center', valign: 'middle'},
        {title: '业务名称', field: 'businessname', visible: true, align: 'center', valign: 'middle'},
        {title: '创建者', field: 'createName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
Flow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Flow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加业务状态
 */
Flow.openAddFlow = function () {
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
Flow.openFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '业务状态详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/flow/flow_update/' + Flow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除业务状态
 */
Flow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/flow/delete", function (data) {
            Feng.success("删除成功!");
            Flow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("flowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询业务状态列表
 */
Flow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Flow.table.refresh({query: queryData});
};

Flow.state = function () {
    if(this.check()){
        $("#flow").empty().load("/flow/flowstate",{bid:this.seItem.bid});
    }
};



$(function () {
    var defaultColunms = Flow.initColumn();
    var table = new BSTable(Flow.id, "/manage/list", defaultColunms);
    table.setPaginationType("client");
    Flow.table = table.init();
});
