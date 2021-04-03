package security;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import enums.Role;
import exceptions.ForbiddenException;
import exceptions.UnauthorisedException;
import io.jsonwebtoken.Claims;

@Aspect
@Configuration
public class SecurityAspect {
	@Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private TokenService tokenService;

    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Around("@annotation(security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Long methodId = null;
        Claims claims = null;

        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
            	claims = tokenService.parseToken(joinPoint.getArgs()[i].toString());
            }
            if (methodSignature.getParameterNames()[i].equals("id")) {
            	if(methodId == null && joinPoint.getArgs()[i] instanceof Long) {
            		methodId = (Long)joinPoint.getArgs()[i];
            	}
            }
        }
        
        if (claims == null) {
            throw new UnauthorisedException("Niste ulogovani.");
        }

        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);

        String roleString = claims.get("role", String.class);
        Role role = Role.valueOf(roleString);
        if (Arrays.asList(checkSecurity.roles()).contains(role)) {
        	if(!role.getGlobalPermissions() && checkSecurity.checkOwnership()) {
        		Long id = claims.get("id", Long.class);
        		if(id.equals(methodId)) {
        			return joinPoint.proceed();
        		}
        	} 
        	else {
                return joinPoint.proceed();
        	}
        }

        throw new ForbiddenException("Nemate dozvolu za ovo.");
    }
}
