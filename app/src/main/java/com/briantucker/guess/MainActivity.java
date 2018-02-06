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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int pickedCeleb = 0;






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


            //splitting the page at th is div so that unwanted images at the bottom aren't selected
            String[] splitResult = result.split("<div class=\"sidebarContainer\">");


            //splitResult[0] because it's choosing the first part of the split
            //This one finds the URLs
            Pattern p = Pattern.compile("src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while(m.find()) {
                celebUrls.add(m.group(1));
            }

            //This one finds the names
            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while(m.find()) {
                celebNames.add(m.group(1));
            }


            //Creating the random numeber to pick random celebs
            Random random = new Random();
            pickedCeleb = random.nextInt(celebUrls.size());

        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
