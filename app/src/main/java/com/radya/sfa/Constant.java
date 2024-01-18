package com.radya.sfa;

/**
 * Created by aderifaldi on 2017-12-24.
 */

public class Constant {

    public static final String DB_NAME = "android-master-db";
    public static final String PREFERENCE_NAME = "android-master-preference";
    public static final String SERIALIZABLE_NAME = "serializable_data";

    public static final int SPLASH_DELAY = 500;
    public static final String RP = "Rp. ";
    public static final String JSON_CONFIG_PATH = "json/config.json";
    public static int MIN_PASSWORD = 6;
    public static int LIMIT_GIRO_INPUT = 1;

    public class Validation {
        public static final int PASSWORD_MINIMUM = 8;
        public static final String EMAIL_ADDRESS_EXPRESSION = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    }

    public class Bundle {
        public static final String PAGE = "PAGE";
        public static final String ASSIGNMENT_ID = "ASSIGNMENT_ID";
        public static final String KEYWORD = "KEYWORD";
        public static final String POSITION = "POSITION";
        public static final String SURVEY_URL = "SURVEY_URL";
    }

    public class Data {
        public static final String NULL = "null";
        public static final String EMPTY_STRING = "";
        public static final String POSITIVE_BUTTON = "Ya";
        public static final String NEGATIVE_BUTTON = "Tidak";
        public static final String LOCATION_JOB = "LOCATION_JOB";

        public class Agent {
            public static final String DRIVER = "DRIVER";
            public static final String SPV = "SPV";
            public static final String SALES = "SALES";
        }

        public class InvoiceType {
            public static final int CASH_BACK = 0;
            public static final int INVOICE = 1;
        }

        public class PaymentMethod {
            public static final String CASH = "Cash";
            public static final String TRANSFER = "Transfer";
            public static final String GIRO = "Giro";
        }

        public class AssignmentCode {
            public static final String AGENT_ARRIVED = "AGENT_ARRIVED";
            public static final String AGENT_STARTED = "AGENT_STARTED";
            public static final String TASK_COMPLETED = "TASK_COMPLETED";
            public static final String TASK_FAILED = "TASK_FAILED";
            public static final String TASK_RECEIVED = "TASK_RECEIVED";
            public static final String TASK_NO_OTP = "TASK_NO_OTP";
            public static final String TASK_NO_PAYMENT = "TASK_NO_PAYMENT";
        }

        public class BankAccount {
            public static final String BRI = "BRI";
            public static final String MANDIRI = "Mandiri";
            public static final String BCA = "BCA";

            public static final String BRI_CODE = "002";
            public static final String MANDIRI_CODE = "003";
            public static final String BCA_CODE = "004";
        }

        public class AssignmentCreateType {
            public static final int GASOLINE = 1;
        }

        public class AssignmentActivity {
            public static final int INVOICE = 1;
            public static final int PRODUCT = 2;
            public static final int SURVEY = 3;
        }
    }

    public class DateFormat {
        public static final String XSHORT = "HH:mm";
        public static final String SHORT = "dd/MM/yyyy";
        public static final String SHORT_2 = "yyyy-MM-dd";
        public static final String SHORT_3 = "dd/MM/yyyy HH:mm";
        public static final String MEDIUM = "EEE, dd-MM-yyyy";
        public static final String MEDIUM_2 = "EEE, dd MMM yyyy";
        public static final String SERVER = "yyyy-MM-dd'T'HH:mm:ss";
    }

    public class TimeFormat {
        public static final String SHORT = "HH";
        public static final String MEDIUM = "HH:mm";
        public static final String LONG = "HH:mm:ss";
    }

    public class Api {
        public static final int TIMEOUT = 1;

        public static final String DEVELOPMENT = "http://salesmapapp.azurewebsites.net/api/";
        public static final String DEVELOPMENT_2 = "https://api-aru.azurewebsites.net/api/";
        public static final String PRODUCTION = "http://mobiforce.azurewebsites.net/api/";
        public static final String PRODUCTION_2 = "https://qionsalesforce.azurewebsites.net/api/";

        public static final String BASE_URL = DEVELOPMENT_2;

        public static final int LIMIT = 10;

        public class Code {
            public static final int SUCCESS = 200;
            public static final int SERVER_ERROR = 500;
            public static final int REQUEST_TIMEOUT = 504;
            public static final int LOGGED_OTHER_DEVICE = 406;
            public static final int INVALID_TOKEN = 401;
            public static final int ALREADY_CHECK_IN = 202;
        }

        public class EndPoint {

            public class Contact {
                public static final String LIST = "contact";
                public static final String DETAIL = "contact";
                public static final String ADD = "customer/add";
            }

