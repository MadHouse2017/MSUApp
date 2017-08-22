package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class ValidateUserProxie implements KvmSerializable {
	
	String _sUser;
	String _sPass;
	String _sGcmId;


	public String get_sUser() {
		return _sUser;
	}

	public void set_sUser(String _sUser) {
		this._sUser = _sUser;
	}

	public String get_sPass() {
		return _sPass;
	}

	public void set_sPass(String _sPass) {
		this._sPass = _sPass;
	}

	public String get_sGcmId() {
		return _sGcmId;
	}

	public void set_sGcmId(String _sGcmId) {
		this._sGcmId = _sGcmId;
	}


	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			
			return _sUser;
			
		case 1:
			
			return _sPass;

		case 2:

			return _sGcmId;


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
			arg2.name = "_sUser";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;
			
		case 1:
			arg2.name = "_sPass";
			arg2.type = PropertyInfo.STRING_CLASS;
			
			break;

		case 2:
			arg2.name = "_sGcmId";
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

			_sUser = arg1.toString();
			break;
			
		case 1:
			_sPass = arg1.toString();
			break;

		case 2:
			_sGcmId = arg1.toString();
			break;


		default:
			break;
		}
		
	}
	

}
