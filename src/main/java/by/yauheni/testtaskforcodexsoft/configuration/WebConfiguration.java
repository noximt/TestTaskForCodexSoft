package by.yauheni.testtaskforcodexsoft.configuration;

import by.yauheni.testtaskforcodexsoft.interceptor.PermissionInterceptor;
import by.yauheni.testtaskforcodexsoft.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
    private final TokenInterceptor tokenInterceptor;
    private final PermissionInterceptor permissionInterceptor;

    @Autowired
    public WebConfiguration(TokenInterceptor tokenInterceptor, PermissionInterceptor permissionInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.permissionInterceptor = permissionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/admin/*", "/items/*", "/users/addToCart", "/users/removeFromCart", "/users/buy, /users/search");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/admin/*");
    }
}
