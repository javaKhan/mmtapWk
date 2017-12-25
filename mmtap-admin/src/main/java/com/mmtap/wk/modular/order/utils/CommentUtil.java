package com.mmtap.wk.modular.order.utils;

import com.alibaba.fastjson.JSON;
import com.mmtap.wk.modular.order.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论格式化工具
 */
public class CommentUtil {
    /**
     * 生成JSON字符串的评论
     * @param wcomList
     * @return
     */
    public static String toJsonArrStr(List wcomList){
        return JSON.toJSONString(wcomList);
    }

    /**
     * 由评论字符串生成To的list
     * @param str
     * @return
     */
    public static List<Comment> toComObj(String str){
        List<Comment> res = new ArrayList();
        if(null!=str && !"".equals(str)){
            res = JSON.parseArray(str,Comment.class);
        }
        return res;
    }
}
