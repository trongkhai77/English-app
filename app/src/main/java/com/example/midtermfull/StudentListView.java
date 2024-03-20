package com.example.midtermfull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentListView extends ArrayAdapter<StudentInfor> {
    Activity context;
    int IdLayout;
    ArrayList<StudentInfor> myList;

    public StudentListView(Activity context, int idLayout, ArrayList<StudentInfor> myList) {
        super(context, idLayout, myList);
        this.context = context;
        IdLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Tạo đế chứa layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //Đặt idLayout lên đế để tạo thành View
        convertView = layoutInflater.inflate(IdLayout, null);
        if (position % 2 == 1){
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
        }
        //Lấy 1 phần tử trong mảng
        StudentInfor user = myList.get(position);
        //Khai báo, tham chiếu Id và hiển thị thông tin lên listView
        TextView studentId = convertView.findViewById(R.id.textViewName);
        TextView name = convertView.findViewById(R.id.textViewPass);
        TextView classId = convertView.findViewById(R.id.textViewRole);
        ImageView img = convertView.findViewById(R.id.ellipsis);

        studentId.setText(user.getStudentId());
        name.setText(user.getName());
        classId.setText(user.getClassID());
        img.setImageResource(user.getEllipsis());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopup();
            }

            private void openPopup() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_layout, null);
                dialogBuilder.setView(dialogView);

                final TextView edtViewDetail = dialogView.findViewById(R.id.edtViewDetail);
                final TextView edtDelete = dialogView.findViewById(R.id.edtDelete);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                edtViewDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(MainActivit, StudentAndCertificateDetail.class);
                    }
                });

                edtDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        remove(user);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        return convertView;
    }
}