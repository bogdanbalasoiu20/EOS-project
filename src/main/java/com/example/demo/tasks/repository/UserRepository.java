package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByInternal(Integer internal);
    List<User> findByUsernameContainingIgnoreCaseAndInternal(String username, Integer internal);
    @Query("""
        SELECT u
        FROM User u
        WHERE NOT EXISTS (
            SELECT t
            FROM Task t
            WHERE t.user = u
        )
        """)
    List<User> findUsersWithoutTasks();
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
