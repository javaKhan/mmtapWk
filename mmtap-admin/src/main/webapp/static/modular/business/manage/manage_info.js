/**
 * 初始化业务中心详情对话框
 */
var ManageInfoDlg = {
    manageInfoData : {},
    validateFields: {
        businessname: {
            validators: {
                notEmpty: {
                    message: '业务名称不能为空'
                }
            }
        },
        busprice: {
            validators: {
                notEmpty: {
                    message: '业务价格不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ManageInfoDlg.clearData = function() {
    this.manageInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ManageInfoDlg.set = function(key, val) {
    this.manageInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ManageInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ManageInfoDlg.close = function() {
    parent.layer.close(window.parent.Manage.layerIndex);
}

/**
 * 收集数据
 */
ManageInfoDlg.collectData = function() {
    this.set('bid').set('businessname').set("busprice");
}

ManageInfoDlg.validate = function () {
    $('#manage_info').data("bootstrapValidator").resetForm();
    $('#manage_info').bootstrapValidator('validate');
    return $("#manage_info").data('bootstrapValidator').isValid();
};


/**
 * 提交添加
 */
ManageInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manage/add", function(data){
        Feng.success("添加成功!");
        window.parent.Manage.table.refresh();
        ManageInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manageInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ManageInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manage/update", function(data){
        Feng.success("修改成功!");
        window.parent.Manage.table.refresh();
        ManageInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manageInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("manage_info", ManageInfoDlg.validateFields)
});
