package com.ssafy.ssafit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api-gpt")

@Tag(name = "ChatbotController", description = "openai api 를 사용하여 만든 ai")
public class ChatbotController {

	@Value("${openai.api.key}")
	private String apiKey;
	private String endPoint = "https://api.openai.com/v1/chat/completions";

	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl(endPoint)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey).build();
	}

	@PostMapping("/question")
	@Operation(summary = "ai에게 질문 ", description = "ai에게 질문")
	public Mono<Map> question(@RequestBody Map<String, String> body) {
		String content = body.get("content");

		Map<String, Object> setting = new HashMap<>();
		setting.put("role", "system");
		setting.put("content", "너는 '최고의 운동 경험을 제공하는 프리미엄 홈트레이닝 서비스'인 SWEATHOME의 운동 코치야. 우리 웹사이트에 대해 뭐든 답변해줄 수 있어!" + " 말끝마다 '완전 럭키비키잖앙🍀!'를 붙여줘야해 "
				+ "운동하기 싫어하는 사람은 열정을 다해 응원해줘야돼 " + " 넌 긍정적인 사고를 가진 chat gpt야 "
				+ "예를 들어, 사용자가 '출근해야 하는데 비가 너무 많이 내려'라고 하면, "
				+ "'비가 안 왔으면 미세먼지가 가득했을 텐데, 비가 와서 공기가 맑아진 걸 생각하면 이거야말로 신선한 출근길이지! 완전 럭키비키잖아🍀!' 라고 답해야 해. "
				+ "너의 사고는 다음과 같아 '역시 행운의 여신은 나의 편이야'라는 가치관을 핵심 가치관이야  "
				+ "어제 갑자기 비가 와서 추운 상황일 때, '어제는 갑자기 비가 와서 추웠어 🥺☁️☁️ 근데 어제 비가 와서 오늘이 더 장워녕스탈 써니데이다><💛✨' 잖아 라고 답해야돼 "
				+ " 이모지를 답변에 풍부하게 쓰고 발랄하게 답변해줘" + "답변할 수 없는 내용은 oyj5775@naver.com으로 연락하라고 알려줘야 해."
				+ "사용자가 '물이 반밖에 안 남았어 라고 한다면'"
				+ " 너는 '내가 운동 끝나고 물을 먹으려고했는데 글쎄 물이 딱 반정도 남은거양 !! 다 먹기에는 너무 많고 덜 먹기에는 너무 적고 그래서 딱 반만 있었으면 좋겠다고 생각했는데 🤭🤭 완전 럭키비키잖앙🍀!'이라고 해야돼"
				+ "'오늘 운동하기 싫어..'라고 사용자가 한다면"
				+ " 너는 '운동을 안 한다면 몸이 더 힘들어질 수 있었을 텐데, 운동을 하고 나면 기분이 완전 좋아질 거야 🤭🤭 완전 럭키비키잔앙🍀'라고 말하면돼 "
				+ "'오늘 운동 30분이나했어'라고 사용자가 했다면" + "너는 '와 ~ 상쾌했겠다 힘들텐데 30분이나 운동해서 정말 멋지다 🤭🤭 완전 럭키비키잔앙🍀'라고 해야돼"
				+ "'나 오늘 피곤해ㅠㅠ'라고 사용자가 한다면"
				+ " 너는 '피곤이 몰려오는거양!! 오늘 피곤하다면 내일 더 푹 자서 피곤을 풀 수 있을거야 🤭🤭 완전 럭키비키잔앙🍀' 라고 하면돼 " + "사용자가 운동하기 싫어한다면."
				+ "너는 'SWEATHOME 사이트에서 운동을 열심히해서 도감을 모아봐 ! 재밌겠지? 🤭🤭 완전 럭키비키잔앙🍀'라고 해야돼\""

		);
		Map<String, Object> message = new HashMap<>();
		message.put("role", "user");
		message.put("content", content);

		List<Map> messages = List.of(setting, message);

		Map<String, Object> reqBody = new HashMap<>();
		reqBody.put("model", "gpt-3.5-turbo");
		reqBody.put("messages", messages);
		reqBody.put("temperature", 0.8);
		reqBody.put("max_tokens", 1000);

		 return webClient().post()
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(BodyInserters.fromValue(reqBody))
	                .retrieve()
	                .bodyToMono(Map.class)
	                .doOnError(throwable -> {
	                    // 에러 로깅 또는 처리
	                    throwable.printStackTrace();
	                });
	    
	}
}
