package HTTPArchive;

import java.util.List;

public class Log {

    private String version;
    private Object creator;
    private Object browser;
    private List<Object> pages;
    private List<Entries> entries;
    private String comment;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Object> getPages() {
        return pages;
    }

    public void setPages(List<Object> pages) {
        this.pages = pages;
    }

    public List<Entries> getEntries() {
        return entries;
    }

    public void setEntries(List<Entries> entries) {
        this.entries = entries;
    }

    public Object getCreator() {
        return creator;
    }

    public void setCreator(Object creator) {
        this.creator = creator;
    }

    public Object getBrowser() {
        return browser;
    }

    public void setBrowser(Object browser) {
        this.browser = browser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
