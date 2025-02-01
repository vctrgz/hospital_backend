package springBoot.hospital.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import springBoot.entity.Nurse;

public interface NurseRepository extends CrudRepository<Nurse, Long> {
    Optional<Nurse> findByNameAndPassword(String name, String password);
    Optional<Nurse> findByName(String name);
    Optional<Nurse> findById(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.name = :name WHERE n.id = :id")
    void updateNameById(String name, Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.user = :user WHERE n.id = :id")
    void updateUserById(String user, Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.password = :password WHERE n.id = :id")
    void updatePasswordById(String password, Integer id);

}
