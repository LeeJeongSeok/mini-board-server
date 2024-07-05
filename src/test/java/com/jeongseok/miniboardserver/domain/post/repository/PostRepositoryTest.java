package com.jeongseok.miniboardserver.domain.post.repository;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.util.PostType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@TestPropertySource("classpath:application.yml")
@Sql("/sql/post-repository-test-data.sql")
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void Post의_useYn값_중_Y_인_post만_조회한다() {
		// given
		// when
		List<Post> result = postRepository.findAllByUseYn(PostType.Y);

		// then
		Assertions.assertThat(result.size()).isEqualTo(3);
	}
}