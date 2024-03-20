package com.example.midtermfull;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.midtermfull.databinding.CertificateRowBinding;

import java.util.ArrayList;

public class CertificateListView extends ArrayAdapter<Certificate> {
    Activity context;
    int IdLayout;
    ArrayList<Certificate> myList;
    int image = R.drawable.ellipsis;
    public CertificateListView(Activity context, int idLayout, ArrayList<Certificate> myList){
        super(context, idLayout, myList);
        this.context = context;
        this.IdLayout = idLayout;
        this.myList = myList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Tạo đế chứa layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //Đặt idLayout lên đế để tạo thành View
        convertView = layoutInflater.inflate(IdLayout, null);
        if (position % 2 == 1) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
        }
        //Lấy 1 phần tử trong mảng
        Certificate certificate = myList.get(position);
        //Khai báo, tham chiếu Id và hiển thị thông tin lên listView
        TextView name = convertView.findViewById(R.id.certificateName);
        TextView id = convertView.findViewById(R.id.certificateID);
        TextView date = convertView.findViewById(R.id.certificateDate);
        ImageView img = convertView.findViewById(R.id.ellipsis);

        name.setText(certificate.getCertificateName());
        id.setText(certificate.getCertificateId());
        date.setText(certificate.getCertificateDate());
        img.setImageResource(image);

        return convertView;
    }
}
