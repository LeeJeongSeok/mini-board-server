package com.jeongseok.miniboardserver.service;

import com.jeongseok.miniboardserver.domain.Post;
import com.jeongseok.miniboardserver.dto.post.PostRequestDto;
import com.jeongseok.miniboardserver.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void save(PostRequestDto.CreatePost createPost) {
		Post post = convertToEntity(createPost);
		postRepository.save(post);
	}

	@Transactional
	public void update(Long postId, PostRequestDto.UpdatePost updatePost) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		post.setTitle(updatePost.getTitle());
		post.setContent(updatePost.getContent());
		// updatedAt은 @PreUpdate에 의해 자동으로 설정됩니다.
	}

	private Post convertToEntity(PostRequestDto.CreatePost createPost) {
		Post post = new Post();
		post.setTitle(createPost.getTitle());
		post.setContent(createPost.getContent());
		post.setAuthor(createPost.getAuthor());

		return post;
	}
}
