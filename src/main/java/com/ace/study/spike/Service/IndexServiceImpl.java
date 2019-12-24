package com.ace.study.spike.Service;

import com.ace.study.spike.DO.IndexDO;
import com.ace.study.spike.mapper.IndexMapper;
import com.ace.study.spike.VO.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public  IndexVO select2Db(Integer id) {
        IndexDO indexDO = indexMapper.select2Db(id);
        IndexVO indexVO = new IndexVO(indexDO == null ? new IndexDO():indexDO);
        return indexVO;
    }

    @Override
    public int insert2Db(IndexVO indexVO) {
        IndexDO indexDO = new IndexDO(indexVO);
        return indexMapper.insert2Db(indexDO);
    }

    @Override
    public int update2Db(IndexVO indexVO) {
        IndexDO indexDO = new IndexDO(indexVO);
        return indexMapper.update2Db(indexDO);
    }

    @Override
    public int delete2Db(Integer id) {
        return indexMapper.delete2Db(id);
    }
}
