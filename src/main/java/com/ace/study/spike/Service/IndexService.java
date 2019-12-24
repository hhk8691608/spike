package com.ace.study.spike.Service;

import com.ace.study.spike.VO.IndexVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

public interface IndexService {

    public IndexVO select2Db( Integer id);

    public int insert2Db( IndexVO indexVO);

    public int update2Db( IndexVO indexVO);

    public int delete2Db( Integer id);

}
