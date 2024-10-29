package com.lol.LEGIONofLENDAS;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Comparator;

public class UserRanking {
    public int avatarImage;
    public String name;
    public int level;
    public int experience;
    public int victories;
    public int losses;
    public int life;
    public int str;
    public int magic;
    public int defence;
    public int magicDefence;

    public UserRanking(String[] userRankingData, Resources res, String packageName){
        this.avatarImage = res.getIdentifier(userRankingData[0], "drawable", packageName);
        this.name = userRankingData[1];
        this.level = Integer.parseInt(userRankingData[2]);
        this.experience = Integer.parseInt(userRankingData[3]);
        this.str = Integer.parseInt(userRankingData[4]);
        this.defence = Integer.parseInt(userRankingData[5]);
        this.magic = Integer.parseInt(userRankingData[6]);
        this.magicDefence = Integer.parseInt(userRankingData[7]);
        this.life = Integer.parseInt(userRankingData[8]);
        this.victories = Integer.parseInt(userRankingData[9]);
        this.losses = Integer.parseInt(userRankingData[10]);
    }
    public UserRanking(int avatarImage, String name, int level, int experience, int victories, int losses, int life, int str, int magic, int defence, int magicDefence) {
        this.avatarImage = avatarImage;
        this.name = name;
        this.level = level;
        this.experience = experience;
        this.victories = victories;
        this.losses = losses;
        this.life = life;
        this.str = str;
        this.magic = magic;
        this.defence = defence;
        this.magicDefence = magicDefence;
    }

    public int comparator() {
        return level;
    }
    public static final Comparator<UserRanking> USER_RANKING_COMPARATOR_DESCENDING_COMPARATOR =
            Comparator.comparingInt(UserRanking::comparator).reversed();
}
