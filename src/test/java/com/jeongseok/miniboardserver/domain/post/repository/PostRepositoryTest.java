package com.jeongseok.miniboardserver.domain.post.repository;

import com.jeongseok.miniboardserver.domain.post.domain.Post;
import com.jeongseok.miniboardserver.domain.post.dto.request.CreatePostRequest;
import com.jeongseok.miniboardserver.domain.post.util.PostType;
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
		createPostRequest.setUseYn(PostType.Y);

		CreatePostRequest createPostRequest2 = new CreatePostRequest();
		createPostRequest2.setTitle("title2");
		createPostRequest2.setContent("content2");
		createPostRequest2.setAuthor("test2");
		createPostRequest2.setUseYn(PostType.Y);

		CreatePostRequest createPostRequest3 = new CreatePostRequest();
		createPostRequest3.setTitle("title2");
		createPostRequest3.setContent("content2");
		createPostRequest3.setAuthor("test2");
		createPostRequest3.setUseYn(PostType.N);

		// when
		postRepository.save(createPostRequest.toEntity());
		postRepository.save(createPostRequest2.toEntity());
		postRepository.save(createPostRequest3.toEntity());

		List<Post> result = postRepository.findAllByUseYn(PostType.Y);

		// then
		Assertions.assertThat(result.size()).isEqualTo(2);
	}
}