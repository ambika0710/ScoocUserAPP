package com.scooc.scooc.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;


import com.scooc.scooc.model.DriverBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.DriverDetailsParser;
import com.scooc.scooc.net.utils.WSConstants;

public class DriverDetailsInvoker extends BaseInvoker{

    public DriverDetailsInvoker(HashMap<String, String> urlParams,
                            JSONObject postData) {
        super(urlParams, postData);
    }

    public DriverBean invokeDriverDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.REQUEST_STATUS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        DriverBean driverBean = null;
        if (wsResponseString.equals("")) {
            return driverBean = null;
        } else {
            driverBean = new DriverBean();
            DriverDetailsParser driverDetailsParser = new DriverDetailsParser();
            driverBean = driverDetailsParser.parseDriverDetailsResponse(wsResponseString);
            return driverBean;
        }
    }
}
