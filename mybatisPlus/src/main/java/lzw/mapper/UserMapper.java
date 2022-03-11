package lzw.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lzw.entity.User;
import lzw.restMap.UserDto;
import org.apache.ibatis.annotations.Param;

/**
 * User对象持久层
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<UserDto> pageCanditionQuery(Page page, @Param(Constants.WRAPPER)Wrapper wrapper);

}
