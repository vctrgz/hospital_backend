package SpringBoot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Nurse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;
    private String user;
    private String password;

    
    // Getter and setter for 'id'
    public Integer getId() {
    	return id;
    }
    public void setId(Integer id) {
    	this.id = id;
    }
    
    // Getter and setter for 'name'
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for 'user'
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    // Getter and setter for 'password'
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

