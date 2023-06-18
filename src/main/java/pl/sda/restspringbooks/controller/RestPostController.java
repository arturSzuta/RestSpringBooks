package pl.sda.restspringbooks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.restspringbooks.model.Post;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@RestController
@RequestMapping("/api/v1/posts")
public class RestPostController {
    private List<Post> posts = new ArrayList<>(
            List.of(
                    Post
                            .builder()
                            .id(1)
                            .title("Test Post 1")
                            .content("Content of a first post")
                            .build(),
                    Post
                            .builder()
                            .id(2)
                            .title("Test Post 2")
                            .content("Content of a second post")
                            .build()
            )
    );
    @GetMapping("")
    public List<Post> getPosts() {
        return posts;
    }
    @PostMapping("")
    public ResponseEntity<Void> createPost(@RequestBody Post post) {
        final OptionalLong max = posts.stream().mapToLong(p -> p.getId()).max();
        post.setId(max.getAsLong() + 1);
        posts.add(post);
        return ResponseEntity.created(URI.create("api/v1/posts" + post.getId())).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id) {
        final Optional<Post> optionalPost = posts
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return ResponseEntity.of(optionalPost);
    }
}
