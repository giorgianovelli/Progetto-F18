package server.tools;

import java.util.StringTokenizer;

public class StringManipulator {
    public static String capitalizeFirstLetter(String strToCapitalize){
        if (strToCapitalize.length() > 0){
            if (strToCapitalize.contains(" ")){
                return capitalizeFirstLetterForEachWord(strToCapitalize);
            }
            strToCapitalize = strToCapitalize.toLowerCase();
            return strToCapitalize.substring(0, 1).toUpperCase() + strToCapitalize.substring(1, strToCapitalize.length());
        } else {
            return "";
        }

    }

    private static String capitalizeFirstLetterForEachWord(String strToCapitalize){
        strToCapitalize = strToCapitalize.toLowerCase();
        StringTokenizer strToken = new StringTokenizer(strToCapitalize, " ");
        String toReturn = "";
        while (strToken.hasMoreTokens()){
            String word = strToken.nextToken();
            toReturn = toReturn + word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
            if (strToken.hasMoreTokens()){
                toReturn = toReturn + " ";
            }
        }
        return toReturn;
    }
}
