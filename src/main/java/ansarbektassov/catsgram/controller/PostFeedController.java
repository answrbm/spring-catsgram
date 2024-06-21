package ansarbektassov.catsgram.controller;

import ansarbektassov.catsgram.model.FriendsBody;
import ansarbektassov.catsgram.model.Post;
import ansarbektassov.catsgram.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/feed/friends")
    public List<Post> getFriendsPosts(@RequestBody String requestBody) {
        try {
            String params = new ObjectMapper().readValue(requestBody,String.class);
            FriendsBody friendsBody = new ObjectMapper().readValue(params, FriendsBody.class);
            return postService.findAll(friendsBody.getSize(), friendsBody.getSort(), 0).stream()
                    .filter(post -> friendsBody.getFriends().contains(post.getAuthor()))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
