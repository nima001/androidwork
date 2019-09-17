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

public class AMUserActivity extends AppCompatActivity {
    EditText Nameofusertext,IDcardofusertext,usernameofusertext,passwordofusertext,phoneofusertext,adressofusertext;
    Button UserAMbtn;
    String result="",id="",res="修改成功";
    private String TAG="AMUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amuser);

        Nameofusertext=findViewById(R.id.Nameofusertext);
        IDcardofusertext=findViewById(R.id.IDcardofusertext);
        usernameofusertext=findViewById(R.id.usernameofusertext);
        passwordofusertext=findViewById(R.id.passwordofusertext);
        phoneofusertext=findViewById(R.id.phoneofusertext);
        adressofusertext=findViewById(R.id.adressofusertext);
        UserAMbtn=findViewById(R.id.UserAMbtn);

        Intent it=getIntent();
        id = it.getStringExtra("id");
        Nameofusertext.setText(it.getStringExtra("name"));
        IDcardofusertext.setText(it.getStringExtra("IDcard"));
        usernameofusertext.setText(it.getStringExtra("username1"));
        passwordofusertext.setText(it.getStringExtra("password"));
        phoneofusertext.setText(it.getStringExtra("phone"));
        adressofusertext.setText(it.getStringExtra("adress"));

        final RequestQueue mQueue= Volley.newRequestQueue(AMUserActivity.this);
        UserAMbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL1=URL.URL+"AMuserServlet?name="+Nameofusertext.getText().toString()+
                        "&IDcard="+IDcardofusertext.getText().toString()+"&username="+usernameofusertext.getText().toString()+
                        "&password="+usernameofusertext.getText().toString()+"&phone="+phoneofusertext.getText().toString()+
                        "&adress="+adressofusertext.getText().toString()+"&id="+id;


                StringRequest srq = new StringRequest(URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s=="修改成功") {
                            result = "修改失败";
                        } else {
                            result = "修改成功";
                        }
                        Toast.makeText(AMUserActivity.this,result+"",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, volleyError.getMessage());
                    }
                });
                mQueue.add(srq);
            }
        });
    }
}
