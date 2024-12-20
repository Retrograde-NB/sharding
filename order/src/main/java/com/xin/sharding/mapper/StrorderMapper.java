package com.xin.sharding.mapper;

import com.xin.sharding.entity.Strorder;
import com.xin.sharding.entity.StrorderExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StrorderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    long countByExample(StrorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    int deleteByExample(StrorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    @Delete({
        "delete from strorder",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    @Insert({
        "insert into strorder (userid)",
        "values (#{userid,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=String.class)
    int insert(Strorder record);

    @Insert({
            "insert into strorder (id,userid)",
            "values (#{id},#{userid,jdbcType=INTEGER})"
    })
    int save(Strorder record);

    @Insert({
            "insert into strorder (id,userid)",
            "values (#{id},#{userid,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT getid('orders') from dual", keyProperty="id", before=true, resultType=String.class)
    int getIdSave(Strorder record);


    @Insert({
            "insert into strorder (userid)",
            "values (#{userid,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT max(id) from strorder where userid=#{userid,jdbcType=INTEGER}", keyProperty="id", before=false, resultType=String.class)
    int shardingIdSave(Strorder record);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    int insertSelective(Strorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    List<Strorder> selectByExample(StrorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, userid",
        "from strorder",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("com.xin.sharding.mapper.StrorderMapper.BaseResultMap")
    Strorder selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Strorder record, @Param("example") StrorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Strorder record, @Param("example") StrorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Strorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strorder
     *
     * @mbg.generated
     */
    @Update({
        "update strorder",
        "set userid = #{userid,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Strorder record);
}