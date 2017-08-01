package com.example.administrator.v2exnetspider_2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Author : YuanPing
 * Address : Huazhong University of Science and Technology
 * Link : https://husteryp.github.io/
 * Description:
 */
public class PostsrciptAdappter extends RecyclerView.Adapter <PostsrciptAdappter.PostscriptViewHolder>{

    private List<ShowContent.Postscript>data;

    public PostsrciptAdappter(List<ShowContent.Postscript> data) {
        this.data = data;
    }

    @Override
    public PostscriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.postscript_item,null);
        return new PostscriptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostscriptViewHolder holder, int position) {
        ShowContent.Postscript postscript = data.get(position);
        holder.postscirptDetail.setText(postscript.getPostscript_detail());
        holder.postscriptInfo.setText(postscript.getPostscript_info());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class PostscriptViewHolder extends RecyclerView.ViewHolder{
        private TextView postscriptInfo;
        private TextView postscirptDetail;

        public PostscriptViewHolder(View itemView) {
            super(itemView);
            postscriptInfo = itemView.findViewById(R.id.postscript_info);
            postscirptDetail = itemView.findViewById(R.id.postscript_detail);
        }
    }
}
