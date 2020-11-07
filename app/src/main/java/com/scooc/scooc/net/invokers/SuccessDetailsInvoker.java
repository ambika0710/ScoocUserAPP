package com.scooc.scooc.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.SuccessBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.SuccessDetailsParser;
import com.scooc.scooc.net.utils.WSConstants;

public class SuccessDetailsInvoker extends BaseInvoker{

    public SuccessDetailsInvoker() {
        super();
    }

    public SuccessDetailsInvoker(HashMap<String, String> urlParams,
                           JSONObject postData) {
        super(urlParams, postData);
    }

    public SuccessBean invokeSuccessDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_COMPLETION_DETAILS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        SuccessBean successBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
			registerBean.setWebError(true);*/
            return successBean = null;
        } else {
            successBean = new SuccessBean();
            SuccessDetailsParser successDetailsParser = new SuccessDetailsParser();
            successBean = successDetailsParser.parseSuccessDetailsResponse(wsResponseString);
            return successBean;
        }
    }
}
