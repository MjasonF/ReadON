package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.FriendlistItemResponse;
import com.example.readon.datamodel.FriendlistResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuelActivity extends AppCompatActivity {

    private ImageView history, mail;
    private TextView number;
    private Spinner friendlist, type;
//    private EditText time;
    private Button request;
    private Integer selectedDuelType = 0;
    private ProgressBar progressBar;

    private AppPreference appPreference;
    private ArrayList<User> arrayFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel);
        history = findViewById(R.id.imageView);
        mail = findViewById(R.id.imageView2);
        number = findViewById(R.id.number);
        friendlist = findViewById(R.id.spinner);
        type = findViewById(R.id.spinner2);
        request = findViewById(R.id.btn_duel);
        progressBar = findViewById(R.id.progress_bar);

        arrayFriendList = new ArrayList<>();
        appPreference = new AppPreference(this);

        history.setOnClickListener(v -> {
            Intent historyIntent = new Intent(this, DuelHistoryActivity.class);
            startActivity(historyIntent);
        });

        mail.setOnClickListener(v -> {
            Intent mailIntent = new Intent(this, DuelRequestActivity.class);
            startActivity(mailIntent);
        });

        request.setOnClickListener(v -> {
            sendDuelRequest();
        });

        loadFriendList();
        loadDuelRequestNumber();

        List<String> list2 = new ArrayList<>();
        list2.add("Point Competition");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list2);
        type.setAdapter(adapter2);
//        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedDuelType = position;
//                if (position != 1) {
//                    time.setText("3");
//                    time.setEnabled(false);
//                } else {
//                    time.setText("");
//                    time.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//        });

//        time.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (selectedDuelType != 1) return;
//                try {
//                    if (Integer.parseInt(s.toString()) < 24 || Integer.parseInt(s.toString()) > 192) {
//                        request.setEnabled(false);
//                    } else {
//                        request.setEnabled(true);
//                    }
//                } catch (Exception e) {
//                    request.setEnabled(false);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void loadFriendList() {
        showProgressBar();
        User user = new User();
        user.setUserId(appPreference.getUserId());
        APIClient.client().viewfriendlist(user).enqueue(new Callback<APIResponse<List<FriendlistResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<FriendlistResponse>>> call, Response<APIResponse<List<FriendlistResponse>>> response) {
                hideProgressBar();
                if (response.body() != null) {
                    handleResult(response.body().getData());
                    enabledButton();
                }
                else {
                    Toast.makeText(DuelActivity.this, "Add friends to enable this feature", Toast.LENGTH_SHORT).show();
                    ArrayList<String> list = new ArrayList<>();
                    list.add("No friend");
                    setFriendAdapter(list);
                    disableButton();
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<FriendlistResponse>>> call, Throwable t) {
                hideProgressBar();
                disableButton();
                Toast.makeText(DuelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enabledButton() {
        request.setEnabled(true);
        request.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));
    }

    private void disableButton() {
        request.setEnabled(false);
        request.setBackgroundColor(ContextCompat.getColor(this, R.color.browser_actions_bg_grey));
    }

    private void handleResult(List<FriendlistResponse> responseFriends) {
        arrayFriendList.clear();
        ArrayList<String> friends = new ArrayList<>();
        for (FriendlistResponse friend : responseFriends) {
            String friendId = friend.getFriend().getUserId();
            FriendlistItemResponse friendItem = friendId.equals(appPreference.getUserId()) ? friend.getUser() : friend.getFriend();
            User user = new User();
            user.setUserId(friendItem.getUserId());
            user.setAvatar(friendItem.getAvatar());
            user.setUsername(friendItem.getUsername());
            friends.add(friendItem.getUsername());
            arrayFriendList.add(user);
        }
        setFriendAdapter(friends);
    }

    private void setFriendAdapter(List<String> listFriends) {
        ArrayAdapter<String> friendAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listFriends);
        friendlist.setAdapter(friendAdapter);
    }

    private void loadDuelRequestNumber() {
        User user = new User();
        user.setUserId(appPreference.getUserId());
        APIClient.client().viewDuelRequest(user).enqueue(new Callback<APIResponse<List<DuelResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DuelResponse>>> call, Response<APIResponse<List<DuelResponse>>> response) {
                if (response.body() != null) {
                    List<DuelResponse> duelResponse = response.body().getData();
                    if (duelResponse == null) {
                        duelResponse = new ArrayList<>();
                    }
                    if (duelResponse.size() != 0) {
                        number.setText(String.valueOf(duelResponse.size()));
                        number.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DuelResponse>>> call, Throwable t) {
                Toast.makeText(DuelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendDuelRequest() {
        progressBar.setVisibility(View.VISIBLE);
        SendDuelRequest request = new SendDuelRequest();
        request.setUserId(appPreference.getUserId());
        int friendPosition = friendlist.getSelectedItemPosition();
        request.setOpponentId(arrayFriendList.get(friendPosition).getUserId());
        request.setType("1"); // Point Competition
        APIClient.client().sendDuelRequest(request).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    Toast.makeText(DuelActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DuelActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DuelActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Toast.makeText(DuelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}