package com.example.newspapersubsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.newspapersubsystem.URL;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private   String username;
    private   String  password;
    private   String ok="0",ok1="99";
    private SharedPreferences pref;
//    final static String URL="http://36.22.157.250:8080/NewsSubSysWeb/";
    String URL1=URL.URL;
    SharedPreferences.Editor editor;
    TextView mRegister;
    EditText mUserEdit;
    EditText mPasswdEdit;
    Button mLoginBtn;
    CheckBox mShowpwdCheckBox,mSaveCheckBox;
    String mUsernmae;
    String mPassword;
    boolean mIsLogged,mIsShowpass,mIsRemeber;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mIsLogged=false;
        mLoginBtn=findViewById(R.id.login);
        mUserEdit=findViewById(R.id.user);
        mPasswdEdit=findViewById(R.id.password);
        mSaveCheckBox=findViewById(R.id.jzmm);
        mRegister=findViewById(R.id.register);

        mIsRemeber = pref.getBoolean("remember_password", false);
        if(mIsRemeber){
             mUsernmae = pref.getString("n1", "");
             mPassword  = pref.getString("password", "");
             mUserEdit.setText(mUsernmae);
             mPasswdEdit.setText(mPassword);
             mSaveCheckBox.setChecked(true);
        }
       mUserEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   int len=mUserEdit.getText().toString().length();
                   if(len==0){
                       mUserEdit.setError(getString(R.string.corrorInfo1));
                   }
               }
           }
       });
       mPasswdEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus){
                   int len=mPasswdEdit.getText().toString().length();
                   if(len<3){
                       mPasswdEdit.setError(getString(R.string.corrorInfo));
                   }
                   if(len==0){
                       mPasswdEdit.setError(getString(R.string.corrorInfo1));
                   }
               }
           }
       });
        final RequestQueue mQueue= Volley.newRequestQueue(MainActivity.this);
       mLoginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               username = mUserEdit.getText().toString();
               password = mPasswdEdit.getText().toString();
               URL.username=mUserEdit.getText().toString();
               URL.pas=mPasswdEdit.getText().toString();
               editor=pref.edit();
               editor.putString("n1",URL.username);
               editor.putString("password",URL.pas);
               int len=mUserEdit.getText().toString().length();
               int len1=mPasswdEdit.getText().toString().length();
               if(len==0 || len1==0){
                   Toast.makeText(MainActivity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
               }
                String url = URL1 + "UserLoginServlet?username="+username+"&password="+password;
               StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String s) {
                           if("99".equals(s)||"0".equals(s)) {
                               URL.type=s;
                               if(mSaveCheckBox.isChecked()){
                                   editor.putBoolean("remember_password",true);
                               }else{
                                   editor.clear();
                               }
                               editor.putString("flag","1");
                               mIsLogged=true;
                               Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                               Intent it=null;
                               it = new Intent(MainActivity.this,  IndexActivity.class);
                               it.putExtra("URL1",URL1);
                               startActivity(it);
                               finish();
                           }else{
                               editor.putString("flag","0");
                               mIsLogged=false;
                               Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_LONG).show();
                           }
                           editor.commit();

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

       //显示隐藏密码
       mShowpwdCheckBox=findViewById(R.id.xianshi);
       mShowpwdCheckBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mShowpwdCheckBox.isChecked()){
                   mIsShowpass=true;
                   mPasswdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
                    mPasswdEdit.setSelection(mPasswdEdit.getText().length());//光标放在最后
               }else{
                   mIsShowpass=false;
                   mPasswdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
                   mPasswdEdit.setSelection(mPasswdEdit.getText().length());//光标放在最后
               }
           }
       });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(it);
            }
        });

    }
}
