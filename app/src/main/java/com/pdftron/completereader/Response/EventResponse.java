package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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
        private String id;
        @SerializedName("event_name")
        @Expose
        private String eventName;
        @SerializedName("event_subject")
        @Expose
        private String eventSubject;
        @SerializedName("userfrontfile")
        @Expose
        private String userfrontfile;
        @SerializedName("speciality")
        @Expose
        private String speciality;
        @SerializedName("business")
        @Expose
        private String business;
        @SerializedName("event_id")
        @Expose
        private Object eventId;
        @SerializedName("event_fee")
        @Expose
        private Object eventFee;
        @SerializedName("isFree")
        @Expose
        private String isFree;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("speaker")
        @Expose
        private String speaker;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("onprice")
        @Expose
        private String onprice;
        @SerializedName("resume")
        @Expose
        private String resume;
        @SerializedName("isFav")
        @Expose
        private String isFav;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("event_date")
        @Expose
        private Object eventDate;
        @SerializedName("event_role")
        @Expose
        private String eventRole;
        @SerializedName("pay_status")
        @Expose
        private String payStatus;

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

        public Object getEventId() {
            return eventId;
        }

        public void setEventId(Object eventId) {
            this.eventId = eventId;
        }

        public Object getEventFee() {
            return eventFee;
        }

        public void setEventFee(Object eventFee) {
            this.eventFee = eventFee;
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

        public String getSpeaker() {
            return speaker;
        }

        public void setSpeaker(String speaker) {
            this.speaker = speaker;
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

        public Object getEventDate() {
            return eventDate;
        }

        public void setEventDate(Object eventDate) {
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
