package com.jkorgol.javapostssimpleapp;

import com.jkorgol.javapostssimpleapp.exceptions.PostAlreadyExistException;
import com.jkorgol.javapostssimpleapp.exceptions.UserAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceHandler {
    private static ServiceHandler instance;

    private List<User> users = new ArrayList<>(); // Act as DB that stores users with login and password

    private List<Session> sessions = new ArrayList<>(); // Act as DB that stores sessions with token and userLogin.

    private List<Post> posts = new ArrayList<>(); // Act as DB that stores posts with id, title, userLogin and content

    private ServiceHandler() {}

    public static synchronized ServiceHandler getInstance() {
        if (instance == null) {
            instance = new ServiceHandler();
        }

        return instance;
    }

    public User findUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }

        return null;
    }

    public Boolean checkIfUserExists(String login) {
        return findUserByLogin(login) != null;
    }

    public Boolean checkIfUserExists(User user) {
        return findUserByLogin(user.getLogin()) != null;
    }

    public void addUser(User user) throws UserAlreadyExistException {
        if (!checkIfUserExists(user)) {
            users.add(user);
            return;
        }

        throw new UserAlreadyExistException("User with login: '" + user.getLogin() + "' already exists");
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public Session findSessionByToken(String token) {
        for (Session session : sessions) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }

        return null;
    }

    public List<Post> findPostsByUserLogin(String userLogin) {
        List<Post> userPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getUserLogin().equals(userLogin)) {
                userPosts.add(post);
            }
        }

        return userPosts;
    }

    public Post findPostById(String id) {
        for (Post post : posts) {
            if (String.valueOf(post.getId()).equals(id)) {
                return post;
            }
        }

        return null;
    }

    public int countPosts() {
        return posts.size();
    }

    public void addPost(Post post) throws PostAlreadyExistException {
        if (findPostById(String.valueOf(post.getId())) != null) {
            throw new PostAlreadyExistException("Post with id: '" + post.getId() + "' already exists");
        }

        posts.add(post);
    }
}
