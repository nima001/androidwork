package com.example.newspapersubsystem;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    RequestQueue mQueue = null;
    TextView username;
    TextView name;
    TextView password;
    EditText adsearname;
    RecyclerView recyclerView_user;
    Button btn_searchuser;
    FloatingActionButton  refresh;
    Button adUser,back;
    boolean state=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        username=findViewById(R.id.adName);
        name=findViewById(R.id.adUsername);
        password=findViewById(R.id.adAdress);
        adUser=findViewById(R.id.adUser);
        back=findViewById(R.id.back);
        recyclerView_user=findViewById(R.id.recycle_user);

        refresh=findViewById(R.id.refresh);
        adsearname=findViewById(R.id.adsearname);
        btn_searchuser=findViewById(R.id.btn_searchuser);
        mQueue= Volley.newRequestQueue(UserActivity.this);
        recyclerView_user.addItemDecoration(new DividerItemDecoration(UserActivity.this, DividerItemDecoration.VERTICAL));

        inituserlist();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               inituserlist();
            }
        });

        refresh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(state==false){
                    adUser.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    state=true;
                }else if(state==true){
                    adUser.setVisibility(View.GONE);
                    back.setVisibility(View.GONE);
                    state=false;
                }
                return true;
            }
        });

        adUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(UserActivity.this,AdUserActivity.class);
                startActivity(it);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        btn_searchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adsearname.getText().toString()==null){
                    Toast.makeText(UserActivity.this,"请输入内容" ,Toast.LENGTH_LONG).show();
                }else{
                    String URL3=URL.URL+"AdminSeaServlet?keyword="+adsearname.getText().toString();
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL3, new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                                for(int i=0;i<response.length();i++) {
                                    Map<String ,Object> map=new HashMap<String,Object>();
                                    JSONObject temp= (JSONObject) response.get(i);
                                    map.put("id",temp.getInt("id"));
                                    map.put("name",temp.getString("name"));
                                    map.put("IDcard",temp.getString("IDcard"));
                                    map.put("username",temp.getString("username"));
                                    map.put("password",temp.getString("password"));
                                    map.put("phone",temp.getString("phone"));
                                    map.put("adress",temp.getString("adress"));
                                    map.put("u_type",temp.getString("u_type"));
                                    mList.add(map);
                                }
                                LinearLayoutManager layoutManager=new LinearLayoutManager(UserActivity.this);
                                recyclerView_user.setLayoutManager(layoutManager);
                                UserAdapter adapter = new UserAdapter(mList);
                                recyclerView_user.setAdapter(adapter);
                                adsearname.getText().clear();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("发生了一个错误！");
                            error.printStackTrace();
                        }
                    });
                    mQueue.add(jsonArrayRequest);
                }
            }
        });


    }

   public void inituserlist(){
       String URL1=URL.URL+"UserlistServlet";
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL1, new Response.Listener<JSONArray>(){
           @Override
           public void onResponse(JSONArray response) {
               try {
                   List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                   for(int i=0;i<response.length();i++) {
                       Map<String ,Object> map=new HashMap<String,Object>();
                       JSONObject temp= (JSONObject) response.get(i);
                       map.put("id",temp.getInt("id"));
                       map.put("name",temp.getString("name"));
                       map.put("IDcard",temp.getString("IDcard"));
                       map.put("username",temp.getString("username"));
                       map.put("password",temp.getString("password"));
                       map.put("phone",temp.getString("phone"));
                       map.put("adress",temp.getString("adress"));
                       map.put("u_type",temp.getString("u_type"));
                       mList.add(map);
                   }

                   LinearLayoutManager layoutManager=new LinearLayoutManager(UserActivity.this);
                   recyclerView_user.setLayoutManager(layoutManager);
                   UserAdapter adapter = new UserAdapter(mList);
                   recyclerView_user.setAdapter(adapter);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener(){
           @Override
           public void onErrorResponse(VolleyError error) {
               System.out.println("发生了一个错误！");
               error.printStackTrace();
           }
       });
       mQueue.add(jsonArrayRequest);
    }
}
