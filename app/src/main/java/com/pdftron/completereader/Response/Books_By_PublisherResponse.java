package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Books_By_PublisherResponse {
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
        @SerializedName("book_name")
        @Expose
        public String bookName;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("author")
        @Expose
        public String author;
        @SerializedName("publisher")
        @Expose
        public String publisher;
        @SerializedName("copyrightname")
        @Expose
        public String copyrightname;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("quantity")
        @Expose
        public String quantity;
        @SerializedName("categoryId")
        @Expose
        public String categoryId;
        @SerializedName("create_date")
        @Expose
        public String createDate;
        @SerializedName("userId")
        @Expose
        public String userId;
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
        @SerializedName("book_image")
        @Expose
        public String bookImage;
        @SerializedName("book_index_image")
        @Expose
        public String bookIndexImage;
        @SerializedName("book_back_image")
        @Expose
        public String bookBackImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
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

        public String getCopyrightname() {
            return copyrightname;
        }

        public void setCopyrightname(String copyrightname) {
            this.copyrightname = copyrightname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public String getBookImage() {
            return bookImage;
        }

        public void setBookImage(String bookImage) {
            this.bookImage = bookImage;
        }

        public String getBookIndexImage() {
            return bookIndexImage;
        }

        public void setBookIndexImage(String bookIndexImage) {
            this.bookIndexImage = bookIndexImage;
        }

        public String getBookBackImage() {
            return bookBackImage;
        }

        public void setBookBackImage(String bookBackImage) {
            this.bookBackImage = bookBackImage;
        }

    }
}

