package com.cloudminds.vending.roc;

public class RodHeader {

    private String tenantId;
    private String robotId;
    private String robotType;
    private String userId;
    private String uploadAddress;

    private static volatile RodHeader mInstance;

    public static RodHeader getInstance() {
        if (mInstance == null) {
            synchronized (RodHeader.class) {
                if (mInstance == null) {
                    mInstance = new RodHeader();
                }
            }
        }
        return mInstance;
    }

    private RodHeader() {

    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotType() {
        return robotType;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    @Override
    public String toString() {
        return "RodHeader{" +
                "tenantId='" + tenantId + '\'' +
                ", robotId='" + robotId + '\'' +
                ", robotType='" + robotType + '\'' +
                ", userId='" + userId + '\'' +
                ", uploadAddress='" + uploadAddress + '\'' +
                '}';
    }
}
