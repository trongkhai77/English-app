package com.example.midtermfull;

public class StudentInfor extends User {
    String studentId, classId;
    public StudentInfor(String userName, String pass, String name, String role, int ellipsis, String phoneNumber,
                        String status, String age, String studentId, String classId) {
        super(userName, pass, name, role, ellipsis, phoneNumber, status, age);
        this.role = "Student";
        this.classId = classId;
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassID() {
        return classId;
    }

    public void setClassID(String classID) {
        this.classId = classID;
    }
}
