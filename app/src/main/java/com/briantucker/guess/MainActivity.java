package com.briantucker.guess;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public class DownloadContent extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;
            }
            catch(Exception e){

                e.printStackTrace();
            }
            return null;
        }
    }



    public void celebPick(View view){



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadContent dwnld = new DownloadContent();
        String result = null;

        try {
            result = dwnld.execute("http://www.posh24.se/kandisar").get();
            Log.i("Content of URL", result);
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
