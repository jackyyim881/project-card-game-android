package com.example.cardgame2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CardItem implements Parcelable {

    private int id; // Added ID field
    private String name;
    private String type;
    private int hp;
    private int attack;
    private int imageRes;
    private int backgroundRes;
    private int rarityRes;

    // Updated constructor with ID field
    public CardItem(int id, String name, String type, int hp, int attack, int imageRes,
        int backgroundRes, int rarityRes) {
        this.id = id; // Initialize the id
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.attack = attack;
        this.imageRes = imageRes;
        this.backgroundRes = backgroundRes;
        this.rarityRes = rarityRes;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter methods for other fields
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getBackgroundRes() {
        return backgroundRes;
    }

    public int getRarityRes() {
        return rarityRes;
    }

    // Parcelable implementation remains the same
    protected CardItem(Parcel in) {
        id = in.readInt();  // Read ID from parcel
        name = in.readString();
        type = in.readString();
        hp = in.readInt();
        attack = in.readInt();
        imageRes = in.readInt();
        backgroundRes = in.readInt();
        rarityRes = in.readInt();
    }

    public static final Creator<CardItem> CREATOR = new Creator<CardItem>() {
        @Override
        public CardItem createFromParcel(Parcel in) {
            return new CardItem(in);
        }

        @Override
        public CardItem[] newArray(int size) {
            return new CardItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id); // Write ID to parcel
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(hp);
        dest.writeInt(attack);
        dest.writeInt(imageRes);
        dest.writeInt(backgroundRes);
        dest.writeInt(rarityRes);
    }
}
