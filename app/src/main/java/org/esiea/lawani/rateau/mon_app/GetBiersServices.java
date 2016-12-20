package org.esiea.lawani.rateau.mon_app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;

import static org.esiea.lawani.rateau.mon_app.ThirdActivity.BIERS_UPDATE;

public class GetBiersServices extends IntentService {

    private static final String ACTIONGetAllBeers = "org.esiea.lawani.rateau.mon_app.action.get_all_biers";
    public static final String TAG = "GetAllBeers";



    public GetBiersServices() {
        super("GetBiersServices");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ThirdActivity.BIERS_UPDATE));
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetAllBeers(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(ACTIONGetAllBeers);
        context.startService(intent);

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTIONGetAllBeers.equals(action)) {

                handleActionGetAllBeers();
            }
        }
    }


    private void handleActionGetAllBeers() {
        Log.i(TAG, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;


        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "bieres.json"));
                Log.i(TAG, "Bieres json downloaded!");

            }
        } catch (MalformedURLException e) {
            System.out.println("Malformation URL: ");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("L'autre IO ");

            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream inputStream, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            System.out.println("Exception Input Stream");
            e.printStackTrace();
        }

    }


    }



