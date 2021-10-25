package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.FriendreqResponse;
import com.example.readon.model.Friendlist;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestActivity extends AppCompatActivity {

    private RecyclerView listfriendreq;
    private FriendReqAdapter.Friendreq action;
    private FriendReqAdapter friendReqAdapter;
    private AppPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        listfriendreq = findViewById(R.id.recyclerView);
        pref = new AppPreference(this);
        action = new FriendReqAdapter.Friendreq() {
            @Override
            public void onDeleteClick(FriendreqResponse friendreqResponse) {
                Friendlist friend = new Friendlist();
                String friendlistId = friendreqResponse.getId();
                friend.setFriendlistId(friendlistId);

                APIClient.client().friendreject(friend).enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            Toast.makeText(FriendRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                            viewfriendreq();
                        }
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(FriendRequestActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(FriendRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(FriendRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onAcceptClick(FriendreqResponse friendreqResponse) {
                Friendlist friend = new Friendlist();
                String friendlistId = friendreqResponse.getId();
                String userId = friendreqResponse.getUserId().getId();
                String frienduserId = friendreqResponse.getFriendUserId();
                Boolean isAccepted = friendreqResponse.getIsAccepted();
                friend.setFriendlistId(friendlistId);
                friend.setUserId(userId);
                friend.setFrienduserId(frienduserId);
                friend.setAccepted(isAccepted);

                APIClient.client().friendaccept(friend).enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            Toast.makeText(FriendRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                            viewfriendreq();
                        }
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(FriendRequestActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(FriendRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(FriendRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        friendReqAdapter = new FriendReqAdapter(pref, action);
        listfriendreq.setLayoutManager(new LinearLayoutManager(this));
        listfriendreq.setAdapter(friendReqAdapter);
        viewfriendreq();
    }

    private void viewfriendreq() {
        String userId = pref.getUserId();

        User user = new User();
        user.setUserId(userId);
        APIClient.client().viewfriendreq(user).enqueue(new Callback<APIResponse<List<FriendreqResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<FriendreqResponse>>> call, Response<APIResponse<List<FriendreqResponse>>> response) {
                if (response.isSuccessful()) {
                    List<FriendreqResponse>friendreq = response.body().getData();
                    friendReqAdapter.setData(friendreq);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(FriendRequestActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(FriendRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<FriendreqResponse>>> call, Throwable t) {
                Toast.makeText(FriendRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}