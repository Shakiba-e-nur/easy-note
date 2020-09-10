package com.shakibaenur.easynote.util;

public class AppConstraints {
   public interface LogConstraints{
         String COMMON_TAG="chk";
   }
   public interface ErrorMessage{
        String ERROR_NOTE_TITLE="Title Required!";
        String ERROR_NOTE_DESCRIPTION="Description Required!";
   }
   public interface IntentData{
       String DATA_TITLE = "title";
       String DATA_DESCRIPTION = "description";
       String DATA_TIME = "data_time";
       String DATA_DATE = "data_date";
       String DATA_ID = "data_id";

   }
   public interface PreferenceData{
       String IS_LOGIN = "is_login";
       String VALUE_POSITIVE_LOGIN = "yes" ;
   }

}
