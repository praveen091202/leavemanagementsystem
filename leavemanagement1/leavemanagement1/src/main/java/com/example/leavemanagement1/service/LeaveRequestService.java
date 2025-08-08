package com.example.leavemanagement1.service;

import com.example.leavemanagement1.model.LeaveRequest;
import com.example.leavemanagement1.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public void saveLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getStudentLeaveRequestsForStaff() {
        return leaveRequestRepository.findByRole("STUDENT");
    }

    public List<LeaveRequest> getLeaveRequestsByUser(String requesterUsername, String role) {
        return leaveRequestRepository.findByRequesterUsernameAndRole(requesterUsername, role);
    }

    public List<LeaveRequest> getStaffLeaveRequestsForAdmin() {
        return leaveRequestRepository.findByRole("STAFF");
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id).orElse(null);
    }

    public void approveLeave(Long id, String approver) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Accepted");
            leaveRequest.setApproverUsername(approver); 
            leaveRequestRepository.save(leaveRequest);
        }
    }

    public void rejectLeave(Long id, String approver) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Rejected");
            leaveRequest.setApproverUsername(approver); 
            leaveRequestRepository.save(leaveRequest);
        }
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
}

