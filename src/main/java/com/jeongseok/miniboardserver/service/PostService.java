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
		List<Post> posts = postRepository.findAll();

		return PostMapper.toDto(posts);
	}

	public PostResponseDto findByPostId(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		return PostMapper.toDto(post);
	}

	@Transactional
	public Long save(PostRequestDto.CreatePost createPost) {
		Post savedPost = postRepository.save(PostMapper.toEntity(createPost));

		return savedPost.getPostId();
	}

	@Transactional
	public PostResponseDto update(Long postId, PostRequestDto.UpdatePost updatePost) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		Post updatedPost = PostMapper.toEntity(updatePost, post);

		return PostMapper.toDto(updatedPost);
	}

	@Transactional
	public void delete(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));

		postRepository.delete(post);
	}
}
