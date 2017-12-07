/**
 * 初始化业务状态详情对话框
 */
var FlowInfoDlg = {
    roleTree :null,
    flowInfoData : {},
    validateFields: {
        flowname: {
            validators: {
                notEmpty: {
                    message: '流程名称不能为空'
                }
            }
        },
        floworder: {
            validators: {
                notEmpty: {
                    message: '流程排序不能为空'
                }
            }
        },
        roleText: {
            validators: {
                notEmpty: {
                    message: '处理角色不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
FlowInfoDlg.clearData = function() {
    this.flowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FlowInfoDlg.set = function(key, val) {
    this.flowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FlowInfoDlg.close = function() {
    parent.layer.close(window.parent.FlowState.layerIndex);
}

/**
 * 收集数据
 */
FlowInfoDlg.collectData = function() {
    this.set('fid').set('bid',window.parent.FlowState.busId).set('flowname').set('floworder').set('flowrole');
}
/**
 * 校验输入
 * @returns {*|jQuery}
 */
FlowInfoDlg.validate = function () {
    $('#flow_state_form').data("bootstrapValidator").resetForm();
    $('#flow_state_form').bootstrapValidator('validate');
    return $("#flow_state_form").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
FlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/flow/add", function(data){
        Feng.success("添加成功!");
        window.parent.FlowState.table.refresh();
        FlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.flowInfoData);
    console.info(this.flowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/flow/update", function(data){
        Feng.success("修改成功!");
        window.parent.FlowState.table.refresh();
        FlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.flowInfoData);
    ajax.start();
}

FlowInfoDlg.showRoleSelectTree = function () {
    Feng.showInputTree("roleText", "roleTextContext");
}

FlowInfoDlg.onClickPName = function (e, treeId, treeNode) {
    $("#roleText").attr("value", FlowInfoDlg.roleTree.getSelectedVal());
    $("#flowrole").attr("value", treeNode.id);
    console.info(this.roleTree);
};


$(function() {
    var roleNameTree = new $ZTree("roleNameTree", "/role/roleTreeList");
    roleNameTree.bindOnClick(FlowInfoDlg.onClickPName);
    roleNameTree.init();
    FlowInfoDlg.roleTree = roleNameTree;

    Feng.initValidator("flow_state_form",FlowInfoDlg.validateFields);
});
