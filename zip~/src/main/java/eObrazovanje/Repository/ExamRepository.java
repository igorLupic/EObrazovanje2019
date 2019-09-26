package eObrazovanje.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import eObrazovanje.Model.Exam;


public interface ExamRepository extends JpaRepository<Exam, Long> {

}
