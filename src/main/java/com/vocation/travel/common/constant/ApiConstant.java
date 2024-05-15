package com.vocation.travel.common.constant;

/**
 * Container api.
 *
 * @author Minh An
 * */
public class ApiConstant {
    //------------------------------------------- CRUD -----------------------------------------------------------------
    public final static String API_CREATE = "/create";
    public final static String API_UPDATE = "/update";
    public final static String API_DELETE = "/delete";
    public final static String API_READ = "/read";
    public final static String API_GET = "/get";
    public final static String API_ADD = "/add";

    //------------------------------------------- API AUTH -------------------------------------------------------------
    public final static String API_AUTH = "/auth";
    public final static String API_LOGIN = "/login";
    public final static String API_REGISTER = "/register";
    public final static String API_AUTHENTICATION = "/authentication";

    //------------------------------------------- API USER -------------------------------------------------------------
    public final static String API_USER = "/user";

    //------------------------------------------- API ADDRESS ----------------------------------------------------------
    public final static String API_ADDRESS = "/address";
    public final static String API_DISTRICT = "/district";
    public final static String API_PROVINCE = "/province";
    public final static String API_WARD = "/ward";

    //------------------------------------------- API TRIP -------------------------------------------------------------
    public final static String API_TRIP = "/trip";

    //------------------------------------------- API INFORMATION ------------------------------------------------------
    public final static String API_INFORMATION = "/information";

    //------------------------------------------- API MEMBER -----------------------------------------------------------
    public final static String API_MEMBER = "/member";

    //------------------------------------------- API MEMBER -----------------------------------------------------------
    public final static String API_FRIEND = "/friend";

    //------------------------------------------- API NOTIFICATION -----------------------------------------------------
    public final static String API_NOTIFICATION = "/notification";
    public final static String API_NOTIFICATION_FRIEND = API_NOTIFICATION + API_FRIEND + API_ADD;
}