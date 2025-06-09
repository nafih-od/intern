package com.example.educare;

import android.app.AlertDialog;
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

public class Teachers extends AppCompatActivity implements JsonResponse{


    ListView l1;



    String[] teacher_name,teacher_phone,teacher_email,teacher_subject,teacher_id,value;

    SharedPreferences sh;

    public static String teacher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teachers);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.list);


        JsonReq jr = new JsonReq();
        jr.json_response= (JsonResponse) Teachers.this;
//        String q = "/view_teachers?id="+sh.getString("log_id","");
        String q = "/view_teachers";

        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) throws JSONException {

        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", status);

            if(method.equalsIgnoreCase("view_teachers")){
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    teacher_name = new String[ja1.length()];
                    teacher_phone = new String[ja1.length()];
                    teacher_email = new String[ja1.length()];
                    teacher_subject = new String[ja1.length()];
                    teacher_id = new String[ja1.length()];
                    value = new String[ja1.length()];



                    for (int i = 0; i < ja1.length(); i++) {
                        teacher_name[i] = ja1.getJSONObject(i).getString("name");
                        teacher_phone[i] = ja1.getJSONObject(i).getString("phone");
                        teacher_email[i] = ja1.getJSONObject(i).getString("email");
                        teacher_subject[i] = ja1.getJSONObject(i).getString("subject");
                        teacher_id[i] = ja1.getJSONObject(i).getString("teacher_id");


                        value[i] = "teacher_name : " + teacher_name[i] + "\nteacher_phone : " + teacher_phone[i] + "\nteacher_email : " + teacher_email[i]+ "\nteacher_subject : " + teacher_subject[i];

                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);

                    Custom_view_teachers ar=new Custom_view_teachers(Teachers.this,teacher_name,teacher_phone,teacher_email,teacher_subject,teacher_id);
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