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
import com.example.project_solo_72dcht21.fragment.Monhoc;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sub_fragment_listMonHoc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sub_fragment_listMonHoc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sub_fragment_listMonHoc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sub_fragment_monhoc.
     */
    // TODO: Rename and change types and number of parameters
    public static Sub_fragment_listMonHoc newInstance(String param1, String param2) {
        Sub_fragment_listMonHoc fragment = new Sub_fragment_listMonHoc();
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
    private ListView lvmonhoc;
    private ArrayAdapter adap_MH ;
    private String key;
    private int index ;
    private EditText maMon_up , tenMon_up, tinChi_up , hocKy_up;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.subfragment_monhoc, container, false);

        //Mở database
        Context context = requireContext();
        mydb=context.openOrCreateDatabase("QLSV", context.MODE_PRIVATE,null);

        //Khai báo các control
        ImageView imgMH= view.findViewById(R.id.backtomonhoc);
        lvmonhoc= view.findViewById(R.id.lvmonhoc);
        getDataMonHoc();

        //Sự kiện long clivck vào item của list view
        lvmonhoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Adapter adapter = adapterView.getAdapter();
                key =getKey(adapter.getItem(i).toString());
                registerForContextMenu(lvmonhoc);
                return false;
            }
        });

        // Sự kiện quay lại form Monhoc
        imgMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment =new Monhoc();
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

    private void getDataMonHoc() {
        List<String> listData = databaseServices.selectAll("MonHoc",mydb);
        adap_MH = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listData);
        lvmonhoc.setAdapter(adap_MH);
        adap_MH.notifyDataSetChanged();
    }

    //Khai báo context Menu


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
            boolean result = databaseServices.deleteOne("MonHoc",key,mydb);
            if(result)
                Toast.makeText(getActivity(), "Deleted Succes", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Deleted False", Toast.LENGTH_SHORT).show();
            getDataMonHoc();
        } else if (item.getItemId()==R.id.menu_sua) {
            OpenUpdateMonHocDialog(Gravity.CENTER);
        }
        return super.onContextItemSelected(item);
    }

    private void OpenUpdateMonHocDialog(int gravity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Gravity.BOTTOM == gravity)
        {
            builder.setCancelable(false);
        }else
        {
            builder.setCancelable(true);
        }

        View dialogView = getLayoutInflater().inflate(R.layout.cus_dialog_monhoc, null);
        builder.setView(dialogView);

        // khai báo các control nhập liệu trong alerdialog
        maMon_up= dialogView.findViewById(R.id.mamon_MH_up);
        tenMon_up= dialogView.findViewById(R.id.tenmon_MH_up);
        tinChi_up= dialogView.findViewById(R.id.tinchi_MH_up);
        hocKy_up= dialogView.findViewById(R.id.hocky_MH_up);

        //đấy dữ liệu lên các control
        FillDataOnControl();

        //Đẩy dữ liệu vào list<Edittext>
        List<EditText> list_MH = new ArrayList<>();
        list_MH.add(maMon_up);
        list_MH.add(tenMon_up);
        list_MH.add(tinChi_up);
        list_MH.add(hocKy_up);

        Button b_clearED , b_submitED;
        b_clearED =dialogView.findViewById(R.id.btnXoaTrang_MH_up);
        b_submitED =dialogView.findViewById(R.id.btnXacNhan_MH_up);

        b_clearED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maMon_up.setText("");
                tenMon_up.setText("");
                tinChi_up.setText("");
                hocKy_up.setText("");
                maMon_up.requestFocus();
            }
        });

        b_submitED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maMon_up.getText().toString().equals("")||tenMon_up.getText().toString().equals("")||tinChi_up.getText().toString().equals("")
                ||hocKy_up.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng điền đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else {
                    boolean result = databaseServices.updateOne("MonHoc", list_MH, key, mydb);
                    if (result)
                        Toast.makeText(getActivity(), "Update Succes", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Update False", Toast.LENGTH_SHORT).show();
                }
                getDataMonHoc();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void FillDataOnControl() {
        String SelectedItems = (String) adap_MH.getItem(index);
        String[] items = databaseServices.splitString(SelectedItems);
        maMon_up.setText(items[0]);
        tenMon_up.setText(items[1]);
        tinChi_up.setText(items[2]);
        hocKy_up.setText(items[3]);
    }
}