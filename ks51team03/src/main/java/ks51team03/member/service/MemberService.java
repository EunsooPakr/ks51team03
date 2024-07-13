package ks51team03.member.service;

import java.util.List;
import java.util.Map;

import ks51team03.company.dto.ComQuestion;
import ks51team03.company.dto.ComReview;
import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.dto.Search;

public interface MemberService {

	// 이미지 삭제
	int memberReviewModify(ComReview review, boolean deleteImage);

	// 회원 리뷰 수정
	int memberReviewModify(ComReview review);

	// 회원 리뷰 수정을 위한 특정 리뷰 검색
	ComReview getCompanyReviewByRevCode(String revCode);

	// 회원 리뷰 검색
	List<ComReview> getCompanyReview(String memberId);

	// 회원 문의 삭제
	int memberQuestionDelete(ComQuestion question);

	// 회원 문의 수정
	int memberQuestionModify(ComQuestion question);

	// 회원 문의 리스트 조회
	List<ComQuestion> getQuestionById(String memberId);

	// 로그인 이력 조회
	Map<String, Object> getLoginHistory(int currentPage);

	// 회원 검색 리스트 조회
	List<Member> getSearchList(Search search);
	
	// 회원 탈퇴
	void removeMember(String memberLevel, String memberId);
	
	// 회원 정보 확인
	Map<String, Object> checkMemberInfo(String memberId, String memberPw);
	
	// 회원 정보 수정
	int modifyMember(Member member);
	
	// 회원 정보 조회
	Member getMemberInfoById(String memberId);
	
	// 회원가입
	void insertMember(Member member);
	
	// 회원등급 조회
	List<MemberLevel> getMemberLevelList();

	// 회원목록 조회
	List<Member> getMemberList();

	String getCompanyCodeByMemberId(String memberId);
}
