package com.sz.jjj.xml.model;

/**
 * Created by jjj on 2017/8/23.
 *
 * @description:
 */

public class Books {
    private int id;
    private String name;
    private float price;
    private Test test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id:" + id + ", name:" + name + ", price:" + price+"aa--"+test.getAa()+"bb--"+test.getBb();
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public static class Test{
        private String aa;
        private String bb;

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }
    }
}
