package net.cloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class CommonUtil {
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

    public static String getRandomCode(int length) {
        String source = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(random.nextInt(9)));
        }
        return sb.toString();
    }

    public static String generateUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private static final String ALL_CHAR = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    public static void sendJsonObject(HttpServletResponse response, Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(objectMapper.writeValueAsString(obj));
            response.flushBuffer();
        } catch (IOException e) {
            log.warn("response json obj exception: {}", e.toString());
        }
    }
}

