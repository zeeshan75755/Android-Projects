package tellmewhere.com.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private Button LoginButton,PhoneLoginButton;
    private EditText UserEmail, UserPassword;
    private TextView NeedNewAccountLink,ForgetPasswordLink;

    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        InitializeField();

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsertoRegisterActivity();
            }


        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        String email = UserEmail.getText().toString();
        String password= UserPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Enter Email..." ,Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Enter Password..." ,Toast.LENGTH_LONG).show();
        }
        else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUsertoMainActivity();
                        Toast.makeText(LoginActivity.this,"Logged in Successfull....", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }
                    else {
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this,"Error : "+ message,Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void InitializeField() {
        LoginButton = findViewById(R.id.Loginbutton);
        PhoneLoginButton = findViewById(R.id.phone_login);
        UserEmail = findViewById(R.id.login_Email);
        UserPassword = findViewById(R.id.login_Password);
        NeedNewAccountLink = findViewById(R.id.need_new_account);
        ForgetPasswordLink = findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);

    }
    private void sendUsertoRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }



    private void sendUsertoMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