            public class Assignment {
                public static final String LIST = "Assignment";
                public static final String CALENDAR = "Assignment/calendar/{from}/{to}";
                public static final String DETAIL = "Assignment/{id}";
                public static final String START = "Assignment/{assignment_id}/start";
                public static final String COMPLETE = "Assignment/{assignment_id}/complete";
                public static final String SEARCH = "Assignment/search";
                public static final String GASOLINE = "Assignment/create";
                public static final String REASON = "Assignment/reason";
                public static final String ALL = "Assignment/all";
            }

            public class User {
                public class Agent {
                    public static final String LIST = "user/agent";
                }

                public static final String ACTIVITY = "user/activity";
            }

            public class Auth {
                public static final String LOGIN = "auth";
                public static final String LOGOUT = "auth/logout";
            }

            public class Attendance {
                public static final String CHECK = "Attendance/check";
                public static final String CHECK_IN = "Attendance/checkin";
                public static final String CHECK_OUT = "Attendance/checkout";
            }

            public class Invoice {
                public static final String OTP = "invoice/{assignment_id}/otp";
                public static final String PAYMENT = "invoice/payment";
            }

            public class Product {
                public static final String LIST = "product";
                public static final String ORDER = "product/order";
            }

            public class News {
                public static final String LIST = "news";
            }

            public class Dashboard {
                public static final String DASHBOARD = "dashborad";
            }

            public class Report {
                public static final String LIST = "Report";
                public static final String DETAIL = "Report/{id}";
            }

        }

        public class Params {

            public static final String LIMIT = "limit";
            public static final String OFFSET = "offset";
            public static final String DATE = "date";
            public static final String STATUS = "status";
            public static final String AUTHORIZATION = "X-Aru-Token";
            public static final String PASSWORD = "password";
            public static final String USERNAME = "username";
            public static final String KEYWORD = "keyword";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String USER_ID = "user_id";
            public static final String COMMENT = "comment";
            public static final String START_TIME = "start_time";
            public static final String STATE = "state";
            public static final String REMARKS = "remarks";
            public static final String ID = "id";
            public static final String ASSIGNMENT_ID = "assignment_id";
            public static final String LATITUDE_2 = "Latitude";
            public static final String LONGITUDE_2 = "Longitude";
            public static final String REMARKS_2 = "Remarks";
            public static final String FROM = "from";
            public static final String TO = "to";
            public static final String ASSIGNMENT_ID_2 = "AssignmentId";
            public static final String INVOICE_CODE = "InvoiceCode";
            public static final String INVOICE_AMOUNT = "InvoiceAmount";
            public static final String PAYMENT_AMOUNT = "PaymentAmount";
            public static final String PAYMENT_DEBT = "PaymentDebt";
            public static final String PAYMENT_CHANNEL = "PaymentChannel";
            public static final String TRANSFER_AMOUNT = "TransferAmount";
            public static final String GIRO_AMOUNT = "GiroAmount";
            public static final String CASH_AMOUNT = "CashAmount";
            public static final String OTP = "Otp";
            public static final String TRANSFER_BANK = "TransferBank";
            public static final String GIRO_PHOTO = "GiroPhoto";
            public static final String GIRO_ATACHMENT = "GiroPhotoAttachment";
            public static final String GIRO_ATACHMENT_1 = "GiroPhotoAttachment1";
            public static final String GIRO_ATACHMENT_2 = "GiroPhotoAttachment2";
            public static final String GIRO_ATACHMENT_3 = "GiroPhotoAttachment3";
            public static final String GIRO_ATACHMENT_4 = "GiroPhotoAttachment4";
            public static final String GIRO_NUMBER = "GiroNumber";
            public static final String ROLE = "role";
            public static final String MODE = "mode";
            public static final String DISTANCE = "distance";
            public static final String ADDRESS = "Address";
            public static final String LITER = "Liter";
            public static final String EVENTDATE = "EventDate";
            public static final String ATTACHMENT = "Attachment";
            public static final String TYPE = "type";
            public static final String GIRO_DUE_DATE = "GiroDueDate";

            public static final String GIRO_NUMBER1 = "GiroNumber1";
            public static final String GIRO_PHOTO1 = "GiroPhoto1";
            public static final String GIRO_AMOUNT1= "GiroAmount1";
            public static final String GIRO_DUE_DATE1 = "GiroDueDate1";

            public static final String GIRO_NUMBER2 = "GiroNumber2";
            public static final String GIRO_PHOTO2 = "GiroPhoto2";
            public static final String GIRO_AMOUNT2= "GiroAmount2";
            public static final String GIRO_DUE_DATE2 = "GiroDueDate2";

            public static final String GIRO_NUMBER3 = "GiroNumber3";
            public static final String GIRO_PHOTO3 = "GiroPhoto3";
            public static final String GIRO_AMOUNT3= "GiroAmount3";
            public static final String GIRO_DUE_DATE3 = "GiroDueDate3";

