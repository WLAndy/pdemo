package com.zy.manage.im.enmu;

public enum User {
    CREATE("https://api.netease.im/nimserver/user/create.action"),UPDATE("https://api.netease.im/nimserver/user/update.action"),
    REFRESHTOKEN("https://api.netease.im/nimserver/user/refreshToken.action"),BLOCK("https://api.netease.im/nimserver/user/block.action"),
    UNBLOCK("https://api.netease.im/nimserver/user/unblock.action");

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    User(String url) {
        this.url = url;
    }
}
