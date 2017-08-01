package com.example.administrator.v2exnetspider_2;

import android.graphics.Bitmap;

/**
 * Author : YuanPing
 * Address : Huazhong University of Science and Technology
 * Link : https://husteryp.github.io/
 * Description:
 */
public class GsonBean {

    /**
     * title :
     * url : http://www.v2ex.com/t/379407
     * member : {"avatar_normal":"//v2ex.assets.uxengine.net/avatar/9381/618b/226828_normal.png?m=1498355327"}
     */

    private String title;   //主题标题
    private String url;     //主题网址
    private MemberEntity member;     //主题作者头像链接
    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public MemberEntity getMember() {
        return member;
    }

    public static class MemberEntity {
        /**
         * avatar_normal : //v2ex.assets.uxengine.net/avatar/9381/618b/226828_normal.png?m=1498355327
         */

        private String avatar_normal;

        public String getAvatar_normal() {
            return avatar_normal;
        }
    }
}
