package ks51team03.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String fileRealPath = "/home/ks51team03/";

    /**
     * 외부리소스 경로설정
     * @param
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = getOSFilePath();
        registry.addResourceHandler("/attachment/**")
                .addResourceLocations(rootPath + fileRealPath + "attachment/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
    public String getOSFilePath() {
        String rootPath = "file:///";
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win")) {
            rootPath = "file:///d:";
        }else if(os.contains("linux")) {
            rootPath = "file:///";
        }else if(os.contains("mac")){
            rootPath = "file:///Users/Shared";
        }
        return rootPath;
    }
}
