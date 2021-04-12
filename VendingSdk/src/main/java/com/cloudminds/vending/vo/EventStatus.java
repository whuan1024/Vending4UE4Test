package com.cloudminds.vending.vo;

public class EventStatus {

    public static final String ACTION_DOOR_CLOSED = "DOOR_CLOSED";
    public static final String ACTION_RCU_DONE = "RCU_DONE";
    public static final String ACTION_HARIX_DONE = "HARIX_DONE";

    public static final int CODE_NORMAL_CLOSE = 0;
    public static final int CODE_OPEN_TIMEOUT = 1;

    public static final int CODE_RCU_DONE = 0;
    public static final int CODE_CAMERA_ERROR = 1;
    public static final int CODE_WEIGHT_ERROR = 2;
    public static final int CODE_VIDEO_TRANSCODING_ERROR = 3;
    public static final int CODE_VIDEO_TRANSCODING_DONE = 4;
    public static final int CODE_UPLOAD_FILE_ERROR = 5;

    private String eventId;
    private String action;
    private int errCode;
    private String errMsg;
    private String errDetails;
    private String videoUrl;
    private String weightDetails;
    private long reportTime;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrDetails() {
        return errDetails;
    }

    public void setErrDetails(String errDetails) {
        this.errDetails = errDetails;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getWeightDetails() {
        return weightDetails;
    }

    public void setWeightDetails(String weightDetails) {
        this.weightDetails = weightDetails;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    @Override
    public String toString() {
        return "EventStatus{" +
                "eventId='" + eventId + '\'' +
                ", action='" + action + '\'' +
                ", errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                ", errDetails='" + errDetails + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", weightDetails='" + weightDetails + '\'' +
                ", reportTime=" + reportTime +
                '}';
    }
}
