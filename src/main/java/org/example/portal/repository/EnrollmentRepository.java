package org.example.portal.repository;

import org.example.portal.model.Enrollment;
import org.example.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(User user);
    boolean existsByUserAndCourseId(User user, Long courseId);
} 