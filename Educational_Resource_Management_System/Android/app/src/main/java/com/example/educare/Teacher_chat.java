package com.example.educare;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;


import android.R.string;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Teacher_chat extends Activity  implements JsonResponse
{
    EditText ed1;
    ImageView b1,b2;
    String chat;
    ListView l1;
    ImageView iv10;
    String method1="",method2="",method3="";
    String namespace="http://Dbcon/";
    String url="";
    String[] aid,aname,r_id1,msg1;
    String[] msgid,s_id,r_id,message,date,re;

    String aid1,aname1,msg;
    SharedPreferences sh;
    String soapaction="";
    String contentcheck,chattedby;

    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chat);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "");
        ed1=(EditText)findViewById(R.id.editText1);
        l1=(ListView)findViewById(R.id.listView1);
        b1=(ImageView)findViewById(R.id.imageView1);
        b2=(ImageView)findViewById(R.id.details);

        //Toast.makeText(getApplicationContext(), "hii1", Toast.LENGTH_SHORT).show();

        startTimer();
        getChats();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Teacher_chat.this); // Use the correct context here
                builder.setTitle("  ")
                        .setMessage("Are you sure you want to Exit?");
                builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "Yes" button click event
//						startActivity(new Intent(getApplicationContext(),ViewOngoingCases.class));



                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "No" button click event
                        // Put your code here for the action to be taken on "No"
                        startActivity(new Intent(getApplicationContext(),Teacher_chat.class));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                chat=ed1.getText().toString();
                if(chat.equalsIgnoreCase(""))
                {
                    ed1.setError("Empty Message ");
                    ed1.setFocusable(true);
                }
                else
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Teacher_chat.this;
                    String q = "/user_teacher_chat?sender_id="+Login.loginid+"&receiver_id="+Custom_view_teachers.teacher+"&details="+chat;
                    q=q.replace(" ","%20");
                    JR.execute(q);

                }
            }
        });
    }

    void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 3000);
    }

    void initializeTimerTask() {
        timerTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        getChats();
                    }
                });
            }
        };
    }

    void getChats() {

        chattedby=sh.getString("by", "");
//		Toast.makeText(getApplicationContext(),"cb"+chattedby, Toast.LENGTH_SHORT).show();
//		if(chattedby.equalsIgnoreCase("TWithteacher"))
//		{
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Teacher_chat.this;
        String q = "/teacher_chat_details?sender_id="+Login.loginid+"&receiver_id="+Custom_view_teachers.teacher;
        q=q.replace(" ","%20");
//	        Toast.makeText(getApplicationContext(),q, Toast.LENGTH_SHORT).show();
        JR.execute(q);



    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("chatdetail")){
                String status=jo.getString("status");
                Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    s_id= new String[ja1.length()];
                    r_id=new String[ja1.length()];
                    message=new String[ja1.length()];
                    date=new String[ja1.length()];

//					 val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        s_id[i]=ja1.getJSONObject(i).getString("sender_id");
                        r_id[i]=ja1.getJSONObject(i).getString("receiver_id");
                        message[i]=ja1.getJSONObject(i).getString("message");
                        date[i]=ja1.getJSONObject(i).getString("date");



                    }
//					ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
//					lv1.setAdapter(ar);
                    l1.setAdapter(new Custom_teacher_chat(this, message, s_id,date));



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if(method.equalsIgnoreCase("chat")){

                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){

//					 Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Teacher_chat.class));

                }

            }


        }catch(Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }


//    public void onBackPressed()
//    {
    // TODO Auto-generated method stub


}


//}
