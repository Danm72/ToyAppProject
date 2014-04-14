package com.dan.toyapp.task;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danmalone
 * Date: 13/07/2013
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class PostTask extends AsyncTask<String, String, String> {
    static String URL = "";
    private String authResponse = null;

    private String response = null;
    private List<NameValuePair> parameters = null;

    public PostTask(List<NameValuePair> data, String url) {
        parameters = data;
        if (url != null && url.length() > 5) {
            URL = url;
        }
    }

    //http://10.79.39.124:8080/AgileBrain/j_spring_security_check?j_password=agile&j_username=admin
    @Override
    protected String doInBackground(String... params) {

        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(URL);
        HttpPost authenticatePost = new HttpPost("http://dev.agilepayments.eu:8081/AgileBrain-0.1/login/auth");


//        String authorizationString = "Basic " + Base64.encodeToString(("merchant" + ":" + "merchant").getBytes(), Base64.DEFAULT); //this line is diffe
//        authorizationString.replace("\n", "");


        try {

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("j_username:", "admin"));
            urlParameters.add(new BasicNameValuePair("j_password", "agile"));

            authenticatePost.setEntity(new UrlEncodedFormEntity(urlParameters));
            post.setEntity(new UrlEncodedFormEntity(parameters));

//            post.setHeader("Authorization", authorizationString);

            authResponse = client.execute(authenticatePost, responseHandler);
            response = client.execute(post, responseHandler);


            System.out.println("response :" + authResponse);
            System.out.println("response :" + response);

            Log.d("AgilePayments-POST", "\nSending 'POST' request to URL : " + URL);
//            Log.d("AgilePayments-POST", "Post parameters : " + post.getParams().);
            Log.d("AgilePayments-POST", "Response Code : " +
                    response + authResponse);

//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//
//            StringBuffer result = new StringBuffer();
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//
//            Log.d("AgilePayments-POST", result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
