package com.example.newspapersubsystem;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class AddnewsActivity extends AppCompatActivity{
    EditText adnewsnum,adnewsname,adprice,adcontent,adpublish,addate;
    Button msub;
    private String TAG="AddnewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);
        adnewsnum=findViewById(R.id.adnewsnum);
        adnewsname=findViewById(R.id.adnewsname);
        adprice=findViewById(R.id.adprice);
        addate=findViewById(R.id.addate);
        adcontent=findViewById(R.id.adcontent);
        adpublish=findViewById(R.id.adpublish);
        msub=findViewById(R.id.subnews);
        final RequestQueue mQueue= Volley.newRequestQueue(AddnewsActivity.this);

        msub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adnewsnum.getText().toString()=="0"||adnewsname.getText().toString()==""||adprice.getText().toString()=="0"||addate.getText().toString()==""||adcontent.getText().toString()==""||adpublish.getText().toString()==""){
                    Toast.makeText(AddnewsActivity.this,"输入为空",Toast.LENGTH_LONG).show();
                }else{

                    String url=URL.URL+"AddnewsServlet?newsnum="+adnewsnum.getText().toString()+"&newsname="+adnewsname.getText().toString()+
                            "&price="+adprice.getText().toString()+"&date="+addate.getText().toString()+
                            "&content="+adcontent.getText().toString()+"&publish="+adpublish.getText().toString();
                    StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s){

                                Toast.makeText(AddnewsActivity.this,""+s,Toast.LENGTH_LONG).show();
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
            }
        });

    }


}
