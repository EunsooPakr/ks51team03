package ks51team03.admin.mapper;

import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.member.dto.Member;
import ks51team03.pet.dto.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Member> getAdminList(Member member);

    List<Member> searchAdminList(@Param("searchType") String searchType, @Param("searchKeyword") String searchKeyword);

    List<Pet> getPetList(Pet pet);

    List<Company> getCompanyList(Company company);

    List<ComStaff> getComStaffList(ComStaff comStaff);
}
