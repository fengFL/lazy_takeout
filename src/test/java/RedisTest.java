import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisTest {
    @Test
    public void test(){

        // 1. get connection
        Jedis jedis = new Jedis("192.168.1.15", 6379);
        jedis.auth("liufeng");
        // 2. execution
        jedis.set("username", "xiaoming");

        String username = jedis.get("username");
        System.out.println(username);

        // jedis.del("username");
        jedis.hset("myhash", "addr", "beijing");
        String addr = jedis.hget("myhash", "addr");
        System.out.println(addr);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        // 3. disconnection
        jedis.close();
    }
}