            public static final String GIRO_NUMBER4 = "GiroNumber4";
            public static final String GIRO_PHOTO4 = "GiroPhoto4";
            public static final String GIRO_AMOUNT4 = "GiroAmount4";
            public static final String GIRO_DUE_DATE4 = "GiroDueDate4";
            public static final String PAYMENT_METHOD = "payment_method";
            public static final String TOTAL_PAYMENT = "total_payment";
            public static final String STATUS_CODE = "status_code";
            public static final String STORE_PHOTO = "StorePhoto";
            public static final String PHOTO_ID_CARD = "PhotoIdCard";
            public static final String PHOTO_NPWP = "PhotoNPWP";
            public static final String PHOTO_BRANDING = "BrandingPhoto";
            public static final String STORE_NAME = "StoreName";
            public static final String STORE_ADDRESS = "StoreAddress";
            public static final String STORE_CITY = "StoreCity";
            public static final String STORE_DISTRICT = "StoreDistrict";
            public static final String STORE_VILLAGE = "StoreVillage";
            public static final String STORE_STATUS = "StoreStatus";
            public static final String STORE_AGE = "StoreAge";
            public static final String STORE_TYPE = "StoreType";
            public static final String WIDTH_ROAD = "WidthRoad";
            public static final String BRANDING_NAME = "BrandingName";
            public static final String STORE_LATITUDE = "StoreLatitude";
            public static final String STORE_LONGITUDE = "StoreLongitude";
            public static final String NOTE = "Note";
            public static final String OWNER_NAME = "OwnerName";
            public static final String OWNER_ADDRESS = "OwnerAddress";
            public static final String OWNER_CITY = "OwnerCity";
            public static final String OWNER_DISTRICT = "OwnerDistrict";
            public static final String OWNER_VILLAGE = "OwnerVillage";
            public static final String OWNER_PHONE = "OwnerPhoneNumber";
            public static final String PIC_NAME = "OwnerPhoneNumber";
            public static final String PIC_PHONE = "PICPhoneNumber";
            public static final String ASSIGNMENT_STATUS = "AssignmentStatus";
            public static final String REASON_CODE = "ReasonCode";
            public static final String PROSESSED_TIME = "ProcessedTime";
        }
    }

    public class Cache {
        public static final String CACHE_TASK_LIST = "CACHE_TASK_LIST";
        public static final String CACHE_CONTACT_LIST = "CACHE_CONTACT_LIST";
    }

    public class Page {
        public static final int LOGIN = 1;
        public static final int DASHBOARD = 2;
    }

    public class Preference {
        public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
        public static final String REQUEST_CODE = "REQUEST_CODE";
        public static final String ASSIGNMENT_COMPLETE = "ASSIGNMENT_COMPLETE";

        public class RequestPermission {
            public static final String LOCATION_GRANTED = "LOCATION_GRANTED";
            public static final String CALL_PHONE_GRANTED = "CALL_PHONE_GRANTED";
        }

        public class User {
            public static final String FCM_TOKEN = "FCM_TOKEN";
            public static final String IS_LOGIN = "IS_LOGIN";
            public static final String AUTH_TOKEN = "AUTH_TOKEN";
            public static final String LATITUDE = "LATITUDE";
            public static final String LONGITUDE = "LONGITUDE";
            public static final String USER_NAME = "USER_NAME";
            public static final String USER_CODE = "USER_CODE";
            public static final String USER_ROLE_CODE = "USER_ROLE_CODE";
            public static final String USER_ROLE_NAME = "USER_ROLE_NAME";
            public static final String WORK_LOCATION_UPDATE = "WORK_LOCATION_UPDATE";
            public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
            public static final String REQUEST_CODE = "REQUEST_CODE";
        }

        public class Config {
            public static final String LOCATION_UPDATE_INTERVAL = "LOCATION_UPDATE_INTERVAL";
        }
    }

    public class Permission {
        public static final int LOCATION = 1;
        public static final int PHONE = 2;
        public static final int CAMERA = 3;
    }

    public class Key {
        public class AES {
            public static final String A = "12344321";
            public static final String R = "23411324";
        }

        public class SSL {
            public static final String TRUST = "trustKey";
        }
    }

    public class Menu {
        public static final String DASHBOARD = "Dashboard";
        public static final String APPOINTMENT = "Kunjungan";
        public static final String CONTACT = "Kontak";
        public static final String SETTINGS = "Pengaturan";
        public static final String LOGOUT = "Keluar";
        public static final String NEWS = "Arahan";
        public static final String REPORT = "Laporan";
    }

    public class SyncType {
        public static final String CHECK_IN = "check-in";
        public static final String START_ASSIGNMENT = "start-assignment";
        public static final String INPUT_INVOICE_PAYMENT = "input-invoice-payment";
        public static final String ASSIGNMENT_COMPLETE = "assignment-complete";
        public static final String ORDER = "order";
    }
}
