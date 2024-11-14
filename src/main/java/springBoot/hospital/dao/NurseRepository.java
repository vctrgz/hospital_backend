package springBoot.hospital.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import springBoot.entity.Nurse;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface NurseRepository extends CrudRepository<Nurse, Integer> {
	Optional<Nurse> findByUserAndPassword(String user, String password);
	Optional<Nurse> findByName(String name);
	  // Método para actualizar el nombre del enfermero por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.name = :name WHERE n.id = :id")
    void updateNameById(String name, Integer id);

    // Método para actualizar el usuario del enfermero por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.user = :user WHERE n.id = :id")
    void updateUserById(String user, Integer id);

    // Método para actualizar la contraseña del enfermero por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Nurse n SET n.password = :password WHERE n.id = :id")
    void updatePasswordById(String password, Integer id);
}
