package sample;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;


public class HTTP {
    String URI;
    String inputVenueID;

    public HTTP(String URI, String inputVenueID) {
        this.URI = URI;
        this.inputVenueID = inputVenueID;
    }

    public String httpGetRequest() throws Exception {
        String fullURI = URI + ":9479/metadataManager/session/venue/" + inputVenueID;
        URL url = new URL(fullURI);
        return httpGetRequester(url);

    }

    public String httpPatchRequest(String sessionID, String status) throws Exception {
        String fullURI = URI + ":9479/metadataManager/session/" + sessionID;
        httpPatchRequester(fullURI,status);
        return "YEY";
    }

    private String httpGetRequester(URL url) throws Exception {
        //HTTP authorisation encoding
        String encoding = Base64.getEncoder().encodeToString(("hawkeye:veCwneGRZb2u7JA").getBytes("UTF-8"));

        //Establish HHTP connection & Parameters
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encoding);

        //Read in the information returned from GET request
        InputStream content = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = in.readLine()) != null) {
//            System.out.println(line);
            return line;
        }
        return "null";
    }

    private void httpPatchRequester(String url, String status) {

        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                new AuthScope("metadata.baseball.data.hawkeyeinnovations.com", 9479),
                new UsernamePasswordCredentials("hawkeye", "veCwneGRZb2u7JA".toCharArray()));

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(provider).build()) {

            System.out.println("URL is: " + url);
            HttpPatch patch = new HttpPatch(url);
            String string = "{\r\n    \"status\":  \"" + status + "\"\r\n}";
            patch.setEntity(new StringEntity(string, StandardCharsets.UTF_8));
            patch.setHeader("Content-Type", "application/json");
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            System.out.println("Executing request " + patch.getRequestUri());
            CloseableHttpResponse response = httpclient.execute(patch);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getEntity());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}

