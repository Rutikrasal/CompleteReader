package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MagazinesResponse {
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
        @SerializedName("magazines_name")
        @Expose
        public String magazinesName;
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
        @SerializedName("book_file")
        @Expose
        public String bookFile;
        @SerializedName("categoryId")
        @Expose
        public String categoryId;
        @SerializedName("userId")
        @Expose
        public String userId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("price")
        @Expose
        public Object price;
        @SerializedName("isFree")
        @Expose
        public String isFree;
        @SerializedName("book_image")
        @Expose
        public String bookImage;
        @SerializedName("book_index_page")
        @Expose
        public String bookIndexPage;
        @SerializedName("book_back_image")
        @Expose
        public String bookBackImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMagazinesName() {
            return magazinesName;
        }

        public void setMagazinesName(String magazinesName) {
            this.magazinesName = magazinesName;
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

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public String getIsFree() {
            return isFree;
        }

        public void setIsFree(String isFree) {
            this.isFree = isFree;
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
