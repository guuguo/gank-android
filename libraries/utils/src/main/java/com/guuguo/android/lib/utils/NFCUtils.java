package com.guuguo.android.lib.utils;

import android.nfc.NfcAdapter;

/**
 * mimi 创造于 2017-08-09.
 * 项目 order
 */

public class NFCUtils {


    /**
     * Is nfc present boolean.
     *
     * @return the boolean
     */
    public static boolean isNfcPresent() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(Utils.getContext());
        return nfcAdapter != null;
    }

    /**
     * Is nfc enabled boolean.
     *
     * @return the boolean
     */
    public static boolean isNfcEnabled() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(Utils.getContext());
        return nfcAdapter != null && nfcAdapter.isEnabled();
    }
}
