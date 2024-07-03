package ks51team03.company.service;


import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.Company;
import ks51team03.company.mapper.CompanyMapper;
import ks51team03.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("CompanyService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyMapper companyMapper;

    // 업체 리스트 반환
    public List<Company> getCompanyList(String keyword) {
        return companyMapper.getCompanyList(keyword);

    }

    // 좌표 반환
    public ComMap getComMapByCCode(String companyCode) {
        log.info("Getting ComMap for cCode: {}", companyCode);
        return companyMapper.getComMapByCCode(companyCode);
    }

    // 업체 등록
    public void insertCompany(Company company) {
    	String ccode="ccode"+String.valueOf(companyMapper.getCompanyCode()+1);
    	
    	company.setCompanyCode(ccode);		//ccode추가
    	company.setCompanyStfCount(1);		//대표자 1명
    	company.setCompanyPage("");			//페이지 링크
    	company.setCompanyParking(false);	//주차 가능 여부
    	
    	int update=companyMapper.updateCeo(company);
		int result = companyMapper.insertCompany(company);
	}
}
