package com.example.practice;

public class Like {
    String uid;
    public Like() {
    } //이건 기본적으로 쓰더라구요.




    //get, set 함수는 커스텀 리스트 뷰를 사용하시는 분들과.. 필요하신 분만 작성하시면 좋습니다.
    public String getlike() {
        return uid;
    }

    public void setlike(String like) {
        this.uid = uid;
    }

    public Like(String uid){
        this.uid = uid;
    }
}
