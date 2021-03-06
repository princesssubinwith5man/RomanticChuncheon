package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rellay1, rellay2;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private Button buttonSignUp;
    private Context mContext;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }

        rellay1 = (RelativeLayout)findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    EditText editText = (EditText) findViewById(R.id.edittext_password);
                    editText.requestFocus();

                    return true;
                }
                return false;
            }
        });
        editTextPassword = (EditText) findViewById(R.id.edittext_password);
        buttonLogIn = (Button) findViewById(R.id.btn_login);
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    buttonLogIn.performClick();
                    return true;
                }
                return false;
            }
        });

        //????????? ?????? ?????? ????????????
        CheckBox save_checkBox = (CheckBox) findViewById(R.id.save_checkbox) ;
        mContext = this;
        if (save_checkBox.isChecked()) {
            editTextEmail.setText(PreferenceManager.getString(mContext, "id"));
            editTextPassword.setText(PreferenceManager.getString(mContext, "pw"));
            save_checkBox.setChecked(true);
        }




        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                    // ????????? ?????? ??????
                    if (save_checkBox.isChecked()) {
                        PreferenceManager.setString(mContext, "id", editTextEmail.getText().toString());//id ???????????? ??????
                        PreferenceManager.setString(mContext, "pw", editTextPassword.getText().toString());//pw ???????????? ??????
                        PreferenceManager.setBoolean(mContext, "check", save_checkBox.isChecked()); //?????? ???????????? ?????? ??? ??????

                    }
                    else {
                        PreferenceManager.setBoolean(mContext, "check", save_checkBox.isChecked()); //?????? ???????????? ?????? ??? ??????
                        PreferenceManager.clear(mContext); //????????? ?????? ??????
                    }

                } else {
                    Toast.makeText(MainActivity.this, "????????? ??????????????? ???????????????.", Toast.LENGTH_LONG).show();
                    Log.d("asdfsadf", "onClick: ????????? ??????????????? ???????????????");
                }
            }
        });
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };
    }
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // ????????? ??????
                            Toast.makeText(MainActivity.this, "????????? ??????", Toast.LENGTH_SHORT).show();
                            Log.d("asdfsadf", "onClick: ????????? ??????");
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        } else {
                            // ????????? ??????
                            Toast.makeText(MainActivity.this, "????????? ?????? ??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                            Log.d("asdfsadf", "onClick: ????????? ?????? ??????????????? ???????????? ????????????.");
                        }
                    }
                });
    }

    public void signup(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


}