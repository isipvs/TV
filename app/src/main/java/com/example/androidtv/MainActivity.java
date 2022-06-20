package com.example.androidtv;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.Calendar;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {
    EditText name;
    EditText phone;
    EditText dateOfBirth;

    Button insert;
    Button select;
    Button edit;
    Button delete;

    DataHelper dataHelper;

    ListView userList;

    String selectedlistname = "";
    String[] datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtNumber);
        dateOfBirth = findViewById(R.id.txtDate);

        insert = findViewById(R.id.btnInsert);
        edit = findViewById(R.id.btnEdit);
        delete = findViewById(R.id.btnDelete);
        select = findViewById(R.id.btnSelect);

        userList = findViewById(R.id.list);

        dataHelper = new DataHelper(this);

        insert.setOnClickListener(view -> {
            selectedlistname = "";
            Boolean checkInsertData = dataHelper.insert(name.getText().toString(), phone.getText().toString(),
                    dateOfBirth.getText().toString());

            if (checkInsertData) {
                Toast.makeText(getApplicationContext(), "Данные успешно добавлены", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }

            Cursor res = dataHelper.getdata();
            int a = 0;
            datalist = new String[res.getCount()];

            while(res.moveToNext()) {
                datalist[a] = (res.getString(0));
                a++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, datalist);
            userList.setAdapter(adapter);
        });

        delete.setOnClickListener(view-> {
            if (selectedlistname != "") {
                try {
                    dataHelper = new DataHelper(this);
                    dataHelper.delete(selectedlistname);
                    Cursor res = dataHelper.getdata();

                    int a = 0;
                    datalist = new String[res.getCount()];

                    while(res.moveToNext()){
                        datalist[a] = (res.getString(0));
                        a++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, datalist);
                    userList.setAdapter(adapter);

                    selectedlistname = "";
                } catch (Exception e) { }
            }
        });

        edit.setOnClickListener(view->{
            if (selectedlistname != "")
            {
                try {
                    dataHelper = new DataHelper(this);
                    dataHelper.edit(selectedlistname, phone.getText().toString(), dateOfBirth.getText().toString());
                    Cursor res = dataHelper.getdata();

                    int a = 0;
                    datalist = new String[res.getCount()];

                    while(res.moveToNext()) {
                        datalist[a]=(res.getString(0));
                        a++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, datalist);
                    userList.setAdapter(adapter);

                    selectedlistname = "";
                } catch (Exception e) { }
            }
        });

        select.setOnClickListener(view -> {
            Cursor res = dataHelper.getdata();

            if (res.getCount() == 0){
                Toast.makeText(MainActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userList.getCount() == 0) {
                int a = 0;
                datalist = new String[res.getCount()];

                while(res.moveToNext()){
                    datalist[a]=(res.getString(0));
                    a++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, datalist);

                userList.setAdapter(adapter);
            }
            else {
                if (selectedlistname != "") {
                    res.move(userList.getCheckedItemPosition() + 1);

                    name.setText(res.getString(0));
                    phone.setText(res.getString(1));
                    dateOfBirth.setText(res.getString(2));
                }
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor res = dataHelper.getdata();
                res.move(userList.getCheckedItemPosition() + 1);

                selectedlistname = res.getString(0);
            }
        });
    }
}