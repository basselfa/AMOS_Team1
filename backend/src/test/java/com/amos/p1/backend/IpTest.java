package com.amos.p1.backend;

import org.junit.jupiter.api.Test;

import java.net.*;
import java.util.Enumeration;

public class IpTest {

    @Test
    void test(){
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String hostAddress = socket.getLocalAddress().getHostAddress();
            System.out.println(hostAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                System.out.println(i.getHostAddress());
            }
        }
    }
}
