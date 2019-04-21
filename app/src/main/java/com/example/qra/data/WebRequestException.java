package com.example.qra.data;

public class WebRequestException extends Exception {
    private String message;

    public WebRequestException(Exception e){
   //     if(e.printStackTrace()){
     //       message = "Check not found";
       // }
    }

    public String showMessage(){
        return message;
    }
}
