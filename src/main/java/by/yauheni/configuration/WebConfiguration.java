package by.yauheni.configuration;

import by.yauheni.interceptor.PermissionInterceptor;
import by.yauheni.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;
    private final PermissionInterceptor permissionInterceptor;

    @Autowired
    public WebConfiguration(TokenInterceptor tokenInterceptor, PermissionInterceptor permissionInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.permissionInterceptor = permissionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/admin/**", "/items/**", "/users/addToCart", "/users/removeFromCart", "/users/buy, /users/search");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/admin/**");
    }
}
