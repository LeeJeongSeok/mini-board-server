package com.jeongseok.miniboardserver.service;

import com.jeongseok.miniboardserver.domain.Post;
import com.jeongseok.miniboardserver.dto.mapper.PostMapper;
import com.jeongseok.miniboardserver.dto.post.PostRequestDto;
import com.jeongseok.miniboardserver.dto.post.PostResponseDto;
import com.jeongseok.miniboardserver.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;


	public List<PostResponseDto> findAll() {
		List<Post> posts = postRepository.findAllByUseYn();

		return PostMapper.toDto(posts);
	}

	public PostResponseDto findByPostId(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		return PostMapper.toDto(post);
	}

	@Transactional
	public void save(PostRequestDto.CreatePost createPost) {
		Post post = PostMapper.toEntity(createPost);
		postRepository.save(post);
	}

	@Transactional
	public void update(Long postId, PostRequestDto.UpdatePost updatePost) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		PostMapper.toEntity(updatePost, post);
	}

	@Transactional
	public void delete(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		postRepository.delete(post);
	}
}
