package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingEventResponse {
    @SerializedName("ResponseCode")
    @Expose
    public String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("event_name")
        @Expose
        public String eventName;
        @SerializedName("event_subject")
        @Expose
        public String eventSubject;
        @SerializedName("userfrontfile")
        @Expose
        public String userfrontfile;
        @SerializedName("speciality")
        @Expose
        public String speciality;
        @SerializedName("business")
        @Expose
        public String business;
        @SerializedName("isFree")
        @Expose
        public String isFree;
        @SerializedName("userId")
        @Expose
        public String userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("onprice")
        @Expose
        public String onprice;
        @SerializedName("resume")
        @Expose
        public String resume;
        @SerializedName("isFav")
        @Expose
        public String isFav;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("event_date")
        @Expose
        public String eventDate;
        @SerializedName("event_role")
        @Expose
        public String eventRole;
        @SerializedName("pay_status")
        @Expose
        public String payStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventSubject() {
            return eventSubject;
        }

        public void setEventSubject(String eventSubject) {
            this.eventSubject = eventSubject;
        }

        public String getUserfrontfile() {
            return userfrontfile;
        }

        public void setUserfrontfile(String userfrontfile) {
            this.userfrontfile = userfrontfile;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getIsFree() {
            return isFree;
        }

        public void setIsFree(String isFree) {
            this.isFree = isFree;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOnprice() {
            return onprice;
        }

        public void setOnprice(String onprice) {
            this.onprice = onprice;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

        public String getIsFav() {
            return isFav;
        }

        public void setIsFav(String isFav) {
            this.isFav = isFav;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventRole() {
            return eventRole;
        }

        public void setEventRole(String eventRole) {
            this.eventRole = eventRole;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

    }
}
