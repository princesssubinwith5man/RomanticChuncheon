package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextName = (EditText) findViewById(R.id.editText_name);

        buttonJoin = (Button) findViewById(R.id.btn_join);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    Name name = new Name(editTextName.getText().toString(),editTextEmail.getText().toString());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    String e = editTextEmail.getText().toString();
                    mDatabase.child("name").child("name").setValue(name);
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                } else {
                    // 이메일이나 비밀번호나 이름이 공백인 경우
                    Toast.makeText(SignupActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                    Log.d("asdfsadf", "onClick: 계정과 비밀번호를 입력하세요");
                }
            }
        });
    }
    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Log.d("asdfsadf", "onClick: 회원가입 성공");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String e = user.getUid();
                            Log.d("dafsadf", "onComplete: "+e);
                            Name name = new Name(editTextName.getText().toString());
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("name");
                            mDatabase.child(e).setValue(name);
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignupActivity.this, "이미 존재하는 계정이거나 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                            Log.d("asdfsadf", "onClick: 이미 존재하는 계정이거나 이메일 형식이 아닙니다.");
                        }
                    }
                });
    }
}