package com.jeongseok.miniboardserver.domain.post.service;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.request.UpdatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.response.PostResponse;
import com.jeongseok.miniboardserver.domain.post.repository.PostRepository;
import com.jeongseok.miniboardserver.domain.post.util.PostType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public List<PostResponse> findAll() {
		List<Post> posts = postRepository.findAllByUseYn(PostType.Y);

		return posts.stream()
			.map(PostResponse::toDto)
			.collect(Collectors.toList());
	}

	public PostResponse getByPostId(long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		// Response에서 DTO로 변환
		return PostResponse.toDto(post);
	}

	@Transactional
	public long createPost(CreatePostRequest createPostRequest) {
		Post savedPost = postRepository.save(createPostRequest.toEntity());

		return savedPost.getPostId();
	}

	@Transactional
	public PostResponse updatePost(long postId, UpdatePostRequest updatePostRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());

		return PostResponse.toDto(post);
	}

	@Transactional
	public void deletePost(long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		postRepository.delete(post);
	}
}
