package com.example.newspapersubsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView musernameView;
    private EditText mPasswordView;
    private EditText mpassword2View;
    private EditText mnameView;
    private EditText adsView;
    private EditText mIDcardView;
    private EditText mphonenumView;
    private EditText mcodeView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mgetcodenum;
    private String TAG="RegisterActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        musernameView = (AutoCompleteTextView) findViewById(R.id.username);
        musernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mpassword2View=(EditText)findViewById(R.id.password2);
        mpassword2View.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mnameView=findViewById(R.id.name);
        mnameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        adsView=findViewById(R.id.ads);
        adsView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mIDcardView=findViewById(R.id.IDcard);
        mIDcardView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mphonenumView=findViewById(R.id.phonenum);
        mphonenumView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mcodeView=findViewById(R.id.codenum);
        mcodeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        IntentFilter intentFilter=new IntentFilter("myreceiver");
        CodenumReceiver receiver=new CodenumReceiver();
        registerReceiver(receiver,intentFilter);

         mgetcodenum =(Button)findViewById(R.id.codebtn);
        mgetcodenum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mphonenumView.getText().toString())) {
                    Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_LONG).show();
                }else if(!isPhoneValid(mphonenumView.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"请输入正确手机号",Toast.LENGTH_LONG).show();
                }else{

                    new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mgetcodenum.setEnabled(false);
                            mgetcodenum.setText(String.format("%ds后重新获取验证码",millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            mgetcodenum.setEnabled(true);
                            mgetcodenum.setText("获取验证码");
                        }
                    }.start();

                    Intent intent = new Intent();
                    intent.putExtra("phone",  mphonenumView.getText().toString());
                    intent.setAction("myreceiver");
                    sendBroadcast(intent);
                }
            }
        });
        final RequestQueue mQueue= Volley.newRequestQueue(RegisterActivity.this);
        Button mRegister = (Button) findViewById(R.id.register_button);
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                String username = musernameView.getText().toString();
                String password = mPasswordView.getText().toString();
                String name = mnameView.getText().toString();
                String adress=adsView.getText().toString();
                String Idcard= mIDcardView.getText().toString();
                String phonenum = mphonenumView.getText().toString();

                String url = URL.URL + "UserRegisterServlet?username="+username+"&password="+password+
                        "&name="+name+"&adress="+adress+"&Idcard="+Idcard+"&phonenum="+phonenum+"&incode="+ mcodeView.getText().toString()+"&rcode="+URL.code;
                StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s){
                        Toast.makeText(RegisterActivity.this,""+s,Toast.LENGTH_LONG).show();
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

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(musernameView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        musernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = musernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mpassword2View.getText().toString();
        String name = mnameView.getText().toString();
        String Idcard= mIDcardView.getText().toString();
        String phonenum = mphonenumView.getText().toString();
        String Code = mcodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_empty_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

     if(!password.equals(password2)){
            mpassword2View.setError(getString(R.string.error_entrue_password));
            focusView = mpassword2View;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mnameView.setError(getString(R.string.error_empty_name));
            focusView = mnameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(Idcard)) {
            mIDcardView.setError(getString(R.string.error_empty_id));
            focusView = mIDcardView;
            cancel = true;
        }else if(!isIdValid(Idcard)){
            mIDcardView.setError(getString(R.string.error_invalid_id));
            focusView = mIDcardView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phonenum)) {
            mphonenumView.setError(getString(R.string.error_empty_phone));
            focusView = mphonenumView;
            cancel = true;
        }else if(!isPhoneValid(phonenum)){
            mphonenumView.setError(getString(R.string.error_invalid_phone));
            focusView = mphonenumView;
            cancel = true;
        }

        if (TextUtils.isEmpty(Code)) {
            mcodeView.setError(getString(R.string.error_empty_code));
            focusView = mcodeView;
            cancel = true;
        }else if(!String.valueOf(URL.code).equals(Code)){
            mcodeView.setError(getString(R.string.error_code));
            focusView = mcodeView;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            musernameView.setError(getString(R.string.error_field_required));
            focusView = musernameView;
            cancel = true;
        }else if(!isUsernameValid(username)){
            musernameView.setError(getString(R.string.err_usernmae_length));
            focusView = musernameView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() > 10;
    }

    private boolean isIdValid(String idcard) {
        return idcard.length() > 17;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        musernameView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

