package com.scooc.scooc.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import com.scooc.scooc.model.UserBean;
import com.scooc.scooc.net.ServiceNames;
import com.scooc.scooc.net.WebConnector;
import com.scooc.scooc.net.parsers.UserInfoParser;
import com.scooc.scooc.net.utils.WSConstants;

public class UserInfoInvoker extends BaseInvoker {

    public UserInfoInvoker() {
        super();
    }

    public UserInfoInvoker(HashMap<String, String> urlParams,
                          JSONObject postData) {
        super(urlParams, postData);
    }

    public UserBean invokeUserInfoWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.USER_INFO), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        UserBean userBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
			registerBean.setWebError(true);*/
            return userBean = null;
        } else {
            userBean = new UserBean();
            UserInfoParser userInfoParser = new UserInfoParser();
            userBean = userInfoParser.parseUserInfoResponse(wsResponseString);
            return userBean;
        }
    }
}
