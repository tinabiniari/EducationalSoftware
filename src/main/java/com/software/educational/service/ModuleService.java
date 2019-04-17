package com.software.educational.service;

import com.software.educational.data.model.Module;
import com.software.educational.data.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;


    public Iterable<Module> getAllModules(){
        return moduleRepository.findAll();
    }

    public Module getModulebyId(long id){
        return moduleRepository.getOne(id);
    }

}
