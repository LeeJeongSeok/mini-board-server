package com.jeongseok.miniboardserver.domain.post.dto.mapper;

import com.jeongseok.miniboardserver.domain.post.dto.response.PostResponseDto;
import com.jeongseok.miniboardserver.domain.post.domain.Post;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

//	public static Post toEntity(PostRequestDto.CreatePost dto) {
//		Post post = new Post();
//		post.setTitle(dto.getTitle());
//		post.setContent(dto.getContent());
//		post.setAuthor(dto.getAuthor());
//		post.setUseYn("Y");
//
//		return post;
//	}

//	public static Post toEntity(PostRequestDto.UpdatePost dto, Post post) {
//		post.setTitle(dto.getTitle());
//		post.setContent(dto.getContent());
//
//		return post;
//	}

	public static List<PostResponseDto> toDto(List<Post> posts) {

		return posts.stream()
			.map(PostMapper::toDto)
			.collect(Collectors.toList());

	}

	public static PostResponseDto toDto(Post post) {
		PostResponseDto dto = new PostResponseDto();
		dto.setPostId(post.getPostId());
		dto.setTitle(post.getTitle());
		dto.setContent(post.getContent());
		dto.setAuthor(post.getAuthor());
		dto.setCreatedAt(post.getCreatedAt());
		dto.setUpdatedAt(post.getUpdatedAt());

		return dto;
	}

}
