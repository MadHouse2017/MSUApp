package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Created by krishnapratapsingh on 14/08/17.
 */

public class UserRegistrationProxy implements KvmSerializable {





    String PHOTO;
    String idType;
    String idNumber;
    String idPhoto;
    String userName;
    String DOB;
    String primMobileNo;
    String secMobileNo;
    String maritalStatus;
    String emailId;
    String password;
    String gcmId;
    String other;

    String AADHAR_ID;
    String MOTHER_NAME_ENG;
    String BHAMASHAH_ID;
    String SPOUCE_NAME_HND;
    String M_ID;
    String FAMILYIDNO;
    String FATHER_NAME_HND;
    String NAME_ENG;
    String FATHER_NAME_ENG;
    String GENDER;
    String NAME_HND;
    String SPOUCE_NAME_ENG;
    String MOTHER_NAME_HND;

    String USER_ROLE;
    String PREFERENCE_HOBBY;
    String HOBBY_PROFICIENCY;
    String ANS1;
    String ANS2;
    String ANS3;
    String ANS4;
    String ANS5;
    String IS_REGISTERED;
    String ADDRESS;





    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:

                return PHOTO;

            case 1:

                return idType;

            case 2:

                return idNumber;

            case 3:

                return idPhoto;

            case 4:

                return userName;


            case 5:

                return DOB;

            case 6:

                return primMobileNo;

            case 7:

                return secMobileNo;

            case 8:

                return maritalStatus;

            case 9:

                return emailId;

            case 10:

                return password;

            case 11:

                return gcmId;

            case 12:

                return other;

            case 13:

                return AADHAR_ID;

            case 14:

                return MOTHER_NAME_ENG;


            case 15:

                return BHAMASHAH_ID;

            case 16:

                return SPOUCE_NAME_HND;

            case 17:

                return M_ID;

            case 18:

                return FAMILYIDNO;

            case 19:

                return FATHER_NAME_HND;


            case 20:

                return NAME_ENG;

            case 21:

                return FATHER_NAME_ENG;

            case 22:

                return GENDER;

            case 23:

                return NAME_HND;

            case 24:

                return SPOUCE_NAME_ENG;

            case 25:

                return MOTHER_NAME_HND;

            case 26:

                return USER_ROLE;

            case 27:

                return PREFERENCE_HOBBY;

            case 28:

                return HOBBY_PROFICIENCY;

            case 29:

                return ANS1;

            case 30:

                return ANS2;

            case 31:

                return ANS3;

            case 32:

                return ANS4;

            case 33:

                return ANS5;

            case 34:

                return IS_REGISTERED;
            case 35:

