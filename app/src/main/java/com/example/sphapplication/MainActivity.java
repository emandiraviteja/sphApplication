package com.example.sphapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {
    public static boolean LOAD_DEFAULT_DATA = true;
    CoordinatorLayout coordinatorLayout;
    private static final String URL_DATA = "https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=100";
    private final SPHService sphService = new SPHService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_item);

        // Manually checking internet connection
        checkConnection();
        if (LOAD_DEFAULT_DATA) {
            try {
                loadUrlData();
            } catch (JSONException e) {
            }
        }
    }

    public void loadUrlData() throws JSONException {
        StringRequest request = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            saveToCache(s);
            showResponse(s);
            }

       }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            if (!showCachedResponse()) {
                showError(error);
            }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void showError(Exception error) {
        if (error instanceof ServerError) {
            showSnack("Server error");
        } else if (error instanceof TimeoutError) {
            showSnack("Timeout error");
        } else if (error instanceof NetworkError) {
            showSnack("Sorry! Not connected to internet");
        } else if (error instanceof ParseError) {
            showSnack("Parse error");
        } else if (error instanceof IOException) {
            showSnack("File error");
        } else if (error instanceof JSONException){
            showSnack("Data parse error");
        } else {
            showSnack(error.getMessage());
        }
    }

    boolean showCachedResponse() {
        try {
            String response = readFromCache();
            return showResponse(response);
        } catch (IOException e) {
            showError(e);
            checkConnection();
        }
        return false;
    }

    public boolean showResponse(String response){
        try {
            TreeMap<String, YearListItem> stringBigDecimalTreeMap = this.sphService.parseJSON(response);
            displayUI(stringBigDecimalTreeMap);
            return true;
        } catch (JSONException e) {
            showError(e);
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    private void displayUI(TreeMap<String, YearListItem> stringBigDecimalTreeMap) {

        ArrayList<YearListItem> yearList = new ArrayList<>();

        for(Map.Entry<String, YearListItem> yearDataItem: stringBigDecimalTreeMap.entrySet()) {
            yearList.add(yearDataItem.getValue());
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(new YearListAdapter(this,yearList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    //Json data storing on file
    void saveToCache(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("volumedata.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
        }
    }

    //Retreving Json data from storing file
    private String readFromCache() throws IOException {
        InputStream inputStream = this.openFileInput("volumedata.txt");

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            inputStream.close();
            return stringBuilder.toString();
        }
        return null;
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected)
        showSnack("Sorry! Not connected to internet");
    }

    // Showing the status in Snackbar
    private void showSnack(String message) {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
    }
}

