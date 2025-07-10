package com.example.leavemanagement1.controller;

import com.example.leavemanagement1.model.LeaveRequest;
import com.example.leavemanagement1.service.LeaveRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "AdminDashboard";
    }

    @GetMapping("/staff-leaves")
    public String viewStaffLeaveRequests(Model model) {
        List<LeaveRequest> staffLeaves = leaveRequestService.getStaffLeaveRequestsForAdmin();
        model.addAttribute("leaveRequests", staffLeaves);
        return "AdminStaffLeaveRequests";
    }

    @PostMapping("/staff-leaves/approve")
    public String approveLeave(@RequestParam Long id, HttpSession session) {
        String approver = (String) session.getAttribute("username");
        leaveRequestService.approveLeave(id, approver);
        return "redirect:/admin/staff-leaves";
    }

    @PostMapping("/staff-leaves/reject")
    public String rejectLeave(@RequestParam Long id, HttpSession session) {
        String approver = (String) session.getAttribute("username");
        leaveRequestService.rejectLeave(id, approver);
        return "redirect:/admin/staff-leaves";
    }

    @GetMapping("/overall-leaves")
    public String viewAllRequests(Model model) {
        List<LeaveRequest> allRequests = leaveRequestService.getAllLeaveRequests();
        model.addAttribute("leaveRequests", allRequests);
        return "AdminOverallLeaveRequests";
    }
}
