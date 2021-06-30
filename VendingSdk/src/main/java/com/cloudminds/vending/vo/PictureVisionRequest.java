package com.cloudminds.vending.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class PictureVisionRequest implements Parcelable {
    private RecognizeType recognizeType;
    private int imageLength;
    private byte[] image;
    private PersonStatus personStatus;
    private RecognizeOption recognizeOption;
    private String extraBody ;
    private String extraType ;
    private Map<String, String> params;
    private List<byte[]> images;

    public PictureVisionRequest() {
        this.personStatus = PersonStatus.COME;
        this.recognizeOption = RecognizeOption.ALWAYS;
    }

    protected PictureVisionRequest(Parcel in) {
        this.recognizeType = RecognizeType.values()[in.readInt()];
        this.imageLength = in.readInt();
        try {
            this.image = new byte[imageLength];
            in.readByteArray(this.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.personStatus = PersonStatus.values()[in.readInt()];
        this.recognizeOption = RecognizeOption.values()[in.readInt()];
        this.extraBody = in.readString();
        this.extraType = in.readString();
        this.params = in.readHashMap(String.class.getClassLoader());
        this.images = in.readArrayList(Byte.class.getClassLoader());
    }

    public static final Creator<PictureVisionRequest> CREATOR = new Creator<PictureVisionRequest>() {
        @Override
        public PictureVisionRequest createFromParcel(Parcel in) {
            return new PictureVisionRequest(in);
        }

        @Override
        public PictureVisionRequest[] newArray(int size) {
            return new PictureVisionRequest[size];
        }
    };

    /**
     * 生成人脸识别需要的请求对象
     * @param image 一张人脸字节数据
     * @return 人脸识别需要的请求对象
     */
    public static PictureVisionRequest createFaceRecognizeInstance(byte[] image) {
        PictureVisionRequest pictureVisionRequest = new PictureVisionRequest();
        pictureVisionRequest.image = image;
        pictureVisionRequest.personStatus = PersonStatus.KEEP;
        pictureVisionRequest.recognizeType = RecognizeType.FACE;
        pictureVisionRequest.recognizeOption = RecognizeOption.ALWAYS;
        return pictureVisionRequest;
    }

    public RecognizeType getRecognizeType() {
        return this.recognizeType;
    }

    public void setRecognizeType(RecognizeType recognizeType) {
        this.recognizeType = recognizeType;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public PersonStatus getPersonStatus() {
        return this.personStatus;
    }

    public void setPersonStatus(PersonStatus personStatus) {
        this.personStatus = personStatus;
    }

    public RecognizeOption getRecognizeOption() {
        return this.recognizeOption;
    }

    public void setRecognizeOption(RecognizeOption recognizeOption) {
        this.recognizeOption = recognizeOption;
    }

    public int getImageLength() {
        return imageLength;
    }

    public void setImageLength(int imageLength) {
        this.imageLength = imageLength;
    }

    public String getExtraType() {
        return extraType;
    }

    public String getExtraBody() {
        return extraBody;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public void setExtraBody(String extraBody) {
        this.extraBody = extraBody;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recognizeType.ordinal());
        dest.writeInt(imageLength);
        dest.writeByteArray(image);
        dest.writeInt(personStatus.ordinal());
        dest.writeInt(recognizeOption.ordinal());
        dest.writeString(extraBody);
        dest.writeString(extraType);
        dest.writeMap(params);
        dest.writeArray(images == null ? null :images.toArray());
    }

    public  enum RecognizeOption {
        NEVER,
        ALWAYS,
        ASK;
    }

    public enum PersonStatus {
        COME,
        KEEP,
        LEAVE;
    }

    public enum RecognizeType {
        OCR,
        MONEY,
        CAR_PLATE,
        OBJECT,
        FACE,
        FACE_ATTR,
        CAPTION,
        CLASSIFY,
        FALL,
        COMPARE,
        VENDING,
        MASK,
        FACE_INFRARED_TRACK,
        DOOR_STATUS,
        VEHICLE,
        GARBAGE_CAN,
        VENDING_DYNAMIC,
        UNKNOWN,
        RGBD,
        LASER_SCAN;
    }
}
