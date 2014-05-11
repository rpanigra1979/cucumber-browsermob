package HTTPArchive;

import java.util.List;


public class Request {

    private String method;
    private String url;
    private String httpVersion;
    private List<Object> cookies;
    private List<Object> headers;
    private List<QueryString> queryString;
    private Integer headersSize;
    private Integer bodySize;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public List<Object> getCookies() {
        return cookies;
    }

    public void setCookies(List<Object> cookies) {
        this.cookies = cookies;
    }

    public List<Object> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }

    public List<QueryString> getQueryString() {
        return queryString;
    }

    public void setQueryString(List<QueryString> queryString) {
        this.queryString = queryString;
    }

    public Integer getHeadersSize() {
        return headersSize;
    }

    public void setHeadersSize(Integer headersSize) {
        this.headersSize = headersSize;
    }

    public Integer getBodySize() {
        return bodySize;
    }

    public void setBodySize(Integer bodySize) {
        this.bodySize = bodySize;
    }
}
