package com.mengcraft.ultimatemenu.ping;

import org.apache.commons.lang.time.StopWatch;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

import static org.bukkit.util.NumberConversions.toInt;

final class PingUtil {

    private final static byte[] HANDSHAKE = {15, 0, 47, 9, 108, 111, 99, 97, 108, 104, 111, 115, 116, 99, -35, 1};
    private final static byte[] PING = {1, 0};
    private final static byte[] EXIT = {9, 1, 0, 0, 1, 84, 92, 38, 11, -124};

    public static PingResponse ping(String host, int port) {
        PingResponse response = new PingResponse();

        try (Socket socket = new Socket(host, port)) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.write(HANDSHAKE);
            out.flush();
            out.write(PING);
            out.flush();

            readVarInt(in);
            readVarInt(in);
            byte[] buf = new byte[readVarInt(in)];

            in.readFully(buf);

            JSONObject root = (JSONObject) JSONValue.parse(new String(buf, "UTF-8"));
            response.setMessage(root.get("description").toString());
            JSONObject tree = (JSONObject) root.get("players");
            response.setOnline(toInt(tree.get("online")));
            response.setMax(toInt(tree.get("max")));

            out.write(EXIT);
            out.flush();
        } catch (Exception ignore) {
        }
        return response;
    }

    private static int readVarInt(DataInput var0) throws IOException {
        int result = 0;
        int bit = 0;
        byte b;
        do {
            b = var0.readByte();
            result |= (b & 127) << bit++ * 7;
            if (bit > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b & 128) == 128);
        return result;
    }

}
