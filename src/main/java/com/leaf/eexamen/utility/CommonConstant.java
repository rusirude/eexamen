package com.leaf.eexamen.utility;

public class CommonConstant {
    /*------------ SYSTEM USER AND ROLE -------------------*/
    public static String SYSTEM = "SYSTEM";
	/*------------ Response Code -------------------*/

    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_FAILED = "FAILED";
	
	/*------------ Status Cotegory Code -------------------*/

    public static final String STATUS_CATEGORY_DEFAULT = "DEFAULT";
    public static final String STATUS_CATEGORY_DELETE = "DELETE";
    public static final String STATUS_CATEGORY_EXAM = "EXAM";

     /*------------ Delete Status Code -------------------*/

    public static final String DELETE_STATUS_DELETE = "DELETE";

    /*------------ Default Status Code -------------------*/

    public static final String DEFAULT_STATUS_ACTIVE = "ACTIVE";
    public static final String DEFAULT_STATUS_INACTIVE = "INACTIVE";

    /*------------ Exam Status Code -------------------*/

    public static final String EXAM_STATUS_PENDING = "PENDING";
    public static final String EXAM_STATUS_START = "START";
    public static final String EXAM_STATUS_CLOSED = "CLOSED";


    /*------------ Password reset Request Status Code -------------------*/

    public static final String PASSWORD_REST_REQUEST_STATUS_ACTIVE = "REQUEST";
    public static final String PASSWORD_REST_REQUEST_STATUS_INACTIVE = "PRESET";
    
    /*------------ Grid Search Type -------------------*/

    public static final String GRID_SEARCH_LIST = "LIST";
    public static final String GRID_SEARC_COUNT = "COUNT";
    
    /*------------ Date Format and Time Zone -------------------*/
    
    public static final String SYSTEM_DATE_FORMAT = "dd/MM/yyyy";
    public static final String SYSTEM_DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm a";
    public static final String SYSTEM_DATE_TIME_2_FORMAT = "MM/dd/yyyy hh:mm a";
    public static final String SYSTEM_DATE_TIME_3_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String DATE_TIMEZONE_JACKSON = "Asia/Colombo";
//    public static final String DATE_TIMEZONE_JACKSON = "Europe/Brussels";

    /**Report JRXML path*/
    public static final String REPORT_PATH = "/reports/";
    
}
