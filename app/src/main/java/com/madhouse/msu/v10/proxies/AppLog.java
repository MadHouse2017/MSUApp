package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class AppLog implements KvmSerializable{
	
	String strIMEI;
	String strActionType;
	String strActionPerform;
	
	public String getStrIMEI() {
		return strIMEI;
	}
	public void setStrIMEI(String strIMEI) {
		this.strIMEI = strIMEI;
	}
	public String getStrActionType() {
		return strActionType;
	}
	public void setStrActionType(String strActionType) {
		this.strActionType = strActionType;
	}
	public String getStrActionPerform() {
		return strActionPerform;
	}
	public void setStrActionPerform(String strActionPerform) {
		this.strActionPerform = strActionPerform;
	}
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:

			return strIMEI;

		case 1:

			return strActionType;

		case 2:

			return strActionPerform;

		default:
			return null;
		}

	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		case 0:

			arg2.name = "strIMEI";
			arg2.type = PropertyInfo.STRING_CLASS;
			break;

		case 1:
			
			arg2.name = "strActionType";
			arg2.type = PropertyInfo.STRING_CLASS;
			break;
			

		case 2:
			
			arg2.name = "strActionPerform";
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

			strIMEI = arg1.toString();
			break;

		case 1:

			strActionType = arg1.toString();
			break;

		case 2:

			strActionPerform = arg1.toString();
			break;

		default:
			break;
		}
	}
	
	

}
