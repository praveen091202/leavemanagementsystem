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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/dashboard")
    public String studentDashboard() {
        return "StudentDashboard";
    }

    @GetMapping("/apply-leave")
    public String showLeaveForm() {
        return "StudentApplyLeave";
    }

    @PostMapping("/apply-leave")
    public String submitLeave(@RequestParam String fromDate,
                              @RequestParam String toDate,
                              @RequestParam String reason,
                              HttpSession session) {

        String username = (String) session.getAttribute("username");

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setRequesterUsername(username); // ✅ Correct
        leaveRequest.setFromDate(LocalDate.parse(fromDate));
        leaveRequest.setToDate(LocalDate.parse(toDate));
        leaveRequest.setReason(reason);
        leaveRequest.setStatus("PENDING");
        leaveRequest.setRole("STUDENT");

        leaveRequestService.saveLeaveRequest(leaveRequest);
        return "redirect:/student/leave-history";
    }

    @GetMapping("/leave-history")
    public String viewLeaveHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByUser(username, "STUDENT");
        model.addAttribute("leaveRequests", leaveRequests);
        return "StudentLeaveHistory";
    }
}
