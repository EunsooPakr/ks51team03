package ks51team03.admin.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class adminController {

    private final MemberService memberService;

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

}
