package ks51team03.company.service;


import ks51team03.company.dto.*;
import ks51team03.company.mapper.CompanyMapper;
import ks51team03.member.dto.Member;
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


    // 직원 신청 insert 작업시 기본키 숫자 알아내기
    public String getNewStfCode() {
        String lastStfCode = companyMapper.getLastStfCode();
        int newCodeNumber = Integer.parseInt(lastStfCode.substring(3)) + 1;
        return "stf" + newCodeNumber;
    }

    // 직원 신청 insert 작업
    public void insertStaff(ComStaff comStaff) {
        companyMapper.insertStaff(comStaff);
    }

    // 업체 수정
    public int modifyCompany(Company company) {
        return companyMapper.modifyCompany(company);
    }

    // 업체 문의 리스트 반환
    public List<ComQuestion> getCompanyQuestion(String cCode){
        log.info("getCompanyQuestion: {}", companyMapper.getCompanyQuestion(cCode));
        return companyMapper.getCompanyQuestion(cCode);

    }

    // 전체 업체 리스트 반환
    public List<Company> getCompanyList(){
        log.info("getCompanyList: {}", companyMapper.getCompanyList());
        return companyMapper.getCompanyList();
    }

    // 업체 코드로 업체 운영시간 리스트 반환
    public List<ComOperTime> getCompanyOperTime(String cCode){
        log.info("getCompanyOperTime: {}", companyMapper.getCompanyOperTime(cCode));
        return companyMapper.getCompanyOperTime(cCode);
    }

    // 세션의 아이디를 통해 업체 리스트 반환
    public List<Company> getCompanyListById(String memberId){
        log.info("getCompanyListById: {}", companyMapper.getCompanyListById(memberId));
        return companyMapper.getCompanyListById(memberId);
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


    // 업체 리뷰 반환
    public List<ComReview> getCompanyReview(String companyCode) {
        log.info("Getting ComReview for cCode: {}", companyCode);
        return companyMapper.getCompanyReview(companyCode);
    }

    // 업체 리뷰수 반환
    public int getCompanyReviewCount(String companyCode) {
        int reviewCount = companyMapper.getCompanyReviewCount(companyCode);
        log.info("Review Count for cCode {}: {}", companyCode, reviewCount);
        return reviewCount;
    }

    // 직원 신청 회원 조회
    public List<ComStaff> getStaffSingList(String cCode){
        log.info("Getting Sign Staff List: {}", companyMapper.getStaffSignList(cCode));
        return companyMapper.getStaffSignList(cCode);
    }

    // 직원 리스트 반환
    public List<ComStaff> getStaffList(String cCode){
        log.info("Getting Staff List: {}", companyMapper.getStaffList(cCode));
        return companyMapper.getStaffList(cCode);
    }

    // 해당 멤버 직원으로 업데이트
    public int acceptStaff(String requestId, String memberId){
        log.info("Accepting Staff: {}", requestId);
        return companyMapper.acceptStaff(requestId,memberId);
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
