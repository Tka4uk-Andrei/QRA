package com.example.qra.model.webRequests.requests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.webRequests.WebRequestException;

import static android.util.Base64.DEFAULT;

public class BasicRequest {

    /**
     * @return the base64 code \\
     * @autor : Ekaterina Novoselova
     */
    public static String base64Encode(String phone, String password) {
        StringBuilder authorizationSB = new StringBuilder();
        authorizationSB.append(phone);
        authorizationSB.append(":");
        authorizationSB.append(password);
        String authorization = authorizationSB.toString();

        byte[] bytes = authorization.getBytes();
        String base64Code = android.util.Base64.encodeToString(bytes, DEFAULT);

        authorizationSB.delete(0, authorizationSB.capacity());
        authorizationSB.append("Basic ");
        authorizationSB.append(base64Code);
        base64Code = authorizationSB.toString();
        return base64Code;
    }

    // todo documentation
    public static Message getExceptionMessage(Handler exceptionHandler, WebRequestException exception){
        Message exceptionMessage = exceptionHandler.obtainMessage();

        Bundle bundle = new Bundle();
        // todo constants
        bundle.putParcelable("exception", exception);
        exceptionMessage.setData(bundle);

        return exceptionMessage;
    }
}
