package com.jeongseok.miniboardserver.domain.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE post SET use_yn = 'N' WHERE post_id = ?")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postId;

	private String title;

	private String content;

	private String author;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	private String useYn;

	// TODO: 생성, 수정, 삭제와 관련된 비지니스 로직을 여기에 추가해야하나?

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
