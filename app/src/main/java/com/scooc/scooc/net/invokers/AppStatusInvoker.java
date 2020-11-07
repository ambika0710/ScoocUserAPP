package com.scooc.scooc.net.invokers;


import com.scooc.scooc.model.DriverBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.DriverDetailsParser;
import com.scooc.scooc.net.utils.WSConstants;

import org.json.JSONObject;

import java.util.HashMap;




public class AppStatusInvoker extends BaseInvoker {

    public AppStatusInvoker() {
        super();
    }

    public AppStatusInvoker(HashMap<String, String> urlParams,
                        JSONObject postData) {
        super(urlParams, postData);
    }

    public DriverBean invokeAppStatusWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.APP_STATUS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        DriverBean driverBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return driverBean = null;
        } else {
            driverBean = new DriverBean();
            DriverDetailsParser driverDetailsParser = new DriverDetailsParser();
            driverBean = driverDetailsParser.parseDriverDetailsResponse(wsResponseString);
            return driverBean;
        }
    }
}
