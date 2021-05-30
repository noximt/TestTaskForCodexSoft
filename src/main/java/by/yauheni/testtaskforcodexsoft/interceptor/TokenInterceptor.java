package by.yauheni.testtaskforcodexsoft.interceptor;

import by.yauheni.testtaskforcodexsoft.entity.Token;
import by.yauheni.testtaskforcodexsoft.entity.User;
import by.yauheni.testtaskforcodexsoft.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("X-Token");
        if (tokenRepository.existsById(header)){
            Token byId = tokenRepository.getById(header);
            User user = byId.getUser();
            request.setAttribute("user", user);
            return true;
        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
