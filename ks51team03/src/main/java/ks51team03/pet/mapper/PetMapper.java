package ks51team03.pet.mapper;

import org.apache.ibatis.annotations.Mapper;

import ks51team03.pet.dto.Pet;

@Mapper
public interface PetMapper {
	 // 반려동물 등록
 	int insertPet(Pet pet);
 	
 	// 반려동물 코드 도출
 	int getPetCode();
}
