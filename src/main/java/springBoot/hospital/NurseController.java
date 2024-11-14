package springBoot.hospital;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginCorrecto);
        }
    }

    // Update nurse information
    @PostMapping("/update")    
    public @ResponseBody ResponseEntity<Optional<Nurse>> updateNurse(@RequestBody Nurse nurse, @RequestParam String newName, @RequestParam String newUser, @RequestParam String newPassword) {
        Optional<Nurse> updatingNurse;
        updatingNurse = nurseRepository.findById(nurse.getId());
        if (updatingNurse.isPresent()) {
            updatingNurse.get().setName(newName);
            updatingNurse.get().setUser(newUser);
            updatingNurse.get().setPassword(newPassword);
            nurseRepository.updateNameById(updatingNurse.get().getName(), updatingNurse.get().getId());
            nurseRepository.updatePasswordById(updatingNurse.get().getPassword(), updatingNurse.get().getId());
            nurseRepository.updateUserById(updatingNurse.get().getUser(), updatingNurse.get().getId());
            if (updatingNurse.get().getName() == null || updatingNurse.get().getUser() == null || updatingNurse.get().getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatingNurse);
            } else {
                return ResponseEntity.ok(updatingNurse);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatingNurse);
        }
    }
    
    @PostMapping("/create")	
    public @ResponseBody ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
		try {
			Nurse createdNurse = nurseRepository.save(nurse);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdNurse);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    // Delete nurse by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNurseById(@PathVariable Long id) {
        if (nurseRepository.existsById(id)) {
            nurseRepository.deleteById(id);
            return ResponseEntity.ok("Nurse deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Nurse not found");
        }
    }

    // Find nurse by ID
    @GetMapping("/id/{id}")
    public @ResponseBody ResponseEntity<?> getNurseById(@PathVariable int id) {
        Optional<Nurse> nurse = nurseRepository.findById(id);
        if (!nurse.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nurse not found");
        }
        return ResponseEntity.ok(nurse.get());
    }
}

