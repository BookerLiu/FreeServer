package com.ff.postpone.mapper;

import com.ff.postpone.pojo.UserInfo;
import com.ff.postpone.pojo.UserInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}