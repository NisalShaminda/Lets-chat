package com.example.subhashana.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class StatusActivity extends AppCompatActivity {


   // private Toolbar mToolbar;
    private Button saveChangesButton;
    private EditText StatusInput;
    private ProgressDialog loadingBar;

    private DatabaseReference changeStatusRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        changeStatusRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

//        mToolbar = (Toolbar) findViewById(R.id.status_app_bar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("Change status");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        saveChangesButton = (Button) findViewById(R.id.save_status_change_button);
        StatusInput = (EditText) findViewById(R.id.status_input);
        loadingBar = new ProgressDialog(this);

        String old_status = getIntent().getExtras().get("user_status").toString();
        StatusInput.setText(old_status);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_status = StatusInput.getText().toString();

                ChangeProfileStatus(new_status);
            }


        });
    }

    private void ChangeProfileStatus(String new_status) {
        if (TextUtils.isEmpty(new_status)){
            Toast.makeText(StatusActivity.this,
                    "Please enter your status", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Change profile status");
            loadingBar.setMessage("Please wait, while updating your profile status");
            loadingBar.show();

            changeStatusRef.child("user_status").setValue(new_status)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                loadingBar.dismiss();

                                Intent settingsIntent = new Intent(StatusActivity.this, SettingActivity.class);
                                startActivity(settingsIntent);

                                Toast.makeText(StatusActivity.this,
                                        "Profile Status Updated Succeessfully...",
                                        Toast.LENGTH_SHORT).show();

                            }

                            else{
                                Toast.makeText(StatusActivity.this, "Error occured...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
