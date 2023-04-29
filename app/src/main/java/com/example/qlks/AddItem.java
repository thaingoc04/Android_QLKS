package com.example.qlks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {
    EditText editName, editRoomId, editPrice, editDay;
    Button btnAdd, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editName = findViewById(R.id.editName);
        editRoomId = findViewById(R.id.editRoomId);
        editPrice = findViewById(R.id.editPrice);
        editDay = findViewById(R.id.editDay);

        btnAdd = findViewById(R.id.btnNew);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItem.this, MainActivity.class);
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
                    bundle.putSerializable("result", item);
                    intent.putExtras(bundle);
                    setResult(250, intent);
                    finish();

                }
                else {
                    Toast.makeText(AddItem.this, validate, Toast.LENGTH_LONG).show();
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