package hackerschool.website.content.service;


import hackerschool.website.content.domain.BlogPost;
import hackerschool.website.content.dto.BlogPostRequest;
import hackerschool.website.content.dto.BlogPostResponse;
import hackerschool.website.content.repository.BlogPostRepository;
import hackerschool.website.content.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final ProjectRepository projectRepository;


    @Transactional
    public BlogPostResponse createBlogPost(BlogPostRequest request, long adminId) {
        //TODO implement validateAdminID
        BlogPost blogPost = blogPostRepository.save(new BlogPost(request.title(), request.content(), adminId));
        return BlogPostResponse.from(blogPost);
    }

    @Transactional
    public BlogPostResponse updateBlogPost(long id, BlogPostRequest request) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blog post not found"));
        blogPost.setTitle(request.title());
        blogPost.setContent(request.content());
        return BlogPostResponse.from(blogPost);
    }

    @Transactional
    public void deleteBlogPost(long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blog post not found"));
        blogPostRepository.delete(blogPost);
    }

    @Transactional
    public List<BlogPostResponse> listAllBlogPosts() {
        return blogPostRepository.findAll().stream()
                .map(BlogPostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<BlogPostResponse> getPagedBlogPosts(Integer page, Integer size, String orderBy, String orderDirection ){
        Sort.Direction direction = "DESC".equalsIgnoreCase(orderDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        validateOrderBy(orderBy);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(orderDirection), orderBy);
        Page<BlogPost> foundBlogPosts = blogPostRepository.findAll(pageRequest);
        return foundBlogPosts.map(BlogPostResponse::from);
    }

    public void validateOrderBy(String orderBy) {
        if (!"createdAt".equalsIgnoreCase(orderBy)) {
            throw new IllegalArgumentException("Invalid orderBy parameter. Only 'createdAt' is allowed.");
        }
    }




}
