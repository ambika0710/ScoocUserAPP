package com.scooc.scooc.net.invokers;

import com.scooc.scooc.model.AuthBean;
import com.scooc.scooc.model.BasicBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.BasicParser;
import com.scooc.scooc.net.parsers.LoginParser;
import com.scooc.scooc.net.utils.WSConstants;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgotChangePwdInvoker extends BaseInvoker {

    public ForgotChangePwdInvoker() {
        super();
    }

    public ForgotChangePwdInvoker(HashMap<String, String> urlParams,
                                  JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeLoginWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.Update_Password), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser newPasswordParser = new BasicParser();
            basicBean = newPasswordParser.parseBasicResponse(wsResponseString);
            return basicBean;

        }
    }
}
