package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyEventOrderResponse {
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
        @SerializedName("userId")
        @Expose
        public String userId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("zipcode")
        @Expose
        public String zipcode;
        @SerializedName("event_location")
        @Expose
        public String eventLocation;
        @SerializedName("event_date")
        @Expose
        public String eventDate;
        @SerializedName("event_time")
        @Expose
        public String eventTime;
        @SerializedName("event_speaker_id")
        @Expose
        public String eventSpeakerId;
        @SerializedName("event_id")
        @Expose
        public String eventId;
        @SerializedName("payment")
        @Expose
        public String payment;
        @SerializedName("event_fee")
        @Expose
        public String eventFee;
        @SerializedName("categoryId")
        @Expose
        public Object categoryId;
        @SerializedName("book_text")
        @Expose
        public Object bookText;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("discount")
        @Expose
        public Object discount;
        @SerializedName("lang")
        @Expose
        public Object lang;
        @SerializedName("isFav")
        @Expose
        public String isFav;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("isFree")
        @Expose
        public String isFree;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("book_image")
        @Expose
        public Object bookImage;
        @SerializedName("book_index_page")
        @Expose
        public Object bookIndexPage;
        @SerializedName("book_back_image")
        @Expose
        public Object bookBackImage;
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
        @SerializedName("onprice")
        @Expose
        public String onprice;
        @SerializedName("resume")
        @Expose
        public String resume;
        @SerializedName("speaker")
        @Expose
        public String speaker;
        @SerializedName("description")
        @Expose
        public String description;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getEventLocation() {
            return eventLocation;
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation = eventLocation;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getEventSpeakerId() {
            return eventSpeakerId;
        }

        public void setEventSpeakerId(String eventSpeakerId) {
            this.eventSpeakerId = eventSpeakerId;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getEventFee() {
            return eventFee;
        }

        public void setEventFee(String eventFee) {
            this.eventFee = eventFee;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public Object getBookText() {
            return bookText;
        }

        public void setBookText(Object bookText) {
            this.bookText = bookText;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Object getLang() {
            return lang;
        }

        public void setLang(Object lang) {
            this.lang = lang;
        }

        public String getIsFav() {
            return isFav;
        }

        public void setIsFav(String isFav) {
            this.isFav = isFav;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIsFree() {
            return isFree;
        }

        public void setIsFree(String isFree) {
            this.isFree = isFree;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getBookImage() {
            return bookImage;
        }

        public void setBookImage(Object bookImage) {
            this.bookImage = bookImage;
        }

        public Object getBookIndexPage() {
            return bookIndexPage;
        }

        public void setBookIndexPage(Object bookIndexPage) {
            this.bookIndexPage = bookIndexPage;
        }

        public Object getBookBackImage() {
            return bookBackImage;
        }

        public void setBookBackImage(Object bookBackImage) {
            this.bookBackImage = bookBackImage;
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

        public String getSpeaker() {
            return speaker;
        }

        public void setSpeaker(String speaker) {
            this.speaker = speaker;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
