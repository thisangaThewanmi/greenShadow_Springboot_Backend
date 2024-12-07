package lk.ijse.greenshadow_springboot.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    public static String generateUserId() {

        // Generate UUID and get the last 4 digits of the string representation
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "USER-" + uuid.substring(uuid.length() - 4);
    }
    public static String generateStaffId() {

        // Generate UUID and get the last 4 digits of the string representation
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "STAFF-" + uuid.substring(uuid.length() - 4);
    }

    public static String generateFieldId() {
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "FIELD-" + uuid.substring(uuid.length() - 4);
    }

    public static String generateCropId() {

        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "CROP-" + uuid.substring(uuid.length() - 4);
    }
    public static String generateLogId() {

        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "LOG-" + uuid.substring(uuid.length() - 4);
    }

    public static String generateVehicleId() {

        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "VEHICLE-" + uuid.substring(uuid.length() - 4);
    }
    public static String generateEquipmentId() {
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        return "EQUIP-" + uuid.substring(uuid.length() - 4);

    }


    public static String imageToBase64(byte [] profilePic) {

        return Base64.getEncoder().encodeToString(profilePic);
    }
}
