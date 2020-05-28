package com.example.fitmvp.bean;

public class UserInfoBean {
    /**
     * tel : 11111
     * nickName : nickname
     * height : 183
     * weight : 65.5
     * gender : 1
     * birthday : 1999-02-02
     */

    private String tel;
    private String nickName;
    private int height;
    private double weight;
    private double cal;
    private double pro;
    private double fat;
    private double ch2o;
    private int gender;
    private String birthday;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public double getPro() {
        return pro;
    }

    public void setPro(double pro) {
        this.pro = pro;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCh2o() {
        return ch2o;
    }

    public void setCh2o(double ch2o) {
        this.ch2o = ch2o;
    }
}
