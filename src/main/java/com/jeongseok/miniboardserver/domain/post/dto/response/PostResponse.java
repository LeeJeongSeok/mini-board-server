package com.jeongseok.miniboardserver.domain.post.dto.response;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostResponse {

	private long postId;
	private String title;
	private String content;
	private String author;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static PostResponse toDto(Post post) {
		return PostResponse.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.content(post.getContent())
			.author(post.getAuthor())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}

}
