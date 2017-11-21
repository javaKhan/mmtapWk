/**
 * 订单管理初始化
 */
var Order = {
    id: "OrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Order.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单号', field: 'oid', visible: true, align: 'center', valign: 'middle'},
        {title: '客户名称', field: 'customName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建者', field: 'createName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', formatter:'operateFormatter', events:'operateEvents', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Order.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Order.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单
 */
Order.openAddOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/order/order_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单详情
 */
Order.openOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/order_update/' + Order.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单
 */
Order.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/order/delete", function (data) {
            Feng.success("删除成功!");
            Order.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单列表
 */
Order.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Order.table.refresh({query: queryData});
};

/**
 * 自定义按钮
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function operateFormatter(value, row, index) {//赋予的参数
    return [
        '<button class="orderState btn btn-small btn-primary" href="#">状态</button>',
        '<button class="orderTrace btn btn-small btn-info">跟踪</button>',
    ].join('');
}


window.operateEvents = {
    'click .orderTrace': function (e, value, row, index) {
        location.href = '/order/order_trace/'+ row.oid;
    },
    'click .orderState': function (e, value, row, index) {
        location.href = '/order/order_state/'+ row.oid;
    }
};

$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("client");
    table.setClickToSelect(true);
    Order.table = table.init();
});
