package test;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class TestStringManipulator {
    public static void main(String[] args) {
        String test = "NICOLAS";
        System.out.println(capitalizeFirstLetter(test));
    }
}
