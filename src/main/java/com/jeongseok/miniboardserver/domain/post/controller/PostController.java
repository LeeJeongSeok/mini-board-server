package com.jeongseok.miniboardserver.domain.post.controller;

import com.jeongseok.miniboardserver.common.ApiResponse;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.request.UpdatePostRequest;
import com.jeongseok.miniboardserver.domain.post.dto.request.VerifyPostAuthRequest;
import com.jeongseok.miniboardserver.domain.post.dto.response.PostResponse;
import com.jeongseok.miniboardserver.domain.post.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
		PostResponse postResponse = postService.getByPostId(postId);
		ApiResponse<PostResponse> response = ApiResponse.success(postResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/api/v1/posts")
	public ResponseEntity<ApiResponse<Long>> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
		long postId = postService.createPost(createPostRequest);
		ApiResponse<Long> response = ApiResponse.success(postId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/api/v1/posts/verify")
	public ResponseEntity<ApiResponse<Long>> verifyPostPassword(@RequestBody VerifyPostAuthRequest verifyPostAuthRequest, HttpServletResponse response) {
		boolean isVerify = postService.verifyPassword(verifyPostAuthRequest);

		if (!isVerify) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Cookie cookie = new Cookie("token", UUID.randomUUID().toString());
		cookie.setMaxAge(3 * 60); // 3분 유효시간 설정
		cookie.setHttpOnly(true); // 보안 설정
		response.addCookie(cookie);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/api/v1/posts/{postId}")
	public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable long postId, @Valid @RequestBody UpdatePostRequest updatePostRequest, HttpServletRequest request) {

		if (!isCookiePresent(request)) {
			// 쿠키가 없으므로 업데이트할 권한이 없음
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		PostResponse postResponse = postService.updatePost(postId, updatePostRequest);
		ApiResponse<PostResponse> response = ApiResponse.success(postResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/api/v1/posts/{postId}")
	public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable long postId, HttpServletRequest request) {

		if (!isCookiePresent(request)) {
			// 업데이트와 마찬가지로 쿠키가 없으므로 삭제할 권한이 없다.
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		postService.deletePost(postId);
		ApiResponse<String> response = ApiResponse.success();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private boolean isCookiePresent(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				return true;
			}
		}

		return false;
	}
}
