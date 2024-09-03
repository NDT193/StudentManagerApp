package com.example.project_solo_72dcht21.subFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.ContextMenu;
import android.view.Gravity;
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
import com.example.project_solo_72dcht21.database.Services;
import com.example.project_solo_72dcht21.fragment.Diem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sub_fragment_listDiem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_fragment_listDiem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sub_fragment_listDiem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sub_fragment_listdiem.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_fragment_listDiem newInstance(String param1, String param2) {
        Sub_fragment_listDiem fragment = new Sub_fragment_listDiem();
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

    private Diem diem = new Diem();
    private ListView lvdiem;
    private EditText diemCC_up , diemGK_up, diemCK_up, maSV , hoTen , maMon , tenMon, hocKy;
    private SQLiteDatabase mydb;
    private Services databaseServices = new Services();
    private ArrayAdapter adap_diem;
    private String key;
    private int index ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.subfragment_listdiem, container, false);
        //Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        //Khai báo các biên trên form
        ImageView img=view.findViewById(R.id.backtodiem);
        lvdiem=view.findViewById(R.id.lvdiem);
        getData_diem();

        lvdiem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Adapter adapter = adapterView.getAdapter();
                key =getKey(adapter.getItem(i).toString());
                registerForContextMenu(lvdiem);
                return false;
            }
        });


        //nút quay lại fragment điểm
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment =new Diem();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
        return view;
    }

    private String getKey(String item){
        return item.split("-")[0];
    }

    //hàm onCreate của menu context


    public void getData_diem(){
        List<String> listData = databaseServices.selectAll("Diema",mydb);
        adap_diem = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listData);
        lvdiem.setAdapter(adap_diem);
        adap_diem.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_monhoc, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_xoa)
        {
            // Sự kiện xóa item
            boolean result = databaseServices.deleteOne("Diema",key,mydb);
            if(result)
                Toast.makeText(getActivity(), "Deleted Succes", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Deleted False", Toast.LENGTH_SHORT).show();
            getData_diem();
        }
        else if (item.getItemId()==R.id.menu_sua) {
            // Sự kiện sửa diễn ra trong dialog
            OpenUpdateDiemDialog(Gravity.CENTER);
        }

        return super.onContextItemSelected(item);
    }

    public void OpenUpdateDiemDialog(int gravity){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(Gravity.BOTTOM==gravity)
        {
            builder.setCancelable(false);
        }else {
            builder.setCancelable(true);
        }

        View dialogView = getLayoutInflater().inflate(R.layout.cus_dialog_diem, null);
        builder.setView(dialogView);

        // khai báo các control nhập liệu trong alerdialog
        diemCC_up=dialogView.findViewById(R.id.diemcc_up);
        diemGK_up= dialogView.findViewById(R.id.diemgk_up);
        diemCK_up=dialogView.findViewById(R.id.diemck_up);
        maSV=dialogView.findViewById(R.id.txt_MaSV_up);
        hoTen=dialogView.findViewById(R.id.txt_HoTen_up);
        maMon=dialogView.findViewById(R.id.mamon_diem_up);
        tenMon=dialogView.findViewById(R.id.tenmon_diem_up);
        hocKy=dialogView.findViewById(R.id.hocky_diem_up);

        // fill dữ liệu lên các control cua dialog
        FillDataOnControl();

        //khai bao List<EditText> và add dữ liệu vào trong
        List<EditText> list_diemUpdate = new ArrayList<>();
        list_diemUpdate.add(maSV);
        list_diemUpdate.add(hoTen);
        list_diemUpdate.add(maMon);
        list_diemUpdate.add(tenMon);
        list_diemUpdate.add(diemCC_up);
        list_diemUpdate.add(diemGK_up);
        list_diemUpdate.add(diemCK_up);
        list_diemUpdate.add(hocKy);

        //Khai báo các button của form
        Button b_clearedit , b_submitdia;
        b_clearedit = dialogView.findViewById(R.id.Clear_diem_up);
        b_submitdia= dialogView.findViewById(R.id.btnXacNhan_diem_up);

        //Các sự kiện click
        b_clearedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               diemCC_up.setText("");
                diemGK_up.setText("");
                diemCK_up.setText("");

            }
        });
        b_submitdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (diemCC_up.getText().toString().equals("")||diemGK_up.getText().toString().equals("")||diemCK_up.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng điền đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                    boolean result = databaseServices.updateOne("Diema", list_diemUpdate, key, mydb);
                    if (result)
                        Toast.makeText(getActivity(), "Update Succes", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Update False", Toast.LENGTH_SHORT).show();
                }
                getData_diem();
            }
        });

        //Show alerdialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    public void FillDataOnControl(){
        String SelectedItems = (String) adap_diem.getItem(index);
        String[] items = databaseServices.splitString(SelectedItems);
        maSV.setText(items[0]);
        hoTen.setText(items[1]);
        maMon.setText(items[2]);
        tenMon.setText(items[3]);
        diemCC_up.setText(items[4]);
        diemGK_up.setText(items[5]);
        diemCK_up.setText(items[6]);
        hocKy.setText(items[7]);

    }
}