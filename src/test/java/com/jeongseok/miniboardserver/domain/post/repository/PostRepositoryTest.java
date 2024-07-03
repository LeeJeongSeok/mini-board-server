package com.jeongseok.miniboardserver.domain.post.repository;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource("classpath:application.yml")
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void PostRepository_가_정상적으로_연결되었다() {
		// given
		CreatePostRequest createPostRequest = new CreatePostRequest();
		createPostRequest.setTitle("title");
		createPostRequest.setContent("content");
		createPostRequest.setAuthor("test");


		// when
		Post result = postRepository.save(createPostRequest.toEntity());

		// then
		Assertions.assertThat(result.getPostId()).isNotNull();
	}

	@Test
	void Post의_useYn값_중_Y_인_post만_조회한다() {
		// given
		CreatePostRequest createPostRequest = new CreatePostRequest();
		createPostRequest.setTitle("title");
		createPostRequest.setContent("content");
		createPostRequest.setAuthor("test");

		// when
		postRepository.save(createPostRequest.toEntity());
		List<Post> result = postRepository.findAllByUseYn();

		// then
		Assertions.assertThat(result.get(0).getUseYn()).isEqualTo("Y");
	}
}