package eObrazovanje.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eObrazovanje.DTO.ChangePasswordDTO;
import eObrazovanje.DTO.UserDTO;
import eObrazovanje.Model.User;
import eObrazovanje.Service.UserService;


@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    UserService userService;    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        for (User user : users) {
        	
            usersDTO.add(new UserDTO(user));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<User>> getUsersPage(Pageable page) {
		Page<User> users = userService.findAll(page);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROFESSOR','ROLE_STUDENT')")
    @RequestMapping(value = "user/{userName}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserByUserName(@PathVariable String userName) {
        User user = userService.findByUsername(userName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setLastName(userDTO.getLastName());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRole("user");
        user.setUserName(userDTO.getUserName());
        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        User user = userService.findOne(userDTO.getId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        user.setLastName(userDTO.getLastName());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user != null) {
            userService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/changePassword/", method = RequestMethod.PUT)
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDTO passwordDTO) throws Exception {
        User user = userService.findOne(passwordDTO.getUserID());
        if (user == null) {
            throw new Exception(); 
            } 
        String newPass = passwordDTO.getNewPassword();
        String repeatPass = passwordDTO.getReapeatPassword();
        if(!newPass.equals(repeatPass)){
        	System.out.println("JBG");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(newPass);
		user.setPassword(hashedPassword);
		userService.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
    }
}

