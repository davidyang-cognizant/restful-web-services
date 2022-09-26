package com.example.restfulwebservices.filtering;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
@JsonFilter("SomeBeanFilter")
public class SomeBean {
//    @JsonIgnore // this is for not returning this value
    private String s1;
    private String s2;
    private String s3;

    public SomeBean(String s1, String s2, String s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                ", s3='" + s3 + '\'' +
                '}';
    }
}
