package com.software.educational.data.repository;

import com.software.educational.data.model.ModuleTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleTestRepository extends JpaRepository<ModuleTest,Long> {

    public ModuleTest findByUserIdAndModuleId(Long userId, Long moduleId);


    @Query(nativeQuery = true, value = "SELECT COUNT(user_id) FROM module_test WHERE user_id=? AND module_id=?")
    public Integer countByUserIdAndModuleId(Long user_id, Long module_id);

    Iterable<ModuleTest> findByUserId(Long userId);

}
