package com.example.project_solo_72dcht21.activytiViews;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.project_solo_72dcht21.R;

public class Signin extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sig_up_layout);
        EditText e1 =findViewById(R.id.tk);
        EditText e2 =findViewById(R.id.mk);
        Button b = findViewById(R.id.but_dn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((e1.getText().toString().equals("admin"))&& (e2.getText().toString().equals("admin")))
                {
                    Intent t = new Intent(Signin.this, FormTrangChu.class);
                    startActivity(t);
                }else {
                    Toast.makeText(Signin.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
