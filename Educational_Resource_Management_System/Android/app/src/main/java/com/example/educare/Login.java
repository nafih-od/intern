package com.example.educare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class Login extends AppCompatActivity implements JsonResponse{





    EditText e1,e2;

    Button b1;

    String username,password;

    String usertype;


    public static String loginid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_login);

        e1=findViewById(R.id.username);
        e2=findViewById(R.id.password);
        b1=findViewById(R.id.login);



//        CheckBox showPasswordCheckbox = findViewById(R.id.show_password_checkbox);
//        showPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=e1.getText().toString();
                password=e2.getText().toString();


                JsonReq jr = new JsonReq();
                jr.json_response= (JsonResponse) Login.this;
                String q = "/student_login?username="+username+"&password="+password;
                jr.execute(q);
            }
        });



    }

    @Override
    public void response(JSONObject jo) throws JSONException {


        try {
            String status = jo.getString("status");
            Log.d("pearl", status);



            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                loginid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("usertype");

//                SharedPreferences.Editor e = sh.edit();
//                e.putString("log_id", loginid);
//                e.commit();


                if (usertype.equals("student")) {
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));
                }
            }else{ // usertype!=user
                Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        }
        catch(Exception e){
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}