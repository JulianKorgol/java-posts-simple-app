package com.jkorgol.javapostssimpleapp;

public class Post {
    private int id;
    private String title;
    private String userLogin;
    private String content;

    private Boolean isPublic;

    public Post(int id, String title, String userLogin, String content, Boolean isPublic) {
        this.id = id;
        this.title = title;
        this.userLogin = userLogin;
        this.content = content;
        this.isPublic = isPublic;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }
}
