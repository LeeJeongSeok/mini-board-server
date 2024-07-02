package com.jeongseok.miniboardserver.domain.post.controller;

import com.jeongseok.miniboardserver.common.ApiResponse;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.request.UpdatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.response.PostResponse;
import com.jeongseok.miniboardserver.domain.post.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/api/v1/posts")
	public ResponseEntity<ApiResponse<List<PostResponse>>> readPosts() {
		List<PostResponse> posts = postService.findAll();
		ApiResponse<List<PostResponse>> response = ApiResponse.success(posts);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/api/v1/posts/{postId}")
	public ResponseEntity<ApiResponse<PostResponse>> readPost(@PathVariable long postId) {
		PostResponse postResponse = postService.findByPostId(postId);
		ApiResponse<PostResponse> response = ApiResponse.success(postResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/api/v1/posts")
	public ResponseEntity<ApiResponse<Long>> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
		long postId = postService.createPost(createPostRequest);
		ApiResponse<Long> response = ApiResponse.success(postId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/api/v1/posts/{postId}")
	public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable long postId, @Valid @RequestBody UpdatePostRequest updatePostRequest) {
		PostResponse postResponse = postService.updatePost(postId, updatePostRequest);
		ApiResponse<PostResponse> response = ApiResponse.success(postResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/api/v1/posts/{postId}")
	public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable long postId) {
		postService.deletePost(postId);
		ApiResponse<String> response = ApiResponse.success();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
