package com.example.project_solo_72dcht21.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_solo_72dcht21.R;
import com.example.project_solo_72dcht21.database.DataBase;
import com.example.project_solo_72dcht21.database.Services;

import java.util.ArrayList;
import java.util.List;

public class Acc_in4 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private Services databaseServices = new Services();
    private List<EditText> list_E1 = new ArrayList<>();
    private SQLiteDatabase mydb;
    private DataBase db = new DataBase();
    private  ListView lv;

    public Acc_in4() {}


    // TODO: Rename and change types and number of parameters
    public static Acc_in4 newInstance(String param1, String param2) {
        Acc_in4 fragment = new Acc_in4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acc_in4, container, false);
        return view;
    }
}