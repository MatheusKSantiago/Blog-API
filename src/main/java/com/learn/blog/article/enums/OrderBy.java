package com.learn.blog.article.enums;

public enum OrderBy {
    NUMBER_OF_LIKES("orderByNumberOfLikes"),MOST_COMMENTED("orderByComments");
    OrderBy(String value){
        this.value = value;
    }
    final String value;

    public String getValue(){
        return this.value;
    }
}
