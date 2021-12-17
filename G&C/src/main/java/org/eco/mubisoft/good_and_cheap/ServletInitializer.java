package org.eco.mubisoft.good_and_cheap;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p><b>Servlet Initializer</b></p>
 * <p>Initializer of the Servlet for the web application.</p>
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GoodAndCheapApplication.class);
    }

}
