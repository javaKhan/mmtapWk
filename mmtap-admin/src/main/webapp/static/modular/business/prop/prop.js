/**
 * 属性管理初始化
 */
var Prop = {
    id: "PropTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    bid:null
};

/**
 * 初始化表格的列
 */
Prop.busColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '业务ID', field: 'bid', visible: false, align: 'center', valign: 'middle'},
        {title: '业务名称', field: 'businessname', visible: true, align: 'center', valign: 'middle'},
        {title: '创建者', field: 'createName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
Prop.propColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '状态ID', field: 'pid', visible: false, align: 'center', valign: 'middle'},
        {title: '属性名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
        {title: '编码', field: 'code', visible: true, align: 'center', valign: 'middle'},
        {title: '排序', field: 'proporder', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Prop.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Prop.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加属性
 */
Prop.openAddProp = function () {
    var index = layer.open({
        type: 2,
        title: '添加属性',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/prop/prop_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看属性详情
 */
Prop.openPropDetail = function () {
    console.info(Prop.seItem);
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '属性详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/prop/prop_update/' + Prop.seItem.pid
        });
        this.layerIndex = index;
    }
};

/**
 * 删除属性
 */
Prop.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/prop/delete", function (data) {
            Feng.success("删除成功!");
            Prop.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("pid",this.seItem.pid);
        ajax.start();
    }
};

/**
 * 查询属性列表
 */
Prop.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Prop.table.refresh({query: queryData});
};

Prop.list = function () {
    if(this.check()){
        $("#prop").empty().load("/prop/prop_list",{bid:this.seItem.bid});
    }
};


