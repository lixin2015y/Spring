package com.lee.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisURIHelper;

import java.util.Set;

public class JedisClusterPlus extends JedisCluster {

    public JedisClusterPlus(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, final GenericObjectPoolConfig poolConfig) {
        super(jedisClusterNode);
        super.connectionHandler = new JedisSlotAdvancedConnectionHandler(jedisClusterNode, poolConfig,
                connectionTimeout, soTimeout);
    }

    public JedisSlotAdvancedConnectionHandler getConnectionHandler() {
        return (JedisSlotAdvancedConnectionHandler) this.connectionHandler;
    }

}
