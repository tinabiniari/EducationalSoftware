package com.software.educational.service;

import com.software.educational.data.model.Module;
import com.software.educational.data.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;


    public Iterable<Module> getAllModules(){
        return moduleRepository.findAll(sortById());
    }

    public Sort sortById(){
        return new Sort(Sort.Direction.ASC,"moduleId");
    }

    public Module getModulebyId(long id){
        return moduleRepository.getOne(id);
    }

    public  Long countModules(){return moduleRepository.count();}
}
