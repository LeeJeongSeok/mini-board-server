package com.jeongseok.miniboardserver.domain.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class VerifyPostRequest {

	private final long postId;
	private final String password;

}
