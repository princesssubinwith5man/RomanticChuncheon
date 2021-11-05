package com.example.practice;

public class Comment {
    String dat; //이름
    String time; //이메일

    public Comment() {
    } //이건 기본적으로 쓰더라구요.




    //get, set 함수는 커스텀 리스트 뷰를 사용하시는 분들과.. 필요하신 분만 작성하시면 좋습니다.
    public String getdat() {
        return dat;
    }

    public void setdat(String dat) {
        this.dat = dat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Comment(String dat, String time){
        this.dat = dat;
        this.time = time;
    }

}