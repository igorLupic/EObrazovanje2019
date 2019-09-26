package eObrazovanje.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import eObrazovanje.Model.User;


public interface UserRepository extends JpaRepository<User, Long>{
	User findUserByUserName(String username);
}
