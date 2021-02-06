package com.amos.p1.backend;

import com.amos.p1.backend.service.ProviderIntervalRequestDummyTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

public class IpTest {

    private static final Logger log = LoggerFactory.getLogger(IpTest.class);

    @Test
    void test(){
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String hostAddress = socket.getLocalAddress().getHostAddress();
            log.info(hostAddress);
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
                log.info(i.getHostAddress());
            }
        }
    }
}
