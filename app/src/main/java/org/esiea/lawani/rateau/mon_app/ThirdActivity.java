package org.esiea.lawani.rateau.mon_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button boutonRetour = (Button) findViewById(R.id.retour1);

        GetBiersServices.startActionGetAllBeers(this);


        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BIERS_UPDATE));


        JSONArray arrayFileJSON = this.getBiersFromFile();
        ListView list = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> tableau = new ArrayAdapter<String>(list.getContext(),
                R.layout.ma_liste);
        System.out.println("Taille JSON: " + arrayFileJSON.length());
        for (int i = 0; i < arrayFileJSON.length(); i++) {
            tableau.add("coucou" + i);
        }
        list.setAdapter(tableau);


        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.retour1) {
                    Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                    startActivity(intent);

           }
            }
        });
    }
    public JSONArray getBiersFromFile(){

        try{
            InputStream is= new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer= new byte[is.available()];
            is.read(buffer);
            System.out.println("LAAAAA2"+buffer);
            is.close();
            System.out.println("LAAAAA");
            return new JSONArray(new String(buffer,"UTF-8"));

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("PB 1");
            return new JSONArray();

        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("PB 2");
            return new JSONArray();
        }
    }





        public static final String BIERS_UPDATE="com.octip.cours.inf4042_11.BIERS_UPDATE";

    public class BierUpdate extends BroadcastReceiver {
        private static final String TAG ="OnReceive";



        @Override
        public void onReceive(Context context, Intent intent){

            if (intent.getAction().equals(TAG)) {

                Log.d(TAG, intent.getAction());

            }
        }




        }





    }

