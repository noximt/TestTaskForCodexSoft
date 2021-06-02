package by.yauheni.interceptor;

import by.yauheni.entity.Type;
import by.yauheni.entity.Token;
import by.yauheni.entity.User;
import by.yauheni.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Transactional
public class PermissionInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;

    @Autowired
    public PermissionInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uuid = request.getHeader("X-Token");
        if (tokenService.tokenContains(uuid)){
            Token token = tokenService.getByUUID(uuid);
            User user = token.getUser();
            if (user.getType().equals(Type.USER)){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }
        return true;
    }
}
