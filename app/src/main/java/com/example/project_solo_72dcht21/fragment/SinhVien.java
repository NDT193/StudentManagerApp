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
import com.example.project_solo_72dcht21.subFragment.Sub_fragment_listSV;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinhVien#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SinhVien extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SinhVien.
     */
    // TODO: Rename and change types and number of parameters
    public static SinhVien newInstance(String param1, String param2) {
        SinhVien fragment = new SinhVien();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SinhVien() {
        // Required empty public constructor
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
    private List<EditText> list_Sinhvien = new ArrayList<>();
    private EditText maSv, hoTen, tenLop, ngaySinh, gioiTinh , sdt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sinh_vien, container, false);
        //Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        //Khai báo các control trên form
        maSv=view.findViewById(R.id.masv_sv);
        hoTen=view.findViewById(R.id.hoten_sv);
        tenLop=view.findViewById(R.id.tenlop_sv);
        ngaySinh=view.findViewById(R.id.ngaysinh_sv);
        gioiTinh=view.findViewById(R.id.gioitinh_sv);
        sdt=view.findViewById(R.id.sdt_sv);
        Button b_cleaSV , b_submit_SV, b_viewlistSV;
        b_cleaSV=view.findViewById(R.id.btnXoaTrang_sv);
        b_submit_SV= view.findViewById(R.id.btnXacNhan_sv);
        b_viewlistSV=view.findViewById(R.id.xemdanhsach_sv);

        //add dữ liệu vào trong Edittext
        list_Sinhvien.add(maSv);
        list_Sinhvien.add(hoTen);
        list_Sinhvien.add(tenLop);
        list_Sinhvien.add(ngaySinh);
        list_Sinhvien.add(gioiTinh);
        list_Sinhvien.add(sdt);

        //Sự kiện ấn Xóa trắng
        b_cleaSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maSv.setText("");
                hoTen.setText("");
                tenLop.setText("");
                ngaySinh.setText("");
                gioiTinh.setText("");
                sdt.setText("");
            }
        });

        //Sự kiện ấn lưu
        b_submit_SV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maSv.getText().toString().equals("")|| hoTen.getText().toString().equals("")|| tenLop.getText().toString().equals("")
                ||ngaySinh.getText().toString().equals("")|| gioiTinh.getText().toString().equals("")|| sdt.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else {
                    boolean result = databaseServices.insert("SinhVien",list_Sinhvien,mydb);
                    if (result)
                        Toast.makeText(getActivity(), "insert success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "insert false", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Sự kiện ấn xem danh sách sinh viên
        b_viewlistSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacetosubF();
            }
        });


        return view;
    }

    public void replacetosubF(){
        Fragment fragment =new Sub_fragment_listSV();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}