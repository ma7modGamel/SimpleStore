package com.example.simplestore.UtilsProduct;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelProduct implements Parcelable {
    private String nameproduct;
    private int imgProduct;
    private String quantityProduct;
    private String priceProduct;
    private String  id;

    public ModelProduct(String nameproduct, int imgProduct, String quantityProduct, String priceProduct) {
        this.nameproduct = nameproduct;
        this.imgProduct = imgProduct;
        this.quantityProduct = quantityProduct;
        this.priceProduct = priceProduct;

    }
    public ModelProduct() {

    }

    protected ModelProduct(Parcel in) {
        nameproduct = in.readString();
        id = in.readString();
        imgProduct = in.readInt();
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

    public int getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(int imgProduct) {
        this.imgProduct = imgProduct;
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
        dest.writeInt(imgProduct);
        dest.writeString(quantityProduct);
        dest.writeString(priceProduct);
        dest.writeString(id);
    }
}
