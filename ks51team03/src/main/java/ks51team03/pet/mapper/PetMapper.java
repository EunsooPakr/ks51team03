package ks51team03.pet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ks51team03.pet.dto.Pet;

@Mapper
public interface PetMapper {
	 // 반려동물 등록
 	int insertPet(Pet pet);
 	
 	// 반려동물 코드 도출
 	int getPetCode();
 	
 	// 회원 아이디로 반려동물 검색 
 	public List<Pet> getPetInfoByMemberId(String memberId);
}
