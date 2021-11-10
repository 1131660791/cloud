package com.consumer.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzw
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("consumer")
public class Consumer extends Model<Consumer> {

    private static final long serialVersionUID=1L;

    /**
     * 消费者id
     */
    private Integer id;

    /**
     * 消费者服务名
     */
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
