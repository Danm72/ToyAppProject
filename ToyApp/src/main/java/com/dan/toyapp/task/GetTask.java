package com.dan.toyapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dan.toyapp.task.taskinterfaces.OnTaskCompleted;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danmalone
 * Date: 13/07/2013
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class GetTask extends AsyncTask<String, String, String> {
    //    static String URL = "";
    private static String TAG = "GetTask";
    private String[] params;
    Map<String, Object> reponseMap = new HashMap<String, Object>();
    JSONParser parser = new JSONParser();
    OnTaskCompleted listener;

    Context context;

    public GetTask(Context context, OnTaskCompleted listener) {
//        if (url != null && url.length() > 5) {
//            URL = url;
        this.context = context;
        this.listener = listener;
//        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected String doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();

        this.params = params;
        for (String string : params) {
            HttpClient client = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(string);

            try {
                Log.i(TAG, "Starting get");
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                Log.i(TAG, statusCode + "");

                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();


                    Reader reader = new BufferedReader(new InputStreamReader(content));
                    reponseMap.put(string, parser.parse(reader));

                } else {
                    Log.e(GetTask.class.toString(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i(TAG + "Builder result", builder.toString());
//            listener.onTaskCompleted(getJSONObject, params);

        }
        return builder.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onTaskCompleted(reponseMap);

    }

    public static void createGetRequest(Context context, OnTaskCompleted listener, String... URL) {
        GetTask task = new GetTask(context, listener);
        task.execute(URL);
    }
}
