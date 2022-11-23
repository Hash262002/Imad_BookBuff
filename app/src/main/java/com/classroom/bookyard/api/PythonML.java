package com.classroom.bookyard.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.classroom.bookyard.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PythonML extends AppCompatActivity {
    EditText category,title;
    Button predict;
    TextView result;
    String url = "https://book-recommender-app.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python_ml);
        category = findViewById(R.id.cgpa);
        title = findViewById(R.id.iq);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, (Response.Listener<String>) response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String data = jsonObject.getString("CATEGORY");
                        if(data.equals("1")){
                            result.setText("Success");
                        }else{
                            result.setText("Failed");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PythonML.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map getParams(){
                        Map<String, Object> params = new HashMap<>();
                        params.put("Category",category.getText().toString());
                        params.put("Title",title.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(PythonML.this);
                queue.add(stringRequest);
            }
        });
    }
}