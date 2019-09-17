package com.example.newspapersubsystem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewslistAdapter extends RecyclerView.Adapter<NewslistAdapter.ViewHolder> {
    private List<Map<String, Object>> mNewsList;
    private String TAG="IndexActivity";
    private int mPosition = -1;
    public void setData(List<Map<String, Object>> list) {
        mNewsList=list;
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsName;
        TextView mnum;
        TextView publish;
        View NewsView;
        private String TAG="IndexActivity";


        public ViewHolder( View itemView) {
            super(itemView);
            NewsView = itemView;
            newsName = itemView.findViewById(R.id.news_name);
            publish = itemView.findViewById(R.id.publish);
            mnum = itemView.findViewById(R.id.nnum);
        }
    }

    public NewslistAdapter(List<Map<String, Object>> NewsList) {
        mNewsList=NewsList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_index1,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.NewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,Object> map;
                int position=holder.getAdapterPosition();
                 map= mNewsList.get(position);
                Intent intent1=new Intent(v.getContext(),ScrollingActivity.class);
                intent1.putExtra("id",map.get("id").toString());
                v.getContext().startActivity(intent1);
            }
        });
            holder.publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String ,Object> map;
                    int position=holder.getAdapterPosition();
                    map=mNewsList.get(position);
                    Intent intent2=new Intent(v.getContext(),ScrollingActivity.class);
                    intent2.putExtra("id",map.get("id").toString());
                    v.getContext().startActivity(intent2);
                }
            });


        return holder;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          Map<String ,Object> map;
          map=mNewsList.get(position);
          holder.newsName.setText(map.get("newsname").toString());
          holder.publish.setText(map.get("publish").toString());
//          holder.mid.setText(map.get("id").toString());
          holder.mnum.setText(map.get("newsnum").toString());
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
