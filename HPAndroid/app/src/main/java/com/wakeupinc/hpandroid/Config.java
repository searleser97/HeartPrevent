package com.wakeupinc.hpandroid;

/**
 * Created by vaioubuntu on 4/23/16.
 */
public interface Config {

    // used to share GCM regId with application server - using php app server
    //static final String APP_SERVER_URL = "http://192.168.0.8:8080/GcmJava/GCMNotification?shareRegId=APA91bEQ2mPP3emZr4s7Q23ojy5wBVoiy_2S97MtG_F6KQEMdUNfnkbZvwXrQHghilbX09uXl3cjgsrsLQGmCkC4ksK6-ENRES7dQ17sn-HTa7LXsAi8UwTXN4-wizjjt4ZWjN7j11iD";

    // GCM server using java
     static final String APP_SERVER_URL ="http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

    // Google Project Number
    static final String GOOGLE_PROJECT_ID = "1014351251785";
    static final String MESSAGE_KEY = "message";
    static final String serverUrl="192.168.0.8:8080";
//	static final String Sender_Id="397439984427";

}