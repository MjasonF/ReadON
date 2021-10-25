package com.example.readon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.FriendlistResponse;
import com.example.readon.datamodel.SearchUserResponse;
import com.example.readon.model.Friendlist;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendlistActivity extends AppCompatActivity {
    private Button friend, rival;
    private ImageView friendrequest;
    private FloatingActionButton searchbtn;
    private RecyclerView listfriend;
    private AppPreference pref;
    private FriendlistAdapter friendlistAdapter;
    private FriendlistAdapter.Friendlist action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        friend = findViewById(R.id.btn_friend);
        rival = findViewById(R.id.btn_rival);
        friendrequest = findViewById(R.id.friendrequest);
        friendrequest.setOnClickListener(v -> {
            Intent friendIntent = new Intent(this, FriendRequestActivity.class);
            startActivity(friendIntent);
        });
        searchbtn = findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(v -> {
            SearchUserDialog dialog = new SearchUserDialog();
            dialog.show(getSupportFragmentManager(), "fragment search dialog");
        });
        listfriend = findViewById(R.id.recyclerView);
        pref = new AppPreference(this);
        action = new FriendlistAdapter.Friendlist() {
            @Override
            public void onDeleteClick(FriendlistResponse friendlistResponse) {
                Friendlist friend = new Friendlist();
                String userId = friendlistResponse.getUser().getUserId();
                String frienduserId = friendlistResponse.getFriend().getUserId();
                friend.setUserId(userId);
                friend.setFrienduserId(frienduserId);

                APIClient.client().deletefriend(friend).enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            Toast.makeText(FriendlistActivity.this, message, Toast.LENGTH_SHORT).show();
                            viewfriendlist();
                        }
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(FriendlistActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(FriendlistActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(FriendlistActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        friendlistAdapter = new FriendlistAdapter(pref, action);
        listfriend.setLayoutManager(new LinearLayoutManager(this));
        listfriend.setAdapter(friendlistAdapter);
        viewfriendlist();
    }

    private void viewfriendlist() {
        String userId = pref.getUserId();

        User user = new User();
        user.setUserId(userId);
        APIClient.client().viewfriendlist(user).enqueue(new Callback<APIResponse<List<FriendlistResponse>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<FriendlistResponse>>> call, Response<APIResponse<List<FriendlistResponse>>> response) {
                if (response.isSuccessful()) {
                    List<FriendlistResponse>friendlist = response.body().getData();
                    friendlistAdapter.setData(friendlist);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(FriendlistActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(FriendlistActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<FriendlistResponse>>> call, Throwable t) {
                Toast.makeText(FriendlistActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}