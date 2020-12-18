package com.example.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.blog.config.auth.PrincipalDetailService;

//bean 등록 : 스프링 컨테이너에서 객체를 관리 할 수 있게 하는 것  커스터 마이징 할수 있는것이다!

@Configuration//bean 등록 (IoC관리)
@EnableWebSecurity//시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어있는데 어떤 설정을 해당 파일에서 하겠다. 시큐리티 필터가 등록된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을 하면 권한 및 인증을 미리 채크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC 가능
	public BCryptPasswordEncoder encodePWD() {
		//String encPassword = new BCryptPasswordEncoder().encode("1234");
		return new BCryptPasswordEncoder();
		
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬되어 회원가입이 되었는지 알아야 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교 할수 있다.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http
	  	.csrf().disable()//csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc")
			.defaultSuccessUrl("/");//스프링 시큐리티가 해당 주소로 오는 로그인을 가로채서 대신 로그인 해준다.
	}
}
