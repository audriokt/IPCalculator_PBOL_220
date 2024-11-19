/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbo.ipcalculator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author puspa
 */
public class IPAddress {

    private ArrayList<Oktet> ipNumber = new ArrayList();
    private ArrayList<Oktet> netMask = new ArrayList();
    private int prefiks;

    public IPAddress() {

    }

    /**
     * Constructor
     *
     * @param input1
     * @param input2
     * @param input3
     * @param input4
     * @throws Exception
     */
    public IPAddress(String input1, String input2, String input3, String input4) throws Exception {
        Oktet oktet1 = new Oktet(input1);
        ipNumber.add(oktet1);
        Oktet oktet2 = new Oktet(input2);
        ipNumber.add(oktet2);
        Oktet oktet3 = new Oktet(input3);
        ipNumber.add(oktet3);
        Oktet oktet4 = new Oktet(input4);
        ipNumber.add(oktet4);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                result = result + String.valueOf(getIpNumber().get(i).getValue()) + ".";
            } else {
                result = result + String.valueOf(getIpNumber().get(i).getValue());
            }
        }
        return result;
    }
    
    public String toStringBiner(){
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                result = result + String.format("%8s",Integer.toBinaryString(getIpNumber().get(i).getValue())).replace(' ', '0') + ".";    
            } else {
                result = result + String.format("%8s",Integer.toBinaryString(getIpNumber().get(i).getValue())).replace(' ', '0');
            }
        }
        return result;
    }

    /**
     * @return the ipNumber
     */
    public ArrayList<Oktet> getIpNumber() {
        return ipNumber;
    }

    /**
     * @param ipNumber the ipNumber to set
     */
    public void setIpNumber(ArrayList<Oktet> ipNumber) {
        this.ipNumber = ipNumber;
    }

    /**
     * @return the netMask
     */
    public String getNetMask() {
        StringBuilder netMask = new StringBuilder();
        for(int i = 0;i<this.netMask.size();i++){
            netMask.append(this.netMask.get(i).getValue());
            if(i < this.netMask.size()-1)netMask.append(".");
        }
        return netMask.toString();
    }

    /**
     * @param netMask the netMask to set
     */
    public void setNetMask(ArrayList<Oktet> netMask) {
        this.netMask = netMask;
    }

    /**
     * @return the prefiks
     */
    public int getPrefiks() {
        return prefiks;
    }

    /**
     * @param prefiks the prefiks to set
     */
    public void setPrefiks(int prefiks) {
        this.prefiks = prefiks;
    }

    /**
     * Fungsi set mask
     *
     * @param i
     */
    void setNetMask(int prefiks) throws Exception {
        int oktetPenting = prefiks / 8;
        int indexPrefix = prefiks % 8;
        double value = 0;
        
        for(int i = 0; i <= 3; i++){
            if(i<oktetPenting){
                netMask.add(new Oktet(255));
            } else if(i == oktetPenting){
                for(int j = 0; j < indexPrefix;j++){
                    value = value + Math.pow(2, 7-j);
                }
                netMask.add(new Oktet((int)value));
            } else{
                netMask.add(new Oktet(0));
            }
        }
    }

    /**
     * Fungsi set NetMask
     *
     * @param string_prefiks
     */
    void setNetMask(String string_prefiks) throws Exception {
        String[] octets = string_prefiks.split("\\.");

        for (int i = 0; i < octets.length; i++) {
            netMask.add(new Oktet(octets[i]));
        }
    }

    IPAddress getNetworkID() throws Exception {
        IPAddress networkID = new IPAddress(); 
        for (int i = 0; i < 4; i++) { 
            int netID = ipNumber.get(i).getValue() & netMask.get(i).getValue(); 
            networkID.getIpNumber().add(new Oktet(netID)); 
        } 
        return networkID;      
    }

    IPAddress getBroadcast() throws Exception {
        IPAddress broadcast = new IPAddress(); 
        for (int i = 0; i < 4; i++) { 
            int wildcardMask = ~netMask.get(i).getValue() & 0xFF; 
            int bcast = ipNumber.get(i).getValue() | wildcardMask; 
            broadcast.getIpNumber().add(new Oktet(bcast)); 
        } 
        return broadcast;
    }
    public int getIPEfektif() {
        if (this.prefiks < 0 || this.prefiks > 32) { 
            throw new IllegalArgumentException("Prefiks tidak valid"); 
        }

        long totalIP = (long) Math.pow(2, 32 - this.prefiks);  // Menggunakan long untuk mencegah overflow
        System.out.println("Total IP: " + totalIP);

        if (this.prefiks == 32) { 
            return 0; 
        }

        long ipEfektif = totalIP - 2;

        if (this.prefiks == 31) {
            ipEfektif = totalIP;
        }
        return (int) ipEfektif;
    }

    public int getIPEfektifString() {
        boolean isFullMask = true; 
        for (int i = 0; i < netMask.size(); i++) { 
            if (netMask.get(i).getValue() != 255) { 
                isFullMask = false; break; } 
        }
        
        if (isFullMask) { 
            return 0;
        } 
        
        int netID = ipNumber.get(3).getValue() & netMask.get(3).getValue();
        int wildcardMask = ~netMask.get(3).getValue() & 0xFF; 
        int bcast = ipNumber.get(3).getValue() | wildcardMask; 
        
        return bcast - netID -1;
    }
    
    public String getNetmask() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < netMask.size(); i++) {
            sb.append(netMask.get(i).getValue());
            if (i < netMask.size() - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }
    
    public String getNetmaskBiner(){
        String result = "";
        for (int i = 0; i < 4; i++) { 
            int netmask = netMask.get(i).getValue();
            if (i != 3) {
                result = result + String.format("%8s",Integer.toBinaryString(netmask)).replace(' ','0') + ".";   
            } else {
                result = result + String.format("%8s",Integer.toBinaryString(netmask)).replace(' ', '0');
            }
        } 
        return result;
    } 
}
