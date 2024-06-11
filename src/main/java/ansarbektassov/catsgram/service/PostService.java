package ansarbektassov.catsgram.service;

import ansarbektassov.catsgram.controller.PostController;
import ansarbektassov.catsgram.exceptions.PostNotFoundException;
import ansarbektassov.catsgram.exceptions.PostRequestException;
import ansarbektassov.catsgram.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private List<Post> posts = new ArrayList<>();

    public List<Post> findAll(int size, String sort, int from) {
        log.debug("Текущее количество постов: " + posts.size());
        if(from >= posts.size() || from < 0) {
            throw new PostRequestException("From value shouldn't be negative or bigger than posts size");
        }
        if(size >= posts.size() || size < 0) {
            throw new PostRequestException("Size value shouldn't be negative or bigger than posts size");
        }
        if(sort.equals("desc")) {
            return posts.stream().sorted(new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o1.getCreationDate().compareTo(o2.getCreationDate());
                }
            }).collect(Collectors.toList()).subList(from,from+size);
        }
        if(sort.equals("asc")) {
            return posts.stream().sorted(new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o2.getCreationDate().compareTo(o1.getCreationDate());
                }
            }).collect(Collectors.toList()).subList(from,from+size);
        }
        return posts.subList(from,from+size);
    }

    public Post findById(Long postId) {
        log.debug("Поиск поста по id...");
        return posts.stream().filter(post -> Objects.equals(post.getPostId(), postId)).findFirst().orElseThrow(() ->
                new PostNotFoundException("Post with such id not found"));
    }

    public Post save(Post post) {
        log.debug("Добавление поста...");
        posts.add(post);
        log.debug("Пост был добавлен");
        return post;
    }


}
