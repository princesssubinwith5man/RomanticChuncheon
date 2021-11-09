package com.example.practice;

public class Board {
    String title; //이름
    String content;
    String uid;//이메일
    String time;

    public Board() {
    } //이건 기본적으로 쓰더라구요.

    public Board(String toString, String toString1) {

    }


    //get, set 함수는 커스텀 리스트 뷰를 사용하시는 분들과.. 필요하신 분만 작성하시면 좋습니다.
    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getcontent() {
        return content;
    }

    public void setcontnet(String content) {
        this.content = content;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.content = time;
    }
    public Board(String title, String content, String uid, String time){
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.time = time;
    }

}