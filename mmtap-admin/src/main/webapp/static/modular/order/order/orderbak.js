/**
 * 订单管理初始化
 */
var Order = {
    id: "OrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

Order.validateFields = {
    bdate: {
        validators: {
            notEmpty: {
                message: '起始时间不能为空!'
            }
        }
    },
    edate: {
        validators: {
            notEmpty: {
                message: '结束时间不能为空'
            }
        }
    },
}
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
        '<button class="backOne btn btn-small btn-primary" href="#">导出</button>',
    ].join('');
}


window.operateEvents = {
    'click .backOne': function (e, value, row, index) {
        location.href = '/order/baksig/'+ row.oid;
    },
    'click .bakall': function (e, value, row, index) {
        console.info("ssss")
        location.href = '/order/bakall/'+ row.oid;
    }
};

/**
 * 点击事件响应
 */
Order.bakAll =function () {
    var bdateVal = $("#bdateStr").val();
    var edateVal = $("#edateStr").val();
    if(''==bdateVal || ''==edateVal){
        Feng.info("时间不能为空!")
    }else {
        var myform = $("<form></form>");
        myform.attr('method','post');
        myform.attr('action',"/order/bakall");

        var myBdate = $("<input type='hidden' name='bdate' />");
        var myEdate = $("<input type='hidden' name='edate' />");

        myBdate.attr('value',bdateVal);
        myEdate.attr('value',edateVal);
        myform.append(myBdate);
        myform.append(myEdate);
        myform.appendTo('body').submit();
    }

}

Order.bakAllPost =function () {

}




$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("client");
    table.setClickToSelect(true);
    Order.table = table.init();

    // Feng.initValidator("bakAllForm",Order.validateFields);
});
