package com.example.simplestore.UtilsProduct;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ModelProduct implements Parcelable {
    private String nameproduct;

    private String quantityProduct;
    private String priceProduct;
    private String  id;
    private String uriImg;

    public ModelProduct(String link) {
    }


    public String getUriImg() {
        return uriImg;
    }

    public void setUriImg(String uriImg) {
        this.uriImg = uriImg;
    }

    public ModelProduct(String nameproduct, String quantityProduct, String priceProduct, String id, String uriImg) {
        this.nameproduct = nameproduct;

        this.quantityProduct = quantityProduct;
        this.priceProduct = priceProduct;

        this.id = id;
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

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameproduct);
        dest.writeString(quantityProduct);
        dest.writeString(priceProduct);
        dest.writeString(id);
    }
}
