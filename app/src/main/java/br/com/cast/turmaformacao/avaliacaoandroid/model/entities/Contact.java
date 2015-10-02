package br.com.cast.turmaformacao.avaliacaoandroid.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrador on 01/10/2015.
 */
public class Contact implements Parcelable {

    //TODO parcelable map

    private Long id;
    private String name;
    private List<String> phoneList;
    private List<String> emailList;
    private List<SocialNetwork> socialNetworks;
    private Address address;

    /* Getters & Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    /* Parcelable */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.phoneList);
        dest.writeStringList(this.emailList);
        dest.writeTypedList(socialNetworks);
        dest.writeParcelable(this.address, 0);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.phoneList = in.createStringArrayList();
        this.emailList = in.createStringArrayList();
        this.socialNetworks = in.createTypedArrayList(SocialNetwork.CREATOR);
        this.address = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
