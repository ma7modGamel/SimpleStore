package com.example.simplestore.UtilsProduct;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ModelProduct implements  Parcelable{
   public   String nameproduct;
  public    String email;
  public    String phoneNumber;
  public    String quantityProduct;
   public   String priceProduct;
    public String  id;
    public String uriImg;

    public ModelProduct(String nameproduct, String email, String phoneNumber, String quantityProduct, String priceProduct, String id, String uriImg) {
        this.nameproduct = nameproduct;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.quantityProduct = quantityProduct;
        this.priceProduct = priceProduct;
        this.id = id;
        this.uriImg = uriImg;
    }

    public static final Creator<ModelProduct> CREATOR = new Creator<ModelProduct>() {
        @Override
        public ModelProduct createFromParcel(Parcel in) {
            return new ModelProduct(in);
        }

        @Override
        public ModelProduct[] newArray(int size) {
            return new ModelProduct[size];
        }
    };


    public ModelProduct(String uriImg) {
        this.uriImg = uriImg;
    }



    public ModelProduct() {
    }

    public ModelProduct(Parcel in) {
        nameproduct = in.readString();
        id = in.readString();

        quantityProduct = in.readString();
        priceProduct = in.readString();
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(String quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUriImg() {
        return uriImg;
    }

    public void setUriImg(String uriImg) {
        this.uriImg = uriImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameproduct);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(quantityProduct);
        dest.writeString(priceProduct);
        dest.writeString(id);
        dest.writeString(uriImg);
    }
}
