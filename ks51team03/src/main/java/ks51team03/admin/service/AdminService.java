package ks51team03.admin.service;

import ks51team03.admin.mapper.AdminMapper;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.member.dto.Member;
import ks51team03.pet.dto.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public List<Member> getAdminList(Member member) {
        return adminMapper.getAdminList(member);
    }

    public List<Member> searchAdminList(String searchType, String searchKeyword) {
        return adminMapper.searchAdminList(searchType, searchKeyword);
    }

    public List<Pet> getPetList(Pet pet) {
        return adminMapper.getPetList(pet);
    }

    public List<Company> getCompanyList(Company company) {
        return adminMapper.getCompanyList(company);
    }

    public List<ComStaff> getComStaffList(ComStaff comStaff) {
        return adminMapper.getComStaffList(comStaff);
    }

}
