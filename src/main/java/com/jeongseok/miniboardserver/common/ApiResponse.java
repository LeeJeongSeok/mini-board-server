package com.jeongseok.miniboardserver.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
	private String status;
	private String message;
	private T data;

	// 성공 응답 생성 메서드 (메세지만)
	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>("success", "요청이 성공적으로 처리되었습니다.", null);
	}

	// 성공 응답 생성 메서드
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>("success", "요청이 성공적으로 처리되었습니다.", data);
	}

	// 실패 응답 생성 메서드
	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>("error", message, null);
	}

	// 실패 응답 생성 메서드 (추가 데이터 포함)
	public static <T> ApiResponse<T> error(String message, T data) {
		return new ApiResponse<>("error", message, data);
	}
}
