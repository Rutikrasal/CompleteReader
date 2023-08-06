package com.pdftron.completereader.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EBooks_By_PublisherResponse {
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
        @SerializedName("book_file")
        @Expose
        public String bookFile;
        @SerializedName("categoryId")
        @Expose
        public String categoryId;
        @SerializedName("book_text")
        @Expose
        public String bookText;
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
