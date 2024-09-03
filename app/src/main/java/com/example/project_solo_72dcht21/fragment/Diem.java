package com.example.project_solo_72dcht21.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_solo_72dcht21.R;
import com.example.project_solo_72dcht21.database.Services;
import com.example.project_solo_72dcht21.subFragment.Sub_fragment_listDiem;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Diem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Diem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Diem.
     */
    // TODO: Rename and change types and number of parameters
    public static Diem newInstance(String param1, String param2) {
        Diem fragment = new Diem();
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
    //private Manager_desgin manager_desgin = new Manager_desgin();

    private SQLiteDatabase mydb;
    private List<EditText> list_diem = new ArrayList<>();
    private Services databaseServices = new Services();
    private EditText hoTen, maSv , maMon , tenMon , diemCC, diemGK, diemCK ,hocKy,id;
    private Button b_clear , b_submit, b_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_diem, container, false);
        //Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        // Khai báo các control và add các edit text vào list edit text đã khai báo bên trên
        id=view.findViewById(R.id.id_diem);
        maSv=view.findViewById(R.id.txt_MaSV);
        hoTen=view.findViewById(R.id.txt_HoTen);
        maMon=view.findViewById(R.id.mamon_diem);
        tenMon=view.findViewById(R.id.tenmon_diem);
        diemCC=view.findViewById(R.id.diemcc);
        diemGK=view.findViewById(R.id.diemgk);
        diemCK=view.findViewById(R.id.diemck);
        hocKy=view.findViewById(R.id.hocky_diem);
        b_clear=view.findViewById(R.id.Clear_diem);
        b_view=view.findViewById(R.id.changeintosubdiem);

       // list_diem.add(id);
        list_diem.add(maSv);
        list_diem.add(hoTen);
        list_diem.add(maMon);
        list_diem.add(tenMon);
        list_diem.add(diemCC);
        list_diem.add(diemGK);
        list_diem.add(diemCK);
        list_diem.add(hocKy);




        b_submit= view.findViewById(R.id.btnXacNhan_diem);
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emptymaSv = maSv.getText().toString();
                String emptyhoten = hoTen.getText().toString();
                String emptymamon = maMon.getText().toString();
                String emptytenmon = tenMon.getText().toString();
                String emptydiemCC = diemCC.getText().toString();
                String emptydiemGK = diemGK.getText().toString();
                String emptydiemCK = diemCK.getText().toString();
                String emptyHK = hocKy.getText().toString();

                if(emptymaSv.isEmpty()|| emptyhoten.isEmpty()|| emptymamon.isEmpty()||emptytenmon.isEmpty()
                        || emptydiemCC.isEmpty()|| emptydiemGK.isEmpty()|| emptydiemCK.isEmpty()|| emptyHK.isEmpty() ){
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else {
                    boolean result = databaseServices.insert("Diema",list_diem,mydb);
                    if (result)
                        Toast.makeText(getActivity(), "insert success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "insert false", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maSv.setText("");
                hoTen.setText("");
                maMon.setText("");
                tenMon.setText("");
                diemCC.setText("");
                diemGK.setText("");
                diemCK.setText("");
                hocKy.setText("");

            }
        });

        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacetosubF();
            }
        });
        return view;
    }
    public void replacetosubF(){
        Fragment fragment =new Sub_fragment_listDiem();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}