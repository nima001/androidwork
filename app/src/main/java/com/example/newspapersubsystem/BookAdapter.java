package com.example.newspapersubsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>  {
    private List<Map<String, Object>> mBookList;
    static  class ViewHolder extends RecyclerView.ViewHolder{
//        TextView username;
        TextView name;
//        TextView bookid;
        TextView newsname;
        TextView booknum;
        TextView pricenum;
        TextView adress;
        View BookView;


        public ViewHolder( View itemView) {
            super(itemView);
            BookView=itemView;
//            bookid=itemView.findViewById(R.id.bookid);
//            username=itemView.findViewById(R.id.username);
            newsname=itemView.findViewById(R.id.newsname);
            booknum=itemView.findViewById(R.id.booknum);
            pricenum=itemView.findViewById(R.id.pricenum);
            adress=itemView.findViewById(R.id.adress);
            name=itemView.findViewById(R.id.name);
        }
    }

    public BookAdapter(List<Map<String, Object>> BookList) {
        mBookList=BookList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_book,viewGroup,false);
        final BookAdapter.ViewHolder holder=new ViewHolder(view);
        holder.BookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,Object> map;
                int position=holder.getAdapterPosition();
                map=mBookList.get(position);
                Intent intent=new Intent(v.getContext(),BookByuserActivity.class);
                intent.putExtra("id",map.get("id").toString());
                v.getContext().startActivity(intent);
            }
        });

        return holder;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Map<String ,Object> map;
        map=mBookList.get(position);
//        holder.username.setText(map.get("username").toString());
        holder.name.setText(map.get("name").toString());
        holder.newsname.setText(map.get("newsname").toString());
//        holder.bookid.setText(map.get("bookid").toString());
        holder.adress.setText(map.get("adress").toString());
        holder.booknum.setText(map.get("booknum").toString());
        holder.pricenum.setText(map.get("pricenum").toString());
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
