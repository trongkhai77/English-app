package com.example.midtermfull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.midtermfull.databinding.ActivityStudentAndCertificateDetailBinding;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StudentAndCertificateDetail extends AppCompatActivity {
    ActivityStudentAndCertificateDetailBinding binding;
    private static final int FILE_CHOOSER_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    ArrayList<Certificate> certificatesList = new ArrayList<Certificate>();
    int image = R.drawable.ellipsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityStudentAndCertificateDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        InitView();
    }
    private void InitView(){
        binding.importCertificateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        binding.exportCertificateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ExportCsv();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void OpenFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a CSV file"), FILE_CHOOSER_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex){
            Log.e("File not found", ex.toString());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri fileUri = data.getData();
            String filePath = fileUri.getPath();
            try {
                ReadCsv(fileUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void ReadCsv(Uri filePath) throws IOException {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(filePath);
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            CSVReader csvReader = new CSVReader(bufferedReader);

            List<String[]> data = csvReader.readAll();


            ArrayList<Certificate> tempList = new ArrayList<Certificate>();
            if(certificatesList.size() > 0){
                data.get(0)[0] = data.get(0)[0].substring(1);
            }
            for (String[] certificate: data) {
                tempList.add(new Certificate(certificate[0], certificate[1], certificate[2]));

            }
            for(int i=0; i < tempList.size(); i++){
                certificatesList.add(tempList.get(i));
            }
            UpdateCertificatesView();
            // Close the CSVReader
            csvReader.close();

        }
    }
    private void UpdateCertificatesView(){
        CertificateListView myListView = new CertificateListView(StudentAndCertificateDetail.this, R.layout.certificate_row, certificatesList);
        binding.certificateList.setAdapter(myListView);

        binding.certificateList.requestLayout();
        binding.scrollView.requestLayout();
    }

    private void WriteCsv() throws FileNotFoundException {

        File csvFile = new File(Environment.getExternalStorageDirectory(), "certificateData.csv");

        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            for(int i=0; i < certificatesList.size(); i++){
                Certificate currentStudent = certificatesList.get(i);
                String data = "";

                data += (currentStudent.getCertificateId() + ",");
                data += currentStudent.getCertificateName() + ",";
                data += currentStudent.getCertificateDate();
                data += "\n";

                fileWriter.append(data);
            }
            fileWriter.flush();
            fileWriter.close();
            Toast toast = Toast.makeText(this, "File exported", Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean CheckWritePermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else return true;
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                try {
                    ExportCsv();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void ExportCsv() throws FileNotFoundException {
        if(CheckWritePermission()){
            WriteCsv();
        }
    }
}