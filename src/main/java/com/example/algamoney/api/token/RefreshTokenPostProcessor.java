package com.example.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@SuppressWarnings("deprecation")
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest reques = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)body; 
		
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshToken(refreshToken,reques, resp);
		removerRefreshTokenDoBody((DefaultOAuth2AccessToken) body);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}


	private void adicionarRefreshToken(String refreshToken, HttpServletRequest reques, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken); 
		refreshTokenCookie.setHttpOnly(false);
		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath(reques.getContentType() + "/oauth/token");
		refreshTokenCookie.setMaxAge(2592000);
		((HttpServletResponse) reques).addCookie(refreshTokenCookie);
	}

}
