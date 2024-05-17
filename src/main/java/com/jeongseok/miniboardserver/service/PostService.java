package com.jeongseok.miniboardserver.service;

import com.jeongseok.miniboardserver.domain.Post;
import com.jeongseok.miniboardserver.dto.post.PostRequestDto;
import com.jeongseok.miniboardserver.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void save(PostRequestDto postRequestDto) {
		Post post = convertToEntity(postRequestDto);
		postRepository.save(post);
	}

	private Post convertToEntity(PostRequestDto postRequestDto) {
		Post post = new Post();
		post.setTitle(postRequestDto.getTitle());
		post.setContent(postRequestDto.getContent());
		post.setAuthor(postRequestDto.getAuthor());

		return post;
	}
}
