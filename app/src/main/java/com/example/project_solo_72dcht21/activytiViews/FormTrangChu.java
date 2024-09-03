package com.example.project_solo_72dcht21.activytiViews;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_solo_72dcht21.R;
import com.example.project_solo_72dcht21.database.DataBase;
import com.example.project_solo_72dcht21.fragment.TrangChu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.project_solo_72dcht21.fragment.*;

public class FormTrangChu extends AppCompatActivity {
    BottomNavigationView bt;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);
        setOnClick();
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    private void setOnClick() {
        bt = findViewById(R.id.bottomNavigationView);
        bt.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int item1 = item.getItemId();
                int item2 = item.getItemId();
                int item3 = item.getItemId();
                if(item1 == R.id.navigation_home)
                {
                    replaceFragment(new TrangChu());

                }
                else if(item2 == R.id.navigation_Manager)
                {
                    replaceFragment(new Manager_desgin());
                } else if (item3 == R.id.navigation_personal) {
                    replaceFragment(new Acc_in4());
                }
                return true;
            }
        });
    }
}