package test;

import static server.tools.Email.mailSyntaxCheck;

public class TestEmail {
    public static void main(String[] args) {
        System.out.println(mailSyntaxCheck("nicolas.carolo@mail.com"));
    }
}
