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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Content> contents;   //技术和创意两个节点的内容存储器
    private List<GsonBean> hot_Contents; //最热话题内容存储器
    private final int techFlag = 0;  //技术节点
    private final int ideaFlag = 1;  //创意节点
    private final int hotFlag = 2;   //最热节点
    private final int newFlag = 3;   //最新节点
    private final int funFlag = 4;   //好玩节点
    private final int appleFlag = 5; //Apple节点
    private final int jobFlag = 6;   //酷工作节点
    private RecyclerView recyclerView;
    private TextView main_lable;
    private static ProgressDialog progressBar = null;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==-1){
                //为什么滚动条在这里申明下面就为null？报错？
                progressBar = new ProgressDialog(MainActivity.this);
                progressBar.setTitle("Loading...");
                progressBar.show();
            }
            else {
                if(progressBar!=null)
                    progressBar.dismiss();

                if (msg.what == techFlag) {
                    main_lable.setText("技术");
                    MyAdapter adapter = new MyAdapter(contents);
                    recyclerView.setAdapter(adapter);
                    //设置条目的点击事件
                    adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });

                }
                else if (msg.what == ideaFlag) {
                    main_lable.setText("创意");
                    MyAdapter adapter = new MyAdapter(contents);
                    recyclerView.setAdapter(adapter);
                    //设置条目的点击事件
                    adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
                else if (msg.what == hotFlag) {
                    main_lable.setText("最热");
                    HotAdapter hotAdapter = new HotAdapter(hot_Contents);
                    recyclerView.setAdapter(hotAdapter);
                    hotAdapter.setOnItemClickListener(new HotAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClickListener(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
                else if (msg.what == newFlag) {
                    main_lable.setText("最新");
                    MyAdapter newAdapter = new MyAdapter(contents);
                    recyclerView.setAdapter(newAdapter);
                    //设置条目的点击事件
                    newAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
                else if (msg.what == funFlag) {
                    main_lable.setText("好玩");
                    MyAdapter newAdapter = new MyAdapter(contents);
                    recyclerView.setAdapter(newAdapter);
                    //设置条目的点击事件
                    newAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
                else if (msg.what == appleFlag) {
                    main_lable.setText("Apple");
                    MyAdapter newAdapter = new MyAdapter(contents);
                    recyclerView.setAdapter(newAdapter);
                    //设置条目的点击事件
                    newAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
                else if (msg.what == jobFlag) {
                    main_lable.setText("酷工作");
                    MyAdapter newAdapter = new MyAdapter(contents);
                    recyclerView.setAdapter(newAdapter);
                    //设置条目的点击事件
                    newAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, String url) {
                            Intent intent = new Intent(MainActivity.this, ShowContent.class);
                            intent.putExtra("Url", url);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hot_node:
                getHotContent();
                break;
            case R.id.new_node:
                String newUrl = "https://www.v2ex.com/?tab=all";
                getContent(newUrl,newFlag);
                break;
            case R.id.tech_node:
                String techUrl = "https://www.v2ex.com/?tab=tech#article-start";
                getContent(techUrl, techFlag);
                break;
            case R.id.idea_node:
                String ideaUrl = "https://www.v2ex.com/?tab=creative";
                getContent(ideaUrl, ideaFlag);
                break;
            case R.id.fun_node:
                String funUrl = "https://www.v2ex.com/?tab=play";
                getContent(funUrl,funFlag);
                break;
            case R.id.apple_node:
                String appleUrl = "https://www.v2ex.com/?tab=apple";
                getContent(appleUrl,appleFlag);
                break;
            case R.id.job_node:
                String jobUrl = "https://www.v2ex.com/?tab=jobs";
                getContent(jobUrl,jobFlag);
                break;
        }
        return true;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        main_lable = (TextView) findViewById(R.id.main_lable);

        contents = new ArrayList<>();
        hot_Contents = new ArrayList<>();
        //默认打开的是最新节点
        String newUrl = "https://www.v2ex.com/?tab=all";
        getContent(newUrl,newFlag);
    }

    //获取技术和创意两个节点的信息；flag = 0表示获取技术节点信息；flag = 1表示获取创意节点信息
    public void getContent(final String Url, final int flag) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Message msg1 = new Message();
                msg1.what = -1;
                handler.sendMessage(msg1);
                //在存储数据之前先清空一下,不然的话选择其他的项时会接在旧的内容之后
                contents.clear();
                try {
                    Document doc = Jsoup.connect(Url).get();
                    Elements elements = doc.getElementsByClass("cell item");
                    String title;
                    String content;
                    String count;
                    String imageUrl;
                    String itemUrl;
                    Bitmap bitmap = null;
                    for (Element e : elements) {
                        title = e.select(".item_title").text();
                        content = e.getElementsByClass("small fade").text();
                        count = e.select(".count_livid").text();
                        if (count.equals("")) {
                            count = "0";
                        }
                        imageUrl = "http:" + e.select("img").attr("src");
                        itemUrl = "https://www.v2ex.com" + e.select(".item_title a").attr("href");

                        //获取图片
                        if (!imageUrl.equals("")) {
                            URL url = new URL(imageUrl);
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

                        contents.add(new Content(title, content, count, bitmap, itemUrl));
                        Message msg = new Message();
                        msg.what = flag;
                        handler.sendMessage(msg);
//                        Log.d("@HusterYP", String.valueOf(title + "--" + content + "--" + count + "--" + imageUrl + "--" + itemUrl));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    //最热事件展示
    public void getHotContent() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Message msg1 = new Message();
                    msg1.what =-1;
                    handler.sendMessage(msg1);
                    hot_Contents.clear();
                    Log.d("@HusterYP", String.valueOf("Hot_Node"));
                    String address = "https://www.v2ex.com/api/topics/hot.json";
                    URL url = new URL(address);
                    URLConnection connection = url.openConnection();
                    InputStream in = connection.getInputStream();
                    String str = "";
                    int n;
                    InputStreamReader reader = new InputStreamReader(in);
                    while ((n = reader.read()) != -1) {
                        str += (char) n;
                    }
                    Gson gson = new Gson();
                    List<JsonElement> list = new ArrayList<>();
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(str);
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    Iterator it = jsonArray.iterator();
                    while (it.hasNext()) {
                        jsonElement = (JsonElement) it.next();
                        str = jsonElement.toString();
                        GsonBean temp = gson.fromJson(str, GsonBean.class);
                        //为什么在这里得到图片就可以，在while循环外面另外用一个for循环就不行呢？
                        String add = "http:"+temp.getMember().getAvatar_normal();
                        URL imageUrl = new URL(add);
                        InputStream imageIn = imageUrl.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(imageIn);
                        temp.setBitmap(bitmap);
                        hot_Contents.add(temp);
                        Message msg = new Message();
                        msg.what = hotFlag;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}
