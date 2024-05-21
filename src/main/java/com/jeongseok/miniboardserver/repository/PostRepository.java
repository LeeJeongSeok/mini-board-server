package com.jeongseok.miniboardserver.repository;

import com.jeongseok.miniboardserver.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	// useYn이 'Y'인 게시글만 조회하는 메서드
	@Query("SELECT p FROM Post p WHERE p.useYn = 'Y'")
	List<Post> findAllByUseYn();
}
