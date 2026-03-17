package com.maintain.vacation.repository;

import com.maintain.vacation.model.User;
import com.maintain.vacation.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, String> {
    List<Vacation> findByUser(User user);
}
