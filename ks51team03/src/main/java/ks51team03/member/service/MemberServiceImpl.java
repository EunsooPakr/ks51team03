package ks51team03.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ks51team03.company.dto.ComQuestion;
import ks51team03.company.dto.ComReview;
import ks51team03.company.dto.Company;
import ks51team03.company.mapper.CompanyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.dto.Search;
import ks51team03.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Transactional : 트랜잭션 처리 (ACID) 
 * 클래스위에 @Transactional 선언하게 되면 클래스 내부의 모든 메소드에 적용
 * 메소드위에 @Transactional 선언하게 되면 특정메소드에 적용
 */
@Service("memberService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper memberMapper;
	private final CompanyMapper companyMapper;

	/**
	 * 회원의 특정 리뷰 수정
	 */
	@Override
	public int memberReviewModify(ComReview review) {
		return memberMapper.memberReviewModify(review);
	}

	/**
	 * 회원의 특정 리뷰 검색
	 */
	@Override
	public ComReview getCompanyReviewByRevCode(String revCode){
		return memberMapper.getCompanyReviewByRevCode(revCode);
	}

	/**
	 * 회원 리뷰 검색
	 */
	@Override
	public List<ComReview> getCompanyReview(String memberId) {
		return memberMapper.getCompanyReview(memberId);
	}

	/**
	 * 회원 문의 삭제
	 */
	@Override
	public int memberQuestionDelete(ComQuestion question){
		memberMapper.deleteAnswersByQuesNum(question.getQuesNum());
		return memberMapper.memberQuestionDelete(question);
	}

	/**
	 * 회원 문의 수정
	 */
	@Override
	public int memberQuestionModify(ComQuestion question){
		return memberMapper.memberQuestionModify(question);
	}

	/**
	 * 회원 문의 조회
	 */
	@Override
	public List<ComQuestion> getQuestionById(String memberId){
		return memberMapper.getQuestionById(memberId);
	}

	/**
	 * 로그인 이력 조회
	 */
	@Override
	public Map<String, Object> getLoginHistory(int currentPage) {

		// 보여줄 행의 갯수
		int rowPerPage = 10;
		// 첫번째 인수값
		int startRow = (currentPage - 1) * rowPerPage;
		// 시작페이지 설정 초기화
		int startPageNum = 1;
		// 마지막페이지 설정 초기화
		int endPageNum = 10;

		List<Map<String,Object>> loginHistory = memberMapper.getLoginHistory(startRow, rowPerPage);

		// 전체 행의 갯수 조회
		double cnt = memberMapper.getLoginHistoryCnt();

		// 마지막 페이지
		int lastPage = (int)Math.ceil(cnt/rowPerPage);

		// 마지막페이지 보다 작을 경우 마지막페이지로 설정
		endPageNum = lastPage < 10 ? lastPage : endPageNum;

		// 동적 페이지설정
		if(currentPage > 6 && endPageNum > 9){
			startPageNum =  currentPage - 5;
			endPageNum = currentPage + 4;
			// 마지막페이지번호가 마지막페이지수보다 클 경우에 페이지번호를 고정
			if(endPageNum >= lastPage){
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}


		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("loginHistory", loginHistory);
		resultMap.put("lastPage", lastPage);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);

		return resultMap;
	}

	/**
	 * 회원 검색 조회
	 * @param search
	 */
	@Override
	public List<Member> getSearchList(Search search) {
		String searchKey = search.getSearchKey();
		String columnName = "";
		switch (searchKey) {
			case  "memberId" -> {
				columnName = "m.m_id";
			}
			case  "memberName" -> {
				columnName = "m.m_name";
			}
			case  "memberAddr" -> {
				columnName = "m.m_addr";
			}
		}
		search.setSearchKey(columnName);
		log.info("search: {}", search);
		
		return memberMapper.getSearchList(search);
	}
	
	/**
	 * 회원 탈퇴 프로세스
	 * @param 회원등급, 회원아이디
	 * @detail 회원등급 회원탈퇴
	 */
	@Override
	public void removeMember(String memberLevel, String memberId) {
		// 회원등급
		switch (memberLevel) {
		/*
		 * // 판매자 (상품테이블, 주문테이블) case 2 -> {
		 * 
		 * } // 구매자 (주문테이블) case 3 -> {
		 * 
		 * }
		 */
		}
		// 공통 (로그인테이블, 회원테이블)
		memberMapper.removeLoginHistoryById(memberId);
		memberMapper.removeMemberById(memberId);
	}
	
	/**
	 * 특정 회원 확인
	 * @param memberId, memberPw
	 * @return Map<String, Object> 결과 isCheck true: memberInfo , false
	 */
	@Override
	public Map<String, Object> checkMemberInfo(String memberId, String memberPw) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isCheck = false;
		
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		
		if(memberInfo != null) {
			String checkPw = memberInfo.getMemberPw();
			if(checkPw != null && checkPw.equals(memberPw)) {
				isCheck = true;
				resultMap.put("memberInfo", memberInfo);
			}
		}
		
		resultMap.put("isCheck", isCheck);
		
		return resultMap;
	}
	
	/**
	 * 특정회원 수정
	 */
	@Override
	public int modifyMember(Member member) {
		
		return memberMapper.modifyMember(member);
	}
	
	/**
	 * 특정 회원 정보 조회
	 */
	@Override
	public Member getMemberInfoById(String memberId) {
		
		return memberMapper.getMemberInfoById(memberId);
	}
	
	/**
	 * 회원가입 프로세스
	 */
	@Override
	public void insertMember(Member member) {
		int result = memberMapper.insertMember(member);
		
		//if(result > 0) // 다음 프로세스
		
	}
	
	/**
	 * 회원등급 조회
	 * @author : ksmart51 홍길동 (2024-06-17)
	 * @detail : 특이사항에 대한 내용기술
	 * @return List<MemberLevel>
	 */
	@Override
	public List<MemberLevel> getMemberLevelList() {
		
		return memberMapper.getMemberLevelList();
	}
	
	@Override
	public List<Member> getMemberList() {
		
		List<Member> memberList = memberMapper.getMemberList();
		
		log.info("memberSerive memberList: {}", memberList);
		
		if(memberList != null) {
			memberList.forEach( member -> {
				String memberLevel = member.getMemberLevel();
				switch (memberLevel) {
				case "level1":
					member.setMemberLevelName("관리자");
					break;
				case "level2":
					member.setMemberLevelName("업체 대표 회원");
					break;
				case "level3":
					member.setMemberLevelName("업체 직원 회원");
					break;
				case "level4":
					member.setMemberLevelName("일반 회원");
					break;
				case "level5":
					member.setMemberLevelName("탈퇴 회원");
					break;
				default:
					member.setMemberLevelName("비로그인");
					break;
				}
			});
		}
		
		return memberList;
	}

	@Override
	public String getCompanyCodeByMemberId(String memberId) {
		Company company = companyMapper.getCompanyByMemberId(memberId);
		return company != null ? company.getCompanyCode() : null;
	}
}











