/**
 * 业务中心管理初始化
 */
var Manage = {
    id: "ManageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Manage.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'bid', field: 'bid', visible: false, align: 'center', valign: 'middle'},
        {title: '业务名称', field: 'businessname', align: 'center', valign: 'middle', sortable: true},
        {title: '发布者', field: 'createName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Manage.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Manage.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加业务中心
 */
Manage.openAddManage = function () {
    var index = layer.open({
        type: 2,
        title: '添加业务中心',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/manage/manage_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看业务中心详情
 */
Manage.openManageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '业务中心详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/manage/manage_update/' + Manage.seItem.bid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除业务中心
 */
Manage.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/manage/delete", function (data) {
            Feng.success("删除成功!");
            Manage.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bid",this.seItem.bid);
        ajax.start();
    }
};

/**
 * 查询业务中心列表
 */
Manage.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Manage.table.refresh({query: queryData});
};
Manage.flow = function () {
    if(this.check()){
        $("#manage").empty().load("/flow",{bid:this.seItem.bid});
    }
};

$(function () {
    var defaultColunms = Manage.initColumn();
    var table = new BSTable(Manage.id, "/manage/list", defaultColunms);
    table.setPaginationType("client");
    Manage.table = table.init();
});
