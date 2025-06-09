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

public class Lecture_note extends AppCompatActivity implements JsonResponse{

    ListView l1;

    String[] subject,teacher_name,title,file,summarize,date,value,note_id,sem;

    SharedPreferences sh;

    public static String note,files;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_lecture_note);

        l1=findViewById(R.id.list);


        JsonReq jr = new JsonReq();
        jr.json_response= (JsonResponse) Lecture_note.this;
        String q = "/view_note";

        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) throws JSONException {

        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", status);

            if(method.equalsIgnoreCase("view_note")){
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    subject = new String[ja1.length()];
                    teacher_name = new String[ja1.length()];
                    title = new String[ja1.length()];
                    file = new String[ja1.length()];
                    summarize = new String[ja1.length()];
                    date = new String[ja1.length()];
                    note_id = new String[ja1.length()];
                    sem = new String[ja1.length()];


                    value = new String[ja1.length()];



                    for (int i = 0; i < ja1.length(); i++) {
                        subject[i] = ja1.getJSONObject(i).getString("subject");
                        teacher_name[i] = ja1.getJSONObject(i).getString("name");
                        title[i] = ja1.getJSONObject(i).getString("title");
                        file[i] = ja1.getJSONObject(i).getString("file");
                        summarize[i] = ja1.getJSONObject(i).getString("summarization");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        note_id[i] = ja1.getJSONObject(i).getString("note_id");
                        sem[i] = ja1.getJSONObject(i).getString("sem");



                        value[i] = "subject : " + subject[i]  + "\nfile : " + file[i]+ "\ndate : " + date[i] +"Summarization : " + summarize[i] ;

                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);

                    Custom_view_lecture_note ar=new Custom_view_lecture_note(Lecture_note.this,subject,teacher_name,title,file,summarize,date,note_id,sem);
                    l1.setAdapter(ar);


//                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            files=file[position];
//                            startActivity(new Intent(getApplicationContext(),View_uploaded_abstract.class));
//
//                        }
//                    });

//


                }
            }
        }


        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}