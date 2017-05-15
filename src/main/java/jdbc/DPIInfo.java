package jdbc;

/**
 * Created by yuandengfeng on 2016/11/24.
 */
public class DPIInfo {


    private long Utc_time;

    private String Src_ip;

    private int Src_port;

    private String Des_ip;

    private int Des_port;

    private String Url;

    private String Host;

    private String Connection;

    private String Accept;

    private String User_agent;

    private String Referer;

    private String Apt_encoding;
    public long getUtc_time() {
        return Utc_time;
    }

    public void setUtc_time(long utc_time) {
        Utc_time = utc_time;
    }

    public String getSrc_ip() {
        return Src_ip;
    }

    public void setSrc_ip(String src_ip) {
        Src_ip = src_ip;
    }

    public int getSrc_port() {
        return Src_port;
    }

    public void setSrc_port(int src_port) {
        Src_port = src_port;
    }

    public String getDes_ip() {
        return Des_ip;
    }

    public void setDes_ip(String des_ip) {
        Des_ip = des_ip;
    }

    public int getDes_port() {
        return Des_port;
    }

    public void setDes_port(int des_port) {
        Des_port = des_port;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getConnection() {
        return Connection;
    }

    public void setConnection(String connection) {
        Connection = connection;
    }

    public String getAccept() {
        return Accept;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }

    public String getUser_agent() {
        return User_agent;
    }

    public void setUser_agent(String user_agent) {
        User_agent = user_agent;
    }

    public String getReferer() {
        return Referer;
    }

    public void setReferer(String referer) {
        Referer = referer;
    }

    public String getApt_encoding() {
        return Apt_encoding;
    }

    public void setApt_encoding(String apt_encoding) {
        Apt_encoding = apt_encoding;
    }

    public String getApt_language() {
        return Apt_language;
    }

    public void setApt_language(String apt_language) {
        Apt_language = apt_language;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public String getIf_modified() {
        return If_modified;
    }

    public void setIf_modified(String if_modified) {
        If_modified = if_modified;
    }

    public String getX_apple_c_type() {
        return X_apple_c_type;
    }

    public void setX_apple_c_type(String x_apple_c_type) {
        X_apple_c_type = x_apple_c_type;
    }

    private String Apt_language;

    private String Cookie;

    private String If_modified;

    private String X_apple_c_type;



}
