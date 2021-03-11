package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText Username;
    private EditText Password;
    private TextView info,signup;
    private Button btn;
    private ImageView ig;
    Animation atg,atg2,atg3,atg4;
    private int c=5;
    public FirebaseAuth fb;
    public FirebaseAuth.AuthStateListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        atg2= AnimationUtils.loadAnimation(this,R.anim.atg2);
        atg3= AnimationUtils.loadAnimation(this,R.anim.atg3);
        atg4= AnimationUtils.loadAnimation(this,R.anim.atg4);

        ig=(ImageView)findViewById(R.id.imageView3);
        ig.startAnimation(atg);
        Username=(EditText)findViewById(R.id.e1);
        Username.startAnimation(atg2);
        Password=(EditText)findViewById(R.id.e2);
        Password.startAnimation(atg2);

        btn=(Button)findViewById(R.id.button);
        btn.startAnimation(atg3);
        info=(TextView)findViewById(R.id.t1);
        info.startAnimation(atg4);
        signup=(TextView)findViewById(R.id.textView2);
        signup.startAnimation(atg4);

        fb=FirebaseAuth.getInstance();
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser=fb.getCurrentUser();
                if(fb!=null)
                {
                    Toast.makeText(MainActivity.this,"Login Successfully.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,sec.class));
                    MainActivity.this.finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Can't find account.",Toast.LENGTH_SHORT).show();
                }

            }
        };
        info.setText("No. of Attempts remaining: 5");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

    }
    void validate(String name,String pwd)
    {
       if(name.isEmpty())
       {
           Username.setError("Please Enter email.");
           Username.requestFocus();
           c--;
           if(c==-1)
           {
               btn.setEnabled(false);
           }
           else {
               info.setText("No. of Attempts remaining: " + String.valueOf(c));
           }
       }
       else if(pwd.isEmpty())
       {
           Password.setError("Please enter password.");
           Password.requestFocus();
           c--;
           if(c==-1)
           {
               btn.setEnabled(false);
           }
           else {
               info.setText("No. of Attempts remaining: " + String.valueOf(c));
           }
       }
       else if(name.isEmpty()|pwd.isEmpty())
       {
           Toast.makeText(MainActivity.this,"Enter all details.",Toast.LENGTH_SHORT).show();
       }
        else
        {
            fb.signInWithEmailAndPassword(name,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        startActivity(new Intent(MainActivity.this,sec.class));
                        MainActivity.this.finish();

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Check your email id and Password.",Toast.LENGTH_SHORT).show();
                        c--;
                        if(c==-1)
                        {
                            btn.setEnabled(false);
                        }
                        else {
                            info.setText("No. of Attempts remaining: " + String.valueOf(c));
                        }
                    }
                }
            });
        }
    }
}
