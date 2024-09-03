package com.example.project_solo_72dcht21.fragment;
import com.example.project_solo_72dcht21.database.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_solo_72dcht21.R;
import com.example.project_solo_72dcht21.subFragment.Sub_fragment_listDiem;
import com.example.project_solo_72dcht21.subFragment.Sub_fragment_listMonHoc;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Monhoc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Monhoc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public Monhoc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Monhoc.
     */
    // TODO: Rename and change types and number of parameters
    public static Monhoc newInstance(String param1, String param2) {
        Monhoc fragment = new Monhoc();
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

    private SQLiteDatabase mydb;
    private Services databaseServices = new Services();
    private List<EditText> list_E = new ArrayList<>();
    private  Button b_saveMH, b_clearMH, b_viewlistMH;
    private EditText maMon , tenMon , tinChi , hocKy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monhoc, container, false);

        // Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        // Khai báo các control
        maMon = view.findViewById(R.id.mamon_MH);
        tenMon= view.findViewById(R.id.tenmon_MH);
        tinChi= view.findViewById(R.id.tinchi_MH);
        hocKy=  view.findViewById(R.id.hocky_MH);

        //Add Edit text vào ListEdittext
        list_E.add(maMon);
        list_E.add(tenMon);
        list_E.add(tinChi);
        list_E.add(hocKy);

        b_clearMH= view.findViewById(R.id.btnXoaTrang_MH);
        b_saveMH=view.findViewById(R.id.btnXacNhan_MH);
        b_viewlistMH=view.findViewById(R.id.xemdanhsach_MH);

        // Sự kiện lưu
        b_saveMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emptyMM= maMon.getText().toString();
                String emptyTM= tenMon.getText().toString();
                String emptyTC= tinChi.getText().toString();
                String emptyHK= hocKy.getText().toString();

               //bắt lỗi ko điền dữu liệu
                if(emptyMM.equals("") || emptyTM.equals("") || emptyTC.equals("") || emptyHK.equals("") )
                {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                        boolean result = databaseServices.insert("MonHoc", list_E, mydb);
                        if (result)
                            Toast.makeText(getActivity(), "insert success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "insert false", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Sự kiện xóa trắng
        b_clearMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maMon.setText("");
                tenMon.setText("");
                tinChi.setText("");
                hocKy.setText("");
            }
        });

        //Sự kiện xem danh sách
        b_viewlistMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment =new Sub_fragment_listMonHoc();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        return view;
    }




 }
