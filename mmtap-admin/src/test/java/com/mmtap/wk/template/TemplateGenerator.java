package com.mmtap.wk.template;

import com.mmtap.wk.core.template.config.ContextConfig;
import com.mmtap.wk.core.template.engine.SimpleTemplateEngine;
import com.mmtap.wk.core.template.engine.base.GunsTemplateEngine;

import java.io.IOException;

/**
 * 测试Guns模板引擎
 *
 * @author imkzp.com
 * @date 2017-05-09 20:27
 */
public class TemplateGenerator {

    public static void main(String[] args) throws IOException {
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName("啊哈");
        contextConfig.setBizEnName("haha");
        contextConfig.setModuleName("tk");
        contextConfig.setProjectPath("D:\\tmp\\guns");

        //contextConfig.setAddPageSwitch(false);
        //contextConfig.setEditPageSwitch(false);

        GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
        gunsTemplateEngine.setContextConfig(contextConfig);
        gunsTemplateEngine.start();
    }

}
