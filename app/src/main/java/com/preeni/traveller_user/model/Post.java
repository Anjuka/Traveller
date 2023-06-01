package com.preeni.traveller_user.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Post {

    private ArrayList<PostData> postData;

    public Post() {
    }

    public Post(ArrayList<PostData> postData) {
        this.postData = postData;
    }

    public ArrayList<PostData> getPostData() {
        return postData;
    }

    public void setPostData(ArrayList<PostData> postData) {
        this.postData = postData;
    }
}
