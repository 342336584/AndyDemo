package com.example.andy.andydemo.sys;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Version {
    private String version_name;
    private String api;
    private String version_code;
    private String key;

    @Generated(hash = 987265284)
    public Version(String version_name, String api, String version_code,
            String key) {
        this.version_name = version_name;
        this.api = api;
        this.version_code = version_code;
        this.key = key;
    }

    @Generated(hash = 1433053919)
    public Version() {
    }

    public String[] toArray() {
        return new String[]{version_name, api, version_code, key};
    }

    public String getVersion_name() {
        return this.version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getApi() {
        return this.api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getVersion_code() {
        return this.version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
