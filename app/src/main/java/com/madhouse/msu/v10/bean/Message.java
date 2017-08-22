package com.madhouse.msu.v10.bean;

import java.io.Serializable;

/**
 * Created by Krishna on 4/27/2016.
 */
public class Message implements Serializable {

    String MSGID;
    String CHATID;
    String MSGDATE;
    String MSGTITLE;
    String MSGDESC;
    String MSGREAD;
    String MSGTYP;
    String MSGIMG;
    String ERRORMSG;

    String SENDER_EMPID;
    String SENDER_GCMID;
    String SENDER_NAME;

    String SEND_BY;
    String SEND_TO;
    String EMP_DESIGNATION;

    public Message(){

    }

    /*public Message(String MSGDATE, String MSGTITLE, String MSGDESC, String MSGREAD,
                       String MSGTYP, String MSGIMG, String ERRORMSG,
                           String SENDER_EMPID,String SENDER_NAME,String SENDER_GCMID ) {
        //this.MSGID = MSGID;
        this.MSGDATE = MSGDATE;
        this.MSGTITLE = MSGTITLE;

        this.MSGDESC = MSGDESC;
        this.MSGREAD = MSGREAD;
        this.MSGTYP = MSGTYP;

        this.MSGIMG = MSGIMG;
        this.ERRORMSG = ERRORMSG;

        this.SENDER_EMPID =  SENDER_EMPID;
        this.SENDER_NAME = SENDER_NAME;
        this.SENDER_GCMID = SENDER_GCMID;

    }*/

    public Message(String MSGID, String CHATID, String MSGDATE, String MSGTITLE, String MSGDESC, String MSGREAD,
                   String MSGTYP, String MSGIMG, String ERRORMSG,
                   String SENDER_EMPID,String SENDER_NAME,String SENDER_GCMID ) {
        this.MSGID = MSGID;

        this.CHATID = CHATID;
        this.MSGDATE = MSGDATE;
        this.MSGTITLE = MSGTITLE;

        this.MSGDESC = MSGDESC;
        this.MSGREAD = MSGREAD;
        this.MSGTYP = MSGTYP;

        this.MSGIMG = MSGIMG;
        this.ERRORMSG = ERRORMSG;

        this.SENDER_EMPID =  SENDER_EMPID;
        this.SENDER_NAME = SENDER_NAME;
        this.SENDER_GCMID = SENDER_GCMID;

    }

   public Message(String CHATID, String MSGDATE, String MSGTITLE, String MSGDESC, String MSGREAD,
                   String MSGTYP, String MSGIMG, String ERRORMSG,
                   String SENDER_EMPID,String SENDER_NAME,String SENDER_GCMID,  String SEND_BY,
                   String SEND_TO, String EMP_DESIGNATION ) {
        //this.MSGID = MSGID;
        this.CHATID = CHATID;
        this.MSGDATE = MSGDATE;
        this.MSGTITLE = MSGTITLE;

        this.MSGDESC = MSGDESC;
        this.MSGREAD = MSGREAD;
        this.MSGTYP = MSGTYP;

        this.MSGIMG = MSGIMG;
        this.ERRORMSG = ERRORMSG;

        this.SENDER_EMPID =  SENDER_EMPID;
        this.SENDER_NAME = SENDER_NAME;
        this.SENDER_GCMID = SENDER_GCMID;

        this.SEND_BY =  SEND_BY;
        this.SEND_TO = SEND_TO;
        this.EMP_DESIGNATION = EMP_DESIGNATION;


    }

    public String getMSGID() {
        return MSGID;
    }

   public void setMSGID(String MSGID) {
        this.MSGID = MSGID;
    }

    public String getCHATID() {
        return CHATID;
    }

    public void setCHATID(String CHATID) {
        this.CHATID = CHATID;
    }

    public String getMSGDATE() {
        return MSGDATE;
    }

    public void setMSGDATE(String MSGDATE) {
        this.MSGDATE = MSGDATE;
    }

    public String getMSGTITLE() {
        return MSGTITLE;
    }

    public void setMSGTITLE(String MSGTITLE) {
        this.MSGTITLE = MSGTITLE;
    }

    public String getMSGDESC() {
        return MSGDESC;
    }

    public void setMSGDESC(String MSGDESC) {
        this.MSGDESC = MSGDESC;
    }

    public String getMSGREAD() {
        return MSGREAD;
    }

    public void setMSGREAD(String MSGREAD) {
        this.MSGREAD = MSGREAD;
    }

    public String getMSGTYP() {
        return MSGTYP;
    }

    public void setMSGTYP(String MSGTYP) {
        this.MSGTYP = MSGTYP;
    }

    public String getMSGIMG() {
        return MSGIMG;
    }

    public void setMSGIMG(String MSGIMG) {
        this.MSGIMG = MSGIMG;
    }

    public String getERRORMSG() {
        return ERRORMSG;
    }

    public void setERRORMSG(String ERRORMSG) {
        this.ERRORMSG = ERRORMSG;
    }

    public String getSENDER_EMPID() {
        return SENDER_EMPID;
    }

    public void setSENDER_EMPID(String SENDER_EMPID) {
        this.SENDER_EMPID = SENDER_EMPID;
    }

    public String getSENDER_GCMID() {
        return SENDER_GCMID;
    }

    public void setSENDER_GCMID(String SENDER_GCMID) {
        this.SENDER_GCMID = SENDER_GCMID;
    }

    public String getSENDER_NAME() {
        return SENDER_NAME;
    }

    public void setSENDER_NAME(String SENDER_NAME) {
        this.SENDER_NAME = SENDER_NAME;
    }

    public String getSEND_BY() {
        return SEND_BY;
    }

    public void setSEND_BY(String SEND_BY) {
        this.SEND_BY = SEND_BY;
    }

    public String getSEND_TO() {
        return SEND_TO;
    }

    public void setSEND_TO(String SEND_TO) {
        this.SEND_TO = SEND_TO;
    }

    public String getEMP_DESIGNATION() {
        return EMP_DESIGNATION;
    }

    public void setEMP_DESIGNATION(String EMP_DESIGNATION) {
        this.EMP_DESIGNATION = EMP_DESIGNATION;
    }
}