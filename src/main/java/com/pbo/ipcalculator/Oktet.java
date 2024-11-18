/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pbo.ipcalculator;

/**
 *
 * @author puspa
 */
public class Oktet {

    private int value;

    public Oktet() {
    }

    /**
     * Konstruktor
     *
     * @param value
     */
    public Oktet(int value) throws Exception  {
        setValue(value);
    }

    /**
     * Konstruktor
     *
     * @param value
     */
    public Oktet(String value) throws Exception {
        setValue(value);
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     * @throws java.lang.Exception
     */
    public void setValue(int value) throws Exception {
        if (value < 0 || value > 255) {
            throw new Exception("value is out of range");
        } else {
            this.value = value;
        }
    }

    /**
     *
     * @param value
     * @throws java.lang.Exception
     */
    public void setValue(String value) throws Exception {
        try {
            setValue(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            throw new Exception("Invalid integer value: " + value);
        }
    }

}
