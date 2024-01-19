import org.example.lazzy.LazzyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@SpringBootTest(classes = LazzyApplication.class)
public class SpringRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  operation for string
     */
    @Test
    public void testString(){
        // opsfor value is for string
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("password", "12345");

        String password = (String)valueOperations.get("password");
        System.out.println(password);
        valueOperations.set("city", "bejing", 10L, TimeUnit.SECONDS);
        // setIfAbset = setnx
        Boolean aBoolean = valueOperations.setIfAbsent("password2", "nanjiing");
        System.out.println(aBoolean);
    }

    /**
     * Test for hash
     */
    @Test
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("002", "name", "zhangsan");
        hashOperations.put("002", "age", "20");
        hashOperations.put("002", "addr", "beijing");

        String name = (String)hashOperations.get("002", "name");
        System.out.println(name);
        Set keys = hashOperations.keys("002");
        for (Object key : keys) {
            System.out.println(key);
        }
        List values = hashOperations.values("002");
        for (Object value : values) {
            System.out.println(value);
        }
    }

    /**
     * Test for list
     */
    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("mylist", "a");
        listOperations.leftPushAll("mylist", "b", "c", "d");

        List mylist = listOperations.range("mylist", 0, -1);
        for (Object o : mylist) {
            System.out.println(o);
        }

        // get the length of listh
        Long mylist2 = listOperations.size("mylist");
        int length = mylist2.intValue();
        for(int i = 0; i < length; i++) {
            String element = (String)listOperations.rightPop("mylist");
            System.out.println(element);
        }

    }

    /**
     *  operation for set
     */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("myset3", "a", "b", "c", "a");

        // get values
        Set<String> myset = setOperations.members("myset3");
        for (String o : myset) {
            System.out.println(o);
        }

        // delete member
        setOperations.remove("myset3", "a", "b");
    }

    /**
     *  operation for sorted set (Zset)
     */
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        // add elements
        zSetOperations.add("zset2", "a", 10.0);
        zSetOperations.add("zset2", "b", 11);
        zSetOperations.add("zset2", "c", 9);
        zSetOperations.add("zset2", "a", 12);

        Set<String> zset2 = zSetOperations.range("zset2", 0, -1);
        for (String element : zset2) {
            System.out.println(element);
        }

        zSetOperations.incrementScore("zset2", "c", 20);
        Set<String> zset3 = zSetOperations.range("zset2", 0, -1);
        for (String element : zset3) {
            System.out.println(element);
        }

        // remove elements b and c
        zSetOperations.remove("zset2", "b", "c");
        Set<String> zset4 = zSetOperations.range("zset2", 0, -1);
        for (String element : zset4) {
            System.out.println(element);
        }
    }

    /**
     * test common methods
     */
    @Test
    public void testCommon(){
        // get all keys
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        // check key
        Boolean aBoolean = redisTemplate.hasKey("myset2");
        System.out.println("key is " + aBoolean);

        // delete key
        redisTemplate.delete("myset2");

        // get the type of the key
        DataType zset = redisTemplate.type("zset");
        System.out.println("type is " + zset.name());
    }
}
