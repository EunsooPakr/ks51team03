package ks51team03.admin.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.admin.service.AdminService;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import ks51team03.pet.dto.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String adminMemberList(HttpSession session, Model model, RedirectAttributes redirectAttributes,
                                  @RequestParam(value = "searchType", required = false) String searchType,
                                  @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                  Member member) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);
        log.info("SearchType: {}", searchType); // 검색 로그
        log.info("SearchKeyword: {}", searchKeyword); // 검색 로그

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

        List<Member> adminMemberList;
        if (searchType != null && searchKeyword != null && !searchType.isEmpty() && !searchKeyword.isEmpty()) {
            adminMemberList = adminService.searchAdminList(searchType, searchKeyword);
            log.info("Search Results: {}", adminMemberList); // 검색 결과 로그
        } else {
            adminMemberList = adminService.getAdminList(member);
        }
        model.addAttribute("adminMemberList", adminMemberList);

        return "admin/admin_member_list";
    }

    @GetMapping("admin/admin_petList")
    public String adminPetList(HttpSession session, Model model, RedirectAttributes redirectAttributes, Pet pet) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

        List<Pet> petList = adminService.getPetList(pet);
        model.addAttribute("petList", petList);

        return "admin/admin_petList";
    }

    @GetMapping("admin/admin_Company")
    public String adminCompanyList(HttpSession session, Model model, RedirectAttributes redirectAttributes, Company company) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

        List<Company> companyList = adminService.getCompanyList(company);
        model.addAttribute("companyList", companyList);

        return "admin/admin_Company";
    }

    @GetMapping("admin/admin_Com_Staff")
    public String adminComStaffList(HttpSession session, Model model, RedirectAttributes redirectAttributes, ComStaff comStaff) {
        String memberId = (String) session.getAttribute("SID");

        log.info("memberId: {}", memberId);

        if (memberId == null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 전용 페이지입니다");
            return "redirect:/member/member_main"; // 로그인 페이지로 리다이렉트
        }

        List<ComStaff> comStaffList = adminService.getComStaffList(comStaff);
        model.addAttribute("comStaffList", comStaffList);

        return "admin/admin_Com_Staff";
    }

}
