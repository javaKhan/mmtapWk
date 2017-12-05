/**
 * 客户管理初始化
 */
var Custom = {
    id: "CustomTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    role:'dialog'
};

/**
 * 初始化表格的列
 */
Custom.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'cid', visible: false, align: 'center', valign: 'middle'},
        {title: '客户', field: 'customname', visible: true, align: 'center', valign: 'middle'},
        {title: '电话', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '旺旺', field: 'wwid', visible: true, align: 'center', valign: 'middle'},
        {title: '创建者', field: 'createrName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', formatter:'customFormatter', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Custom.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Custom.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加客户
 */
Custom.openAddCustom = function () {
    var index = layer.open({
        type: 2,
        title: '添加客户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/custom/custom_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看客户详情
 */
Custom.openCustomDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '客户详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/custom/custom_update/' + Custom.seItem.cid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除客户
 */
Custom.delete = function () {
    if (this.check()) {
        var oper = function () {
            var ajax = new $ax(Feng.ctxPath + "/custom/delete", function (data) {
                Feng.success("删除成功!");
                Custom.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("customId",Custom.seItem.cid);
            ajax.start();
        }
        Feng.confirm("是否要删除客户["+Custom.seItem.customname+"]?",oper);
    }
};

/**
 * 查询客户列表
 */
Custom.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Custom.table.refresh({query: queryData});
};


/**
 * 自定义按钮
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function customFormatter(value, row, index) {//赋予的参数
    return [
        '<a class="orderState btn btn-sm btn-primary" href="/custom/append/'+row.cid+'">新增业务</a>',
        '<a class="orderTrace btn btn-sm btn-info" href="/custom/works/'+row.cid+'">已有业务</a>',
    ].join('');
}

$(function () {
    var defaultColunms = Custom.initColumn();
    var table = new BSTable(Custom.id, "/custom/list", defaultColunms);
    table.setPaginationType("client");
    table.setClickToSelect(true);
    Custom.table = table.init();
});
