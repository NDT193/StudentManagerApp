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
import com.example.project_solo_72dcht21.fragment.SinhVien;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sub_fragment_listSV#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_fragment_listSV extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sub_fragment_listSV() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sub_fragment_listSV.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_fragment_listSV newInstance(String param1, String param2) {
        Sub_fragment_listSV fragment = new Sub_fragment_listSV();
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

    private Services databaseServices = new Services();
    private SQLiteDatabase mydb;
    private ArrayAdapter adap_SV;
    private ListView listSV;
    private String key;
    private int index ;
    private  EditText maSV_up , hoTen_up, tenLop_up , ngaySinh_up, gioiTinh_up, sdt_up ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.subfragment_list_sv, container, false);

        //Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        //Khai báo các control trong form
        listSV= view.findViewById(R.id.lvsinhvien);
        ImageView img_back = view.findViewById(R.id.backtosv);
        getData_SV(); // phải để dưới phần khai báo listview

        //Sự kiện longclick item listview
        listSV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Adapter adapter = adapterView.getAdapter();
                key =getKey(adapter.getItem(i).toString());
                registerForContextMenu(listSV);
                return false;
            }
        });

        //Sự kiện back lại form trước
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment =new SinhVien();
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
    public void getData_SV(){
        List<String> listData = databaseServices.selectAll("SinhVien",mydb);
        adap_SV = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listData);
        listSV.setAdapter(adap_SV);
        adap_SV.notifyDataSetChanged();
    }

    // Khởi tạo menu context
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_sv, menu);
    }

    //Sự kiện click item menu

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.xoa_sv)
        {
            boolean result = databaseServices.deleteOne("SinhVien",key,mydb);
            if(result)
                Toast.makeText(getActivity(), "Deleted Succes", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Deleted False", Toast.LENGTH_SHORT).show();
            getData_SV();
        } else if (item.getItemId()==R.id.sua_sv) {
            OpenUpdateSinhVienDialog(Gravity.CENTER);

        } else if (item.getItemId()==R.id.chuyenlop_sv) {

        }
        return super.onContextItemSelected(item);
    }
    // Tạo hàm gọi dialog để sửa thông tin trong danh sách
       public void OpenUpdateSinhVienDialog(int gravity){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(Gravity.BOTTOM==gravity)
        {
            builder.setCancelable(false);
        }else {
            builder.setCancelable(true);
        }

        View dialogView = getLayoutInflater().inflate(R.layout.cus_dialog_sinhvien, null);
        builder.setView(dialogView);

        // khai báo các control nhập liệu trong alerdialog
           maSV_up= dialogView.findViewById(R.id.masv_sv_up);
           hoTen_up= dialogView.findViewById(R.id.hoten_sv_up);
           tenLop_up= dialogView.findViewById(R.id.tenlop_sv_up);
           ngaySinh_up= dialogView.findViewById(R.id.ngaysinh_sv_up);
           gioiTinh_up= dialogView.findViewById(R.id.gioitinh_sv_up);
           sdt_up= dialogView.findViewById(R.id.sdt_sv_up);

        // fill dữ liệu lên các control cua dialog
        FillDataOnControl();

        //khai bao List<EditText> và add dữ liệu vào trong
        List<EditText> list_SVUpdate = new ArrayList<>();
           list_SVUpdate.add(maSV_up);
           list_SVUpdate.add(hoTen_up);
           list_SVUpdate.add(tenLop_up);
           list_SVUpdate.add(ngaySinh_up);
           list_SVUpdate.add(gioiTinh_up);
           list_SVUpdate.add(sdt_up);

        //Khai báo các button của form
        Button b_clearedit , b_submitdia;
        b_clearedit = dialogView.findViewById(R.id.btnXoaTrang_sv_up);
        b_submitdia= dialogView.findViewById(R.id.btnXacNhan_sv_up);

        //Các sự kiện click
        b_clearedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                maSV_up.setText("");
                hoTen_up.setText("");
                tenLop_up.setText("");
                ngaySinh_up.setText("");
                gioiTinh_up.setText("");
                sdt_up.setText("");

            }
        });
        b_submitdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (maSV_up.getText().toString().equals("")|| hoTen_up.toString().equals("")|| tenLop_up.getText().toString().equals("")
                        ||ngaySinh_up.getText().toString().equals("")|| gioiTinh_up.getText().toString().equals("")|| sdt_up.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng điền đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                    boolean result = databaseServices.updateOne("SinhVien", list_SVUpdate, key, mydb);
                    if (result)
                        Toast.makeText(getActivity(), "Update Succes", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Update False", Toast.LENGTH_SHORT).show();
                }
                getData_SV();
            }
        });

        //Show alerdialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    public void FillDataOnControl(){
        String SelectedItems = (String) adap_SV.getItem(index);
        String[] items = databaseServices.splitString(SelectedItems);
        maSV_up.setText(items[0]);
        hoTen_up.setText(items[1]);
        tenLop_up.setText(items[2]);
        ngaySinh_up.setText(items[3]);
        gioiTinh_up.setText(items[4]);
        sdt_up.setText(items[5]);
    }

    public void Changeclass(){

    }
}