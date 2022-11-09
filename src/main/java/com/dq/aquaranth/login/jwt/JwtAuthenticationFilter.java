package com.dq.aquaranth.login.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dq.aquaranth.login.dto.LoginReqDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// 로그인이 성공할 때마다 access token 과 refresh token 을 발급해줄 filter
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("지나갈게요@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        super.doFilter(request, response, chain);
    }

    /**
     * 인증 시도 호출되는 메서드
     */


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginReqDTO dto = null;

        try {
            dto = om.readValue(request.getInputStream(), LoginReqDTO.class);
        } catch (Exception e) {
            log.error("로그인 데이터를 읽어오던 중 오류가 발생했습니다. message => {}", e.getMessage());
        }

        if (Objects.isNull(dto)) {
            log.error("전달받은 login 객체가 없습니다.");
        }

        String username = Objects.requireNonNull(dto).getUsername();
        String password = dto.getPassword();

        log.info("Username is : {}", username);
        log.info("password is : {}", password);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // ** 인증 시도시
        // 1. authenticate() 메서드 호출
        // 2. 인증 프로바이더가 유저 디테일서비스의 loadUserByUsername(토큰의 첫번째 파라메터) 를 호출
        // 3. UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과 UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // 4. Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증 성공시(로그인이 되었을 때 호출 될 메서드),
     * 사용자에게 access token 과,
     * 성공적으로 로그인 한 후 refresh token 을 제공하는 메서드
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal(); // 현재 인증된(로그인한) 사용자의 정보를 가져온다
        Algorithm algorithm = Algorithm.HMAC256(JwtProperties.SECRET.getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        log.info("{} 님이 로그인 하였습니다.", user.getUsername());

        /* token body 로 던지기 */
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

//        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        redisTemplate.opsForValue().set(
                authentication.getName(),
                refresh_token,
                JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), tokens);
    }
}
