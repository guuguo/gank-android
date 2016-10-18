package com.guuguo.learnsave.util;

/**
 * Created by guodeqing on 7/2/16.
 */

public class Constant {
    /**
     * method
     */
    public static final String ALTERSERVICE = "AlterService";
    public static final String SEARCH = "Search";
    public static final String SONG = "Song";
    public static final String CHECKEMP = "CheckEmp";
    public static final String SHOWFOOD = "ShowFood";
    public static final String APPLYBILL = "ApplyBill";
    public static final String CHANGEROM = "ChangeRom";
    public static final String UPORDERDETAIL_MOBILE = "UpOrderDetail_Mobile";
    public static final String SHOWFOODTYPE = "ShowFoodType";
    public static final String SHOWFOODREMARK = "ShowFoodRemark";
    public static final String SHOWAREA = "ShowArea";
    public static final String SHOWROOMSTATE = "ShowRoomState";
    public static final String SHOWBILLDETAIL = "ShowBillDetail";
    public static final String OPENROOM = "OpenRoom";
    public static final String SHOWSERVICE = "ShowService";
    public static final String REGDEVICE = "RegDevice";
    public static final String GETFOODDETAIL = "GetFoodDetail";
    public static final String CHGSALEFOODSTAT = "ChgSaleFoodStat";
    public static final String REMINDERFOOD = "ReminderFood";


    /**
     * yunrui api method
     */
    public static final String GET_SESSION = "/iapi/getSession/";
    public static final String GET_ADVERTISEMENT = "/ipackage/getPackageInstance/";
    public static final String GET_GOODSLIST = "/igoods/getGoodsList/";
    public static final String GET_CATEGORYLIST = "/igoods/getCategoryList/";
    public static final String GET_DESKNOLIST = "/igoods/getDeskNoList/";
    public static final String SET_DESKNO = "/iapp/setDeskNo/";
    public static String getJson(String response) {
        return response.replaceAll("<.*?>", "");
    }


}