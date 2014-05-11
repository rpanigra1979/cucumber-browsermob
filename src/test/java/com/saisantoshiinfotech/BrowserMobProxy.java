package com.saisantoshiinfotech;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;


public class BrowserMobProxy {

    private static String getFormattedEndpoint(String endPoint) {
        if (endPoint.endsWith("/")) {
            endPoint.subSequence(0, endPoint.length() - 2);
        }
        return endPoint;
    }


    public static void createHar(String endPoint) throws ClientProtocolException, IOException {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            HttpPut httpput = new HttpPut(getFormattedEndpoint(endPoint) + "/har");

            HttpResponse response = httpclient.execute(httpput);

            System.out.println(response.toString());

            for (Header header : response.getAllHeaders()) {
                System.out.println(header);
            }
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public void createNewPageForHar(String endPoint, String pageRef) throws ClientProtocolException, IOException {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            HttpPut httpput = new HttpPut(getFormattedEndpoint(endPoint) + "/har/pageRef?pageRef=" + pageRef);

            HttpResponse response = httpclient.execute(httpput);

            System.out.println(response.toString());

            for (Header header : response.getAllHeaders()) {
                System.out.println(header);
            }
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static void deleteHar(String endPoint) {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpDelete httpdelete = new HttpDelete(getFormattedEndpoint(endPoint));
            httpdelete.addHeader("Accept", "none");

            //Delete the asset
            HttpResponse response = httpclient.execute(httpdelete);
        } catch (IOException e) {

        } finally {
            httpclient.getConnectionManager().shutdown();

        }
    }

    public static String getHar(String endPoint) throws HttpException, IOException, ParserConfigurationException, SAXException {

        String responseBody = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            HttpGet httpget = new HttpGet(getFormattedEndpoint(endPoint) + "/har");

            HttpResponse response = httpclient.execute(httpget);


            if (!response.toString().contains("200 OK")) {
                throw new Error("Damn, couldn't get har!");
            }

            HttpEntity entity = response.getEntity();


            ResponseHandler<String> handler = new BasicResponseHandler();

            responseBody = handler.handleResponse(response);

//        	InputStream instream = entity.getContent();
//        	try {
//
//        		BufferedReader reader = new BufferedReader(
//        				new InputStreamReader(instream));
//        		har = reader.readLine();
////        		System.out.println(har);
//        	} catch (IOException ex) {
//        		throw ex;
//
//        	} catch (RuntimeException ex) {
//        		throw ex;
//
//        	}
//
//            EntityUtils.consume(entity);
        } catch (IOException e) {

        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return responseBody;
    }


    public static String getSpecificPageHar(String endPoint, String pageRef) throws HttpException, IOException, ParserConfigurationException, SAXException {

        String har = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            HttpGet httpget = new HttpGet(getFormattedEndpoint(endPoint));

            HttpResponse response = httpclient.execute(httpget);

            System.out.println(response.toString());

//            if (!response.toString().contains("200 OK")){
//            	throw new Error("Damn, couldn't get har!");
//            }

//            for (Header header: response.getAllHeaders()) {
//            	System.out.println(header);
//             }

            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            try {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(instream));
                har = reader.readLine();
//        		System.out.println(har);
            } catch (IOException ex) {
                throw ex;

            } catch (RuntimeException ex) {
                throw ex;

            }


            EntityUtils.consume(entity);
        } catch (IOException e) {

        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return har;
    }

    public String get(String endPoint, Map<String, String> header) throws HttpException, IOException, ParserConfigurationException, SAXException {

        String responseBody = "";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {

            HttpGet httpget = new HttpGet(getFormattedEndpoint(endPoint));

            Iterator<Map.Entry<String, String>> entries = header.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                httpget.addHeader(entry.getKey(), entry.getValue());
            }

            HttpResponse response = httpclient.execute(httpget);


//            if (!response.toString().contains("200 OK")){
//            	throw new Error("Get request failed with an error!");
//            }

            ResponseHandler<String> handler = new BasicResponseHandler();

            responseBody = handler.handleResponse(response);

        } catch (IOException e) {

        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return responseBody;
    }

    public String getWithCurrentBrowserSession(String url, String jSessionId, Map<String, String> header) throws ClientProtocolException, IOException, TransformerException, ParserConfigurationException, SAXException, HttpException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String responseBody = null;

        try {

            HttpGet httpget = new HttpGet(getFormattedEndpoint(url));

            //Add headers
            Iterator<Map.Entry<String, String>> entries = header.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                httpget.addHeader(entry.getKey(), entry.getValue());
            }

            // Setup the JSessionId
            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jSessionId);

            cookie.setDomain(url.subSequence(7, url.length()).toString());
            System.out.println("The string is: " + url.subSequence(7, url.length()).toString());
            cookie.setPath("/");

            cookieStore.addCookie(cookie);
            httpclient.setCookieStore(cookieStore);

            HttpResponse response = httpclient.execute(httpget);
            ResponseHandler<String> handler = new BasicResponseHandler();

            responseBody = handler.handleResponse(response);

            System.out.println("The response is: " + response.toString());
            if (!response.toString().contains("HTTP/1.1 200 OK")) {
                throw new Error("Couldn't create session");
            }

        } catch (HttpHostConnectException h) {
            System.out.println("Http exception!");
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

        return responseBody;
    }

    public static String getUrl(String endPoint) throws HttpException, IOException, ParserConfigurationException, SAXException {


        String responseBody = null;

        DefaultHttpClient httpclient = new DefaultHttpClient();

        try {

            HttpGet httpget = new HttpGet(getFormattedEndpoint(endPoint));

            HttpResponse response = httpclient.execute(httpget);

            if (!response.toString().contains("200 OK")) {
                throw new Error("Damn, wrong error code!");
            }
        } catch (IOException e) {
        } finally {
            httpclient.getConnectionManager().shutdown();
        }


        return responseBody;

    }

}
