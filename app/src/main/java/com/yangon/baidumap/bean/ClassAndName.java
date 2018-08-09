package com.yangon.baidumap.bean;

/**
 * Created by Yangon on 2018/8/7
 */

public class ClassAndName {
    /**用于保存Activity的字节码*/
    public Class<?> clazz;
    /**用于保存Activity对应的名字*/
    private String name;

    public ClassAndName(Class<?> clas, String name) {
        this.clazz = clas;
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
