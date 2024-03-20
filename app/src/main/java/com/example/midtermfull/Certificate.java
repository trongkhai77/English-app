package com.example.midtermfull;

public class Certificate {
    private String certificateId, certificateName, certificateDate;

    public Certificate(String certificateId, String certificateName, String certificateDate) {
        this.certificateId = certificateId;
        this.certificateName = certificateName;
        this.certificateDate = certificateDate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(String certificateDate) {
        this.certificateDate = certificateDate;
    }
}
