package com.codepath.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vf608 on 7/13/17.
 */

public class User implements Parcelable{
    private int averageToneLevels[];
    private int averageStyleLevels[];
    private int averageSocialLevels[];
    private int averageUtteranceLevels[];
    private int messageCount;
    private String name;
    private String number;

    public User(){
        averageToneLevels = new int[5];
        averageStyleLevels =  new int[3];
        averageSocialLevels = new int[5];
        averageUtteranceLevels = new int[7];
        messageCount = 0;
        name = "";
        number = "";
    }

    public void updateScores(TextBody textBody){
        messageCount++;
        for(int i=0; i<7; i++){
            if(i<4)
                averageStyleLevels[i] = (averageStyleLevels[i] + textBody.getStyleLevel(i)) / messageCount;
            if(i<6){
                averageToneLevels[i] = (averageToneLevels[i] + textBody.getToneLevel(i)) / messageCount;
                averageSocialLevels[i] = (averageSocialLevels[i] + textBody.getSocialLevel(i)) / messageCount;
            }
            averageUtteranceLevels[i] = (averageUtteranceLevels[i] + textBody.getUtteranceLevel(i)) / messageCount;
        }
    }

    public int getAverageToneLevels(int tone){ return averageToneLevels[tone]; }

    public int getAverageStyleLevels(int style){ return averageStyleLevels[style]; }

    public int getAverageSocialLevels(int social){ return averageToneLevels[social]; }

    public int getAverageUtteranceLevels(int utterance){ return averageUtteranceLevels[utterance]; }

    public void setName(String name){
        this.name = name;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getName(){ return name; }

    public String getNumber(){ return number; }

    public void clear(){
        messageCount = 0;
        for(int i=0; i<7; i++){
            if(i<4)
                averageStyleLevels[i] = 0;
            if(i<6){
                averageToneLevels[i] = 0;
                averageSocialLevels[i] = 0;
            }
            averageUtteranceLevels[i] = 0;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.averageToneLevels);
        dest.writeIntArray(this.averageStyleLevels);
        dest.writeIntArray(this.averageSocialLevels);
        dest.writeIntArray(this.averageUtteranceLevels);
        dest.writeInt(this.messageCount);
        dest.writeString(this.name);
        dest.writeString(this.number);
    }

    protected User(Parcel in) {
        this.averageToneLevels = in.createIntArray();
        this.averageStyleLevels = in.createIntArray();
        this.averageSocialLevels = in.createIntArray();
        this.averageUtteranceLevels = in.createIntArray();
        this.messageCount = in.readInt();
        this.name = in.readString();
        this.number = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}