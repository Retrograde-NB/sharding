package com.xin.sharding.order;

import com.xin.sharding.config.Snowflake;
import com.xin.sharding.entity.Incorder;
import com.xin.sharding.entity.IncorderExample;
import com.xin.sharding.entity.Strorder;
import com.xin.sharding.mapper.IncorderMapper;
import com.xin.sharding.mapper.StrorderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IncorderMapper incorderMapper;
    @Autowired
    StrorderMapper strorderMapper;
    @Autowired
    RedisTemplate template;
    @Autowired
    Snowflake snowflake;

    /**
     * 验证分表及自增主键
     */
    @GetMapping("/incId")
    public Incorder incId(int userid){
        Incorder incorder = new Incorder();
        incorder.setUserid(userid);
        incorderMapper.insert(incorder);
        return incorder;
    }


    /**
     * 验证自定义主键，追加userid
     */
    @GetMapping("/busiId")
    public Strorder busiId(int userid){
        Strorder order = new Strorder();
        order.setId(System.currentTimeMillis()+""+userid);
        order.setUserid(userid);
        strorderMapper.save(order);
        return order;
    }

    /**
     * maxid表验证
     */
    @GetMapping("/maxId")
    public Strorder maxId(int userid){
        Strorder order = new Strorder();
        order.setUserid(userid);
        strorderMapper.getIdSave(order);
        return order;
    }

    /**
     * 验证redis
     */
    @GetMapping("/redisId")
    public Strorder redisId(int userid){
        Strorder order = new Strorder();
        order.setId(template.opsForValue().increment("next_order_id").toString());
        order.setUserid(userid);
        strorderMapper.save(order);
        return order;
    }

    /**
     * 验证redis
     */
    @GetMapping("/uuid")
    public Strorder uuid(int userid){
        Strorder order = new Strorder();
        order.setId(UUID.randomUUID().toString());
        order.setUserid(userid);
        strorderMapper.save(order);
        return order;
    }

    /**
     * 自定义雪花算法
     */
    @GetMapping("/myflake")
    public Strorder myflake(int userid){
        Strorder order = new Strorder();
        order.setId(String.valueOf(snowflake.nextId()));
        order.setUserid(userid);
        strorderMapper.save(order);
        return order;
    }

    /**
     * sharding的雪花算法
     */
    @GetMapping("/shardingFlake")
    public Strorder shardingFlake(int userid){
        Strorder order = new Strorder();
        order.setUserid(userid);
        strorderMapper.shardingIdSave(order);
        // System.out.println(Snowflake.toBit(Long.valueOf(order.getId())));
        return order;
    }

    @GetMapping("/query")
    public List<Incorder> query(){
        IncorderExample example = new IncorderExample();
        example.createCriteria().andUseridEqualTo(2);
        return incorderMapper.selectByExample(example);
    }

}
