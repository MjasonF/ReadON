package com.example.readon;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.SearchUserResponse;
import com.example.readon.model.Friendlist;
import com.example.readon.model.User;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserDialog extends DialogFragment {
    private SearchView searchuser;
    private AppPreference pref;
    private ImageView avatar, status, addfriend;
    private TextView name, statustext;
    private CardView cardview;
    private SearchUserResponse result;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_user_dialog, container, false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatar = view.findViewById(R.id.avatar);
        name = view.findViewById(R.id.username);
        statustext = view.findViewById(R.id.statustext);
        status = view.findViewById(R.id.status);
        addfriend = view.findViewById(R.id.addfriend);
        addfriend.setOnClickListener(v -> {
            Friendlist friend = new Friendlist();
            String userId = pref.getUserId();
            String frienduserId = result.getUserId();
            friend.setUserId(userId);
            friend.setFrienduserId(frienduserId);

            APIClient.client().addfriend(friend).enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(requireContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        pref = new AppPreference(requireContext());
        cardview = view.findViewById(R.id.cardview);
        searchuser = view.findViewById(R.id.searchuser);
        searchuser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                User user = new User();
                String username = searchuser.getQuery().toString();
                String userId = pref.getUserId();
                user.setUsername(username);
                user.setUserId(userId);
                APIClient.client().searchuser(user).enqueue(new Callback<APIResponse<SearchUserResponse>>() {
                    @Override
                    public void onResponse(Call<APIResponse<SearchUserResponse>> call, Response<APIResponse<SearchUserResponse>> response) {
                        if (response.isSuccessful()) {
                            result = response.body().getData();
                            Glide.with(requireContext()).load(result.getAvatar()).into(avatar);
                            name.setText(result.getUsername());
                            cardview.setVisibility(View.VISIBLE);
                            if (result.getOnline()){
                                statustext.setText("Online");
                                status.setImageResource(R.drawable.status2);
                            }else{
                                statustext.setText("Offline");
                                status.setImageResource(R.drawable.status);
                            }
                            if(result.getFriend()){
                                addfriend.setVisibility(View.GONE);
                            }
                            else{
                                addfriend.setVisibility(View.VISIBLE);
                            }

                        }
                        else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(requireContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse<SearchUserResponse>> call, Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
