package com.example.practice;

public class BoardListViewItem {
    private String Like;
    private String Title;
    private String Content;
    private String Name;
    private String Time;
    private String Key;
    //final private double lat, lng;

    public void setTitle(String title) {
        Title = title;
    }

    public void setLike(String like) {
        Like = like;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setName(String name) {Name= name;}
    public void setKey(String key){Key = key;}
    public void setTime(String time){Time = time;}

    public String getTitle() {
        return this.Title;
    }


    public String getLike() {
        return this.Like;
    }

    public String getContent() {
        return this.Content;
    }

    public String getName() {
        return this.Name;
    }
    public String getKey() {return this.Key;}
    public String getTime() {return this.Time;}
}
