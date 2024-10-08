package ks51team03.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks51team03.member.dto.Member;
import ks51team03.pet.dto.Pet;
import ks51team03.pet.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("PetService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PetService {
	private final PetMapper petMapper;
	
	// 반려동물 등록
    public void insertPet(Pet pet,String memberId) {
    	String pcode="pet"+String.valueOf(petMapper.getPetCode()+1);
    	
    	pet.setPetCode(pcode);
    	pet.setMemberId(memberId);
    	pet.setPetUrl("notFound");
    	
		int result = petMapper.insertPet(pet);
	}
    
    // 회원 아이디로 반려동물 검색
    public List<Pet> getPetInfoByMemberId(String memberId) {
		
		return petMapper.getPetInfoByMemberId(memberId);
	}
    
    //펫코드로 반려동물 검색
    public Pet getPetInfoByPetCode(String petCode)
    {
    	return petMapper.getPetInfoByPetCode(petCode);
    }
    
    //펫코드로 반려동물정보 수정
    public int updatePet(Pet pet) {
		
		return petMapper.updatePet(pet);
	}
    
    //펫코드로 반려동물 삭제
    public void removePet(String petCode)
    {
    	petMapper.removePet(petCode);
    }
	
}
