package com.ec.vone.utils;


/**
 * Created by user on 2017/4/7.
 */

public class DataUtils {
    private static byte[] tmpBytes;

    public static byte[] int2Bytes(int num) {
        byte[] byteNum = new byte[4];
        for (int ix = 0; ix < 4; ++ix) {
            int offset = 32 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    public static int byte2Int(byte byteNum) {
        return byteNum & 0xff;
    }

    public static int bytes2IntUnsigned(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    public static int bytes2IntSignedGrav(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        if (num >= 2048) num -= 4096;
        return num;
    }

    public static int bytes2IntSigned(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        if (num >= 32768) num -= 65536;
        return num;
    }

    public static int bytes2IntSignedMagXY(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        if (num >= 4096) num -= 8192;
        return num;
    }

    public static int bytes2IntSignedMagZ(byte[] byteNum) {
        int num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        if (num >= 8912) num -= 16384;
        return num;
    }

    public static byte int2OneByte(int num) {
        return (byte) (num & 0x000000ff);
    }

    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    public static String byte2hex(byte b) {
        String result = Integer.toHexString(b & 0xFF);
        if (result.length() == 1) {
            result = '0' + result;
        }
        return result;
    }

    /**
     * 将byte[]转化成16进制的String命令
     */
    public static String bytes2hex(byte[] b) {
        // String Buffer can be used instead
        String hs = "0x";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + ",0x";
            }
        }
        return hs;
    }

    /**
     * 将byte[]转化成格式的String命令
     *
     * @param b
     * @return
     */
    public static String bytesFormat2Mac(byte[] b) {
        // String Buffer can be used instead
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs;
    }


    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        //由高位到低位
        for (int i = 0; i < bytes.length; i++) {
            int shift = (bytes.length - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }

    /**
     * @param bytes
     * @param reverse 所传数组是否需要反转
     * @return
     */
    public static int byteArrayToInt(byte[] bytes, boolean reverse) {
        if (!reverse) byteArrayToInt(bytes);
        byte[] c = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            c[i] = bytes[bytes.length - 1 - i];
        }
        return byteArrayToInt(c);
    }

    public static int byte2Int(byte[] byteNum) {
        short s = 0;
        short s0 = (short) (byteNum[0] & 0xff);// 最低位
        short s1 = (short) (byteNum[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    public static char getCrc(char[] f, int len) {
        char check_sum = 0;
        for (int i = 0; i < len; i++) {
            check_sum += f[i];
        }
        check_sum = (char) (byte2Int((byte) 0xff) - check_sum + byte2Int((byte) 0x01));
        return check_sum;
    }

    public static byte CalculateCRC(byte _cmd, byte[] _data) {
        int iTemp = _cmd;
        for (int i = 0; i < _data.length; i++) {
            iTemp += _data[i];
        }
        byte bTemp = Integer.valueOf(iTemp).byteValue();
        iTemp = 256 - bTemp;
        bTemp = Integer.valueOf(iTemp).byteValue();
        return bTemp;
    }

    /**
     * 小于4位字节数组转换为整型
     *
     * @param b 字节数组
     * @return int
     */
    public static int shortBytes2Int(byte[] b) {
        int def = 4 - b.length;
        int intValue = 0;
        for (int i = 0; i < b.length; i++, def++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - def));
        }
        return intValue;
    }

    public static void main(String[] args) {
    }

}
