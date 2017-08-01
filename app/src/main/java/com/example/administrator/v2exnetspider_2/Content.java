package com.example.administrator.v2exnetspider_2;

import android.graphics.Bitmap;

/**
 * Author : YuanPing
 * Address : Huazhong University of Science and Technology
 * Link : https://husteryp.github.io/
 * Description:
 */
public class Content {
    private String title;  //标题部分
    private String content;//介绍
    private String count; //计数
    private Bitmap bitmap; //头像
    private String url;   //每一个条目的链接

    private String responsorName; //回复者的名字

    //该构造函数用于首页展示各个条目用
    public Content(String title, String content, String count, Bitmap bitmap, String url) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.bitmap = bitmap;
        this.url = url;
    }

    //改构造函数用于展示每条条目的详细信息（回复信息）时用
    public Content(String content, Bitmap bitmap, String responsorName) {
        this.content = content;
        this.bitmap = bitmap;
        this.responsorName = responsorName;
    }

    public Content() {}

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCount() {
        return count;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }

    public String getResponsorName() {
        return responsorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setResponsorName(String responsorName) {
        this.responsorName = responsorName;
    }
}
