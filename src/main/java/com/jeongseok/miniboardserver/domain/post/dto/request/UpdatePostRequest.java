package com.jeongseok.miniboardserver.domain.post.dto.request;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequest {

	@NotBlank(message = "제목은 필수 입력 항목입니다.")
	@Size(max = 150, message = "제목은 최대 150자까지 입력할 수 있습니다.")
	private String title;

	@NotBlank(message = "내용은 필수 입력 항목입니다.")
	private String content;
}
