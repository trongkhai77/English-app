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
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.midtermfull.databinding.ActivityStudentManagerBinding;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class StudentManager extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final int FILE_CHOOSER_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    ActivityStudentManagerBinding binding;
    ArrayList<StudentInfor> studentInforList;
    int image = R.drawable.ellipsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityStudentManagerBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        InitView();
    }
    private void InitView(){
        binding.importStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });
        binding.exportStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ExportCsv();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        studentInforList = new ArrayList<StudentInfor>();
    }
    private void OpenFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        Log.v("testLog", "click");
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


            ArrayList<StudentInfor> tempList = new ArrayList<StudentInfor>();
            if(studentInforList.size() > 0){
                data.get(0)[0] = data.get(0)[0].substring(1);
            }
            for (String[] student: data) {

                tempList.add(new StudentInfor(student[0], student[1], student[2], "Student", image, student[4], student[5],
                        student[3], student[6], student[7]));

            }
//            Log.v("csvData", data.get(0)[0]);
            // Close the CSVReader
            csvReader.close();
            for(int i=0; i < tempList.size(); i++){
                studentInforList.add(tempList.get(i));
            }
            UpdateStudentsView();
        }
    }
    private void WriteCsv() throws FileNotFoundException {

        File csvFile = new File(Environment.getExternalStorageDirectory(), "studentData.csv");

        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            for(int i=0; i < studentInforList.size(); i++){
                StudentInfor currentStudent = studentInforList.get(i);
                String data = "";

                data += (currentStudent.getUserName() + ",");

                data += currentStudent.getPass() + ",";
                data += currentStudent.getName() + ",";
                data += currentStudent.getAge() + ",";
                data += currentStudent.getPhoneNumber() + ",";
                data += currentStudent.getStatus() + ",";
                data += currentStudent.getStudentId() + ",";
                data += currentStudent.getClassID();
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
    private void UpdateStudentsView(){
        StudentListView myListView = new StudentListView(StudentManager.this, R.layout.custom_layout_user, studentInforList);
        binding.lvUserInfor.setAdapter(myListView);
        Log.v("testsort", "Update");
    }
    public void ShowPopUp(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int i = menuItem.getItemId();
        if(i == R.id.aToZ){
            ascendingChar();
            UpdateStudentsView();
        } else if(i == R.id.zToA){
            descendingChar();
            UpdateStudentsView();
        } else if(i == R.id.ascending){
            ascendingID();
            UpdateStudentsView();
        } else if(i == R.id.descending){
            descendingID();;
            UpdateStudentsView();
        }

        return false;
    }
    public void ascendingChar(){
        ArrayList<StudentInfor> temp = new ArrayList<>(studentInforList);
        for(int i=0; i < temp.size(); i++){
            for(int y=0; y < temp.size() - i - 1; y++){
                if(!temp.get(y).getName().equals(temp.get(y+1).getName())){
                    int index = 0;
                    while(true){

                        if(temp.get(y).getName().charAt(index) > temp.get(y+ 1).getName().charAt(index)){
                            StudentInfor tempInfor = temp.get(y);
                            temp.set(y, temp.get(y+1));
                            temp.set(y+1, tempInfor);
                            break;
                        } else if(temp.get(y).getName().charAt(index) < temp.get(y+ 1).getName().charAt(index)){
                            break;
                        } else {
                            index++;
                        }
                    }
                }

            }
        }
        for(int i=0; i < temp.size(); i++){
            studentInforList.set(i, temp.get(i));
        }
    }

    public void descendingChar(){
        ArrayList<StudentInfor> temp = new ArrayList<>(studentInforList);
        for(int i=0; i < temp.size(); i++){
            for(int y=0; y < temp.size() - i - 1; y++){
                if(!temp.get(y).getName().equals(temp.get(y+1).getName())){
                    int index = 0;
                    while(true){

                        if(temp.get(y).getName().charAt(index) < temp.get(y+ 1).getName().charAt(index)){
                            StudentInfor tempInfor = temp.get(y);
                            temp.set(y, temp.get(y+1));
                            temp.set(y+1, tempInfor);
                            break;
                        } else if(temp.get(y).getName().charAt(index) > temp.get(y+ 1).getName().charAt(index)){
                            break;
                        } else {
                            index++;
                        }
                    }
                }

            }
        }
        for(int i=0; i < temp.size(); i++){
            studentInforList.set(i, temp.get(i));
        }
    }
    public void ascendingID(){
        ArrayList<StudentInfor> temp = new ArrayList<>(studentInforList);
        for(int i=0; i < temp.size(); i++){
            for(int y=0; y < temp.size() - i - 1; y++){
                if(!temp.get(y).getStudentId().equals(temp.get(y+1).getStudentId())){
                    int index = 0;
                    while(true){

                        if(temp.get(y).getStudentId().charAt(index) > temp.get(y+ 1).getStudentId().charAt(index)){
                            StudentInfor tempInfor = temp.get(y);
                            temp.set(y, temp.get(y+1));
                            temp.set(y+1, tempInfor);
                            break;
                        } else if(temp.get(y).getStudentId().charAt(index) < temp.get(y+ 1).getStudentId().charAt(index)){
                            break;
                        } else {
                            index++;
                        }
                    }
                }

            }
        }
        for(int i=0; i < temp.size(); i++){
            studentInforList.set(i, temp.get(i));
        }
    }
    public void descendingID(){
        ArrayList<StudentInfor> temp = new ArrayList<>(studentInforList);
        for(int i=0; i < temp.size(); i++){
            for(int y=0; y < temp.size() - i - 1; y++){
                if(!temp.get(y).getStudentId().equals(temp.get(y+1).getStudentId())){
                    int index = 0;
                    while(true){

                        if(temp.get(y).getStudentId().charAt(index) < temp.get(y+ 1).getStudentId().charAt(index)){
                            StudentInfor tempInfor = temp.get(y);
                            temp.set(y, temp.get(y+1));
                            temp.set(y+1, tempInfor);
                            break;
                        } else if(temp.get(y).getStudentId().charAt(index) > temp.get(y+ 1).getStudentId().charAt(index)){
                            break;
                        } else {
                            index++;
                        }
                    }
                }

            }
        }
        for(int i=0; i < temp.size(); i++){
            studentInforList.set(i, temp.get(i));
        }
    }
}