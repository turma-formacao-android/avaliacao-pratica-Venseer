package br.com.cast.turmaformacao.avaliacaoandroid.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrador on 01/10/2015.
 */
public class SocialNetwork implements Parcelable {

    private Long id;
    private String socialNetoworkName;
    private String value;

    /* Getters & Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialNetoworkName() {
        return socialNetoworkName;
    }

    public void setSocialNetoworkName(String socialNetoworkName) {
        this.socialNetoworkName = socialNetoworkName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /* Parcelable */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.socialNetoworkName);
        dest.writeString(this.value);
    }

    public SocialNetwork() {
    }

    protected SocialNetwork(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.socialNetoworkName = in.readString();
        this.value = in.readString();
    }

    public static final Creator<SocialNetwork> CREATOR = new Creator<SocialNetwork>() {
        public SocialNetwork createFromParcel(Parcel source) {
            return new SocialNetwork(source);
        }

        public SocialNetwork[] newArray(int size) {
            return new SocialNetwork[size];
        }
    };
}
