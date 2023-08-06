package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateEventResponse {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("id")
        @Expose
        private Object id;
        @SerializedName("event_name")
        @Expose
        private Object eventName;
        @SerializedName("event_subject")
        @Expose
        private Object eventSubject;
        @SerializedName("userfrontfile")
        @Expose
        private Object userfrontfile;
        @SerializedName("speciality")
        @Expose
        private Object speciality;
        @SerializedName("business")
        @Expose
        private Object business;
        @SerializedName("isFree")
        @Expose
        private Object isFree;
        @SerializedName("userId")
        @Expose
        private Object userId;
        @SerializedName("speaker")
        @Expose
        private Object speaker;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("price")
        @Expose
        private Object price;
        @SerializedName("onprice")
        @Expose
        private Object onprice;
        @SerializedName("resume")
        @Expose
        private Object resume;
        @SerializedName("isFav")
        @Expose
        private Object isFav;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("event_date")
        @Expose
        private Object eventDate;
        @SerializedName("event_role")
        @Expose
        private Object eventRole;
        @SerializedName("pay_status")
        @Expose
        private Object payStatus;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getEventName() {
            return eventName;
        }

        public void setEventName(Object eventName) {
            this.eventName = eventName;
        }

        public Object getEventSubject() {
            return eventSubject;
        }

        public void setEventSubject(Object eventSubject) {
            this.eventSubject = eventSubject;
        }

        public Object getUserfrontfile() {
            return userfrontfile;
        }

        public void setUserfrontfile(Object userfrontfile) {
            this.userfrontfile = userfrontfile;
        }

        public Object getSpeciality() {
            return speciality;
        }

        public void setSpeciality(Object speciality) {
            this.speciality = speciality;
        }

        public Object getBusiness() {
            return business;
        }

        public void setBusiness(Object business) {
            this.business = business;
        }

        public Object getIsFree() {
            return isFree;
        }

        public void setIsFree(Object isFree) {
            this.isFree = isFree;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getSpeaker() {
            return speaker;
        }

        public void setSpeaker(Object speaker) {
            this.speaker = speaker;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getOnprice() {
            return onprice;
        }

        public void setOnprice(Object onprice) {
            this.onprice = onprice;
        }

        public Object getResume() {
            return resume;
        }

        public void setResume(Object resume) {
            this.resume = resume;
        }

        public Object getIsFav() {
            return isFav;
        }

        public void setIsFav(Object isFav) {
            this.isFav = isFav;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getEventDate() {
            return eventDate;
        }

        public void setEventDate(Object eventDate) {
            this.eventDate = eventDate;
        }

        public Object getEventRole() {
            return eventRole;
        }

        public void setEventRole(Object eventRole) {
            this.eventRole = eventRole;
        }

        public Object getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(Object payStatus) {
            this.payStatus = payStatus;
        }

    }
}
