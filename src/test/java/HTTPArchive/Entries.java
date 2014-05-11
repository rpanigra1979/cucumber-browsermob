package HTTPArchive;

public class Entries {

    private String pageref;
    private String startedDateTime;
    private Integer time;
    private Request request;
    private Object response;
    private Object cache;
    private Object timings;
    private String serverIPAddress;

    public String getPageRef() {
        return pageref;
    }

    public void setPageRef(String pageRef) {
        this.pageref = pageRef;
    }

    public String getStartedDateTime() {
        return startedDateTime;
    }

    public void setStartedDateTime(String startedDateTime) {
        this.startedDateTime = startedDateTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Object getCache() {
        return cache;
    }

    public void setCache(Object cache) {
        this.cache = cache;
    }

    public Object getTimings() {
        return timings;
    }

    public void setTimings(Object timings) {
        this.timings = timings;
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public void setServerIPAddress(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
    }
}

