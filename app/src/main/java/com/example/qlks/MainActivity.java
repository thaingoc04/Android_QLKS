package com.example.qlks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTitle;
    SearchView editSearch;
    ListView lstView;
    Button btnAdd;
    ArrayList<MinhAnh_0909> arrayList;
    Adapter adapter;
    int Id;

    Sqlite_De02 mysql = new Sqlite_De02(this, "MinhAnh_0909", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTitle = findViewById(R.id.txtTitle);
        editSearch = findViewById(R.id.editSearch);
        lstView = findViewById(R.id.lstRoom);
        btnAdd = findViewById(R.id.btnAdd);

//        mysql.addContact(new MinhAnh_0909("Đào Minh Anh", 405, 500000, 5));
//        mysql.addContact(new MinhAnh_0909("Nguyễn Ngọc Thái", 501, 23000, 100));
//        mysql.addContact(new MinhAnh_0909("Erling Haaland", 754, 21000, 100));
//        mysql.addContact(new MinhAnh_0909("Kevin De Bruyne", 244, 20000, 100));
//        mysql.addContact(new MinhAnh_0909("Jack 100 củ", 405, 12500, 100));
//        mysql.addContact(new MinhAnh_0909("Toni Kroos", 417, 12500, 100));
//        mysql.addContact(new MinhAnh_0909("Ruud Van Nistelrooy", 802, 10500, 100));

        arrayList = mysql.getAllContact();
        adapter = new Adapter(this, arrayList);
        lstView.setAdapter(adapter);

        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayList = mysql.getContactBySearch(newText);
                adapter = new Adapter(MainActivity.this, arrayList);
                lstView.setAdapter(adapter);
                return false;
            }
        });
        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
               Id = i;
               registerForContextMenu(view);
               //openContextMenu(view); // add this line to open context menu
               return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                startActivityForResult(intent,150);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.option_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuDelete:{
                AlertDialog.Builder checkItem = new AlertDialog.Builder(this);
                checkItem.setMessage("Are you sure want to Delete!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mysql.deleteContact(arrayList.get(Id).getId());
                                arrayList.remove(arrayList.get(Id));
                                lstView.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = checkItem.create();
                alert.show();
                break;

            }
            case R.id.mnuShow:{
                int count = 0;
                int money = arrayList.get(Id).tongTien();
                for (MinhAnh_0909 x: arrayList ) {
                    if(x.tongTien() > money){
                        count++;
                    }
                }
                Toast.makeText(MainActivity.this,"Minh Anh Peach, Số lượng hóa đơn: " + count, Toast.LENGTH_LONG).show();
                return false;
            }
            case R.id.mnuEdit:{
                Intent intent = new Intent(MainActivity.this, EditItem.class);
                Bundle bundle = new Bundle();

                MinhAnh_0909 x = new MinhAnh_0909(arrayList.get(Id).getName(), arrayList.get(Id).getRoomId(),
                        arrayList.get(Id).getPrice(), arrayList.get(Id).getDay());
                bundle.putSerializable("editItem", x);
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 150 && resultCode == 250){
            Bundle bundle = data.getExtras();
            MinhAnh_0909 item = (MinhAnh_0909) bundle.getSerializable("result");
            item.setId(arrayList.size()+1);
            mysql.addContact(item);
            arrayList.add(item);
            arrayList = mysql.getAllContact();
            adapter = new Adapter(MainActivity.this, arrayList);
            lstView.setAdapter(adapter);
        }
        else if(requestCode == 200 && resultCode == 300){
            Bundle bundle = data.getExtras();
            MinhAnh_0909 item = (MinhAnh_0909) bundle.getSerializable("editResult");
            int idToUpdate = arrayList.get(Id).getId();
            item.setId(idToUpdate);
            mysql.updateContact(idToUpdate, item);
            arrayList = mysql.getAllContact();
            adapter = new Adapter(MainActivity.this, arrayList);
            lstView.setAdapter(adapter);
        }
    }
}