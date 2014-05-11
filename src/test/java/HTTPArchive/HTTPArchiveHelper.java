package HTTPArchive;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class HTTPArchiveHelper {

//		private static String pageRefOfHar = "testsai1";

    public List<Request> getRequestListForGoogleAnalyticsUrl(String harObject, String pageRefOfHar, String url) throws FileNotFoundException, MalformedURLException {

        if (harObject == null || harObject == "") {
            throw new IllegalArgumentException("Have you started the proxy server and created a HAR?");
        }

        Gson gson = new Gson();

        HTTPArchive harGson = gson.fromJson(harObject, HTTPArchive.class);

        //Get appropriate entry from Entries list based on pageRef parameter
        List<Entries> entries = harGson.getLog().getEntries();
        List<Request> googleAnalyticsRequestList = new ArrayList<Request>();

        for (Entries entry : entries) {
            String page = entry.getPageRef();
            Request request = entry.getRequest();

            //Only bother about google analytics query strings
            if (pageRefOfHar.equals(page) && request.getUrl().contains(url)) {

                googleAnalyticsRequestList.add(request);
            }
        }


        return googleAnalyticsRequestList;

    }

    public List<String> getValueOfParameter(String parameterName, List<Request> requestList) {

        List<String> parameterValueList = new ArrayList<String>();

        for (Request request : requestList) {

            List<QueryString> queryStringList = request.getQueryString();

            for (QueryString queryString : queryStringList) {
                String parameter = queryString.getName();

                if (parameterName.equals(parameter)) {
                    parameterValueList.add(queryString.getValue());
                }

            }
        }

        return parameterValueList;
    }

}

