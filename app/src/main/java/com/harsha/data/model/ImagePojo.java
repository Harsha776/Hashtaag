package com.harsha.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ImagePojo")
public class ImagePojo implements Parcelable {
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("image")
    private byte[] image;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public ImagePojo() {
    }

    protected ImagePojo(Parcel in) {
        image = in.createByteArray();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(image);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImagePojo> CREATOR = new Creator<ImagePojo>() {
        @Override
        public ImagePojo createFromParcel(Parcel in) {
            return new ImagePojo(in);
        }

        @Override
        public ImagePojo[] newArray(int size) {
            return new ImagePojo[size];
        }
    };

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
