package com.jeongseok.miniboardserver.domain.post.service;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.dto.mapper.PostMapper;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.request.UpdatePostRequest;
import com.jeongseok.miniboardserver.domain.post.repository.PostRepository;
import com.jeongseok.miniboardserver.domain.post.dto.response.PostResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public List<PostResponseDto> findAll() {
		List<Post> posts = postRepository.findAllByUseYn();

		return PostMapper.toDto(posts);
	}

	public PostResponseDto findByPostId(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		// Response에서 DTO로 변환
		return PostMapper.toDto(post);
	}

	@Transactional
	public long createPost(CreatePostRequest createPostRequest) {
		Post savedPost = postRepository.save(createPostRequest.toEntity());

		return savedPost.getPostId();
	}

	@Transactional
	public PostResponseDto updatePost(long postId, UpdatePostRequest updatePostRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());

		return PostMapper.toDto(post);
	}

	@Transactional
	public void delete(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		postRepository.delete(post);
	}
}
