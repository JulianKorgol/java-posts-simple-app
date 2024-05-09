package com.jkorgol.javapostssimpleapp.requests;

public class CreatePostData {

    private String title;

    private String content;

    private Boolean isPublic;

    public CreatePostData(String title, String content, Boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }
}
