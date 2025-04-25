package br.com.onboarding.infraestructure.adapter.in.valid.dto;

import java.util.List;


public class ValidOnboardingDataDTO {

    private String hash;
    private String dadosOriginais;
    private String companyId;
    private String scheduleConfigurationId;
    private String name;
    private String identifier;
    private String employee;
    private String emailClient;
    private String phoneNumber;
    private String scheduleType;
    private String scheduleStatus;
    private String scheduleTime;
    private String dateStarted;
    private String dateCompleted;
    private String dateDuration;
    private String capturePlatform;
    private boolean externalInitialize;

    private List<String> chat;

    private List<ValidCapturesReportDTO> capturesReport;

    private String certificationFacematchRecord;
    private String relationshipScheduleConfigurationId;
    private String clientGeolocationLatitude;
    private String clientGeolocationLongitude;
    private String crSummaryResponse;
    
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getScheduleConfigurationId() {
        return scheduleConfigurationId;
    }
    public void setScheduleConfigurationId(String scheduleConfigurationId) {
        this.scheduleConfigurationId = scheduleConfigurationId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getEmployee() {
        return employee;
    }
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    public String getEmailClient() {
        return emailClient;
    }
    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getScheduleType() {
        return scheduleType;
    }
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }
    public String getScheduleStatus() {
        return scheduleStatus;
    }
    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }
    public String getScheduleTime() {
        return scheduleTime;
    }
    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
    public String getDateStarted() {
        return dateStarted;
    }
    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }
    public String getDateCompleted() {
        return dateCompleted;
    }
    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
    public String getDateDuration() {
        return dateDuration;
    }
    public void setDateDuration(String dateDuration) {
        this.dateDuration = dateDuration;
    }
    public String getCapturePlatform() {
        return capturePlatform;
    }
    public void setCapturePlatform(String capturePlatform) {
        this.capturePlatform = capturePlatform;
    }
    public boolean isExternalInitialize() {
        return externalInitialize;
    }
    public void setExternalInitialize(boolean externalInitialize) {
        this.externalInitialize = externalInitialize;
    }
    public List<String> getChat() {
        return chat;
    }
    public void setChat(List<String> chat) {
        this.chat = chat;
    }
    public List<ValidCapturesReportDTO> getCapturesReport() {
        return capturesReport;
    }
    public void setCapturesReport(List<ValidCapturesReportDTO> capturesReport) {
        this.capturesReport = capturesReport;
    }
    public String getCertificationFacematchRecord() {
        return certificationFacematchRecord;
    }
    public void setCertificationFacematchRecord(String certificationFacematchRecord) {
        this.certificationFacematchRecord = certificationFacematchRecord;
    }
    public String getRelationshipScheduleConfigurationId() {
        return relationshipScheduleConfigurationId;
    }
    public void setRelationshipScheduleConfigurationId(String relationshipScheduleConfigurationId) {
        this.relationshipScheduleConfigurationId = relationshipScheduleConfigurationId;
    }
    public String getClientGeolocationLatitude() {
        return clientGeolocationLatitude;
    }
    public void setClientGeolocationLatitude(String clientGeolocationLatitude) {
        this.clientGeolocationLatitude = clientGeolocationLatitude;
    }
    public String getClientGeolocationLongitude() {
        return clientGeolocationLongitude;
    }
    public void setClientGeolocationLongitude(String clientGeolocationLongitude) {
        this.clientGeolocationLongitude = clientGeolocationLongitude;
    }
    public String getCrSummaryResponse() {
        return crSummaryResponse;
    }
    public void setCrSummaryResponse(String crSummaryResponse) {
        this.crSummaryResponse = crSummaryResponse;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getDadosOriginais() {
        return dadosOriginais;
    }
    public void setDadosOriginais(String dadosOriginais) {
        this.dadosOriginais = dadosOriginais;
    }

    
}