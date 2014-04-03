

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DataUtils {

	private HashMap<String,String> hsGlobalCellIdCellNmMapping     = null;
	private HashMap<String,String> hsGlobalCellIdTACMapping        = null;
	private HashMap<String,String> hsCellNmMarketMapping           = null;
	private HashMap<String,String> hs_IMEIFromToDeviceType2        = null;
	private String [] imei                                         = null;
	
	public static void main(String []  args) {
		System.out.println("hello");
	}

	public void setGlobalCellIdCellNmMapping(HashMap<String,String> hsGlobalCellIdCellNmMapping){      
		this.hsGlobalCellIdCellNmMapping = hsGlobalCellIdCellNmMapping;    
	}
	
	public HashMap<String,String> getGlobalCellIdCellNmMapping(){      
		return hsGlobalCellIdCellNmMapping;    
	}

	public void setGlobalCellIdTACMapping(HashMap<String,String> hsGlobalCellIdTACMapping){      
		this.hsGlobalCellIdTACMapping = hsGlobalCellIdTACMapping;    
	}
	
	public HashMap<String,String> getGlobalCellIdTACMapping(){      
		return hsGlobalCellIdTACMapping;    
	}

	public void setCellNmMarketMapping(HashMap<String,String> hsCellNmMarketMapping){
		this.hsCellNmMarketMapping = hsCellNmMarketMapping;
	}

	public HashMap<String,String> getCellNmMarketMapping(){      
		return hsCellNmMarketMapping;    
	}
	
	public void setIMEIDeviceTypeMapping2(HashMap<String,String> hs_IMEIFromToDeviceType2) {
		this.hs_IMEIFromToDeviceType2 = hs_IMEIFromToDeviceType2;
	}

	public HashMap<String, String> getIMEIDeviceTypeMapping2() {
		return hs_IMEIFromToDeviceType2;
	}

	public void setIMEIArray(String [] imei) {
		this.imei = imei;
	}

	public String [] getIMEIArray() {
		return imei;
	}

    public static String returnColumnValue(String [] vals, int index) {

    	String ret    = null;
    	int len       = vals.length;
		if (len      <= index) {
			ret        = "";
		}
		else {
			ret        = vals[index].trim(); 
		}

		return ret;
		
	}
    
    public static String getTargetCell(String NEIGHBOR_CGI) {

    	int idx310_410 = NEIGHBOR_CGI.indexOf("310-410-");

    	if (idx310_410 == -1) {
    		return "";
    	}
    	return NEIGHBOR_CGI.substring(idx310_410 + 8);
    }

    public static boolean furtherInvestigationRequired1(String RelQCI) {
		  
		if (RelQCI.isEmpty() || RelQCI.indexOf(':') == -1) {
			return false;
		}

		String [] splitQCI = RelQCI.split(":");
		int lenQCI         = splitQCI.length; 
		if (lenQCI == 0) {
			return false;
		}
		  
		for (int idx = 0; idx < lenQCI; idx++) {
			if (!splitQCI[idx].isEmpty() && splitQCI[idx].equals("1")) {
				return true;
			}
		}
		  
		return false;
	}

    public static boolean furtherInvestigationRequired2(String RelQCI, String RelARP) {
		  
		if (RelQCI.isEmpty() || RelQCI.indexOf(':') == -1) {
			return false;
		}

		String [] splitQCI = RelQCI.split(":");
		int lenQCI         = splitQCI.length; 
		if (lenQCI == 0) {
			return false;
		}
		  
		if (!RelARP.isEmpty() && RelARP.indexOf(':') != -1) {
			String [] splitRelARP = RelARP.split(":");
			int lenRelARP         = splitRelARP.length;
			for (int idx = 0; idx < lenQCI; idx++) {
				if (!splitQCI[idx].isEmpty() && splitQCI[idx].equals("1")) {
					if (lenRelARP > idx) {
						if (!splitRelARP[idx].isEmpty() && splitRelARP[idx].equals("11")) {
							return true;
						}
					}
				}
			}
		}
		  
		return false;
	}

	  public static String getDeviceType0(String IMEI, HashMap<String,IMEIFromToPair> hs) {
		  if (IMEI.isEmpty()) {
			  return "";
		  }
		  Iterator<String> itr = hs.keySet().iterator();
		  while (itr.hasNext()) {
			  String deviceType         = itr.next();
			  IMEIFromToPair imeiFromTo = hs.get(deviceType);
			  if (IMEI.compareTo(imeiFromTo.getIMEIFrom()) >=0 && IMEI.compareTo(imeiFromTo.getIMEITo()) <= 0) {
				  return deviceType; 
			  }
		  }
		  return "";
	  }

	  public static String getDeviceType(String IMEI, HashMap<IMEIFromToPair, String> hs) {
		  if (IMEI.isEmpty()) {
			  return "";
		  }
		  Iterator<IMEIFromToPair> itr = hs.keySet().iterator();
		  while (itr.hasNext()) {
			  IMEIFromToPair imeiFromTo = itr.next();
			  String deviceType         = hs.get(imeiFromTo);
			  if (IMEI.compareTo(imeiFromTo.getIMEIFrom()) >=0 && IMEI.compareTo(imeiFromTo.getIMEITo()) <= 0) {
				  int len = IMEI.length();
				  return deviceType + "|OS" + IMEI.substring(len-2, len); 
			  }
		  }
		  return "";
	  }
	  /**
	   * 
	   * @param IMEI
	   * @param IMEIs
	   * @param hsIMEIMapToDeviceType
	   * @return
	   */
	  public static String getDeviceType(String IMEI, String [] IMEIs, HashMap<String, String> hsIMEIMapToDeviceType) {
		  
		  if (IMEI.equals("") || IMEI.length() < 14) {
			  return "";
		  }

		  String IMEI0 = IMEI.substring(0, 14);
		  if (IMEI0.compareTo("00000000000000") < 0 || IMEI0.compareTo("99999999999999") > 0) {
			  return "";
		  }
		  int length = IMEI.length();

		  int count  = 0;
		  int len    = IMEIs.length;
		  int hlen   = len/2;

		  while(true) {
			  
			  String [] subs = IMEIs[hlen].split("\\|");
			  count++;
			  if (IMEI0.compareTo(subs[0]) >= 0 && IMEI0.compareTo(subs[1]) <= 0) {
				  //return hsIMEIMapToDeviceType.get(IMEIs[hlen]) + "|OS" + IMEI.substring(length-2, length);
				  return hsIMEIMapToDeviceType.get(IMEIs[hlen]);
			  }
			  else if (IMEI0.compareTo(subs[0]) < 0) {
				  len  = hlen;
				  hlen = hlen/2;
			  }
			  else if (IMEI0.compareTo(subs[1]) > 0) {
				  hlen = hlen + (len - hlen)/2;
			  }
			  if (count > IMEIs.length) {
				  return "";
			  }
		  }
	  }

	  public static String getDayStamp() {
	    	Date currDate = new Date();
	    	SimpleDateFormat formatedCurrDate = new SimpleDateFormat(PropertyNames.DATE_FORMAT_4);
	    	return formatedCurrDate.format(currDate);
	  }
	  
	  public static String getYesterdayStamp() {
		  	Calendar today = Calendar.getInstance();  
			today.add(Calendar.DATE, -1);  
			java.sql.Date yesterday = new java.sql.Date(today.getTimeInMillis());
			SimpleDateFormat formatedCurrDate = new SimpleDateFormat(PropertyNames.DATE_FORMAT_4);
			return formatedCurrDate.format(yesterday);
	  }

}
