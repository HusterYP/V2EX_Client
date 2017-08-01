package com.example.administrator.v2exnetspider_2;

import android.content.Context;
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
public class ShowAdapter extends RecyclerView.Adapter <ShowAdapter.ShowViewHolder>{
    private List<Content> data;
    private Context mContext;

    public ShowAdapter(List<Content> data,Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.show_item,null);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item, null,false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowViewHolder holder, int position) {
        Content content = data.get(position);
        holder.responsorName.setText(content.getResponsorName());;
        holder.answer.setText(content.getContent());
        holder.resoponsorImage.setImageBitmap(content.getBitmap());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ShowViewHolder extends RecyclerView.ViewHolder{
        private TextView responsorName;
        private TextView answer;
        private ImageView resoponsorImage;

        public ShowViewHolder(View itemView) {
            super(itemView);
            resoponsorImage = itemView.findViewById(R.id.show_responsorImage);
            responsorName = itemView.findViewById(R.id.show_responsorName);
            answer = itemView.findViewById(R.id.show_answer);
        }
    }
}
