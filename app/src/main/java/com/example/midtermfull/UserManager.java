package com.example.midtermfull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UserManager extends AppCompatActivity {

    ListView listView;
    String name[] = {"Khải", "Đăng", "Huy", "Trường"};
    String pass[] = {"123", "123", "123", "123"};
    String role[] = {"abcxyz", "abcxyz", "abcxyz", "abcxyz"};
    int image = R.drawable.ellipsis;
    ArrayList<User> myList;
    CustomListView myListView;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        listView = (ListView) findViewById(R.id.lvUserInfo);
        edtSearch = (EditText) findViewById(R.id.edtSearchUser);
        myList = new ArrayList<>();

        for(int i = 0; i < name.length; i++){
            myList.add(new User(name[i], pass[i], "Dang", role[i], image, "0765079531", "normal", "20"));
        }

        myListView = new CustomListView(UserManager.this, R.layout.custom_layout_user, myList);
        listView.setAdapter(myListView);

    }
}