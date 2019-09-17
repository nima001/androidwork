package com.example.newspapersubsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    CardView uerinfo;
    FloatingActionButton flabtn,addbookflabtn;
    RequestQueue mQueue = null;
    String outname="",type="",out="退出成功";
    String musername="",mpwd="",URL1="";
    boolean mIsLooged=false;
    LinearLayout li;
    Button msearch;
    Button msearch1;
    Button mout;
    TextView mname,mIDcard,muser,mphone,madress,mtype;
    RecyclerView recycleView;
    RecyclerView recycleView1;
    Button modify,usermanage;
    EditText msearchtext;
    EditText msearchtext1;
    RecyclerView recy;
    RecyclerView recy1;
    private SharedPreferences pref;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                  initNewspaper();
                    return true;
                case R.id.navigation_dashboard:
                    if(!URL.type.equals("99")) {
                        inituserBook();
                    }else{
                        initallBooks();
                    }
                    return true;
                case R.id.navigation_notifications:
                    inituser();
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("RestrictedApi")
    private void initallBooks() {
        String url="BookServlet";
        url=URL.URL+url;

        addbookflabtn.setVisibility(View.VISIBLE);
        flabtn.setVisibility(View.GONE);
        recy1.setVisibility(View.VISIBLE);
        recy.setVisibility(View.GONE);
        li=findViewById(R.id.u);
        li.setVisibility(View.GONE);
        msearchtext1=findViewById(R.id.et_search1);
        msearch1=findViewById(R.id.btn_serarch1);
        msearchtext1.setVisibility(View.VISIBLE);
        msearch1.setVisibility(View.VISIBLE);

        msearchtext=findViewById(R.id.et_search);
        msearch=findViewById(R.id.btn_serarch);
        msearch.setVisibility(View.GONE);
        msearchtext.setVisibility(View.GONE);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        map.put("id",temp.getInt("id"));
                        map.put("bookid",temp.getString("bookid"));
                        map.put("username",temp.getString("username"));
                        map.put("name",temp.getString("name"));
                        map.put("newsname",temp.getString("newsname"));
                        map.put("booknum",temp.getString("booknum"));
                        map.put("pricenum",temp.getString("pricenum"));
                        map.put("adress",temp.getString("adress"));
                        mList.add(map);
                    }

                    LinearLayoutManager layoutManager=new LinearLayoutManager(IndexActivity.this, LinearLayoutManager.VERTICAL,false);
                    recycleView1.setLayoutManager(layoutManager);
                    BookAdapter adapter = new  BookAdapter(mList);
                    recycleView1.setAdapter(adapter);
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


        addbookflabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(IndexActivity.this,AddBookActivity.class);
                startActivity(it);
            }
        });
    }

    private NewslistAdapter adapter;
    @SuppressLint("RestrictedApi")
    private void inituser() {
        usermanage=findViewById(R.id.usermanage);
        if(URL.type.equals("99")){
            usermanage.setVisibility(View.VISIBLE);
        }else{
            usermanage.setVisibility(View.GONE);
        }


        addbookflabtn.setVisibility(View.GONE);
        flabtn.setVisibility(View.GONE);
        recy=findViewById(R.id.recycle_view);
        msearchtext=findViewById(R.id.et_search);
        msearch=findViewById(R.id.btn_serarch);
        msearch.setVisibility(View.GONE);
        msearchtext.setVisibility(View.GONE);
        msearchtext1=findViewById(R.id.et_search1);
        msearch1=findViewById(R.id.btn_serarch1);
        msearch1.setVisibility(View.GONE);
        msearchtext1.setVisibility(View.GONE);
        recy.setVisibility(View.GONE);
        recy1.setVisibility(View.GONE);
        mout=findViewById(R.id.out);

        li=findViewById(R.id.u);
        li.setVisibility(View.VISIBLE);


        mname=findViewById(R.id.uname);
        mIDcard=findViewById(R.id.uIdcard);
        muser=findViewById(R.id.uUsername);
        mphone=findViewById(R.id.uPhone);
        madress=findViewById(R.id.uAdress);
        mtype=findViewById(R.id.uType);
        modify=findViewById(R.id.moads);

        if(URL.type.equals("99")){
            modify.setVisibility(View.GONE);
        }
        usermanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(IndexActivity.this,UserActivity.class);
                startActivity(it);
            }
        });

        mout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="OutServlet?username="+outname+"&type="+type;
                url=URL1+url;

                StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("退出成功")){
                            SharedPreferences.Editor editor = pref.edit();
                            URL.username="";
                            URL.pas="";
                            URL.type="";
                            editor.remove("flag").commit();
                            Toast.makeText(IndexActivity.this,"退出成功",Toast.LENGTH_LONG).show();
                            finish();
                            Intent it=new Intent(IndexActivity.this,MainActivity.class);
                            startActivity(it);
                        }else{
                            Toast.makeText(IndexActivity.this,"退出失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG,volleyError.getMessage());
                    }
                });
                mQueue.add(srq);
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IndexActivity.this,ModifyActivity.class);
                startActivity(intent);
            }
        });

        String url="UserCardServlert";
        url=URL1+url;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        outname=temp.getString("username");
                        type=temp.getString("u_type");
                        mname.setText("姓名:"+temp.getString("name"));
                        mIDcard.setText("身份证号：\n"+temp.getString("IDcard"));
                        muser.setText("用户名"+temp.getString("username"));
                        mphone.setText("电话："+temp.getString("phone"));
                        madress.setText("地址：\n"+temp.getString("adress"));
                        if(temp.getString("u_type").equals("0")){
                            mtype.setText("用户类型:用户");
                        }else if(temp.getString("u_type").equals("99")){
                            mtype.setText("用户类型:管理员");
                        }else{
                            mtype.setText("用户类型");
                        }

                    }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);



        pref = PreferenceManager.getDefaultSharedPreferences(this);

        flabtn=findViewById(R.id.addbo);
        addbookflabtn=findViewById(R.id.addbo1);
        recycleView=findViewById(R.id.recycle_view);
        recycleView1=findViewById(R.id.recycle_view1);
        //添加Android自带的分割线,只能调用一次，不然间距会一直增加
        recycleView.addItemDecoration(new DividerItemDecoration(IndexActivity.this, DividerItemDecoration.VERTICAL));
        recycleView1.addItemDecoration(new DividerItemDecoration(IndexActivity.this, DividerItemDecoration.VERTICAL));

        recy=findViewById(R.id.recycle_view);
        recy1=findViewById(R.id.recycle_view1);
        msearch=findViewById(R.id.btn_serarch);
        msearchtext=findViewById(R.id.et_search);
        msearch1=findViewById(R.id.btn_serarch1);
        msearchtext1=findViewById(R.id.et_search1);

        mQueue= Volley.newRequestQueue(IndexActivity.this);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="SearchServlet";
                url=URL1+url+"?keyword="+msearchtext.getText().toString();
            if(!msearchtext.getText().toString().equals("")) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
                            for (int i = 0; i < response.length(); i++) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                JSONObject temp = (JSONObject) response.get(i);
                                map.put("newsname", temp.getString("newsname"));
                                map.put("publish", temp.getString("publish"));
                                map.put("id", temp.getInt("id"));
                                map.put("price", temp.getString("price"));
                                map.put("date", temp.getString("date"));
                                map.put("newsnum", temp.getString("newsnum"));
                                map.put("content", temp.getString("content"));
                                mList.add(map);
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(IndexActivity.this, LinearLayoutManager.VERTICAL, false);
                            recycleView.setLayoutManager(layoutManager);
                            NewslistAdapter adapter = new NewslistAdapter(mList);
                            recycleView.setAdapter(adapter);
                            msearchtext.getText().clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("发生了一个错误！");
                        error.printStackTrace();
                    }
                });
                mQueue.add(jsonArrayRequest);
            }else{
                Toast.makeText(IndexActivity.this,"请输入内容",Toast.LENGTH_LONG).show();
            }
            }
        });

        initNewspaper();

        msearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="SearchServlet";
                url=URL1+url+"?keyword1="+msearchtext1.getText().toString();
                if(!msearchtext1.getText().toString().equals("")) {
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
                                for (int i = 0; i < response.length(); i++) {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    JSONObject temp = (JSONObject) response.get(i);
                                    map.put("id", temp.getInt("id"));
                                    map.put("bookid", temp.getString("bookid"));
                                    map.put("username", temp.getString("username"));
                                    map.put("name", temp.getString("name"));
                                    map.put("booknum", temp.getString("booknum"));
                                    map.put("pricenum", temp.getString("pricenum"));
                                    map.put("newsname", temp.getString("newsname"));
                                    map.put("adress", temp.getString("adress"));
                                    mList.add(map);
                                }

                                LinearLayoutManager layoutManager = new LinearLayoutManager(IndexActivity.this, LinearLayoutManager.VERTICAL, false);
                                recycleView1.setLayoutManager(layoutManager);
                                BookAdapter adapter = new BookAdapter(mList);
                                recycleView1.setAdapter(adapter);
                                msearchtext1.getText().clear();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("发生了一个错误！");
                            error.printStackTrace();
                        }
                    });
                    mQueue.add(jsonArrayRequest);
                }else{
                    Toast.makeText(IndexActivity.this,"请输入内容",Toast.LENGTH_LONG).show();
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent it=getIntent();
        if(it!=null){
            musername=it.getStringExtra("USER");
            if(musername==null){
                musername="";
            }
            mpwd=it.getStringExtra("PASS");
            if(mpwd==null){
                mpwd="";
            }
            mIsLooged=it.getBooleanExtra("LOGGED",false);
        }
    }

   @SuppressLint("RestrictedApi")
   public void inituserBook(){
       String url="UserBookServlet";
        url=URL1+url+"?username="+URL.username;

       addbookflabtn.setVisibility(View.GONE);
       flabtn.setVisibility(View.GONE);
       recy1.setVisibility(View.VISIBLE);
       recy.setVisibility(View.GONE);
       li=findViewById(R.id.u);
       li.setVisibility(View.GONE);
       msearchtext1=findViewById(R.id.et_search1);
       msearch1=findViewById(R.id.btn_serarch1);
       msearch1.setVisibility(View.VISIBLE);
       msearchtext1.setVisibility(View.VISIBLE);

       msearchtext=findViewById(R.id.et_search);
       msearch=findViewById(R.id.btn_serarch);
       msearch.setVisibility(View.GONE);
       msearchtext.setVisibility(View.GONE);



//       final RequestQueue mQueue= Volley.newRequestQueue(IndexActivity.this);
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){
           @Override
           public void onResponse(JSONArray response) {
               try {
                   List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                   for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        map.put("id",temp.getInt("id"));
                        map.put("bookid",temp.getInt("bookid"));
                        map.put("username",temp.getString("username"));
                        map.put("name",temp.getString("name"));
                        map.put("newsname",temp.getString("newsname"));
                        map.put("booknum",temp.getString("booknum"));
                        map.put("pricenum",temp.getString("pricenum"));
                        map.put("adress",temp.getString("adress"));
                        mList.add(map);
                    }

                    LinearLayoutManager layoutManager=new LinearLayoutManager(IndexActivity.this, LinearLayoutManager.VERTICAL,false);
                    recycleView1.setLayoutManager(layoutManager);
                    BookAdapter adapter = new  BookAdapter(mList);
                    recycleView1.setAdapter(adapter);
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

    @SuppressLint("RestrictedApi")
    private void initNewspaper() {
        Intent intent=getIntent();
        String url="NewsServlet";
        url=intent.getStringExtra("URL1")+url;
        URL1 = intent.getStringExtra("URL1");

        if(URL.type.equals("99")){
            flabtn.setVisibility(View.VISIBLE);
        }else{
            flabtn.setVisibility(View.GONE);
        }

        li=findViewById(R.id.u);
        li.setVisibility(View.GONE);
        addbookflabtn.setVisibility(View.GONE);
        msearchtext=findViewById(R.id.et_search);
        msearch=findViewById(R.id.btn_serarch);
        msearch.setVisibility(View.VISIBLE);
        msearchtext.setVisibility(View.VISIBLE);

        msearchtext1=findViewById(R.id.et_search1);
        msearch1=findViewById(R.id.btn_serarch1);
        msearch1.setVisibility(View.GONE);
        msearchtext1.setVisibility(View.GONE);

        recy=findViewById(R.id.recycle_view);
        recy.setVisibility(View.VISIBLE);
        recy1.setVisibility(View.GONE);



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        map.put("newsname",temp.getString("newsname"));
                        map.put("publish",temp.getString("publish"));
                        map.put("id",temp.getInt("id"));
                        map.put("price",temp.getString("price"));
                        map.put("date",temp.getString("date"));
                        map.put("newsnum",temp.getString("newsnum"));
                        map.put("content",temp.getString("content"));
                        mList.add(map);
                    }

                    LinearLayoutManager layoutManager=new LinearLayoutManager(IndexActivity.this);
                    recycleView.setLayoutManager(layoutManager);
//                    UpdateList(mList, recycleView);
                    NewslistAdapter adapter = new  NewslistAdapter(mList);
                    recycleView.setAdapter(adapter);
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

        flabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(IndexActivity.this,AddnewsActivity.class);
                startActivity(it);
            }
        });
    }

}
