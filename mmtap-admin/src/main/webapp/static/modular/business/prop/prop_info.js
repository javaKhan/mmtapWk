/**
 * 初始化属性详情对话框
 */
var PropInfoDlg = {
    propInfoData : {},
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '流程名称不能为空'
                }
            }
        },
        proporder: {
            validators: {
                notEmpty: {
                    message: '流程排序不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
PropInfoDlg.clearData = function() {
    this.propInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PropInfoDlg.set = function(key, val) {
    this.propInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PropInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PropInfoDlg.close = function() {
    parent.layer.close(window.parent.Prop.layerIndex);
}

/**
 * 收集数据
 */
PropInfoDlg.collectData = function() {
    console.info(window.parent.Prop.seItem);
    this.set('pid').set('bid',window.parent.Prop.bid).set('title').set('proporder').set('code');
}

/**
 * 校验输入
 * @returns {*|jQuery}
 */
PropInfoDlg.validate = function () {
    $('#flow_prop_form').data("bootstrapValidator").resetForm();
    $('#flow_prop_form').bootstrapValidator('validate');
    return $("#flow_prop_form").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
PropInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prop/add", function(data){
        Feng.success("添加成功!");
        window.parent.Prop.table.refresh();
        PropInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.propInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PropInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prop/update", function(data){
        Feng.success("修改成功!");
        window.parent.Prop.table.refresh();
        PropInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.propInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("flow_prop_form",PropInfoDlg.validateFields);
});
