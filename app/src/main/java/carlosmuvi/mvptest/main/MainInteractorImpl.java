package carlosmuvi.mvptest.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import carlosmuvi.mvptest.entities.Beer;

/**
 * Created by Carlos on 24/11/2014.
 */
public class MainInteractorImpl implements MainInteractor {

    Gson gson;

    @Override
    public void getBeers(final OnBeersFinishedListener listener) {

        Thread background = new Thread(new Runnable() {

            private final HttpClient client = new DefaultHttpClient();
            private String URL = "http://ontariobeerapi.ca:80/beers/";

            public void run() {
                try {
                    gson = new Gson();
                    String responseString = "";
                    HttpGet httpget = new HttpGet(URL);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    responseString = client.execute(httpget, responseHandler);

                    Type listBeerType = new TypeToken<List<Beer>>() {
                    }.getType();
                    List<Beer> bc = gson.fromJson(responseString, listBeerType);

                    threadMsgSuccess(bc);

                } catch (Throwable t) {
                    threadMsgError("error connecting with server");

                }
            }

            private void threadMsgSuccess(List<Beer> bc) {

                if (null != bc) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putSerializable("beerList", (Serializable) bc);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private void threadMsgError(String error) {

                if (null != error & !error.isEmpty()) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("error", error);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            // Define the Handler that receives messages from the thread and update the progress
            private final Handler handler = new Handler() {

                public void handleMessage(Message msg) {

                    String error = msg.getData().getString("error", "");
                    List<Beer> bc = (List<Beer>) msg.getData().getSerializable("beerList");

                    if (error.isEmpty() && (null != bc)) {
                        listener.onBeersSuccess(bc);
                    } else {
                        listener.onBeersError(error);
                    }
                }
            };

        });
        // Start Thread
        background.start();  //After call start method thread called run Method
    }

}



