package com.mmtap.wk.core.util;

import com.mmtap.wk.common.constant.Const;
import com.mmtap.wk.core.node.MenuNode;
import com.mmtap.wk.config.properties.WkProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口文档显示过滤
 *
 * @author imkzp.com
 * @date 2017-08-17 16:55
 */
public class ApiMenuFilter extends MenuNode {


    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
        WkProperties wkProperties = SpringContextHolder.getBean(WkProperties.class);
        if (!wkProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
