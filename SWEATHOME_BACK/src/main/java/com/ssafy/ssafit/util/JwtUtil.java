package com.ssafy.ssafit.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private String key ="SSAFY_NonMajor_JavaTrack_SecretKey";
	// secretkey 에 암호해서 넣음
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	// 다양한 데이터를 Map으로 받아서 처리를 할 수도 있지만
	// 심플하게  Id 만입력받아서 토큰을 만들어보자
	public String createToken(String id ) {
		// 토큰 만료시간 1시간으로 설정
		Date exp = new Date(System.currentTimeMillis()+1000*60*60);// 1시간
		return Jwts.builder().header().add("typ","JWT").and().claim("id", id).expiration(exp).signWith(secretKey).compact();
	}
	
	
	// 실제로 확인하려는 용도가아니고 유호시간이 지났다면 알아서 에러를 발생시키고함
	public Jws<Claims> vaildate(String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
}	
