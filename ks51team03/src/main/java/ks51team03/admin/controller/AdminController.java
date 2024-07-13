package ks51team03.admin.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.admin.service.AdminService;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberService memberService;
    private final AdminService adminService;

    @GetMapping("admin/admin_index")
    public String adminIndex(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

       return "admin/admin_index";
    };

    @GetMapping("admin/admin_member_list")
    public String adminMemberList(HttpSession session, Model model, RedirectAttributes redirectAttributes, Member member) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

        List<Member> adminMemberList = adminService.getAdminList(member);
        model.addAttribute("adminMemberList", adminMemberList);

        return "admin/admin_member_list";
    };

}
