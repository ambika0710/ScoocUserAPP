package com.scooc.scooc.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.RegistrationParser;
import com.scooc.scooc.net.utils.WSConstants;

public class RegistrationInvoker extends BaseInvoker {

    public RegistrationInvoker() {
        super();
    }

    public RegistrationInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public AuthBean invokeRegistrationWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.USER_REGISTRATION), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AuthBean authBean = null;
        if (wsResponseString.equals("")) {

            return authBean = null;
        } else {
            authBean = new AuthBean();
            RegistrationParser registrationParser = new RegistrationParser();
            authBean = registrationParser.parseRegistrationResponse(wsResponseString);
            return authBean;
        }
    }
}
