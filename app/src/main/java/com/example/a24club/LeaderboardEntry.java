package com.example.a24club;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    private String nickname;
    private int score;

    public LeaderboardEntry(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(LeaderboardEntry other) {
        return Integer.compare(other.score, this.score); // Sort in descending order
    }
}

