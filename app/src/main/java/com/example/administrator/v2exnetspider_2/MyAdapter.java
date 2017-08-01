package com.example.administrator.v2exnetspider_2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Author : YuanPing
 * Address : Huazhong University of Science and Technology
 * Link : https://husteryp.github.io/
 * Description:
 */
public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{


    private List<Content> data;

    public MyAdapter(List<Content> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.recycler_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Content content = data.get(position);
        holder.bitmap.setImageBitmap(content.getBitmap());
        holder.title.setText(content.getTitle());
        holder.content.setText(content.getContent());
        holder.count.setText(content.getCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView count;
        private ImageView bitmap;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content_title);
            content = itemView.findViewById(R.id.content_content);
            count = itemView.findViewById(R.id.content_count);
            bitmap = itemView.findViewById(R.id.content_image);

            //设置条目的点击事件
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int pos = getLayoutPosition();
                        String url = data.get(pos).getUrl();
                        onItemClickListener.OnItemClick(view,url);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    //设置条目的点击事件
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void OnItemClick(View view, String url);
    }
}
