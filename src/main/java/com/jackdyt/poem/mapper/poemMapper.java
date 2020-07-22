package com.jackdyt.poem.mapper;

import com.jackdyt.poem.dto.poemDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface poemMapper {
    List<poemDTO> findAll();

    List<poemDTO> findByPage(@Param("start") Integer start, @Param("size") Integer size);

    Long findTotal();
}
