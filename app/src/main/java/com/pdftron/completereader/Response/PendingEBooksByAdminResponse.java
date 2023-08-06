package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingEBooksByAdminResponse {
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
        @SerializedName("ebook_name")
        @Expose
        private String ebookName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("publisher")
        @Expose
        private String publisher;
        @SerializedName("copyright_person")
        @Expose
        private String copyrightPerson;
        @SerializedName("payment_status")
        @Expose
        private Object paymentStatus;
        @SerializedName("book_file")
        @Expose
        private String bookFile;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("book_text")
        @Expose
        private String bookText;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("isFav")
        @Expose
        private String isFav;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("isFree")
        @Expose
        private String isFree;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("book_image")
        @Expose
        private String bookImage;
        @SerializedName("book_index_page")
        @Expose
        private String bookIndexPage;
        @SerializedName("book_back_image")
        @Expose
        private String bookBackImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    }
}
