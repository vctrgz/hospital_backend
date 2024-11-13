package springBoot.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springBoot.hospital.dao.NurseRepository;
import springBoot.entity.Nurse;

@RestController
@RequestMapping("/nurse")
public class NurseController {
    @Autowired
    private NurseRepository nurseRepository;
    
    // Get all registered nurses
    @GetMapping("/nurses")
    public @ResponseBody ResponseEntity<Iterable<Nurse>> getAllNurses() {
        return ResponseEntity.ok(nurseRepository.findAll());
    }
    
    // Login functionality
    @PostMapping("/login")	
	public @ResponseBody ResponseEntity<Boolean> login(@RequestBody Nurse nurse) {
		boolean loginCorrecto = false;
		Optional<Nurse> loggedNurse;
		loggedNurse = nurseRepository.findByUserAndPassword(nurse.getUser(), nurse.getPassword());
		if (loggedNurse.isPresent()) {
			loginCorrecto = true;
		}
		if (loginCorrecto) {
			return ResponseEntity.ok(loginCorrecto);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginCorrecto);
		}
	}
	
    // Find nurse by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Nurse> findByName(@PathVariable String name) {
        Optional<Nurse> loggedNurse;
		loggedNurse = nurseRepository.findByName(name);
		if (loggedNurse.isPresent()) {
			return ResponseEntity.ok(loggedNurse.get());
		}
        return ResponseEntity.notFound().build();
    }
    
    // Find nurse by id
 // Endpoint to find nurse by id
 	@GetMapping("/findnursebyid/{id}")
 	public @ResponseBody ResponseEntity<?> getNurseById(@PathVariable int id) {
 	    // Find nurse by id
 	    Optional<Nurse> nurse = nurseRepository.findById(id);

 	    // If nurse is not found
 	    if (!nurse.isPresent()) {
 	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found"); // 404 Not Found
 	    }

 	    // If nurse is found
 	    return ResponseEntity.ok(nurse.get()); // 200 OK
 	}

}

