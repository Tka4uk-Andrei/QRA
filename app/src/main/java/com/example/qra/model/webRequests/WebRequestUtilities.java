package com.example.qra.model.webRequests;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import static android.util.Base64.DEFAULT;

/**
 * Class what provides additional functionality to do web requests
 */
@Deprecated
public class WebRequestUtilities {

    /**
     * @return the base64 code \\
     * @autor : Ekaterina Novoselova
     */
    @Deprecated
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

    /**
     * Method that forms Message with WebRequestException
     * @param exceptionHandler where user forms WebRequestException
     * @param exception Exception that we send
     * @return formed Message with WebRequestException
     */
    @Deprecated
    public static Message getExceptionMessage(Handler exceptionHandler, WebRequestException exception){
        Message exceptionMessage = exceptionHandler.obtainMessage();

        Bundle bundle = new Bundle();
        // todo constants
        bundle.putParcelable("exception", exception);
        exceptionMessage.setData(bundle);

        return exceptionMessage;
    }
}
