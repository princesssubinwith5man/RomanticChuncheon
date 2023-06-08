package com.example.practice;

public class Comment {
    String dat;
    String uid;
    String time;

    public Comment() {
    } //이건 기본적으로 쓰더라구요.




    //get, set 함수는 커스텀 리스트 뷰를 사용하시는 분들과.. 필요하신 분만 작성하시면 좋습니다.
    public String getdat() {
        return dat;
    }

    public void setdat(String dat) {
        this.dat = dat;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Comment(String dat,String uid, String time){
        this.dat = dat;
        this.uid = uid;
        this.time = time;
    }

}