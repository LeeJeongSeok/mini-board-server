package com.jeongseok.miniboardserver.domain.post.domain;

import com.jeongseok.miniboardserver.domain.post.util.PostType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	private PostType useYn;

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

	@Builder
	public Post(long postId, String title, String content, String author, LocalDateTime createdAt, LocalDateTime updatedAt, PostType useYn) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.author = author;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.useYn = useYn;
	}

	public void updatePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void deletePost(long postId) {
		this.useYn = PostType.N;
	}
}
