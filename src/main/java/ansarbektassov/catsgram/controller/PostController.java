package ansarbektassov.catsgram.controller;

import ansarbektassov.catsgram.exceptions.PostNotFoundException;
import ansarbektassov.catsgram.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public List<Post> findAll() {
        log.debug("Текущее количество постов: " + posts.size());
        return posts;
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable("postId") Long postId) {
        return posts.stream().filter(post -> Objects.equals(post.getPostId(), postId)).findFirst().orElseThrow(() ->
                new PostNotFoundException("Post with such id not found"));
    }
}
