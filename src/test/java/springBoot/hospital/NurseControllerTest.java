package springBoot.hospital;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import springBoot.hospital.dao.NurseRepository;
import springBoot.entity.Nurse;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@WebMvcTest(NurseController.class)
public class NurseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NurseRepository nurseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateNurse() throws Exception {
        Nurse nurse = new Nurse();
        nurse.setName("Ana");
        nurse.setUser("ana@nurse.com");
        nurse.setPassword("password");

        given(nurseRepository.save(nurse)).willReturn(nurse);

        mockMvc.perform(post("/nurse/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nurse)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllNurses() throws Exception {
        mockMvc.perform(get("/nurse/nurses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldUpdateNurse() throws Exception {
        Nurse nurse = new Nurse();
        nurse.setId(1);
        nurse.setName("Updated Name");
        nurse.setUser("updated@nurse.com");
        nurse.setPassword("newpassword");

        given(nurseRepository.findById(1)).willReturn(Optional.of(nurse));

        mockMvc.perform(post("/nurse/update")
                .param("newName", "Updated Name")
                .param("newUser", "updated@nurse.com")
                .param("newPassword", "newpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nurse)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteNurseById() throws Exception {
        given(nurseRepository.existsById(1L)).willReturn(true);

        mockMvc.perform(delete("/nurse/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Nurse deleted successfully"));
    }

    @Test
    void shouldReturnNotFoundWhenNurseDoesNotExist() throws Exception {
        given(nurseRepository.findById(999)).willReturn(Optional.empty());

        mockMvc.perform(get("/nurse/id/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Nurse not found"));
    }
}