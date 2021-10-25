package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.model.User;
import com.example.readon.service.APIClient;
import com.mukesh.OtpView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputCodeActivity extends AppCompatActivity {

    private OtpView etOtp;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);

        etOtp = findViewById(R.id.otp);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(v -> compareotp());
    }

    private void compareotp(){
        String email = getIntent().getStringExtra("email");
        String otp = etOtp.getText().toString();

        User user = new User();
        user.setEmail(email);
        user.setOtp(otp);
        APIClient.client().compareotp(user).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(InputCodeActivity.this, message, Toast.LENGTH_SHORT).show();
                    gotoNewPasswordActivity(email);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(InputCodeActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(InputCodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(InputCodeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoNewPasswordActivity(String email){
        Intent intent = new Intent(this, NewPasswordActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}