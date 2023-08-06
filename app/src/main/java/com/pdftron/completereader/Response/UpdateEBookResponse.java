package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateEBookResponse {
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
        @SerializedName("ebook_name")
        @Expose
        public Object ebookName;
        @SerializedName("description")
        @Expose
        public Object description;
        @SerializedName("author")
        @Expose
        public Object author;
        @SerializedName("publisher")
        @Expose
        public Object publisher;
        @SerializedName("copyright_person")
        @Expose
        public Object copyrightPerson;
        @SerializedName("book_file")
        @Expose
        public Object bookFile;
        @SerializedName("categoryId")
        @Expose
        public Object categoryId;
        @SerializedName("book_text")
        @Expose
        public Object bookText;
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
        @SerializedName("price")
        @Expose
        public Object price;
        @SerializedName("isFree")
        @Expose
        public Object isFree;
        @SerializedName("type")
        @Expose
        public Object type;
        @SerializedName("book_image")
        @Expose
        public Object bookImage;
        @SerializedName("book_index_page")
        @Expose
        public Object bookIndexPage;
        @SerializedName("book_back_image")
        @Expose
        public Object bookBackImage;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getEbookName() {
            return ebookName;
        }

        public void setEbookName(Object ebookName) {
            this.ebookName = ebookName;
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

        public Object getCopyrightPerson() {
            return copyrightPerson;
        }

        public void setCopyrightPerson(Object copyrightPerson) {
            this.copyrightPerson = copyrightPerson;
        }

        public Object getBookFile() {
            return bookFile;
        }

        public void setBookFile(Object bookFile) {
            this.bookFile = bookFile;
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

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getIsFree() {
            return isFree;
        }

        public void setIsFree(Object isFree) {
            this.isFree = isFree;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
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

    }

}
