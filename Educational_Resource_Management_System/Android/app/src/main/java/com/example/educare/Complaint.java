package com.example.educare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Complaint extends AppCompatActivity implements JsonResponse{



    EditText e1;
    ListView l1;

    String description;
    Button b1;

    SharedPreferences sh;

    String[] descriptions,reply,date,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_complaint);

        e1=findViewById(R.id.description);
        b1=findViewById(R.id.send);
        l1=findViewById(R.id.list);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=e1.getText().toString();


                JsonReq jr = new JsonReq();
                jr.json_response= (JsonResponse) Complaint.this;
                String q = "/complaint?description="+description+"&id="+Login.loginid;
                jr.execute(q);
            }
        });

        JsonReq jr = new JsonReq();
        jr.json_response= (JsonResponse) Complaint.this;
        String q = "/view_complaint?id="+Login.loginid;
        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (method.equalsIgnoreCase("complaint")) {
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Complaint Sent..!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Complaint.class));

                }
            }
            if(method.equalsIgnoreCase("view_complaint")){
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    descriptions = new String[ja1.length()];
                    reply = new String[ja1.length()];
                    date = new String[ja1.length()];
                    value = new String[ja1.length()];



                    for (int i = 0; i < ja1.length(); i++) {
                        descriptions[i] = ja1.getJSONObject(i).getString("complaint");
                        reply[i] = ja1.getJSONObject(i).getString("reply");
                        date[i] = ja1.getJSONObject(i).getString("date");


                        value[i] = "description : " + descriptions[i] + "\nreply : " + reply[i]+ "\ndate : " + date[i];

                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);

                    Custom_view_complaint ar=new Custom_view_complaint(Complaint.this,descriptions,reply,date);
                    l1.setAdapter(ar);



                }
            }
        }


        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}