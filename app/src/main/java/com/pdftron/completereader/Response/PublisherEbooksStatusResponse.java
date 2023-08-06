package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublisherEbooksStatusResponse {

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

        @SerializedName("orderId")
        @Expose
        public String orderId;
        @SerializedName("userId")
        @Expose
        public String userId;
        @SerializedName("total_price")
        @Expose
        public String totalPrice;
        @SerializedName("paymentcheck")
        @Expose
        public String paymentcheck;
        @SerializedName("dateTime")
        @Expose
        public String dateTime;
        @SerializedName("ebookId")
        @Expose
        public String ebookId;
        @SerializedName("payment_id")
        @Expose
        public String paymentId;
        @SerializedName("ebook_name")
        @Expose
        public String ebookName;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("author")
        @Expose
        public String author;
        @SerializedName("publisher")
        @Expose
        public String publisher;
        @SerializedName("copyright_person")
        @Expose
        public String copyrightPerson;
        @SerializedName("payment_status")
        @Expose
        public Object paymentStatus;
        @SerializedName("book_file")
        @Expose
        public String bookFile;
        @SerializedName("categoryId")
        @Expose
        public String categoryId;
        @SerializedName("book_text")
        @Expose
        public String bookText;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("lang")
        @Expose
        public String lang;
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
        public String bookImage;
        @SerializedName("book_index_page")
        @Expose
        public String bookIndexPage;
        @SerializedName("book_back_image")
        @Expose
        public String bookBackImage;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("user_type")
        @Expose
        public String userType;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getPaymentcheck() {
            return paymentcheck;
        }

        public void setPaymentcheck(String paymentcheck) {
            this.paymentcheck = paymentcheck;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getEbookId() {
            return ebookId;
        }

        public void setEbookId(String ebookId) {
            this.ebookId = ebookId;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getEbookName() {
            return ebookName;
        }

        public void setEbookName(String ebookName) {
            this.ebookName = ebookName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getCopyrightPerson() {
            return copyrightPerson;
        }

        public void setCopyrightPerson(String copyrightPerson) {
            this.copyrightPerson = copyrightPerson;
        }

        public Object getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(Object paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getBookFile() {
            return bookFile;
        }

        public void setBookFile(String bookFile) {
            this.bookFile = bookFile;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getBookText() {
            return bookText;
        }

        public void setBookText(String bookText) {
            this.bookText = bookText;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
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

        public String getBookImage() {
            return bookImage;
        }

        public void setBookImage(String bookImage) {
            this.bookImage = bookImage;
        }

        public String getBookIndexPage() {
            return bookIndexPage;
        }

        public void setBookIndexPage(String bookIndexPage) {
            this.bookIndexPage = bookIndexPage;
        }

        public String getBookBackImage() {
            return bookBackImage;
        }

        public void setBookBackImage(String bookBackImage) {
            this.bookBackImage = bookBackImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

    }
}
