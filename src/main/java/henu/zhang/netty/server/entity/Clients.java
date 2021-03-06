package henu.zhang.netty.server.entity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张向兵
 */
@Component
public class Clients {

    /**
     * 所有的客户端信息
     */
    public ConcurrentHashMap<Object, ChannelHandlerContext> clients = new ConcurrentHashMap<>();

    /**
     * 新增客户端链接
     *
     * @param key
     * @param ctx
     */
    public void put(String key, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channel.attr(AttributeMapConstant.token).set(key);
        clients.put(key, ctx);
    }

    /**
     * 获取客户端链接
     *
     * @param key
     * @return
     */
    public ChannelHandlerContext get(String key) {
        System.out.println("目前链接数量：" + clients.size());
        return clients.get(key);
    }

    /**
     * 删除链接
     *
     * @param ctx
     */
    public void remove(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String token = channel.attr(AttributeMapConstant.token).get();
        if (!StringUtils.isEmpty(token)) {
            clients.remove(token);
        }
    }

    /**
     * 查看本地是否有该连接
     *
     * @param ctx
     * @return
     */
    public boolean hasClient(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String IMEM = channel.attr(AttributeMapConstant.token).get();
        if (IMEM == null || !clients.containsKey(IMEM)) {
            return false;
        }
        return true;
    }
}
