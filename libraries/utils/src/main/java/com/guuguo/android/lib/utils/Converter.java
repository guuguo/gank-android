//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 13SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.guuguo.android.lib.utils;
//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//13SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////

/**
 * Convert help class.
 * <hr>
 * <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 *
 * @author Guidebee Pty Ltd.
 * @version 1.00, 13/09/11
 */
public class Converter {
    // Hex help
    private static final byte[] HEX_CHAR_TABLE = {(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'};
    // //////////////////////////////////////////////////////////////////////////
    // --------------------------------- REVISIONS
    // ------------------------------
    // Date Name Tracking # Description
    // --------- ------------------- ------------- ----------------------
    // 13SEP2011 James Shen Initial Creation
    // //////////////////////////////////////////////////////////////////////////
    /**
     * convert a byte array to hex string
     *
     * @param raw byte array
     * @param len convert length of the array.
     * @return hex string.
     */
    public static String getHexString(byte[] raw, int len) {
        byte[] hex = new byte[2 * len];
        int index = 0;
        int pos = 0;
        for (byte b : raw) {
            if (pos >= len) {
                break;
            }
            pos++;
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex);
    }

    /**
     * convert a byte array to int result
     *
     * @param src byte array
     * @return result int.
     */
    public static int bytesToInt2(byte[] src) {
        int value;
        value = (int) ( ((src[0] & 0xFF)<<24)
                |((src[1] & 0xFF)<<16)
                |((src[2] & 0xFF)<<8)
                |(src[3] & 0xFF));
        return value;
    }
}