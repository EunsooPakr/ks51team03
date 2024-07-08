package ks51team03.pet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import ks51team03.pet.dto.Pet;
import ks51team03.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PetController {
	private final PetService petService;
	private final MemberService memberService;
	
	/*업체 등록*/
	@PostMapping("/pet/insertPet")
	public String insertPet(Pet pet,HttpSession session) {
		
		Member member=(Member)session.getAttribute("SMEM");
		log.info("반려동물등록 Company:{}", pet);
		log.info("회원가입 Member:{}", member);
		memberService.insertMember(member);
		petService.insertPet(pet,member.getMemberId());
		
		session.invalidate();
		
		return "redirect:/member/member_main";
	}
	
	@GetMapping("/pet/insertPet")
	public String insertPet(Model model) {
		
		model.addAttribute("title", "업체등록");
		
		return "member/member_login_insert_pet";
	}
}
