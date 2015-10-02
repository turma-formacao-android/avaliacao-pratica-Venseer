package br.com.cast.turmaformacao.avaliacaoandroid.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrador on 01/10/2015.
 */
public class Address implements Parcelable {
    @JsonIgnore
    private Long  id;
    @JsonIgnore
    private Long  contactId;
    @JsonProperty("cep")
    private String zipCode;
    @JsonProperty("tipoDeLogradouro")
    private String addressType;
    @JsonProperty("logradouro")
    private String address;
    @JsonProperty("bairro")
    private String neighborhood;
    @JsonProperty("cidade")
    private String city;
    @JsonProperty("estado")
    private String state;

    /* Getters & Setters */
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    /* Parcelable */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.contactId);
        dest.writeString(this.zipCode);
        dest.writeString(this.addressType);
        dest.writeString(this.address);
        dest.writeString(this.neighborhood);
        dest.writeString(this.city);
        dest.writeString(this.state);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.contactId = (Long) in.readValue(Long.class.getClassLoader());
        this.zipCode = in.readString();
        this.addressType = in.readString();
        this.address = in.readString();
        this.neighborhood = in.readString();
        this.city = in.readString();
        this.state = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
