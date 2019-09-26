package eObrazovanje.Repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import eObrazovanje.Model.Student;



public interface StudentRepository extends JpaRepository<Student, Long> {

	Page<Student> findAllByNameLikeOrUserNameLikeOrLastNameLike(String name,String username, String lastname, Pageable page);

}
