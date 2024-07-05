package com.jeongseok.miniboardserver.domain.post.repository;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.util.PostType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByUseYn(PostType useYn);

}
