package ks51team03.company.mapper;

import ks51team03.company.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {

    // 업체  수정
    int modifyCompany(Company company);

    // 업체 문의 리스트 가져오기
    List<ComQuestion> getCompanyQuestion(@Param("cCode") String cCode);

    // 전체 업체 리스트 가져오기
    List<Company> getCompanyList();

    // 업체 운영 시간리스트 가져오기
    List<ComOperTime> getCompanyOperTime(String cCode);

    // 업체 리스트 아이디로 가져오기
    List<Company> getCompanyListById(String memberId);

    // 업체 리스트 키워드로 가져오기
    List<Company> getCompanyListByKeyWord(@Param("keyword") String keyword);

    // 업체 좌표 가져오기
    ComMap getComMapByCCode(@Param("cCode") String cCode);

    // 신청 직원 가져오기
    List<ComStaff> getStaffSignList(String cCode);

    int acceptStaff(String requestId, String memberId);

    // 아이디로 업체 정보 가져오기
    Company getCompanyByMemberId(@Param("memberId") String memberId);

    // 업체 코드로 해당 업체 리뷰 불러오기
    List<ComReview> getCompanyReview(@Param("cCode") String cCode);

    // 업체 코드로 해당 업체 리뷰수 불러오기
    Integer getCompanyReviewCount(@Param("cCode") String cCode);

}
