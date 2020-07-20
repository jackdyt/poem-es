package com.jackdyt.poem.mapper;

import com.jackdyt.poem.dto.poemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface poemMapper {
    List<poemDTO> findAll();

    List<poemDTO> findByPage(@Param("start") Integer start, @Param("size") Integer size);

    Long findTotal();
}
