package ks51team03.pet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
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
		String result="";
		
		if(session.getAttribute("SID")!=null)
		{
			String memberId=(String) session.getAttribute("SID");
			
			petService.insertPet(pet,memberId);
			memberService.IncreasePetByMemberId(memberId);
			
			result="/member/member_mypage_list_pet";
		}
		else
		{
			Member member=(Member)session.getAttribute("SMEM");
			log.info("반려동물등록 Company:{}", pet);
			log.info("회원가입 Member:{}", member);
			memberService.insertMember(member);
			petService.insertPet(pet,member.getMemberId());
			
			session.invalidate();
			
			result="/member/member_main";
		}
		
		
		return "redirect:"+result;
	}
	
	@GetMapping("/pet/insertPet")
	public String insertPet(Model model) {
		
		model.addAttribute("title", "업체등록");
		
		return "member/member_login_insert_pet";
	}
	
	@PostMapping("/pet/updatePet")
	public String updatePet(Pet pet) {
		log.info("반려동물수정 Pet:{}", pet);
		petService.updatePet(pet);
		return "redirect:/pet/member_mypage_petinfo?petCode=" + pet.getPetCode();
	}
	
	@GetMapping("/pet/updatePet")
	public String updatePet(@RequestParam(value="petCode") String petCode
							  ,Model model) {
		log.info("수정화면 petCode:{}", petCode);
		Pet petInfo = petService.getPetInfoByPetCode(petCode);
		
		model.addAttribute("title", "회원수정");
		model.addAttribute("petInfo", petInfo);
		
		return "member/member_login_update_pet";
	}
	
	@GetMapping("/pet/removePet")
	public String removePet(@RequestParam(value="petCode") String petCode
							  ,Model model,HttpSession session) {
		
		String memberId=(String) session.getAttribute("SID");
		
		petService.removePet(petCode);
		memberService.DeclinePetByMemberId(memberId);
		
		return "redirect:/member/member_mypage_list_pet";
	}
	
	
	@GetMapping("/pet/member_mypage_petinfo")
	public String userMyPagePetInfo(@RequestParam(value = "petCode") String petCode, Model model) 
	{
	    Pet petInfo = petService.getPetInfoByPetCode(petCode);
	    model.addAttribute("petInfo", petInfo);
	    return "member/member_mypage_petinfo";
	}
}
