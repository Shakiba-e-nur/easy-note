package com.shakibaenur.easynote.util;

public class AppConstraints {
   public interface LogConstraints{
         final String COMMON_TAG="chk";
   }
   public interface ErrorMessage{
        final String ERROR_NOTE_TITLE="Title Required!";
        final String ERROR_NOTE_DESCRIPTION="Description Required!";
   }
   public interface IntentData{
       final String DATA_TITLE = "title";
       final String DATA_DESCRIPTION = "description";
       final String DATA_TIME = "data_time";
       final String DATA_DATE = "data_date";
       String DATA_ID = "data_id";
   }

}
