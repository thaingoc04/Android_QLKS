package com.example.qlks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItem extends AppCompatActivity {
    EditText editName, editRoomId, editPrice, editDay;
    Button btnAdd, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editName = findViewById(R.id.editName2);
        editRoomId = findViewById(R.id.editRoomId2);
        editPrice = findViewById(R.id.editPrice2);
        editDay = findViewById(R.id.editDay2);

        btnAdd = findViewById(R.id.btnNew2);
        btnExit = findViewById(R.id.btnExit2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            MinhAnh_0909 x = new MinhAnh_0909();
            x = (MinhAnh_0909) bundle.getSerializable("editItem");
            editName.setText(x.getName());
            editRoomId.setText(String.valueOf(x.getRoomId()));
            editPrice.setText(String.valueOf(x.getPrice()));
            editDay.setText(String.valueOf(x.getDay()));
        }
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditItem.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validate = isValidate(editName.getText().toString(), editRoomId.getText().toString(),
                        editPrice.getText().toString(), editDay.getText().toString());
                if(validate.equals("")){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    MinhAnh_0909 item = new MinhAnh_0909(editName.getText().toString(), Integer.parseInt(editRoomId.getText().toString()),
                            Integer.parseInt(editPrice.getText().toString()), Integer.parseInt(editDay.getText().toString()));
                    bundle.putSerializable("editResult", item);
                    intent.putExtras(bundle);
                    setResult(300, intent);
                    finish();

                }
                else {
                    Toast.makeText(EditItem.this, validate, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private String isValidate(String name, String roomId, String price, String day){
        if(name.equals("")) return "Name cannot be empty!";
        if(roomId.equals("")) return "Room Id cannot be empty!";
        if(price.equals("")) return "Price cannot be empty!";
        if(day.equals("")) return "Day cannot be empty!";
        return "";
    }
}