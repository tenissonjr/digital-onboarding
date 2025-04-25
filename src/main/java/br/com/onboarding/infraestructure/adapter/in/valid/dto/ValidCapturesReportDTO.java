package br.com.onboarding.infraestructure.adapter.in.valid.dto;

import java.util.List;

public class ValidCapturesReportDTO {

    private Long id;

    private String oid;
    private String name;

    private List<ValidCaptureItemReportDTO> captureItemReport;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ValidCaptureItemReportDTO> getCaptureItemReport() {
        return captureItemReport;
    }

    public void setCaptureItemReport(List<ValidCaptureItemReportDTO> captureItemReport) {
        this.captureItemReport = captureItemReport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}