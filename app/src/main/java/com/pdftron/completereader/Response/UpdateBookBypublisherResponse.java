package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBookBypublisherResponse {

    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("ResponseCode")
    @Expose
    public Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("data")
    @Expose
    public Data data;

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
        public Object id;
        @SerializedName("book_name")
        @Expose
        public Object bookName;
        @SerializedName("description")
        @Expose
        public Object description;
        @SerializedName("author")
        @Expose
        public Object author;
        @SerializedName("publisher")
        @Expose
        public Object publisher;
        @SerializedName("copyrightname")
        @Expose
        public Object copyrightname;
        @SerializedName("price")
        @Expose
        public Object price;
        @SerializedName("quantity")
        @Expose
        public Object quantity;
        @SerializedName("categoryId")
        @Expose
        public Object categoryId;
        @SerializedName("create_date")
        @Expose
        public Object createDate;
        @SerializedName("userId")
        @Expose
        public Object userId;
        @SerializedName("status")
        @Expose
        public Object status;
        @SerializedName("discount")
        @Expose
        public Object discount;
        @SerializedName("lang")
        @Expose
        public Object lang;
        @SerializedName("isFav")
        @Expose
        public Object isFav;
        @SerializedName("book_image")
        @Expose
        public Object bookImage;
        @SerializedName("book_index_image")
        @Expose
        public Object bookIndexImage;
        @SerializedName("book_back_image")
        @Expose
        public Object bookBackImage;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getBookName() {
            return bookName;
        }

        public void setBookName(Object bookName) {
            this.bookName = bookName;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getAuthor() {
            return author;
        }

        public void setAuthor(Object author) {
            this.author = author;
        }

        public Object getPublisher() {
            return publisher;
        }

        public void setPublisher(Object publisher) {
            this.publisher = publisher;
        }

        public Object getCopyrightname() {
            return copyrightname;
        }

        public void setCopyrightname(Object copyrightname) {
            this.copyrightname = copyrightname;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getQuantity() {
            return quantity;
        }

        public void setQuantity(Object quantity) {
            this.quantity = quantity;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
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

        public Object getIsFav() {
            return isFav;
        }

        public void setIsFav(Object isFav) {
            this.isFav = isFav;
        }

        public Object getBookImage() {
            return bookImage;
        }

        public void setBookImage(Object bookImage) {
            this.bookImage = bookImage;
        }

        public Object getBookIndexImage() {
            return bookIndexImage;
        }

        public void setBookIndexImage(Object bookIndexImage) {
            this.bookIndexImage = bookIndexImage;
        }

        public Object getBookBackImage() {
            return bookBackImage;
        }

        public void setBookBackImage(Object bookBackImage) {
            this.bookBackImage = bookBackImage;
        }

    }
}
