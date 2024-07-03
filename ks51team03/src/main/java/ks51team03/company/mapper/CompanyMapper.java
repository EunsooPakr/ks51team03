package ks51team03.company.mapper;

import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.Company;
import ks51team03.member.dto.Member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<Company> getCompanyList(@Param("keyword") String keyword);

    // 업체 좌표 가져오기
    ComMap getComMapByCCode(@Param("cCode") String cCode);
    
    // 업체 등록
 	int insertCompany(Company company);
 	
 	// 업체 코드 도출
 	int getCompanyCode();
 	
 	// 업체 대표 권한 변경
 	int updateCeo(Company company);
}
