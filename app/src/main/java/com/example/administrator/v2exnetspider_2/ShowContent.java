package com.example.administrator.v2exnetspider_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowContent extends AppCompatActivity {
    private String url;
    private QuestionInfo questionInfo;
    private List<Postscript> show_postscript;  //附言
    private List<Content> content;  //回复的内容
    private TextView show_title;
    private TextView show_content;
    private ImageView show_author;
    private static ProgressDialog progressBar = null;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //为什么滚动条在这里申明下面就为null？报错？
                progressBar = new ProgressDialog(ShowContent.this);
                progressBar.setTitle("Loading...");
                progressBar.show();
            }

            //设置主题的详细信息
            if (msg.what == 1) {
                if(progressBar!=null)
                    progressBar.dismiss();

                show_title.setText(questionInfo.show_title);
                show_content.setText(questionInfo.show_content);
                show_author.setImageBitmap(questionInfo.show_author);
                //设置附言
                if (show_postscript != null) {
                    PostsrciptAdappter postAdapter = new PostsrciptAdappter(show_postscript);
                    RecyclerView postRecyclerView = (RecyclerView) findViewById(R.id.show_postscript);
                    postRecyclerView.setAdapter(postAdapter);
//                postRecyclerView.setLayoutManager(new LinearLayoutManager(ShowContent.this,LinearLayoutManager.VERTICAL,false));
                    postRecyclerView.setLayoutManager(new LinearLayoutManager(ShowContent.this));
                }
                else {

                }

                //设置回复消息
                ShowAdapter showAdapter = new ShowAdapter(content, ShowContent.this);
                RecyclerView showRecyclerView = (RecyclerView) findViewById(R.id.show_recyclerView);
                showRecyclerView.setAdapter(showAdapter);
                showRecyclerView.setLayoutManager(new LinearLayoutManager(ShowContent.this, LinearLayoutManager.VERTICAL, false));
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        Intent intent = getIntent();
        url = intent.getStringExtra("Url");
        show_postscript = new ArrayList<>();
        content = new ArrayList<>();

        show_title = (TextView) findViewById(R.id.show_title);
        show_content = (TextView) findViewById(R.id.show_content);
        show_author = (ImageView) findViewById(R.id.show_author);

        getShowContent();
    }

    //作者和问题描述的信息类
    class QuestionInfo {
        private String show_title;
        private String show_content;
        private Bitmap show_author;

        public QuestionInfo(String show_title, String show_content, Bitmap show_author) {
            this.show_title = show_title;
            this.show_content = show_content;
            this.show_author = show_author;
        }
    }

    //附言类，因为附言是用一个RecyclerView来展现的
    class Postscript {
        private String postscript_info;
        private String postscript_detail;

        public Postscript(String postscript_info, String postscript_detail) {
            this.postscript_info = postscript_info;
            this.postscript_detail = postscript_detail;
        }

        public String getPostscript_info() {
            return postscript_info;
        }

        public String getPostscript_detail() {
            return postscript_detail;
        }
    }

    //根据传回的链接获取信息
    public void getShowContent() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Message msg1 = new Message();
                msg1.what = 0;
                handler.sendMessage(msg1);
                try {
                    Document doc = Jsoup.connect(url).get();

                    /*
                    获取主题详细信息，除了主题附言
                    * */
                    //主题标题
                    String title = doc.title();
                    //获取主题的详细信息
                    Elements elements_Content = doc.select("#Main .cell .markdown_body");
                    //详细主题描述信息
                    String content_detail = "";
                        content_detail += elements_Content.text();
                    //获取主题作者的头像链接,注意下面这种得到的还包括了回复者的头像链接，只要第一个是作者的
                    Elements elements_Image = doc.getElementsByClass("avatar");
                    Bitmap bitmap = null;
                    String bitmapUrl;
                    Element e = elements_Image.first();
                    bitmapUrl = "http:" + e.attr("src");
                    if (!bitmapUrl.equals("http:")) {
                        URL url = new URL(bitmapUrl);
                        InputStream in = null;
                        if (url != null) {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(3000);
                            connection.setRequestMethod("GET");
                            connection.setDoInput(true);
                            int response = connection.getResponseCode();
                            if (response == HttpURLConnection.HTTP_OK)
                                in = connection.getInputStream();
                            bitmap = BitmapFactory.decodeStream(in);
                        }
                    }
                    questionInfo = new QuestionInfo(title, content_detail, bitmap); //主题信息获取完毕，除了附言

                    /*
                    * 获取主题附言
                    * */
                    //获取主题附言信息,要注意有些可能没有附言
                    //附言回复时间等信息
                    Elements elements_postscirptInfo = doc.select("#Main .subtle .fade");
                    //如果有附言的话
                    if (elements_postscirptInfo.size() != 0) {
                        //附言的详细信息
                        Elements elements_postscriptDetail = doc.select("#Main .subtle .topic_content");
                        for (int i = 0; i < elements_postscirptInfo.size(); i++) {
                            show_postscript.add(new Postscript(elements_postscirptInfo.get(i).text(), elements_postscriptDetail.get(i).text()));
                        }
                    }

                    /*
                    * 获取回复的详细信息
                    * */
                    //获取回复内容
                    Elements elements_responseContent = doc.select("#Main .reply_content");
                    //获取回复者姓名
                    Elements elements_responsorName = doc.select("#Main .dark");
                    for (int i = 1; i < elements_Image.size(); i++) {
                        bitmapUrl = "http:" + elements_Image.get(i).attr("src");
                        if (!bitmapUrl.equals("")) {
                            URL url = new URL(bitmapUrl);
                            InputStream in = null;
                            bitmap = null;
                            if (url != null) {
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setConnectTimeout(3000);
                                connection.setRequestMethod("GET");
                                connection.setDoInput(true);
                                int response = connection.getResponseCode();
                                if (response == HttpURLConnection.HTTP_OK)
                                    in = connection.getInputStream();
                                bitmap = BitmapFactory.decodeStream(in);
                            }
                        }
                        content.add(new Content(elements_responseContent.get(i - 1).text(), bitmap, elements_responsorName.get(i - 1).text()));
                    }
                    //发送消息更新UI
                    //若放在for循环中，可以边加载数据边跟新UI，可以防止界面长时间的空白，但是这样的话
                    //在加载过程中界面就会很不稳定，用户体验较差

                    Message msg2 = new Message();
                    msg2.what = 1;
                    handler.sendMessage(msg2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}
