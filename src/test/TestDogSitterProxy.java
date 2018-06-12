package test;

import dogSitterClient.DogSitterProxy;

public class TestDogSitterProxy {
    public static void main(String[] args) {
        DogSitterProxy proxy = new DogSitterProxy("MARCO.CARTA@GMAIL.COM");
        DogSitterProxy proxyLogin = new DogSitterProxy();
        System.out.println("test login true: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGETTO123"));
        System.out.println("test login false: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGGGGETTO123"));
    }
}
