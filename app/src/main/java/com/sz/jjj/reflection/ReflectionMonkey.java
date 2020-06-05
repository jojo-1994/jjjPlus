package com.sz.jjj.reflection;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */
public class ReflectionMonkey {
    String name;
    int age;
    String gender;
    String language;

    public ReflectionMonkey(String name, String gender, int age) {
        this.name=name;
        this.gender=gender;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
