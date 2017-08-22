package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by krishnapratapsingh on 14/08/17.
 */

public class UserBhamashahBean{



    String AADHAR_ID;
    String FAMILYIDNO;
    String FATHER_NAME_ENG;
    String FATHER_NAME_HND;
    String M_ID;
    String SPOUCE_NAME_HND;
    String SPOUCE_NAME_ENG;
    String MOTHER_NAME_ENG;
    String MOTHER_NAME_HND;


    public String getAADHAR_ID() {
        return AADHAR_ID;
    }

    public void setAADHAR_ID(String AADHAR_ID) {
        this.AADHAR_ID = AADHAR_ID;
    }

    public String getFAMILYIDNO() {
        return FAMILYIDNO;
    }

    public void setFAMILYIDNO(String FAMILYIDNO) {
        this.FAMILYIDNO = FAMILYIDNO;
    }

    public String getFATHER_NAME_ENG() {
        return FATHER_NAME_ENG;
    }

    public void setFATHER_NAME_ENG(String FATHER_NAME_ENG) {
        this.FATHER_NAME_ENG = FATHER_NAME_ENG;
    }

    public String getFATHER_NAME_HND() {
        return FATHER_NAME_HND;
    }

    public void setFATHER_NAME_HND(String FATHER_NAME_HND) {
        this.FATHER_NAME_HND = FATHER_NAME_HND;
    }

    public String getM_ID() {
        return M_ID;
    }

    public void setM_ID(String m_ID) {
        M_ID = m_ID;
    }

    public String getSPOUCE_NAME_HND() {
        return SPOUCE_NAME_HND;
    }

    public void setSPOUCE_NAME_HND(String SPOUCE_NAME_HND) {
        this.SPOUCE_NAME_HND = SPOUCE_NAME_HND;
    }

    public String getSPOUCE_NAME_ENG() {
        return SPOUCE_NAME_ENG;
    }

    public void setSPOUCE_NAME_ENG(String SPOUCE_NAME_ENG) {
        this.SPOUCE_NAME_ENG = SPOUCE_NAME_ENG;
    }

    public String getMOTHER_NAME_ENG() {
        return MOTHER_NAME_ENG;
    }

    public void setMOTHER_NAME_ENG(String MOTHER_NAME_ENG) {
        this.MOTHER_NAME_ENG = MOTHER_NAME_ENG;
    }

    public String getMOTHER_NAME_HND() {
        return MOTHER_NAME_HND;
    }

    public void setMOTHER_NAME_HND(String MOTHER_NAME_HND) {
        this.MOTHER_NAME_HND = MOTHER_NAME_HND;
    }
}
