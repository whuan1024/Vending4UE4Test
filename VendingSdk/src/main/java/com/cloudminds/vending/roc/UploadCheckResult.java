package com.cloudminds.vending.roc;

/**
 * @author godway
 *
 */
public class UploadCheckResult {
    // md5
    private String fileMd5;
    // 文件总大小
    private long fileSize = 0L;
    // 文件当前位置
    private long filePos = 0L;
    /**
     * @return the fileMd5
     */
    public String getFileMd5() {
        return fileMd5;
    }
    /**
     * @param fileMd5 the fileMd5 to set
     */
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }
    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    /**
     * @return the filePos
     */
    public long getFilePos() {
        return filePos;
    }
    /**
     * @param filePos the filePos to set
     */
    public void setFilePos(long filePos) {
        this.filePos = filePos;
    } 
}
