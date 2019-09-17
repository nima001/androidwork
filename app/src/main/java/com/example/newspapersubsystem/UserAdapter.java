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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {
    private List<Map<String, Object>> mUserList;
    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView username;
        TextView adress;
        View UserView;


        public ViewHolder( View itemView) {
            super(itemView);
            UserView=itemView;
            name=itemView.findViewById(R.id.adName);
            username=itemView.findViewById(R.id.adUsername);
            adress=itemView.findViewById(R.id.adAdress);
        }
    }

    public UserAdapter(List<Map<String, Object>> UserList) {
        mUserList=UserList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_user1,viewGroup,false);
        final UserAdapter.ViewHolder holder=new UserAdapter.ViewHolder(view);
        holder.UserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,Object> map;
                int position=holder.getAdapterPosition();
                map=mUserList.get(position);
                Intent intent=new Intent(v.getContext(),UserDetailActivity.class);
                intent.putExtra("id",map.get("id").toString());
                v.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Map<String ,Object> map;
        map=mUserList.get(position);
        holder.name.setText(map.get("name").toString());
        holder.username.setText(map.get("username").toString());
        holder.adress.setText(map.get("adress").toString());
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
