package com.pdftron.completereader.WebService;

import com.pdftron.completereader.Response.AcceptedBooksByAdminResponse;
import com.pdftron.completereader.Response.AcceptedEBooksByAdminResponse;
import com.pdftron.completereader.Response.AcceptedEventResponse;
import com.pdftron.completereader.Response.AddBookByAuthorResponse;
import com.pdftron.completereader.Response.AddBookResponse;
import com.pdftron.completereader.Response.AddBookReviewResponse;
import com.pdftron.completereader.Response.AddEBookOrderResponse;
import com.pdftron.completereader.Response.AddEBookResponse;
import com.pdftron.completereader.Response.AddENoteOrderResponse;
import com.pdftron.completereader.Response.AddEventResponse;
import com.pdftron.completereader.Response.AddEventorderResponse;
import com.pdftron.completereader.Response.AddMagazineResponse;
import com.pdftron.completereader.Response.AddfeedbackResponse;
import com.pdftron.completereader.Response.AllCategoryResponse;
import com.pdftron.completereader.Response.BookViewResponse;
import com.pdftron.completereader.Response.BookedEventResponse;
import com.pdftron.completereader.Response.Books_By_PublisherResponse;
import com.pdftron.completereader.Response.CategorywiseBooksResponse;
import com.pdftron.completereader.Response.DeleteBookBypublisherResponse;
import com.pdftron.completereader.Response.DeleteEBookBypublisherResponse;
import com.pdftron.completereader.Response.DeleteEventResponse;
import com.pdftron.completereader.Response.DeleteMagazineBypublisherResponse;
import com.pdftron.completereader.Response.DemoResponse;
import com.pdftron.completereader.Response.EBooks_By_PublisherResponse;
import com.pdftron.completereader.Response.EventDetailResponse;
import com.pdftron.completereader.Response.EventResponse;
import com.pdftron.completereader.Response.LoginResponse;
import com.pdftron.completereader.Response.MagazinesResponse;
import com.pdftron.completereader.Response.Magazines_By_PublisherResponse;
import com.pdftron.completereader.Response.MyBookorderResponse;
import com.pdftron.completereader.Response.MyEventOrderResponse;
import com.pdftron.completereader.Response.MyEventResponse;
import com.pdftron.completereader.Response.PendingBooksByAdminResponse;
import com.pdftron.completereader.Response.PendingEBooksByAdminResponse;
import com.pdftron.completereader.Response.PendingEventResponse;
import com.pdftron.completereader.Response.ProfileViewResponse;
import com.pdftron.completereader.Response.PublisherAcceptedBooksResponse;
import com.pdftron.completereader.Response.PublisherEbooksStatusResponse;
import com.pdftron.completereader.Response.RecentBookResponse;
import com.pdftron.completereader.Response.RecenteBookResponse;
import com.pdftron.completereader.Response.RegisterResponse;
import com.pdftron.completereader.Response.SearchResponse;
import com.pdftron.completereader.Response.StudentTextNotesResponse;
import com.pdftron.completereader.Response.StudentTexteNotesResponse;
import com.pdftron.completereader.Response.StudenteBookResponse;
import com.pdftron.completereader.Response.StudentsBookResponse;
import com.pdftron.completereader.Response.TopBookResponse;
import com.pdftron.completereader.Response.TopeBookResponse;
import com.pdftron.completereader.Response.UpdateBookBypublisherResponse;
import com.pdftron.completereader.Response.UpdateEBookResponse;
import com.pdftron.completereader.Response.UpdateEventResponse;
import com.pdftron.completereader.Response.UpdateProfileResponse;
import com.pdftron.completereader.Response.ViewBookReviewResponse;
import com.pdftron.completereader.Response.ViewTopebookResponse;
import com.pdftron.completereader.Response.ViewfeedbackResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    //Registration Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST(" ")
    Call<RegisterResponse> register(@Field("action") String action, @Field("name") String name, @Field("contact") String contact, @Field("email") String email, @Field("password") String password, @Field("address") String address, @Field("city") String city, @Field("user_type") String user_type, @Field("type") String type);

    //Login Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("action") String action, @Field("email") String email, @Field("password") String password);


    //Top Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("top_books.php")
    Call<TopBookResponse> topbook(@Field("action") String action);

    //Top Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("top_ebooks.php")
    Call<TopeBookResponse> topebook(@Field("action") String action);

    //Recent Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("recent_books.php")
    Call<RecentBookResponse> recentbook(@Field("action") String action, @Field("keyword") String keyword);

    //E Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("recent_ebooks.php")
    Call<RecenteBookResponse> recentebook(@Field("action") String action, @Field("keyword") String keyword);

    //Student Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("student_books.php")
    Call<StudentsBookResponse> studentbook(@Field("action") String action);


    //Student TextNotes Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("student_text_notes.php")
    Call<StudentTextNotesResponse> studenttextnotes(@Field("action") String action);


    //Student eBook Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("student_ebooks.php")
    Call<StudenteBookResponse> studentebook(@Field("action") String action);


    //Student eNote Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("student_text_enotes.php")
    Call<StudentTexteNotesResponse> studenttextenotes(@Field("action") String action);


    //Magazies Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("magazines.php")
    Call<MagazinesResponse> magazines(@Field("action") String action);


    //Search Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("search_book.php")
    Call<SearchResponse> searchbook(@Field("action") String action, @Field("keyword") String keyword);


    //BookView Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("book_view.php")
    Call<BookViewResponse> bookview(@Field("action") String action, @Field("id") String id);


    //AddReview Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_review.php")
    Call<AddBookReviewResponse> addreview(@Field("review") String review, @Field("bookId") String bookId, @Field("userId") String userId);


    //ViewReview Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("review.php")
    Call<ViewBookReviewResponse> viewreview(@Field("action") String action, @Field("id") String id);

    //Addfeedback Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_feedback.php")
    Call<AddfeedbackResponse> addfeedback(@Field("name") String name, @Field("email") String email, @Field("message") String message);

    //Viewfeedback Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("feedback.php")
    Call<ViewfeedbackResponse> viewfeedback(@Field("action") String action);


    //Events Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("events.php")
    Call<EventResponse> events(@Field("action") String action);


    //EventsDeails Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("event_view.php")
    Call<EventDetailResponse> eventdetail(@Field("action") String action, @Field("id") String id);


    //AddEvent Order Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_event_order.php")
    Call<AddEventorderResponse> addeventorder(@Field("action") String action, @Field("userId") String userId, @Field("name") String name, @Field("email") String email, @Field("contact") String contact, @Field("address") String address, @Field("event_location") String event_location, @Field("zipcode") String zipcode, @Field("event_date") String event_date, @Field("event_time") String event_time, @Field("event_speaker_id") String event_speaker_id, @Field("event_id") String event_id, @Field("payment") String payment, @Field("event_type ") String event_type, @Field("event_fee") String event_fee);

    //AddEbook Order Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_ebook_order.php")
    Call<AddEBookOrderResponse> addebookorder(@Field("action") String action, @Field("userId") String userId, @Field("total_price") String total_price, @Field("paymentcheck") String paymentcheck, @Field("ebookId") String ebookId, @Field("payment_id") String payment_id);

    //AddEnote Order Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_enote_order.php")
    Call<AddENoteOrderResponse> addenoteorder(@Field("action") String action, @Field("userId") String userId, @Field("total_price") String total_price, @Field("paymentcheck") String paymentcheck, @Field("enoteId") String enoteId, @Field("payment_id") String payment_id);

    //AddBook Order Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_book_order.php")
    Call<AddBookResponse> addbookoreder(@Field("action") String action, @Field("userId") String userId, @Field("ship_name") String ship_name, @Field("email") String email, @Field("contact") String contact, @Field("address") String address, @Field("city") String city, @Field("zipcode") String zipcode, @Field("total_price") String total_price, @Field("paymentcheck") String paymentcheck, @Field("bookId") String bookId, @Field("quantity") String quantity, @Field("payment_id") String payment_id);

    //All Category Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("category.php")
    Call<AllCategoryResponse> allcategory(@Field("action") String action);

    //CategoryWise Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("book_by_category.php")
    Call<CategorywiseBooksResponse> categorywisebooks(@Field("action") String action, @Field("id") String id);

    //View top E-Book Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("top_ebooks.php")
    Call<ViewTopebookResponse> viewtopebook(@Field("action") String action, @Field("userId") String userId);



    //View my Bookorder Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("book_order.php")
    Call<MyBookorderResponse> mybookorder(@Field("action") String action, @Field("userId") String userId);

    //Update Profile Response Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("edit_profile.php")
    Call<UpdateProfileResponse> updateprofile(@Field("action") String action, @Field("id") String id, @Field("name") String name, @Field("contact") String contact, @Field("email") String email, @Field("password") String password, @Field("address") String address,@Field("city") String city, @Field("user_type") String user_type, @Field("type") String type);

    //View Profile Response Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("view_user.php")
    Call<ProfileViewResponse> viewprofile(@Field("action") String action, @Field("id") String id);


    //Add Book By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_book.php")
    Call<AddBookByAuthorResponse> addbooks(@Field("action") String action, @Field("book_name") String book_name, @Field("description") String description, @Field("author") String author, @Field("publisher") String publisher, @Field("copyrightname") String copyrightname, @Field("price") String price, @Field("quantity") String quantity, @Field("categoryId") String categoryId, @Field("book_image") String book_image, @Field("book_index_image") String book_index_image, @Field("book_back_image") String book_back_image, @Field("userId") String userId, @Field("status") String status, @Field("discount") String discount, @Field("lang") String lang, @Field("isFav") String isFav);



    //Add Magazine Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_magazine.php")
    Call<AddMagazineResponse> addmagazine(@Field("action") String action, @Field("description") String description, @Field("author") String author, @Field("publisher") String publisher, @Field("copyright_person") String copyright_person, @Field("price") String price, @Field("categoryId") String categoryId, @Field("userId") String userId, @Field("status") String status, @Field("isFree") String isFree, @Field("magazines_name") String magazines_name, @Field("book_file") String book_file, @Field("book_image") String book_image, @Field("book_index_page") String book_index_page, @Field("book_back_image") String book_back_image);

    //Add EBook Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_ebook.php")
    Call<AddEBookResponse> addebook(@Field("action") String action, @Field("description") String description, @Field("author") String author, @Field("publisher") String publisher, @Field("copyright_person") String copyright_person, @Field("price") String price, @Field("categoryId") String categoryId, @Field("userId") String userId, @Field("status") String status, @Field("discount") String discount, @Field("type") String type, @Field("isFree") String isFree, @Field("ebook_name") String ebook_name, @Field("book_text") String book_text, @Field("lang") String lang, @Field("isFav") String isFav, @Field("book_file") String book_file, @Field("book_image") String book_image, @Field("book_index_page") String book_index_page, @Field("book_back_image") String book_back_image);


    //View Books By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("book_by_publisher.php")
    Call<Books_By_PublisherResponse> books(@Field("action") String action, @Field("userId") String userId);

    //View EBooks By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("ebook_by_publisher.php")
    Call<EBooks_By_PublisherResponse> ebooks(@Field("action") String action, @Field("userId") String userId);


    //View Magazines By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("magazine_by_publisher.php")
    Call<Magazines_By_PublisherResponse> magazines(@Field("action") String action, @Field("userId") String userId);

    //Update Book By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("update_book.php")
    Call<UpdateBookBypublisherResponse> updatebooks(@Field("action") String action, @Field("description") String description, @Field("author") String author, @Field("publisher") String publisher, @Field("copyrightname") String copyrightname, @Field("price") String price, @Field("categoryId") String categoryId, @Field("userId") String userId, @Field("status") String status, @Field("discount") String discount, @Field("book_name") String book_name, @Field("isFav") String isFav, @Field("book_image") String book_image, @Field("book_index_image") String book_index_image, @Field("book_back_image") String book_back_image, @Field("id") String id, @Field("quantity") String quantity, @Field("lang") String lang);

    //Update EBook Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("update_ebook.php")
    Call<UpdateEBookResponse> updateebook(@Field("action") String action, @Field("description") String description, @Field("author") String author, @Field("publisher") String publisher, @Field("copyright_person") String copyright_person, @Field("price") String price, @Field("categoryId") String categoryId, @Field("userId") String userId, @Field("status") String status, @Field("discount") String discount, @Field("type") String type, @Field("isFree") String isFree, @Field("ebook_name") String ebook_name, @Field("book_text") String book_text, @Field("lang") String lang, @Field("isFav") String isFav, @Field("book_file") String book_file, @Field("book_image") String book_image, @Field("book_index_page") String book_index_page, @Field("book_back_image") String book_back_image, @Field("id") String id);


    //Delete Book By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("delete_book.php")
    Call<DeleteBookBypublisherResponse> deletebook(@Field("action") String action, @Field("id") String id);

    //Delete EBook By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("delete_ebook.php")
    Call<DeleteEBookBypublisherResponse> deleteebook(@Field("action") String action, @Field("id") String id);


    //Delete EBook By Publisher Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("delete_magazine.php")
    Call<DeleteMagazineBypublisherResponse> deletemagazin(@Field("action") String action, @Field("id") String id);


    //Eventorder Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("event_order.php")
    Call<MyEventOrderResponse> eventorder(@Field("action") String action, @Field("userId") String userId);

    //Admin Book Status Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("admin_books.php")
    Call<PublisherAcceptedBooksResponse> bookstatus(@Field("action") String action, @Field("userId") String userId);


    //Admin EBook Status Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("admin_ebooks.php")
    Call<PublisherEbooksStatusResponse> ebookstatus(@Field("action") String action, @Field("userId") String userId);


    //Accepted Book ByAdmin Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("accepted_books.php")
    Call<AcceptedBooksByAdminResponse> acceptedbooks(@Field("action") String action, @Field("userId") String userId);


    //Pending Book ByAdmin Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("pending_books.php")
    Call<PendingBooksByAdminResponse> pendingbooks(@Field("action") String action, @Field("userId") String userId);


    //Accepted EBook ByAdmin Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("accepted_ebooks.php")
    Call<AcceptedEBooksByAdminResponse> acceptedebooks(@Field("action") String action, @Field("userId") String userId);

    //Accepted EBook ByAdmin Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("pending_ebooks.php")
    Call<PendingEBooksByAdminResponse> pendingebooks(@Field("action") String action, @Field("userId") String userId);



    //Add Event Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_event.php")
    Call<AddEventResponse> addevent(@Field("action") String action, @Field("event_name") String event_name, @Field("event_subject") String event_subject, @Field("speciality") String speciality, @Field("busines") String busines, @Field("isFree") String isFree, @Field("price") String price, @Field("onprice") String onprice, @Field("description") String description, @Field("userId") String userId, @Field("status") String status, @Field("event_role") String event_role, @Field("pay_status") String pay_status, @Field("isFav") String isFav, @Field("resume") String resume, @Field("userfrontfile") String userfrontfile);


    //ViewMy Event Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("my_events.php")
    Call<MyEventResponse> myevent(@Field("action") String action, @Field("userId") String userId);


    //ViewMy BookedEvent Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("book_events.php")
    Call<BookedEventResponse> bookedevent(@Field("action") String action, @Field("userId") String userId);


    //ViewMy accepted_events Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("accepted_events.php")
    Call<AcceptedEventResponse> acceptedevent(@Field("action") String action, @Field("userId") String userId);


    //ViewMy pending_events Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("pending_events.php")
    Call<PendingEventResponse> pendingevent(@Field("action") String action, @Field("userId") String userId);


    //Update events Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("update_event.php")
    Call<UpdateEventResponse> updateevent(@Field("action") String action, @Field("id") String id, @Field("event_name") String event_name, @Field("event_subject") String event_subject,@Field("speciality") String speciality,@Field("business") String business,@Field("isFree") String isFree,@Field("price") String price,@Field("onprice") String onprice,@Field("description") String description,@Field("userId") String userId,@Field("status") String status,@Field("event_role") String event_role,@Field("pay_status") String pay_status,@Field("isFav") String isFav,@Field("resume") String resume,@Field("userfrontfile") String userfrontfile);

    //Delete events Response
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("delete_event.php")
    Call<DeleteEventResponse> deleteevent(@Field("action") String action, @Field("id") String id);



    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("imagedemo.php")
    Call<DemoResponse> adddemoimage(@Field("action") String action, @Field("image_name") String image_name, @Field("hotel_image") String hotel_image);



    /*

    //Login Response
    @GET("users/login")
    Call<LoginResponse> login_user(@Query("mobileno") String mobile, @Query("password") String password, @Query("apikey") String apikey, @Query("imei") String imei);

    //Generate OTP Response
    @GET("users/generateotp")
    Call<GenerateOtpResponse> generate_otp(@Query("mobile") String mobile, @Query("apikey") String apikey);

    //Verify OTP Response
    @GET("users/verifyotp")
    Call<VerifyOtpResponse> verify_otp(@Query("mobileno") String mobile, @Query("apikey") String apikey, @Query("otp") String otp);


    //Category Response
    @GET("product/catfetch")
    Call<CategoryResponse> get_category(@Query("apikey") String apikey);

    //All Product Response
    @GET("product/prodfetch")
    Call<AllProductResponse> get_all_product(@Query("apikey") String apikey);

    //Category Wise Product Response
    @GET("product/catprodfetch")
    Call<ProductCatWiseResponse> cat_product(@Query("apikey") String apikey, @Query("catid") String catid);

    //Slider Response
    @GET("product/sliderfetch")
    Call<SliderResponse> slide_images(@Query("apikey") String apikey);

    //Forget Password Response
    @GET("product/forgetpassword")
    Call<ForgetPassResponse> forget_pass(@Query("apikey") String apikey, @Query("mobileno") String mobileno);

    //Change Password Response
    @GET("product/changepassword")
    Call<ChangePasswordResponse> change_password(@Query("apikey") String apikey, @Query("id") String id, @Query("oldpswd") String oldpswd, @Query("newpswd") String newpswd);

    //Fetch Profile Response
    @GET("product/profilefetch")
    Call<FetchProfileResponse>fetch_profile(@Query("apikey") String apikey, @Query("id") String id);


    //Profile update response
    @GET("product/updateprofile")
    Call<ProfileupdateResponse> profile_update(@Query("apikey") String apikey, @Query("id") String id, @Query("name") String name, @Query("email") String email);

    //Add Cart response
    @GET("product/addcartitem")
    Call<AddCartResponse> add_cart(@Query("apikey") String apikey, @Query("userid") String userid, @Query("IMEI") String IMEI, @Query("productid") String productid, @Query("productcount") String productcount);

    // View User Cart response
    @GET("product/usercart")
    Call<UsercartResponse> cart(@Query("apikey") String apikey, @Query("userid") String userid, @Query("IMEI") String IMEI);

    // View User Cart response
    @GET("product/updateitemcart")
    Call<UpdateCartResponse> update_cart(@Query("apikey") String apikey, @Query("userid") String userid, @Query("IMEI") String IMEI, @Query("productid") String productid, @Query("productcount") String productcount);

    //Remove Cart Response
    @GET("product/removeitemcart")
    Call<RemovecartResponse> remove_cart(@Query("apikey") String apikey, @Query("userid") String userid, @Query("IMEI") String IMEI, @Query("productid") String productid, @Query("productcount") String productcount);

    //Product Count Response
    @GET("product/productcount")
    Call<ProductCountResponse> cart_count(@Query("userid") String userid, @Query("apikey") String apikey, @Query("IMEI") String IMEI);


    //Contact Response
    @GET("product/contactinfo")
    Call<ContactResponse> contact_us(@Query("apikey") String apikey);

    //Order History Response
    @GET("product/orderhistory")
    Call<OrderHistoryResponse> view_orders(@Query("userid") String userid, @Query("apikey") String apikey);

    //Monthly Savings Response
    @GET("product/cust_month_profite")
    Call<MonthlySavingsResponse> monthly_saving(@Query("userid") String userid, @Query("apikey") String apikey);

    //Order Products Response
    @GET("product/orderprodhistory")
    Call<OrderProductsResponse> order_products(@Query("apikey") String apikey, @Query("orderid") String orderid);

    //Order Cancel Response
    @GET("product/cancelorder")
    Call<CancelOrderResponse> order_cancel(@Query("userid") String userid, @Query("apikey") String apikey, @Query("orderid") String orderid);

    //Wallet Response
    @GET("product/updatewalletbalance")
    Call<WalletResponse> add_money(@Query("id") String id, @Query("apikey") String apikey, @Query("wallet") String wallet);

    //Wallet pay Response
    @GET("product/deductwalletbalance")
    Call<WalletPayResponse> pay_wallet(@Query("id") String id, @Query("apikey") String apikey, @Query("wallet") String wallet);

    //Download pdf Response
    @GET("product/generatpdfView")
    Call<DownloadResponse> pdf_download(@Query("apikey") String apikey, @Query("cust_id") String cust_id, @Query("order_id") String order_id);

    //Holiday Response
    @GET("product/holifetch")
    Call<HolidayResponse> holiday_date(@Query("apikey") String apikey);

    //Add Order Response
    @GET("product/adddelivery")
        Call<AddOrderResponse> add_order(@Query("userid") String userid, @Query("apikey") String apikey, @Query("orderdate") String orderdate, @Query("deliverydate") String deliverydate, @Query("totalamount") String totalamount,
                                         @Query("address") String address, @Query("pay_type") String pay_type, @Query("deliverycharges") String deliverycharges, @Query("tran_id") String tran_id, @Query("latitude") String latitude, @Query("longitude") String longitude);

    //check location response
    @GET("product/check_location")
    Call<CheckLocationResponse> checklocation(@Query("lat") String lat, @Query("lon") String lon, @Query("apikey") String apikey);

*/
}
