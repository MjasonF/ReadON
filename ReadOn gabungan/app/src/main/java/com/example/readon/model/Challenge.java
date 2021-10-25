package com.example.readon.model;

public class Challenge {
    private String challengeId;
    private String challengeName;
    private String challengeDesc;
    private Integer progress;
    private Integer reward;

    public Challenge(String challengeId, String challengeName, String challengeDesc, Integer progress, Integer reward) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.challengeDesc = challengeDesc;
        this.progress = progress;
        this.reward = reward;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeDesc() {
        return challengeDesc;
    }

    public void setChallengeDesc(String challengeDesc) {
        this.challengeDesc = challengeDesc;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
