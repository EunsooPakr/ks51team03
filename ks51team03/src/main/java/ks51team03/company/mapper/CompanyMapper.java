package ks51team03.company.mapper;

import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.ComOperTime;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {

    // 업체 운영 시간리스트 가져오기
    List<ComOperTime> getCompanyOperTime(String cCode);

    // 업체 리스트 가져오기
    List<Company> getCompanyList(String memberId);

    // 업체 리스트 키워드로 가져오기
    List<Company> getCompanyListByKeyWord(@Param("keyword") String keyword);

    // 업체 좌표 가져오기
    ComMap getComMapByCCode(@Param("cCode") String cCode);

    // 신청 직원 가져오기 (나중에 세션으로 업체코드 넣어야함 매개변수에)
    List<ComStaff> getStaffSingList(String ccode);

    int acceptStaff(String requestId, String memberId);

    Company getCompanyByMemberId(@Param("memberId") String memberId);

}
