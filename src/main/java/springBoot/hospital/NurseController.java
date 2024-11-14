package springBoot.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    //Delete nurse by ID (200 OK, 404 Not Found)
 	@DeleteMapping("/deletenurse/{id}")
 	public ResponseEntity<String> deleteNurseById(@PathVariable int id) {
 	    // Check if the nurse exists by ID
 	    if (nurseRepository.existsById(id)) {
 	        nurseRepository.deleteById(id); // Delete the nurse
 	        return ResponseEntity.ok("Nurse deleted successfully");
 	    } else {
 	        // If nurse not found, return 404 Not Found
 	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Nurse not found");
 	    }
 	}
}

