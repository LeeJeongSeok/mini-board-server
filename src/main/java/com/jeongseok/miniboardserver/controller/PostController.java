package com.jeongseok.miniboardserver.controller;

import com.jeongseok.miniboardserver.common.ApiResponse;
import com.jeongseok.miniboardserver.dto.post.PostRequestDto;
import com.jeongseok.miniboardserver.dto.post.PostResponseDto;
import com.jeongseok.miniboardserver.service.PostService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<PostResponseDto>>> readPosts() {
		List<PostResponseDto> posts = postService.findAll();
		ApiResponse<List<PostResponseDto>> response = ApiResponse.success(posts);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<PostResponseDto>> readPost(@PathVariable Long postId) {
		PostResponseDto postResponseDto = postService.findByPostId(postId);
		ApiResponse<PostResponseDto> response = ApiResponse.success(postResponseDto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse<Long>> createPost(@Valid @RequestBody PostRequestDto.CreatePost createPost) {

		// 실제 post 처리 로직
		// 성공적으로 DB에 적재가 된 것을 판별하기 위해 적재된 데이터의 id값을 가져온다.
		Long postId = postService.save(createPost);

		ApiResponse<Long> response = ApiResponse.success(postId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto.UpdatePost updatePost) {
		PostResponseDto postResponseDto = postService.update(postId, updatePost);
		ApiResponse<PostResponseDto> response = ApiResponse.success(postResponseDto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long postId) {
		postService.delete(postId);

		ApiResponse<String> response = ApiResponse.success();

		// 성공적으로 삭제될 경우 204 NO_CONTENT를 응답으로 내보내는데, 이때 본문의 콘텐츠가 없다. 그래서 200 OK로 수정
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 유효성 검사 실패 시 발생하는 예외를 처리하는 메서드
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		ApiResponse<Map<String, String>> response = ApiResponse.error("유효성 검사 실패", errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
