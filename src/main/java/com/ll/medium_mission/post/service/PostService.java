package com.ll.medium_mission.post.service;

import com.ll.medium_mission.member.entity.Member;
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

    public Page<Post> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return postRepository.findAll(pageable);
    }

    public Page<Post> getAuthorsPosts(Member author, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return postRepository.findAllByAuthor(author, pageable);
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
}
