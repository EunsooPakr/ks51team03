package ks51team03.pet.mapper;

import org.apache.ibatis.annotations.Mapper;

import ks51team03.pet.dto.Pet;

@Mapper
public interface PetMapper {
	 // 업체 등록
 	int insertPet(Pet pet);
 	
 	// 업체 코드 도출
 	int getPetCode();
}
