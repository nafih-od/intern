package com.example.educare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question_paper extends AppCompatActivity implements JsonResponse{

    ListView l1;

    String[] subject,year,file,date,qp_id,value;

    SharedPreferences sh;

//    public static String qp,files;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_question_paper);

        l1=findViewById(R.id.list);


        JsonReq jr = new JsonReq();
        jr.json_response= (JsonResponse) Question_paper.this;
        String q = "/view_qp";

        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) throws JSONException {

        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", status);

            if(method.equalsIgnoreCase("view_qp")){
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    subject = new String[ja1.length()];
                    year = new String[ja1.length()];
                    file = new String[ja1.length()];
                    date = new String[ja1.length()];
                    qp_id = new String[ja1.length()];
                    value = new String[ja1.length()];



                    for (int i = 0; i < ja1.length(); i++) {
                        subject[i] = ja1.getJSONObject(i).getString("subject");
                        year[i] = ja1.getJSONObject(i).getString("year");
                        file[i] = ja1.getJSONObject(i).getString("file");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        qp_id[i] = ja1.getJSONObject(i).getString("qp_id");


                        value[i] = "subject : " + subject[i] + "\nyear : " + year[i] + "\nfile : " + file[i]+ "\ndate : " + date[i];

                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);

                    Custom_view_question_paper ar=new Custom_view_question_paper(Question_paper.this,subject,year,file,date,qp_id);
                    l1.setAdapter(ar);


//                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            files=file[position];
//                            startActivity(new Intent(getApplicationContext(),View_question_paper.class));
//
//                        }
//                    });




                }
            }
        }


        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

}