package com.scooc.scooc.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.RequestBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.RequestRideParser;
import com.scooc.scooc.net.utils.WSConstants;

public class RequestRideInvoker extends BaseInvoker {

    public RequestRideInvoker() {
        super();
    }

    public RequestRideInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public RequestBean invokeRequestRideWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.REQUEST_RIDE), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RequestBean requestBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return requestBean = null;
        } else {
            requestBean = new RequestBean();
            RequestRideParser requestRideParser = new RequestRideParser();
            requestBean = requestRideParser.parseRequestRideResponse(wsResponseString);
            return requestBean;
        }
    }
}
