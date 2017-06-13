package com.speach.speach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) throws JSONException {
        final Intent intent = new Intent(this, DisplayMessageActivity.class);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final String message = editText.getText().toString();

        String url = "http://neonbevz.pythonanywhere.com/app/getwords";
        JSONObject obj = new JSONObject();
        obj.put("text", message);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            intent.putExtra("request", message);
                            intent.putExtra("response", response.get("your_text").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("hello", error.toString());
                    }
                });
        queue.add(jsObjRequest);
    }
}
