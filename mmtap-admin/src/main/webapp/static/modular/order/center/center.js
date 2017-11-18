/**
 * 订单中心管理初始化
 */
var Center = {
    id: "CenterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Center.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Center.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Center.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单中心
 */
Center.openAddCenter = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单中心',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/center/center_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单中心详情
 */
Center.openCenterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单中心详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/center/center_update/' + Center.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单中心
 */
Center.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/center/delete", function (data) {
            Feng.success("删除成功!");
            Center.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("centerId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单中心列表
 */
Center.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Center.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Center.initColumn();
    var table = new BSTable(Center.id, "/center/list", defaultColunms);
    table.setPaginationType("client");
    Center.table = table.init();
});
