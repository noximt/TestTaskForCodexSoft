package by.yauheni.interceptor;

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
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;

    @Autowired
    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uuid = request.getHeader("X-Token");
        if (tokenService.tokenContains(uuid)){
            Token byId = tokenService.getByUUID(uuid);
            User user = byId.getUser();
            request.setAttribute("user", user);
            return true;
        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
