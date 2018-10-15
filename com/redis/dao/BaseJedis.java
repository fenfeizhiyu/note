package redis.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.*;

/**
 * @Author yu.yang
 * @Date 2018/10/15 16:12
 */
public class BaseJedis {

    private static JedisPool pool;
    private static JedisPoolConfig config;
    private static ResourceBundle bundle;
    static
    {
         bundle = ResourceBundle.getBundle("redis");
        if(config == null){
            config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxTotal")));
            config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
            config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWaitMillis")));
            config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
        }
        pool = new JedisPool(config, bundle.getString("host"), Integer.valueOf(bundle.getString("port")), 2000);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void main(String[] args) {

    }




}
