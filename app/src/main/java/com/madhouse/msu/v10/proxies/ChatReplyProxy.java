package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by rv software on 15-Aug-17.
 */

public class ChatReplyProxy implements KvmSerializable{

    String strLoginIdFrom;
    String strLoginIdTo;
    String strStatus;
    String strMsg;

    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return strLoginIdFrom;

            case 1:
                return strLoginIdTo;

            case 2:
                return strStatus;

            case 3:
                return strMsg;

            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 4;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub

        switch (arg0) {
            case 0:
                arg2.name = "strLoginIdFrom";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 1:
                arg2.name = "strLoginIdTo";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 2:
                arg2.name = "strStatus";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 3:

                arg2.name = "strMsg";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            default:
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                strLoginIdFrom= arg1.toString();
                break;

            case 1:
                strLoginIdTo= arg1.toString();
                break;

            case 2:
                strStatus= arg1.toString();
                break;

            case 3:
                strMsg= arg1.toString();
                break;

            default:
                break;
        }
    }


    public String getStrLoginIdFrom() {
        return strLoginIdFrom;
    }

    public void setStrLoginIdFrom(String strLoginIdFrom) {
        this.strLoginIdFrom = strLoginIdFrom;
    }

    public String getStrLoginIdTo() {
        return strLoginIdTo;
    }

    public void setStrLoginIdTo(String strLoginIdTo) {
        this.strLoginIdTo = strLoginIdTo;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrMsg() {
        return strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }
}
