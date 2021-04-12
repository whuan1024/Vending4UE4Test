package com.cloudminds.vending.roc;


public class LogInfo {
    // 操作描述
    private String operatorId;

    // 操作描述
    private String operatorCode;

    // 操作描述
    private String tenantCode;

    // 操作描述
    private String description;

    // 操作范围
    private String optAffected;
    
    // 操作描述
    private Byte logCategory;

    /**
     * @return the logCategory
     */
    public Byte getLogCategory() {
        return logCategory;
    }

    /**
     * @param logCategory the logCategory to set
     */
    public void setLogCategory(Byte logCategory) {
        this.logCategory = logCategory;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOptAffected() {
        return optAffected;
    }

    public void setOptAffected(String optAffected) {
        this.optAffected = optAffected;
    }
}
