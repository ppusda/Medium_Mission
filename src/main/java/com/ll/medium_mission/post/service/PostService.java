package com.ll.medium_mission.post.service;

import com.ll.medium_mission.global.exception.NoHasAuthorityException;
import com.ll.medium_mission.global.exception.NotExistPostException;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.post.dto.PostResponse;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public void write(String title, String content, Boolean isPaid, Member author) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .isPaid(isPaid)
                .author(author)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public Page<PostResponse> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 5);

        return convertToPageResponse(postRepository.findAllByOrderByCreateDateDesc(pageable));
    }

    @Transactional
    public Page<PostResponse> getHotPosts() {
        Pageable pageable = PageRequest.of(0, 5);

        return convertToPageResponse(postRepository.getHotPosts(pageable));
    }

    @Transactional
    public Page<PostResponse> getAuthorsPosts(Member author, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return convertToPageResponse(postRepository.findAllByAuthorOrderByCreateDateDesc(author, pageable));
    }

    @Transactional
    public Page<PostResponse> getSearchPosts(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 5);
        return convertToPageResponse(postRepository.searchPosts(keyword, pageable));
    }

    @Transactional
    public PostResponse getPostResponse(Long postId) {
        Post post = getPost(postId);
        post.increaseViewCount();

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getNickname())
                .recommendCount((long) post.getPostRecommends().size())
                .viewCount(post.getViewCount())
                .isPaid(post.getIsPaid())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    @Transactional
    public Post getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new NotExistPostException();
        }

        return post.get();
    }

    @Transactional
    public void cancelMembershipPost(List<Post> posts) {
        for (Post post: posts) {
            post.cancelMembership();
        }
    }

    @Transactional
    public void modify(Long postId, String title, String content, Boolean isPaid, Member author) {
        Post post = getPost(postId);

        if (!post.getAuthor().equals(author)) {
            throw new NoHasAuthorityException();
        }

        post.modifyPost(title, content, isPaid);
    }

    @Transactional
    public void delete(Long postId, Member author) {
        Post post = getPost(postId);

        if (!post.getAuthor().equals(author)) {
            throw new NoHasAuthorityException();
        }

        postRepository.deleteById(postId);
    }

    public Page<PostResponse> convertToPageResponse(Page<Post> postPage) {
        return postPage.map(post -> PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getNickname())
                .recommendCount((long) post.getPostRecommends().size())
                .isPaid(post.getIsPaid())
                .createDate(post.getCreateDate())
                .modifiedDate(post.getModifiedDate())
                .build());
    }

}
