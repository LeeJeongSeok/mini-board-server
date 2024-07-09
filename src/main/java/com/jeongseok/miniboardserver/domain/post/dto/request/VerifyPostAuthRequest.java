package com.jeongseok.miniboardserver.domain.post.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerifyPostAuthRequest {

	private final long postId;
	private final String password;

}
