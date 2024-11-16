package lk.ijse.greenshadow_springboot.util;

import java.util.Base64;

public class AppUtil {
    public static String imageToBase64(byte [] profilePic) {
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
