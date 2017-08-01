package com.example.administrator.v2exnetspider_2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Author : YuanPing
 * Address : Huazhong University of Science and Technology
 * Link : https://husteryp.github.io/
 * Description:  最热门话题的适配器
 */
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {
    private List<GsonBean> data;

    public HotAdapter(List<GsonBean> data) {
        this.data = data;
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.hot_item, null);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
        GsonBean gsonBean = data.get(position);
        if (gsonBean.getBitmap() != null) {
            holder.hot_imageView.setImageBitmap(gsonBean.getBitmap());
        }
        else{
            Log.d("@HusterYP", String.valueOf("Hotbitmap is null"));
        }
        holder.hot_title.setText(gsonBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private ImageView hot_imageView;
        private TextView hot_title;

        public HotViewHolder(View itemView) {
            super(itemView);
            hot_imageView = itemView.findViewById(R.id.hot_image);
            hot_title = itemView.findViewById(R.id.hot_title);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(OnItemClickListener!=null){
                        String url = data.get(getLayoutPosition()).getUrl();
                        OnItemClickListener.OnItemClickListener(view, url);
                    }
                }
            });
        }
    }


    //设置条目的点击事件
    private OnItemClickListener OnItemClickListener;

    public void setOnItemClickListener(HotAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClickListener(View view, String url);
    }
}
