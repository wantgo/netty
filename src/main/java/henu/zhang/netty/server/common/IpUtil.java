package henu.zhang.netty.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 张向兵
 */
@Service
public class IpUtil {

    private static Logger logger = LoggerFactory.getLogger(IpUtil.class);

    private static String localIP;

    private static String publicIP;

    @Autowired
    private HttpRequestUtil httpRequestUtil;

    /**
     * 获取内网IP
     * @return
     */
    public String getLocalIP(){
        if (localIP == null){
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                localIP = addr.getHostAddress();
            } catch (UnknownHostException e) {
                logger.error("获取本机地址失败：{0}",e);
            }
        }
        return localIP;
    }

    /**
     * 获取本机的公网IP
     * @return
     */
    public String getPublicIP(){
        String url = "http://members.3322.org/dyndns/getip";
        if (publicIP == null){
            try {
                publicIP = httpRequestUtil.doGet(url,null);
            } catch (IOException e) {
                logger.error("获取本机外网地址失败：{0}",e);
            }
        }

        return publicIP;
    }

}
