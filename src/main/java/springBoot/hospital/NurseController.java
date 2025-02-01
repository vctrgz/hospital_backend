package springBoot.hospital;

import java.util.List;
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


    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Optional<Nurse>> login(@RequestBody Nurse nurse) {
        Optional<Nurse> loggedNurse = nurseRepository.findByNameAndPassword(nurse.getName(), nurse.getPassword());
        if (loggedNurse.isPresent()) {
            return ResponseEntity.ok(loggedNurse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loggedNurse);
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
