package com.jeongseok.miniboardserver.controller;

import com.jeongseok.miniboardserver.dto.post.PostRequestDto;
import com.jeongseok.miniboardserver.dto.post.PostResponseDto;
import com.jeongseok.miniboardserver.service.PostService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId) {
		PostResponseDto postResponseDto = postService.findByPostId(postId);

		return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<String> createPost(@Valid @RequestBody PostRequestDto.CreatePost createPost) {

		// 실제 post 처리 로직
		postService.save(createPost);

		return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<String> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto.UpdatePost updatePost) {
		 postService.update(postId, updatePost);
		return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
	}

	// 유효성 검사 실패 시 발생하는 예외를 처리하는 메서드
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