                return ADDRESS;





            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 36;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable hashtable, PropertyInfo arg2) {
        // TODO Auto-generated method stub

        switch (arg0) {
            case 0:
                arg2.name = "PHOTO";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 1:
                arg2.name = "idType";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 2:
                arg2.name = "idNumber";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 3:

                arg2.name = "idPhoto";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 4:
                arg2.name = "userName";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 5:
                arg2.name = "DOB";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 6:
                arg2.name = "primMobileNo";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 7:
                arg2.name = "secMobileNo";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 8:
                arg2.name = "maritalStatus";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 9:
                arg2.name = "emailId";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 10:
                arg2.name = "password";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 11:
                arg2.name = "gcmId";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 12:
                arg2.name = "other";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 13:
                arg2.name = "AADHAR_ID";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 14:
                arg2.name = "MOTHER_NAME_ENG";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 15:
                arg2.name = "BHAMASHAH_ID";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 16:
                arg2.name = "SPOUCE_NAME_HND";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 17:
                arg2.name = "M_ID";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 18:
                arg2.name = "FAMILYIDNO";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 19:
                arg2.name = "FATHER_NAME_HND";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 20:
                arg2.name = "NAME_ENG";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 21:
                arg2.name = "FATHER_NAME_ENG";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 22:
                arg2.name = "GENDER";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 23:
                arg2.name = "NAME_HND";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 24:
                arg2.name = "SPOUCE_NAME_ENG";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 25:
                arg2.name = "MOTHER_NAME_HND";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 26:
                arg2.name = "USER_ROLE";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 27:
                arg2.name = "PREFERENCE_HOBBY";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 28:
                arg2.name = "HOBBY_PROFICIENCY";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 29:
                arg2.name = "ANS1";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 30:
                arg2.name = "ANS2";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;
            case 31:
                arg2.name = "ANS3";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 32:
                arg2.name = "ANS4";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 33:
                arg2.name = "ANS5";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 34:
                arg2.name = "IS_REGISTERED";
                arg2.type = PropertyInfo.STRING_CLASS;
                break;

            case 35:
                arg2.name = "ADDRESS";
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
                PHOTO= arg1.toString();
                break;

            case 1:
                idType= arg1.toString();
                break;

            case 2:
                idNumber= arg1.toString();
                break;

            case 3:
                idPhoto= arg1.toString();
                break;

            case 4:
                userName= arg1.toString();
                break;

            case 5:
                DOB= arg1.toString();
                break;

            case 6:
                primMobileNo= arg1.toString();
                break;

            case 7:
                secMobileNo= arg1.toString();
                break;

            case 8:
                maritalStatus= arg1.toString();
                break;

            case 9:
                emailId= arg1.toString();
                break;

            case 10:
                password= arg1.toString();
                break;

            case 11:
                gcmId= arg1.toString();
                break;

            case 12:
                other= arg1.toString();
                break;

            case 13:
                AADHAR_ID= arg1.toString();
                break;

            case 14:
                MOTHER_NAME_ENG= arg1.toString();
                break;

            case 15:
                BHAMASHAH_ID= arg1.toString();
                break;

            case 16:
                SPOUCE_NAME_HND= arg1.toString();
                break;

            case 17:
                M_ID= arg1.toString();
                break;

            case 18:
                FAMILYIDNO= arg1.toString();
                break;

            case 19:
                FATHER_NAME_HND= arg1.toString();
                break;

            case 20:
                NAME_ENG= arg1.toString();
                break;

            case 21:
                FATHER_NAME_ENG= arg1.toString();
                break;

            case 22:
                GENDER= arg1.toString();
                break;

            case 23:
                NAME_HND= arg1.toString();
                break;

            case 24:
                SPOUCE_NAME_ENG= arg1.toString();
                break;

            case 25:
                MOTHER_NAME_HND= arg1.toString();
                break;
            case 26:
                USER_ROLE= arg1.toString();
                break;
            case 27:
                PREFERENCE_HOBBY= arg1.toString();
                break;
            case 28:
                HOBBY_PROFICIENCY= arg1.toString();
                break;

            case 29:
                ANS1= arg1.toString();
                break;

            case 30:
                ANS2= arg1.toString();
                break;

            case 31:
                ANS3= arg1.toString();
                break;

            case 32:
                ANS4= arg1.toString();
                break;
            case 33:
                ANS5= arg1.toString();
                break;

            case 34:
                IS_REGISTERED= arg1.toString();
                break;
            case 35:
                ADDRESS= arg1.toString();
                break;


            default:
                break;
        }
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPrimMobileNo() {
        return primMobileNo;
    }

    public void setPrimMobileNo(String primMobileNo) {
        this.primMobileNo = primMobileNo;
    }

    public String getSecMobileNo() {
        return secMobileNo;
    }

    public void setSecMobileNo(String secMobileNo) {
        this.secMobileNo = secMobileNo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAADHAR_ID() {
        return AADHAR_ID;
    }

    public void setAADHAR_ID(String AADHAR_ID) {
        this.AADHAR_ID = AADHAR_ID;
    }

    public String getMOTHER_NAME_ENG() {
        return MOTHER_NAME_ENG;
    }

    public void setMOTHER_NAME_ENG(String MOTHER_NAME_ENG) {
        this.MOTHER_NAME_ENG = MOTHER_NAME_ENG;
    }

    public String getBHAMASHAH_ID() {
        return BHAMASHAH_ID;
    }

    public void setBHAMASHAH_ID(String BHAMASHAH_ID) {
        this.BHAMASHAH_ID = BHAMASHAH_ID;
    }

    public String getSPOUCE_NAME_HND() {
        return SPOUCE_NAME_HND;
    }

    public void setSPOUCE_NAME_HND(String SPOUCE_NAME_HND) {
        this.SPOUCE_NAME_HND = SPOUCE_NAME_HND;
    }

    public String getM_ID() {
        return M_ID;
    }

    public void setM_ID(String m_ID) {
        M_ID = m_ID;
    }

    public String getFAMILYIDNO() {
        return FAMILYIDNO;
    }

    public void setFAMILYIDNO(String FAMILYIDNO) {
        this.FAMILYIDNO = FAMILYIDNO;
    }

    public String getFATHER_NAME_HND() {
        return FATHER_NAME_HND;
    }

    public void setFATHER_NAME_HND(String FATHER_NAME_HND) {
        this.FATHER_NAME_HND = FATHER_NAME_HND;
    }

    public String getNAME_ENG() {
        return NAME_ENG;
    }

    public void setNAME_ENG(String NAME_ENG) {
        this.NAME_ENG = NAME_ENG;
    }

    public String getFATHER_NAME_ENG() {
        return FATHER_NAME_ENG;
    }

    public void setFATHER_NAME_ENG(String FATHER_NAME_ENG) {
        this.FATHER_NAME_ENG = FATHER_NAME_ENG;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getNAME_HND() {
        return NAME_HND;
    }

    public void setNAME_HND(String NAME_HND) {
        this.NAME_HND = NAME_HND;
    }

    public String getSPOUCE_NAME_ENG() {
        return SPOUCE_NAME_ENG;
    }

    public void setSPOUCE_NAME_ENG(String SPOUCE_NAME_ENG) {
        this.SPOUCE_NAME_ENG = SPOUCE_NAME_ENG;
    }

    public String getMOTHER_NAME_HND() {
        return MOTHER_NAME_HND;
    }

    public void setMOTHER_NAME_HND(String MOTHER_NAME_HND) {
        this.MOTHER_NAME_HND = MOTHER_NAME_HND;
    }

    public String getUSER_ROLE() {
        return USER_ROLE;
    }

    public void setUSER_ROLE(String USER_ROLE) {
        this.USER_ROLE = USER_ROLE;
    }

    public String getPREFERENCE_HOBBY() {
        return PREFERENCE_HOBBY;
    }

    public void setPREFERENCE_HOBBY(String PREFERENCE_HOBBY) {
        this.PREFERENCE_HOBBY = PREFERENCE_HOBBY;
    }

    public String getHOBBY_PROFICIENCY() {
        return HOBBY_PROFICIENCY;
    }

    public void setHOBBY_PROFICIENCY(String HOBBY_PROFICIENCY) {
        this.HOBBY_PROFICIENCY = HOBBY_PROFICIENCY;
    }

    public String getANS1() {
        return ANS1;
    }

    public void setANS1(String ANS1) {
        this.ANS1 = ANS1;
    }

    public String getANS2() {
        return ANS2;
    }

    public void setANS2(String ANS2) {
        this.ANS2 = ANS2;
    }

    public String getANS3() {
        return ANS3;
    }

    public void setANS3(String ANS3) {
        this.ANS3 = ANS3;
    }

    public String getANS4() {
        return ANS4;
    }

    public void setANS4(String ANS4) {
        this.ANS4 = ANS4;
    }

    public String getANS5() {
        return ANS5;
    }

    public void setANS5(String ANS5) {
        this.ANS5 = ANS5;
    }

    public String getIS_REGISTERED() {
        return IS_REGISTERED;
    }

    public void setIS_REGISTERED(String IS_REGISTERED) {
        this.IS_REGISTERED = IS_REGISTERED;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
}
