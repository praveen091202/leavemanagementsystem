package com.example.leavemanagement1.controller;

import com.example.leavemanagement1.model.LeaveRequest;
import com.example.leavemanagement1.service.LeaveRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/dashboard")
    public String staffDashboard() {
        return "StaffDashboard";
    }

    @GetMapping("/student-requests")
    public String viewStudentLeaveRequests(Model model) {
        List<LeaveRequest> studentLeaves = leaveRequestService.getStudentLeaveRequestsForStaff();
        model.addAttribute("leaveRequests", studentLeaves);
        return "StaffStudentRequests";
    }

    @GetMapping("/apply-leave")
    public String showStaffLeaveForm() {
        return "StaffApplyLeave";
    }

    @PostMapping("/apply-leave")
    public String submitStaffLeave(@RequestParam String fromDate,
                                   @RequestParam String toDate,
                                   @RequestParam String reason,
                                   HttpSession session) {

        String username = (String) session.getAttribute("username");

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setRequesterUsername(username);
        leaveRequest.setFromDate(LocalDate.parse(fromDate));
        leaveRequest.setToDate(LocalDate.parse(toDate));
        leaveRequest.setReason(reason);
        leaveRequest.setStatus("PENDING");
        leaveRequest.setRole("STAFF");

        leaveRequestService.saveLeaveRequest(leaveRequest);
        return "redirect:/staff/leave-history";
    }

    @GetMapping("/leave-history")
    public String viewStaffLeaveHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByUser(username, "STAFF");
        model.addAttribute("leaveRequests", leaveRequests);
        return "StaffLeaveHistory";
    }

    @PostMapping("/approve-leave")
    public String approveStudentLeave(@RequestParam Long id, HttpSession session) {
        String approver = (String) session.getAttribute("username");
        leaveRequestService.approveLeave(id, approver);
        return "redirect:/staff/student-requests";
    }

    @PostMapping("/reject-leave")
    public String rejectStudentLeave(@RequestParam Long id, HttpSession session) {
        String approver = (String) session.getAttribute("username");
        leaveRequestService.rejectLeave(id, approver);
        return "redirect:/staff/student-requests";
    }
}
