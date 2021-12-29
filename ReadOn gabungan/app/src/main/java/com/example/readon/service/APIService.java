package com.example.readon.service;

import com.example.readon.datamodel.duel.DuelResponse;
import com.example.readon.datamodel.FriendlistResponse;
import com.example.readon.datamodel.FriendreqResponse;
import com.example.readon.datamodel.LoginResponse;
import com.example.readon.datamodel.SearchUserResponse;
import com.example.readon.datamodel.duel.DuelTextRequest;
import com.example.readon.datamodel.duel.DuelTextResponse;
import com.example.readon.datamodel.duel.SendDuelRequest;
import com.example.readon.datamodel.mission.MissionRequest;
import com.example.readon.model.Friendlist;
import com.example.readon.model.QuestItem;
import com.example.readon.model.Questions;
import com.example.readon.model.Reads;
import com.example.readon.model.User;
import com.example.readon.datamodel.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("/signup")
    public Call<APIResponse> signup(@Body User user);

    @POST("/login")
    public Call<APIResponse<LoginResponse>> login(@Body User user);

    @POST("/sendotp")
    public Call<APIResponse> sendotp(@Body User user);

    @POST("/compareotp")
    public Call<APIResponse> compareotp(@Body User user);

    @POST("/changepassword")
    public Call<APIResponse> changepassword(@Body User user);

    @POST("/viewfriendlist")
    public Call<APIResponse<List<FriendlistResponse>>> viewfriendlist(@Body User user);

    @POST("/deletefriend")
    public Call<APIResponse> deletefriend(@Body Friendlist friendlist);

    @POST("/searchuser")
    public Call<APIResponse<SearchUserResponse>> searchuser(@Body User user);

    @POST("/addfriend")
    public Call<APIResponse> addfriend(@Body Friendlist friendlist);

    @POST("/viewfriendreq")
    public Call<APIResponse<List<FriendreqResponse>>> viewfriendreq(@Body User user);

    @POST("/friendaccept")
    public Call<APIResponse> friendaccept(@Body Friendlist friendlist);

    @POST("/friendreject")
    public Call<APIResponse> friendreject(@Body Friendlist friendlist);

    @POST("/statusonline")
    public Call<APIResponse> statusonline(@Body User user);

    @POST("/statusoffline")
    public Call<APIResponse> statusoffline(@Body User user);

    @POST("/requestlist")
    Call<APIResponse<List<DuelResponse>>> viewDuelRequest(@Body User user);

    @POST("/sendduel")
    Call<APIResponse> sendDuelRequest(@Body SendDuelRequest request);

    @POST("/duelresponse")
    Call<APIResponse> acceptDuel(@Body SendDuelRequest request);

    @POST("/deleterequest")
    Call<APIResponse> declineDuel(@Body SendDuelRequest request);

    @POST("/getduelquestion")
    Call<APIResponse<List<DuelTextResponse>>> getDuelQuestionData(@Body SendDuelRequest request);

    @POST("/getactiveduel")
    Call<APIResponse<DuelResponse>> getActiveDuel(@Body SendDuelRequest request);

    @POST("/updatescore")
    Call<APIResponse<DuelResponse>> updateScore(@Body SendDuelRequest request);

    @POST("/updateduelstatus")
    Call<APIResponse<DuelResponse>> updateDuelStatus(@Body SendDuelRequest request);

    @POST("/getduelhistory")
    Call<APIResponse<List<DuelResponse>>> getDuelHistory(@Body SendDuelRequest request);

    @POST("/adddueltext")
    Call<APIResponse> addDuelText(@Body DuelTextRequest request);

    @GET("/getreads")
    Call<List<Reads>> getreads();

    @GET("/getquestions")
    Call<List<Questions>> getquestions();

    @POST("/mission-complete")
    Call<APIResponse> completeMission(@Body MissionRequest request);

    @POST("/get-mission")
    Call<APIResponse<List<QuestItem>>> getMissions(@Body MissionRequest request);

    APIService create(Class<APIService> apiServiceClass);
}
