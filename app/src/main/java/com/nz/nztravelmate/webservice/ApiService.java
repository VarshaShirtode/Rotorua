package com.nz.nztravelmate.webservice;
import com.nz.nztravelmate.model.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "Accept:application/json",
            "Content-Type: application/json"
    })
    @POST(ApiConstants.GET_CITY)
    Call<UserResponse> getCityList();

  /*  @POST(ApiConstants.REGISTER_URL)
    Call<UserResponse> registerUser(@Body User user);

    @POST(ApiConstants.LOGIN_URL)
    Call<UserResponse> loginUser(@Body User user);

    @POST(ApiConstants.VIEW_USER)
    Call<UserResponse> viewUser(@Query("userId") String userId);

    @POST(ApiConstants.UPDATE_USER)
    Call<UserResponse> updateUser(@Body User user); //pending-500-Internal Server Error

    @POST(ApiConstants.ADD_CONTACTS)
    Call<UserResponse> addContacts(@Body Contacts contacts);

    @POST(ApiConstants.GET_CONTACTS)
    Call<UserResponse> getContacts(@Query("userId") String userId);

    @POST(ApiConstants.GET_ALARMS)
    Call<UserResponse> getAlarms(@Query("userId") String userId);

    @POST(ApiConstants.PANIC_ALERT)
    Call<UserResponse> sendPanic(@Query("userId") String userId);

    @POST(ApiConstants.panicAlertToEmails)
    Call<UserResponse> sendpanicAlertToEmails(@Body PanicAlert panicAlert);

    @FormUrlEncoded
    @POST(ApiConstants.DELETE_ALARM)
    Call<UserResponse> deleteAlarm(@Field("alarmId[]") ArrayList<Integer> alarmId);//@Query("alarmId") int[] alarmId);

    @POST(ApiConstants.ADD_ALARMS)
    Call<UserResponse> addAlarms(@Body Alarms alarms);

    @POST(ApiConstants.UPDATE_CONTACT)
    Call<UserResponse> updateContact(@Body Contacts contacts);

    @POST(ApiConstants.UPDATE_ALARM)
    Call<UserResponse> updateAlarm(@Body Alarms alarms);

    @FormUrlEncoded
    @POST(ApiConstants.DELETE_CONTACT)
    Call<UserResponse> deleteContact(@Field("contactId[]") ArrayList<Integer> contactId);//@Query("contactId") String contactId);

    @POST(ApiConstants.VIEW_CONTACT)
    Call<UserResponse> viewContact(@Query("contactId") String contact);

    @POST(ApiConstants.SEND_NOTIFICATION)
    Call<UserResponse> sendAlerts(@Body Alert alert);

    @POST(ApiConstants.GET_NOTIFICATION_HISTORY)
    Call<UserResponse> getStartNotifications(@Query("userId") String id);

    @POST(ApiConstants.GET_NOTIFICATION_HISTORY)
    Call<UserResponse> getNextPageNotifications(@Body PagingItem pagingItem);

    @POST(ApiConstants.UPDATE_SETTING)
    Call<UserResponse> updateSetting(@Body Setting settings);

    @POST(ApiConstants.GET_SETTING)
    Call<UserResponse> getSetting(@Query("userId") String id);

    @FormUrlEncoded
    @POST(ApiConstants.DELETE_NOTIFICATION)
    Call<UserResponse> deleteNotification(@Field("notificationId[]") ArrayList<Integer> notificationId);//@Body UserResponse settings);

    @POST(ApiConstants.REQUEST_API)
    Call<UserResponse> approveContact(@Body Contacts contact);

    @POST(ApiConstants.UPDATE_LOCATION)
    Call<UserResponse> updateLocation(@Body LocationUpdate locationUpdate);

    @POST(ApiConstants.ALARM_RESPONSE)
    Call<UserResponse> sendAlarmResponse(@Body AlarmResponse alarmResponse);

    @POST(ApiConstants.FALSE_ALARM_MAIL)
    Call<UserResponse> sendAlarmMails(@Body FalseAlarm user);

    @POST(ApiConstants.FORGOT_PASSCODE)
    Call<UserResponse> forgotPasscode(@Query("userId") String userId);

    @POST(ApiConstants.ENABLE_ALARMS)
    Call<UserResponse> enableAlarms(@Query("userId") String userId);

    @POST(ApiConstants.RESET_PASSWORD)
    Call<UserResponse> resetPassword(@Query("email") String userEmail);

    @POST(ApiConstants.GET_TRUSTEES)
    Call<UserResponse> getTrustees(@Query("userId") String userId);

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_SUBSCRIPTION)
    Call<UserResponse> updateSubscription(@Field("userId") String userId,
                                          @Field("subscription_end_date") String subscription_end_date,
                                          @Field("is_subscribed") String is_subscribed);*/
}
