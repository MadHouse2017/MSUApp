package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Rajveer on 14/08/2017.
 */

public class ChatUserDetailsProxy implements KvmSerializable {

    String strLoginId;

    public String getStrLoginId() {
        return strLoginId;
    }

    public void setStrLoginId(String strLoginId) {
        this.strLoginId = strLoginId;
    }

    @Override
    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:

                return strLoginId;

            default:
                return null;
        }
    }
    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 1;
    }
    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub

        switch (arg0) {
            case 0:

                arg2.name = "strLoginId";
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

                strLoginId = arg1.toString();
                break;

            default:
                break;
        }
    }

}
