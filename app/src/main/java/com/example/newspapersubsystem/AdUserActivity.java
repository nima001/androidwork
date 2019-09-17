package com.example.newspapersubsystem;

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

public class AdUserActivity extends AppCompatActivity {
    Button Adduser1;
    EditText amoname,amoid,amousername,amopass,amophone,amoadress;
    private String TAG="AdUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_user);
        Adduser1=findViewById(R.id.Adduser1);
        amoname=findViewById(R.id.amoname);
        amoid=findViewById(R.id.amoid);
        amousername=findViewById(R.id.amousername);
        amopass=findViewById(R.id.amopass);
        amophone=findViewById(R.id.amophone);
        amoadress=findViewById(R.id.amoadress);

        final RequestQueue mQueue= Volley.newRequestQueue(AdUserActivity.this);
        Adduser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL1=URL.URL+"AddUserServlet?name="+amoname.getText().toString()+
                        "&IDcard="+amoid.getText().toString()+"&username="+amousername.getText().toString()+
                        "&password="+amopass.getText().toString()+"&phone="+amophone.getText().toString()+
                        "&adress="+amoadress.getText().toString();

                StringRequest srq = new StringRequest(URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s){
                            Toast.makeText(AdUserActivity.this,""+s,Toast.LENGTH_LONG).show();
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
