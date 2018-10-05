package com.canerkorkmaz.monopoly.util;

public final class Validate {
    private Validate() {
    }

    public static String getValidatedIp(String ip) {
        if (ip == null || (ip = ip.trim()).equals("")) {
            throw new RuntimeException("Ip address cannot be empty");
        }
        return ip;
    }

    public static int getValidatedPort(String portStr) {
        if (portStr == null) {
            throw new RuntimeException("Cannot set port");
        }
        int port = Integer.parseInt(portStr);
        if (port < 0 || port > 65535) {
            throw new RuntimeException("Port cannot be smaller than 0 or bigger than 65535");
        }
        return port;
    }

    public static int getValidatedLocalPlayers(Object selectedItem) {
        Integer selected = (Integer) selectedItem;
        if (selected == null) {
            throw new RuntimeException("Please select player count");
        }
        return selected;
    }


}
