package server.tools;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Email class to control the syntax.
 */
public class Email {

    /**
     * Method that check if the email syntax is valid.
     * @param email
     * @return true if the email is correct,else
     * @return false
     */
    public static boolean mailSyntaxCheck(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean matchFound = m.matches();
        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        if (matchFound && lastToken.length() >= 2 && email.length() - 1 != lastToken.length()) {
            return true;
        } else {
            return false;
        }

    }
}
