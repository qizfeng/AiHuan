package com.zipingfang.aihuan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 
//	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 * 
	System.out.println("__________________ip/mac______________________");
	System.out.println("______________"+SysWifiInfo.getLocalIpAddress());
	System.out.println("______________"+SysWifiInfo.getLocalMacAddressFromBusybox());
	System.out.println("______________"+SysWifiInfo.getLocalMacAddressFromIp(this));
	System.out.println("______________"+SysWifiInfo.getLocalMacAddressFromWifiInfo(this));
	System.out.println("______________"+SysWifiInfo.getIPAddress(this));
	System.out.println("____________________end____________________"); 
 *
 */
public class WifiUtils {

    /** 
     * >>>192.168.2.127 [没网络也可以] 
     * @param ctx
     * @return
     */
    public static String getIPAddress(Context ctx){
        WifiManager wifi_service = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
        WifiInfo wifiinfo = wifi_service.getConnectionInfo();
        System.out.println("Wifi info----->"+wifiinfo.getIpAddress());
        System.out.println("DHCP info gateway----->"+ Formatter.formatIpAddress(dhcpInfo.gateway));
        System.out.println("DHCP info netmask----->"+ Formatter.formatIpAddress(dhcpInfo.netmask));
        //DhcpInfo中的ipAddress是一个int型的变量，通过Formatter将其转化为字符串IP地址  
        return Formatter.formatIpAddress(dhcpInfo.ipAddress);
    }  

    /**
     * 获取本地IP
     * >>192.168.2.127
     * @return
     */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}

		return null;
	}   

    
	/**
	 * 根据Wifi信息获取本地Mac
	 * >>20:F3:A3:C6:61:62 [没网络也可以] 
	 * @param context
	 * @return
	 */
    public static String getLocalMacAddressFromWifiInfo(Context context){
    	try{
            WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiMan != null) {
                WifiInfo wifiInf = wifiMan.getConnectionInfo();
                if (wifiInf != null && wifiInf.getMacAddress() != null) {//48位，如FA:34:7C:6D:E4:D7 
                    return wifiInf.getMacAddress();
                }
            } 
		}catch(Exception e){
			Lg.error(e.toString());
		} 

		return ""; 
    }
    
    /**
     * 根据busybox获取本地Mac
     * >>20:F3:A3:C6:61:62
     * @return
     */
    public static String getLocalMacAddressFromBusybox(){
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig","HWaddr");
         
        //如果返回的result == null，则说明网络不可取
        if(result==null){
            return "网络出错，请检查网络";
        }
         
        //对该行数据进行解析
        //例如：eth0      Link encap:Ethernet  HWaddr 00:16:E8:3E:DF:67
        if(result.length()>0 && result.contains("HWaddr")==true){
            Mac = result.substring(result.indexOf("HWaddr")+6, result.length()-1);
            //Log.i("test","Mac:"+Mac+" Mac.length: "+Mac.length()); 
            result = Mac;
            //Log.i("test",result+" result.length: "+result.length());            
        }
        return result.trim();
    }
    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            
            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine ()) != null && line.contains(filter)== false) {   
                //result += line;
                Log.i("test","line: "+line);
            } 
            result = line;
            //Log.i("test","result: "+result);
        }   
        catch(Exception e) {
            e.printStackTrace();   
        }   
        return result;   
    }

    
    /**
     * 根据IP获取本地Mac
     * >>20f3a3c66162
     * @param context
     * @return
     */ 
    @SuppressLint("NewApi")
	public static String getLocalMacAddressFromIp(Context context) {
        String mac_s= "";
       try {
            byte[] mac;
            NetworkInterface ne= NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
       } catch (Exception e) {
           e.printStackTrace();
       }
       
        return mac_s;
    } 

	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}
    
}
