package com.example.educare;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

//import com.squareup.picasso.Picasso;


public class Custom_view_teachers extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] teacher_name,teacher_phone,teacher_email,teacher_subject,teacher_id;

    public static String teacher;



    public Custom_view_teachers(Activity context, String[] teacher_name,String[] teacher_phone,String[] teacher_email,String[] teacher_subject,String[] teacher_id) {
        //constructor of this class to get the values from main_activity_class


        super(context, R.layout.activity_custom_view_teachers, teacher_name);
        this.context = context;
        this.teacher_name=teacher_name;
        this.teacher_phone=teacher_phone;
        this.teacher_email=teacher_email;
        this.teacher_subject=teacher_subject;
        this.teacher_id=teacher_id;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_view_teachers, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t=(TextView)listViewItem.findViewById(R.id.name);
        TextView t1=(TextView)listViewItem.findViewById(R.id.phone);
        TextView t2=(TextView)listViewItem.findViewById(R.id.email);
        TextView t3=(TextView)listViewItem.findViewById(R.id.subject);



//
//
////        ImageView img1=(ImageView)listViewItem.findViewById(R.id.image);
        Button b1=(Button)listViewItem.findViewById(R.id.chat);
//        Button b2=(Button)listViewItem.findViewById(R.id.chat);


//        Button b2=(Button)listViewItem.findViewById(R.id.reject);




//        t.setText("NAME : "+fname[position]);
        t.setText(teacher_name[position]);
        t1.setText(teacher_phone[position]);
        t2.setText(teacher_email[position]);
        t3.setText(teacher_subject[position]);


//
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher=teacher_id[position];
                Intent intent=new Intent(context.getApplicationContext(),Teacher_chat.class);
                getContext().startActivity(intent);
            }
        });
//








        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+teacher_name[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
//        Picasso.with(context)
//                .load(pth)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(img1);


        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public void response(JSONObject jo) throws JSONException {

//        try {
//            String status = jo.getString("status");
//            Log.d("pearl", status);
//
//            if (status.equalsIgnoreCase("success")) {
//
//                Toast.makeText(context.getApplicationContext(), "Appointment Taken", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(context.getApplicationContext(),Custom_view_doctors.class));
//
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//
//            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }


    }

    private void startActivity(Intent intent) {
    }


}