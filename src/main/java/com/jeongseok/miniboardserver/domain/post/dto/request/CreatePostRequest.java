package com.jeongseok.miniboardserver.domain.post.dto.request;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.util.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {

	@NotBlank(message = "제목은 필수 입력 항목입니다.")
	@Size(max = 150, message = "제목은 최대 150자까지 입력할 수 있습니다.")
	private String title;

	@NotBlank(message = "내용은 필수 입력 항목입니다.")
	private String content;

	@NotBlank(message = "작성자는 필수 입력 항목입니다.")
	@Size(max = 25, message = "작성자는 최대 25자까지 입력할 수 있습니다.")
	private String author;

	private PostType useYn = PostType.Y;


	public Post toEntity() {
		return Post.builder()
			.title(getTitle())
			.content(getContent())
			.author(getAuthor())
			.useYn(getUseYn())
			.build();
	}

}
