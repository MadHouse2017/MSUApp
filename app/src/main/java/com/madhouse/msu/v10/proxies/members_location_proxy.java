package com.madhouse.msu.v10.proxies;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class members_location_proxy implements KvmSerializable {
	
	String strWorkHobby;
	String strLat;
	String strLong;


	public String getStrWorkHobby() {
		return strWorkHobby;
	}

	public void setStrWorkHobby(String strWorkHobby) {
		this.strWorkHobby = strWorkHobby;
	}

	public String getStrLat() {
		return strLat;
	}

	public void setStrLat(String strLat) {
		this.strLat = strLat;
	}

	public String getStrLong() {
		return strLong;
	}

	public void setStrLong(String strLong) {
		this.strLong = strLong;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return strWorkHobby;

		case 1:
			return strLat;


		case 2:
			return strLong;



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
			  arg2.name = "strWorkHobby";
			  arg2.type = PropertyInfo.STRING_CLASS;

			case 1:
				arg2.name = "strLat";
				arg2.type = PropertyInfo.STRING_CLASS;

			case 2:
				arg2.name = "strLong";
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

				strWorkHobby = arg1.toString();
				break;

			case 1:

				strLat = arg1.toString();
				break;

			case 2:

				strLong = arg1.toString();
				break;



		default:
			break;
		}
		
	}
	

}
