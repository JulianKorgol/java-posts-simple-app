package com.jkorgol.javapostssimpleapp;

import com.jkorgol.javapostssimpleapp.exceptions.PostAlreadyExistException;
import com.jkorgol.javapostssimpleapp.exceptions.UserAlreadyExistException;
import com.jkorgol.javapostssimpleapp.requests.CreatePostData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class PostsEndpoints {

    @GetMapping(path="/health-check", produces = "application/json")
    public Map<String, String> healthCheck() {
        Map<String, String> res = new HashMap<>();
        res.put("status", "ok");

        return res;
    }

    @PostMapping(path="/user/sign/up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody User user) {
        ServiceHandler serviceHandler = ServiceHandler.getInstance();

        Map<String, String> res = new HashMap<>();

        try {
            serviceHandler.addUser(user);
            res.put("status", "ok");

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (UserAlreadyExistException e) {
            res.put("status", "error");
            res.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } catch (Exception e) {
            res.put("status", "error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping(path="/user/sign/in", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody User user) {
        ServiceHandler serviceHandler = ServiceHandler.getInstance();

        Map<String, String> res = new HashMap<>();

        User requestedUser = serviceHandler.findUserByLogin(user.getLogin());
        if (requestedUser != null && requestedUser.getPassword().equals(user.getPassword())) {
            String newSessionToken = String.valueOf(UUID.randomUUID());

            Session newUserSession = new Session(newSessionToken, requestedUser.getLogin());
            serviceHandler.addSession(newUserSession);

            res.put("status", "ok");
            res.put("token", newSessionToken);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        res.put("status", "error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @GetMapping(path="/post/get/my", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getMyPosts(@RequestParam(value = "token") String token) {
        ServiceHandler serviceHandler = ServiceHandler.getInstance();

        Map<String, Object> res = new HashMap<>();

        Session session = serviceHandler.findSessionByToken(token);
        if (session != null) {
            res.put("status", "ok");
            res.put("posts", serviceHandler.findPostsByUserLogin(session.getUserLogin()));

            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        res.put("status", "error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @GetMapping(path="/post/get/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getPostById(@RequestParam(value = "token", required = false) String token, @PathVariable String id) {
        ServiceHandler serviceHandler = ServiceHandler.getInstance();

        Map<String, Object> res = new HashMap<>();

        Post post = serviceHandler.findPostById(id);

        if (post == null) {
            res.put("status", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        if (token != null) {
            Session session = serviceHandler.findSessionByToken(token);

            if (session != null && post.getUserLogin().equals(session.getUserLogin())) {
                res.put("status", "ok");
                res.put("post", post);

                return ResponseEntity.status(HttpStatus.OK).body(res);
            }
        } else if (post.getIsPublic()) {
            res.put("status", "ok");
            res.put("post", post);

            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        res.put("status", "error");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }

    @PostMapping(path="/post/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> createPost(@RequestParam(value = "token") String token, @RequestBody CreatePostData post) {
        ServiceHandler serviceHandler = ServiceHandler.getInstance();

        Map<String, String> res = new HashMap<>();

        Session session = serviceHandler.findSessionByToken(token);
        if (session != null) {
            int newPostId = serviceHandler.countPosts() + 1;
            Post newPost = new Post(newPostId, post.getTitle(), session.getUserLogin(), post.getContent(), post.getIsPublic());

            try {
                serviceHandler.addPost(newPost);
            } catch (Exception e) {
                res.put("status", "error");

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
            }

            res.put("status", "ok");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        res.put("status", "error");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
}
