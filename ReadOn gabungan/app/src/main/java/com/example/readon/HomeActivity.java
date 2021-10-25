package com.example.readon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.readon.datamodel.APIResponse;
import com.example.readon.datamodel.UserItemResponse;
import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.pref.AppPreference;
import com.example.readon.service.APIClient;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Button button, button2, button3, buttonedit;
    private AppPreference appPreference;
    private TextView username;
    private TextView tvActiveDuel, tvDuel, tvScore, tvStatus;
    private Button btnStartDuel;
    ViewFlipper flipperView;

    private Group activeDuelGroup;
    private ShimmerFrameLayout shimmerFrameLayout;

    private Button btnAddDuelText;
    private Button btnShop;
    private Button btnEditProfile;
    private Button btnTipsAndTrick;

    private String duelId;

    public static final String HOME_MESSAGE = "home_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String message = getIntent().getStringExtra(HOME_MESSAGE);

        if (message != null) {
            displayAlertDialog(message);
        }

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        username = findViewById(R.id.username);
        tvActiveDuel = findViewById(R.id.text_active_duel);
        activeDuelGroup = findViewById(R.id.active_duel_group);
        tvDuel = findViewById(R.id.tv_duel);
        tvScore = findViewById(R.id.tv_score);
        tvStatus = findViewById(R.id.tv_status);
        btnStartDuel = findViewById(R.id.btn_start_duel);
        btnAddDuelText = findViewById(R.id.btn_add_text);
        btnTipsAndTrick = findViewById(R.id.tips);
        btnShop = findViewById(R.id.btn_shop);

        btnAddDuelText.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDuelTextActivity.class);
            startActivity(intent);
        });

        btnShop.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopActivity.class);
            startActivity(intent);
        });

        btnStartDuel.setOnClickListener(v -> {
            Intent intent = new Intent(this, DuelStartActivity.class);
            intent.putExtra(DuelStartActivity.DUEL_UNIQUE_ID, duelId);
            startActivity(intent);
        });

        btnTipsAndTrick.setOnClickListener(v -> {
            Intent intent = new Intent(this, TipsActivity.class);
            startActivity(intent);
        });

        appPreference = new AppPreference(this);

        if (!appPreference.isAdmin()) {
            btnAddDuelText.setVisibility(View.GONE);
        }
        else {
            btnAddDuelText.setVisibility(View.VISIBLE);
        }

        username.setText(appPreference.getUserLoggedInUsername());
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        int images[] = {R.drawable.skateboard, R.drawable.design, R.drawable.cellphone};
//
        flipperView = findViewById(R.id.flipperView);
        flipperView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movelibrary();

            }
        });
            for (int image: images){
                flipperimage(image);
            }

        button = (Button) findViewById(R.id.duel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveduel();
            }
        });

        button2 = (Button) findViewById(R.id.challenge);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movechal();
            }
        });

        button3 = (Button) findViewById(R.id.tips);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetips();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActiveDuel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!appPreference.isLogin()) {
            gotoFrontActivity();
        }
    }

    private void gotoFrontActivity() {
        Intent frontIntent = new Intent(this, FrontActivity.class);
        startActivity(frontIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                moveprofile();
                return true;
            case R.id.shelf:
                moveshelf();
                return true;
            case R.id.library:
                movelibrary();
                return true;
            case R.id.friend:
                movefriend();
                return true;
            case R.id.tips:
                movetips();
                return true;
            case R.id.logout:
                appPreference.logout();
                gotoFrontActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadActiveDuel() {
        tvActiveDuel.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
        SendDuelRequest request = new SendDuelRequest();
        request.setUserId(appPreference.getUserId());
        APIClient.client().getActiveDuel(request).enqueue(new Callback<APIResponse<DuelResponse>>() {
            @Override
            public void onResponse(Call<APIResponse<DuelResponse>> call, Response<APIResponse<DuelResponse>> response) {
                if (response.isSuccessful()) {
                    DuelResponse result = response.body().getData();

                    if (result != null) {
                        duelId = result.getDuelId();
                        UserItemResponse you = result.getUser();
                        UserItemResponse opponent = result.getOpponent();
                        boolean isOpponent = !result.getUser().getUserId().equals(appPreference.getUserId());
                        if (isOpponent) {
                            you = result.getOpponent();
                            opponent = result.getUser();
                        }

                        if (result.getDuelFinished()) {
                            tvStatus.setText("Status: Duel is already finished");
                            btnStartDuel.setVisibility(View.INVISIBLE);
                        }
                        else {
                            boolean isDuelFinished = isOpponent ? result.getOpponentFinished() : result.getUserFinished();
                            Integer yourScore = isOpponent ? result.getOpponentPoint() : result.getPoint();
                            tvScore.setText(String.format("Your Score: %s", yourScore));
                            if (isDuelFinished) {
                                btnStartDuel.setVisibility(View.INVISIBLE);
                            }
                            else {
                                btnStartDuel.setVisibility(View.VISIBLE);
                            }
                            String status = isDuelFinished ? "Status: Waiting for your opponent to finish the duel" : "Status: Dueling";
                            tvStatus.setText(status);
                        }

                        tvDuel.setText(String.format("%s vs %s", you.getUsername(), opponent.getUsername()));
                        tvActiveDuel.setVisibility(View.VISIBLE);
                        activeDuelGroup.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvActiveDuel.setVisibility(View.VISIBLE);
                        tvActiveDuel.setText("No Active Duel");
                        activeDuelGroup.setVisibility(View.GONE);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(HomeActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                shimmerFrameLayout.stopShimmer();
            }

            @Override
            public void onFailure(Call<APIResponse<DuelResponse>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
            }
        });
    }

    private void displayAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", ((dialog, which) -> {
            dialog.cancel();
        }));
        builder.show();
    }

    public void moveprofile() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void moveshelf() {
        Intent i = new Intent(this, ShelfActivity.class);
        startActivity(i);
    }

    public void movelibrary() {
        Intent i = new Intent(this, LibraryActivity.class);
        startActivity(i);
    }

    public void movefriend() {
        Intent i = new Intent(this, FriendlistActivity.class);
        startActivity(i);
    }

    public void movetips() {
        Intent i = new Intent(this, TipsActivity.class);
        startActivity(i);
    }

    public void moveduel() {
        Intent i = new Intent(this, DuelActivity.class);
        startActivity(i);
    }

    public void movechal() {
        Intent i = new Intent(this, ChallengeActivity.class);
        startActivity(i);
    }

    public void edit() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void flipperimage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        flipperView.addView(imageView);
        flipperView.setFlipInterval(3000);
        flipperView.setAutoStart(true);
        flipperView.setInAnimation(this, android.R.anim.slide_in_left);
        flipperView.setInAnimation(this, android.R.anim.slide_out_right);


    }

}