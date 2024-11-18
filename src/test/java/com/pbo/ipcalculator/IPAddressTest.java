package com.pbo.ipcalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class IPAddressTest {
    
    public IPAddressTest() {
    }

    @Test
    public void test1() {
        try {
            IPAddress test = new IPAddress("170", "16", "1", "1");
            assertEquals("170.16.1.1", test.toString());
            System.out.println("IPAddress = " + test);
        } catch (Exception ex) {
            assertNotEquals("value is out of range", ex.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            IPAddress test = new IPAddress("170", "16", "1", "1");
            test.setNetMask(24);
            assertEquals("255.255.255.0", test.getNetMask());
        } catch (Exception ex) {
            assertNotEquals("value is out of range", ex.getMessage());
        }
    }

    @Test
    public void test3() {
        try {
            IPAddress test = new IPAddress("170", "16", "1", "1");
            test.setNetMask("255.255.255.0");
            assertEquals("255.255.255.0", test.getNetMask());
        } catch (Exception ex) {
            assertNotEquals("value is out of range", ex.getMessage());
        }
    }

    @Test
    public void test4() {
        try {
            IPAddress test = new IPAddress("170", "16", "1", "1");
            test.setNetMask("255.255.255.0");
            IPAddress netId = test.getNetworkID();
            IPAddress broadcast = test.getBroadcast();
            assertEquals("170.16.1.0", netId.toString());
            assertEquals("170.16.1.255", broadcast.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception should not be thrown: " + ex.getMessage());
        }
    }

    //testToStringBiner
    @Test 
    public void test5() throws Exception { 
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        assertEquals("11000000.10101000.00000001.00000001", ipAddress.toStringBiner());
    }

    //testSetAndGetIpNumber
    @Test
    public void test6() throws Exception {
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        ArrayList<Oktet> ipNumber = new ArrayList<>();
        ipNumber.add(new Oktet(10));
        ipNumber.add(new Oktet(0));
        ipNumber.add(new Oktet(0));
        ipNumber.add(new Oktet(1));
        ipAddress.setIpNumber(ipNumber);
        assertEquals("10.0.0.1", ipAddress.toString());
    }

    //testSetAndGetNetMask
    @Test
    public void test7() throws Exception {
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        ArrayList<Oktet> netMask = new ArrayList<>();
        netMask.add(new Oktet(255));
        netMask.add(new Oktet(255));
        netMask.add(new Oktet(255));
        netMask.add(new Oktet(0));
        ipAddress.setNetMask(netMask);
        assertEquals("255.255.255.0", ipAddress.getNetMask());
    }

    //testSetAndGetPrefiks
    @Test
    public void test8() throws Exception {
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        ipAddress.setPrefiks(24);
        assertEquals(24, ipAddress.getPrefiks());
    }

    //testGetIPEfektif
    @Test
    public void test9() throws Exception {
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        ipAddress.setNetMask(24);
        ipAddress.setPrefiks(24);
        assertEquals(254, ipAddress.getIPEfektif());

        ipAddress.setNetMask(30);
        ipAddress.setPrefiks(30);
        assertEquals(2, ipAddress.getIPEfektif());

        ipAddress.setNetMask(32);
        ipAddress.setPrefiks(32);
        assertEquals(0, ipAddress.getIPEfektif());
    }

    //testGetNetmaskBiner
    @Test
    public void test10() throws Exception {
        IPAddress ipAddress = new IPAddress("192", "168", "1", "1");
        ipAddress.setNetMask(24);
        assertEquals("11111111.11111111.11111111.00000000", ipAddress.getNetmaskBiner());
    }
}
