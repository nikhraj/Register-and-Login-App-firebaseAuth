package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    // declaring variables
private EditText name,email,pass;
ImageView ig;
Animation atg,atg2,atg3;
FirebaseAuth fb;
private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        atg2= AnimationUtils.loadAnimation(this,R.anim.atg2);
        atg3= AnimationUtils.loadAnimation(this,R.anim.atg3);

        ig=(ImageView)findViewById(R.id.imageView);
        ig.startAnimation(atg);
        name=(EditText)findViewById(R.id.ename);
        name.startAnimation(atg2);
        email=(EditText)findViewById(R.id.email);
        email.startAnimation(atg2);
        pass=(EditText)findViewById(R.id.pwd);
        pass.startAnimation(atg2);
        btn2=(Button)findViewById(R.id.button2);
        btn2.setAnimation(atg3);
        fb=FirebaseAuth.getInstance();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {
                    fb.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Register.this,"Registered Successfully.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent( Register.this ,MainActivity.class));
                                Register.this.finish();
                            }
                            else
                            {
                                Toast.makeText(Register.this,"Please enter valid email id and password.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });



    }
    boolean validate()
    {
        boolean result;
        result = false;
        String s1=name.getText().toString();
        String s2=email.getText().toString();
        String s3=pass.getText().toString();
        if(s1.isEmpty())
        {
            name.setError("Please enter Name.");
            name.requestFocus();
        }
        else if(s2.isEmpty())
        {
            email.setError("Please enter email.");
            email.requestFocus();
        }
        else if(s3.isEmpty())
        {
            pass.setError("Please enter Password.");
            pass.requestFocus();
        }

        else if(s1.isEmpty()|s2.isEmpty()|s3.isEmpty())
        {
            Toast.makeText(this,"Please fill all details.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result=true;
        }
        return result;
    }

}
