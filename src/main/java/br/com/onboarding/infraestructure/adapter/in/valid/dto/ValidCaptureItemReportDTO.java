package br.com.onboarding.infraestructure.adapter.in.valid.dto;

public class ValidCaptureItemReportDTO {

    private Long id;
    private String oid;
    private String time;
    private String type;
    private String url;
    private String url_2;

    private ValidLinessesTwoDimensionResultDTO linessesTwoDimensionResult;
    private ValidOcrDocumentReportDTO ocrDocumentReport;

    private String base64;

    private String base64_2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_2() {
        return url_2;
    }

    public void setUrl_2(String url_2) {
        this.url_2 = url_2;
    }

    public ValidLinessesTwoDimensionResultDTO getLinessesTwoDimensionResult() {
        return linessesTwoDimensionResult;
    }

    public void setLinessesTwoDimensionResult(ValidLinessesTwoDimensionResultDTO linessesTwoDimensionResult) {
        this.linessesTwoDimensionResult = linessesTwoDimensionResult;
    }

    public ValidOcrDocumentReportDTO getOcrDocumentReport() {
        return ocrDocumentReport;
    }

    public void setOcrDocumentReport(ValidOcrDocumentReportDTO ocrDocumentReport) {
        this.ocrDocumentReport = ocrDocumentReport;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getBase64_2() {
        return base64_2;
    }

    public void setBase64_2(String base64_2) {
        this.base64_2 = base64_2;
    }

}