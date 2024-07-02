package ks51team03.company.service;


import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.ComOperTime;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.company.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CompanyService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyMapper companyMapper;

    // 업체 코드로 업체 운영시간 리스트 반환
    public List<ComOperTime> getCompanyOperTime(String cCode){
        log.info("getCompanyOperTime: {}", companyMapper.getCompanyOperTime(cCode));
        return companyMapper.getCompanyOperTime(cCode);
    }

    // 세션의 아이디를 통해 업체 리스트 반환
    public List<Company> getCompanyList(String memberId){
        log.info("getCompanyList: {}", companyMapper.getCompanyList(memberId));
        return companyMapper.getCompanyList(memberId);
    }

    // 업체 리스트 키워드로 반환
    public List<Company> getCompanyListByKeyWord(String keyword) {
        return companyMapper.getCompanyListByKeyWord(keyword);

    }

    // 좌표 반환
    public ComMap getComMapByCCode(String companyCode) {
        log.info("Getting ComMap for cCode: {}", companyCode);
        return companyMapper.getComMapByCCode(companyCode);
    }

    // 직원 신청 회원 조회
    public List<ComStaff> getStaffSingList(String ccode){
        log.info("Getting Staff List: {}", companyMapper.getStaffSingList(ccode));
        return companyMapper.getStaffSingList(ccode);
    }

    // 해당 멤버 직원으로 업데이트
    public int acceptStaff(String requestId, String memberId){
        log.info("Accepting Staff: {}", requestId);
        return companyMapper.acceptStaff(requestId,memberId);
    }

}
