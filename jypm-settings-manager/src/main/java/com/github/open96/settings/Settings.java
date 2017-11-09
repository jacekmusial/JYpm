package com.github.open96.settings;

public class Settings {
    private String youtubeDlExecutable;
    private OS_TYPE osType;
    private String fileManagerCommand;
    private boolean notificationPolicy;
    private String youtubeDlVersion;
    private String runtimeVersion;

    public Settings() {
        youtubeDlExecutable = "";
        fileManagerCommand = "";
        notificationPolicy = true;
        youtubeDlVersion = "";
    }

    String getYoutubeDlExecutable() {
        return youtubeDlExecutable;
    }

    void setYoutubeDlExecutable(String youtubeDlExecutable) {
        this.youtubeDlExecutable = youtubeDlExecutable;
    }

    OS_TYPE getOsType() {
        return osType;
    }

    void setOsType(OS_TYPE osType) {
        this.osType = osType;
    }

    String getFileManagerCommand() {
        return fileManagerCommand;
    }

    void setFileManagerCommand(String fileManagerCommand) {
        this.fileManagerCommand = fileManagerCommand;
    }

    boolean getNotificationPolicy() {
        return notificationPolicy;
    }

    void setNotificationPolicy(boolean notificationPolicy) {
        this.notificationPolicy = notificationPolicy;
    }

    String getYoutubeDlVersion() {
        return youtubeDlVersion;
    }

    void setYoutubeDlVersion(String youtubeDlVersion) {
        this.youtubeDlVersion = youtubeDlVersion;
    }

    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    public void setRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }
}
