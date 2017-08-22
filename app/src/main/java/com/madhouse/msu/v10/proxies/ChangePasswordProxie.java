package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class ChangePasswordProxie implements KvmSerializable {
	
	String strEmpId;
	String strOldPass;
	String strNewPass;

	public String getStrEmpId() {
		return strEmpId;
	}

	public void setStrEmpId(String strEmpId) {
		this.strEmpId = strEmpId;
	}

	public String getStrOldPass() {
		return strOldPass;
	}

	public void setStrOldPass(String strOldPass) {
		this.strOldPass = strOldPass;
	}

	public String getStrNewPass() {
		return strNewPass;
	}

	public void setStrNewPass(String strNewPass) {
		this.strNewPass = strNewPass;
	}


	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			
			return strEmpId;
			
		case 1:
			
			return strOldPass;

		case 2:

			return strNewPass;


		default:
			return null;
		}
		
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 5;
	}
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		case 0:
			arg2.name = "strEmpId";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;
			
		case 1:
			arg2.name = "strOldPass";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;

		case 2:
			arg2.name = "strNewPass";
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

			strEmpId = arg1.toString();
			break;
			
		case 1:
			strOldPass = arg1.toString();
			break;

		case 2:
			strNewPass = arg1.toString();
			break;


		default:
			break;
		}
		
	}
	

}
