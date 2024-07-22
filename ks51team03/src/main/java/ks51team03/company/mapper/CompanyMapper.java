package ks51team03.company.mapper;


import ks51team03.company.dto.*;
import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {

    // 업체 운영시간 수정
    int updateOperTime(ComOperTime comOperTime);

    // 업체 운영시간 등록
    int insertOperTime(ComOperTime comOperTime);

    // 모든 리뷰 조회
    List<ComReview> getAllReview();

    // 업체 대표 이미지 삭제
    int deleteCompanyImage(String imageId);

    // 업체 대표 이미지 리스트 조회
    List<CompanyImg> getCompanyImgByCcode(String cCode);

    // 업체 알림 수신자 등록
    int insertComInformReciPient(ComInformReciPient comInformReciPient);

    // 업체 알림 내용 저장
    int insertComInform(ComInform comInform);

    // 업체 구독자 리스트
    List<ComLike> getCompanyLikeMemberByCcode(String cCode);

    // 업체 리뷰 별점 평균 반환
    Double avgReviewScore(String companyCode);

    // 업체의 리뷰 삭제
    int deleteReview(ComReview comReview);

    // 업체 대표 이미지 등록
    void insertCompanyImg(CompanyImg companyImg);

    // 리뷰 등록
    void insertReview(ComReview comReview);

    // 문의 등록
    void insertQuestion(ComQuestion comQuestion);

    // 업체의 문의 삭제 전 답변부터 삭제
    void deleteAnswersByQuesNum(String quesNum);

    // 업체의 문의 삭제
    void deleteQuestion(String comQuestion);

    // 문의 답변 검색
    ComQuestionAnswer getAnswerByQuesNum(String quesnum);

    // 문의 답변 수정
    void updateAnswer(ComQuestionAnswer comQuestionAnswer);

    // 문의 답변 등록
    void insertAnswer(ComQuestionAnswer comQuestionAnswer);

    // 직원 신청 로직
    void insertStaff(ComStaff comStaff);

    // 업체  수정
    int modifyCompany(Company company);

    // 업체 문의 답변 리스트 가져오기
    List<ComQuestionAnswer> getCompanyQuestionAnswer(@Param("cCode") String cCode);

    // 업체 문의 리스트 가져오기
    List<ComQuestion> getCompanyQuestion(@Param("cCode") String cCode);

    // 전체 업체 리스트 가져오기
    List<Company> getCompanyList();

    // 특정 문의 조회
    ComQuestion getCompanyQuestionById(@Param("quesnum") String quesnum);

    // 업체 운영 시간리스트 가져오기
    List<ComOperTime> getCompanyOperTime(String cCode);

    // 업체 정보 아이디로 가져오기
    List<Company> getCompanyInfoById(String memberId);

    // 회원 아이디로 업체 코드 가져오기
    String getCompanyCodeByMemberId(String memberId);

    // 업체 코드로 업체 정보 가져오기(직원용)
    List<Company> getCompanyInfoByCcode(String companyCode);

    // 업체 리스트 키워드로 가져오기
    List<Company> getCompanyListByKeyWord(@Param("keyword") String keyword);

    // 업체 좌표 가져오기
    ComMap getComMapByCCode(@Param("cCode") String cCode);

    // 신청 직원 가져오기
    List<ComStaff> getStaffSignList(String cCode);

    // 직원 리스트 가져오기
    List<ComStaff> getStaffList(String cCode);

    // 직원 승인 전 회원 레벨 변경
    int updateLevel(String memberId);

    // 직원 승인
    int acceptStaff(String requestId, String memberId);

    // 직원 해고
    int deleteStaff(String requestId);

    // 아이디로 업체 정보 가져오기
    Company getCompanyByMemberId(@Param("memberId") String memberId);

    // 업체 코드로 해당 업체 리뷰 불러오기
    List<ComReview> getCompanyReview(@Param("cCode") String cCode);

    // 업체 코드로 해당 업체 리뷰수 불러오기
    Integer getCompanyReviewCount(@Param("cCode") String cCode);
    
    // 업체 등록
 	int insertCompany(Company company);
 	
 	// 업체 코드 도출
 	int getCompanyCode();
 	
 	// 업체 대표 권한 변경
 	int updateCeo(Company company);


}
