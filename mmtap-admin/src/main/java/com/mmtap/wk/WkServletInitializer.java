package com.mmtap.wk;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 *  Web程序启动类
 *
 * @author imkzp.com
 * @date 2017-05-21 9:43
 */
public class WkServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WkApplication.class);
    }

}
