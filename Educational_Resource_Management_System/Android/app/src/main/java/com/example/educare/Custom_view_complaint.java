package com.example.educare;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class Custom_view_complaint extends ArrayAdapter<String>  implements JsonResponse{

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] descriptions,reply,date;




    public Custom_view_complaint(Activity context, String[] descriptions, String[] reply, String[] date) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_view_complaint, descriptions);
        this.context = context;
        this.descriptions=descriptions;
        this.reply=reply;
        this.date=date;





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_view_complaint, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t1=(TextView)listViewItem.findViewById(R.id.descriptions);
        TextView t2=(TextView)listViewItem.findViewById(R.id.reply);
        TextView t3=(TextView)listViewItem.findViewById(R.id.date);






        t1.setText(descriptions[position]);
        t2.setText(reply[position]);
        t3.setText(date[position]);



        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+descriptions[position];
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



    }



    private void startActivity(Intent intent) {
    }


}