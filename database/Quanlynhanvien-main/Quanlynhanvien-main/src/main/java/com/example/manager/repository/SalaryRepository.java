package com.example.manager.repository;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    @Query("SELECT s FROM Salary s WHERE s.month = :month AND s.year = :year")
    List<Salary> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("select s from Salary s where s.employee = :employee and s.month = :month and s.year = :year")
    Optional<Salary> findByEmployeeAndMonthAndYear(Employee employee, int month, int year);

    Optional<Salary> findByEmployee(Employee employee);
}