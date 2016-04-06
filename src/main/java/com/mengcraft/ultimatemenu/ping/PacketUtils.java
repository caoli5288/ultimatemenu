package com.mengcraft.ultimatemenu.ping;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PacketUtils {
    public static final Charset UTF16BE = Charset.forName("UTF-16BE");
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static void a(DataOutputStream var0, String var1) throws IOException {
        int var2 = var1.length();
        byte[] var3 = new byte[var2 / 2];

        for (int var4 = 0; var4 < var2; var4 += 2) {
            var3[var4 / 2] = (byte) ((Character.digit(var1.charAt(var4), 16) << 4) + Character.digit(var1.charAt(var4 + 1), 16));
        }

        var0.write(var3);
    }

    public static void writeString(DataOutputStream var0, String var1, Charset var2) throws IOException {
        if (var2 == UTF8) {
            writeVarInt(var0, var1.length());
        } else {
            var0.writeShort(var1.length());
        }

        var0.write(var1.getBytes(var2));
    }

    public static int readVarInt(DataInputStream var0) throws IOException {
        int var1 = 0;
        int var2 = 0;

        byte var3;
        do {
            var3 = var0.readByte();
            var1 |= (var3 & 127) << var2++ * 7;
            if (var2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 128) == 128);

        return var1;
    }

    public static void writeVarInt(DataOutputStream var0, int var1) throws IOException {
        while ((var1 & -128) != 0) {
            var0.write(var1 & 127 | 128);
            var1 >>>= 7;
        }

        var0.write(var1);
    }

    public static void closeQuietly(Closeable var0) {
        try {
            if (var0 != null) {
                var0.close();
            }
        } catch (IOException ignored) {
        }

    }
}
