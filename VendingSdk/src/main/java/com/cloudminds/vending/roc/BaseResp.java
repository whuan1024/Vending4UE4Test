package com.cloudminds.vending.roc;


import java.util.List;

public class BaseResp {
    // 平台标识
    private String sys = Constant.ROC_SYS;

    // 返回码
    private int code;

    // 提示消息
    private String messages;

    // 响应数据
    private Object data;

    // 异常集
    private List<?> errors;

    // 操作范围
    private LogInfo logInfo;
    
    // 0不处理（默认），1内部窗口重定向，2浏览器重定向
    private int action = 0;
    
    // action=1、2时为重定向绝对地址
    private String script = "";

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
    }

    /**
     * @return the script
     */
    public String getScript() {
        return script;
    }

    /**
     * @param script the script to set
     */
    public void setScript(String script) {
        this.script = script;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<?> getErrors() {
        return errors;
    }

    public void setErrors(List<?> errors) {
        this.errors = errors;
    }

    public void setDefaultSuccess() {
        this.code = Constant.CODE_OPERATE_SUCCESS;
        this.messages = "OK";
    }

    public void setDefaultError() {
        this.code = Constant.CODE_OPERATE_FAIL;
        this.messages = "FAIL";
    }

    public BaseResp success(Object data) {
        this.data = data;
        this.messages = "OK";
        this.code = Constant.CODE_OPERATE_SUCCESS;
        return this;
    }

    public BaseResp failure() {
        this.data = null;
        this.code = Constant.CODE_OPERATE_FAIL;
        this.messages = "FAIL";
        return this;
    }

    public BaseResp exception(Exception e) {
        this.data = null;
        this.code = Constant.CODE_OPERATE_FAIL;
        this.messages = "FAIL:" + e.getMessage();
        return this;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}