package staticClasses;

public class StringManipulator {
    public static String capitalizeFirstLetter(String strToCapitalize){
        if (strToCapitalize.length() > 0){
            strToCapitalize = strToCapitalize.toLowerCase();
            return strToCapitalize.substring(0, 1).toUpperCase() + strToCapitalize.substring(1, strToCapitalize.length());
        } else {
            return "";
        }

    }
}
