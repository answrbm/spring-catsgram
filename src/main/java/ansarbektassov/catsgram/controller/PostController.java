package ansarbektassov.catsgram.controller;

import ansarbektassov.catsgram.exceptions.PostNotFoundException;
import ansarbektassov.catsgram.model.Post;
import ansarbektassov.catsgram.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final Logger log = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(name = "size", defaultValue = "10", required = false) int size,
                              @RequestParam(name = "sort", defaultValue = "asc", required = false) String sort,
                              @RequestParam(name = "from", defaultValue = "0", required = false) int from) {
        return postService.findAll(size,sort,from);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.save(post);
    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable("postId") Long postId) {
        try {
            return postService.findById(postId);
        } catch (PostNotFoundException e) {
            log.error("Post findById error",e);
            throw new PostNotFoundException("Post with such id not found");
        }
    }
}
