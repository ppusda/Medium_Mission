package com.ll.medium_mission.post.service;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.post.dto.PostResponse;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void write(String title, String content, Member author) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        postRepository.save(post);
    }

    public Page<PostResponse> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 5);

        return convertToResponse(postRepository.findAll(pageable));
    }

    public Page<PostResponse> getAuthorsPosts(Member author, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return convertToResponse(postRepository.findAllByAuthor(author, pageable));
    }

    public PostResponse getPostResponse(Long postId) {
        Post post = getPost(postId);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getEmail().split("@")[0])
                .build();
    }

    public Post getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 글입니다.");
        }

        return post.get();
    }

    public void modify(Long postId, String title, String content, Member author) {
        Post post = getPost(postId);

        if (!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        post.modifyPost(title, content);
    }

    public void delete(Long postId, Member author) {
        Post post = getPost(postId);

        if (!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.deleteById(postId);
    }

    public Page<PostResponse> convertToResponse(Page<Post> postPage) {
        return postPage.map(post -> PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getEmail().split("@")[0])
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build());
    }
}
