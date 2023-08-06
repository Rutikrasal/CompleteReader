package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddfeedbackResponse {
    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("ResponseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    public String responseMessage;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
