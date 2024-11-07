package springboot.hospital.dao;


import org.springframework.data.repository.CrudRepository;

import springboot.entity.Nurse;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface nurseRepository extends CrudRepository<Nurse, Integer> {

}