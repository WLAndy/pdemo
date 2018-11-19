package com.zy.manage.id.service;

import com.zy.manage.id.IdGenerate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdGenerateService {

    @Value("${id_generate_work_id}")
    private Long workId;

    @Value("${id_generate_data_id}")
    private Long dataId;

    private IdGenerate idGenerate = null;

    public long getNexId(){
        if (idGenerate == null){
            synchronized (this){
                if (idGenerate == null){
                    idGenerate = new IdGenerate(workId,dataId);
                }
            }
        }
        return idGenerate.nextId();
    }
}
