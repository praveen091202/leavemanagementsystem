package com.example.leavemanagement1.repository;

import com.example.leavemanagement1.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {


    List<LeaveRequest> findByRequesterUsernameAndRole(String requesterUsername, String role);

    List<LeaveRequest> findByRole(String role);
}
