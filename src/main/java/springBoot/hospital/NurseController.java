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
    public ResponseEntity<Iterable<Nurse>> getAllNurses() {
        Iterable<Nurse> nurses = nurseRepository.findAll();
        // Verificar si hay imagenes asignadas
        for (Nurse nurse : nurses) {
            if (nurse.getProfilePictureUrl() == null || nurse.getProfilePictureUrl().isEmpty()) {
                nurse.setProfilePictureUrl("https://static.nationalgeographicla.com/files/styles/image_3200/public/3897187267_f36b5e4e7a_c.webp?w=1600&h=1200");
            }
        }
        return ResponseEntity.ok(nurses);
    }

    // Login functionality
    @PostMapping("/login")    
    public @ResponseBody ResponseEntity<Optional<Nurse>> login(@RequestBody Nurse nurse) {
        Optional<Nurse> loggedNurse;
        loggedNurse = nurseRepository.findByNameAndPassword(nurse.getName(), nurse.getPassword());
        if (loggedNurse.isPresent()) {
            return ResponseEntity.ok(loggedNurse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loggedNurse);
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
    	Optional<Nurse> newNurse = nurseRepository.findByName(nurse.getName());
    	if (!newNurse.isPresent()) {
    		try {
    			if (nurse.getProfilePictureUrl() == null || nurse.getProfilePictureUrl().isEmpty()) {
                    nurse.setProfilePictureUrl("https://example.com/default-profile.jpg");
                }
    			Nurse createdNurse = nurseRepository.save(nurse);
    			return ResponseEntity.status(HttpStatus.CREATED).body(createdNurse);
    		} catch (Exception e) {
    			// TODO: handle exception
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    		}			
		}else {
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
