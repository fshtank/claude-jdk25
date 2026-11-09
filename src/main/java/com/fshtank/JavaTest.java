package com.fshtank;

/**
 * A quick smoke-test class to verify the JDK installation is working.
 */
public class JavaTest {

    public static void main(String[] args) {
        System.out.println("Java is working!");
        System.out.println("  Version : " + System.getProperty("java.version"));
        System.out.println("  VM      : " + System.getProperty("java.vm.name"));
        System.out.println("  OS      : " + System.getProperty("os.name")
                         + " " + System.getProperty("os.version"));
        System.out.println("  Home    : " + System.getProperty("java.home"));
    }
}
