package com.example.newspapersubsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AddBookActivity extends AppCompatActivity {

    EditText adminname,adminuser,nameadmin,adminbooknum;
    Button  maddbook;
    String URL2=URL.URL;
    private String TAG="AddBookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        maddbook=findViewById(R.id.adbookadmin);
        adminname=findViewById(R.id.adnameofnews);
        adminuser=findViewById(R.id.adnameofuser);
        nameadmin=findViewById(R.id.aname);
        adminbooknum=findViewById(R.id.adnumofbook);


        final RequestQueue mQueue= Volley.newRequestQueue(AddBookActivity.this);
        maddbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL2=URL2+"AdmaddbookServlet?newsname="+adminname.getText().toString()+"&username="+adminuser.getText().toString()+
                        "&name="+nameadmin.getText().toString()+"&booknum="+adminbooknum.getText().toString();
                StringRequest srq = new StringRequest(URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s){
                            Toast.makeText(AddBookActivity.this,""+s,Toast.LENGTH_LONG).show();
                            finish();
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG,volleyError.getMessage());
                    }
                });
                mQueue.add(srq);
            }
        });
    }
}
