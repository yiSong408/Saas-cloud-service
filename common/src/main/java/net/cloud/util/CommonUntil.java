package net.cloud.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Random;

public class CommonUntil {
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddr = null;
        try {
            ipAddr = request.getHeader("x-forwarded-for");
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getRemoteAddr();
                if (ipAddr.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddr = inet.getHostAddress();
                }
            }
            if (ipAddr != null && ipAddr.length() > 15) {
                if (ipAddr.indexOf(".") > 0) {
                    ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddr = "";
        }

        return ipAddr;
    }

    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte item : array) {
                stringBuilder.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return stringBuilder.toString().toUpperCase();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getRandomCode(int length){
        String source = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(random.nextInt(9)));
        }
        return sb.toString();
    }

}

