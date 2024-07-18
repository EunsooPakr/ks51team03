package ks51team03.member.mapper;

import java.util.List;
import java.util.Map;

import ks51team03.company.dto.ComInform;
import ks51team03.company.dto.ComInformReciPient;
import ks51team03.company.dto.ComQuestion;
import ks51team03.company.dto.ComReview;
import org.apache.ibatis.annotations.Mapper;

import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.dto.Search;
import org.springframework.data.repository.query.Param;

@Mapper
public interface MemberMapper {

	// 회원 알림 내용 조회
	List<ComInformReciPient> getInform(String memberId);

	// 회원 알림 수 조회
	Integer getInformCount(String memberId);

	// 회원 리뷰 삭제
	int memberReviewDelete(ComReview review);

	// 회원 리뷰 수정
	int memberReviewModify(ComReview review);

	// 회원 리뷰 수정을 위한 특정 리뷰 검색
	ComReview getCompanyReviewByRevCode(String revCode);

	// 회원 리뷰 검색
	List<ComReview> getCompanyReview(String memberId);

	// 회원 문의 답변 삭제
	int deleteAnswersByQuesNum(String quesNum);

	// 회원 문의 삭제
	int memberQuestionDelete(ComQuestion question);

	// 회원 문의 수정
	int memberQuestionModify(ComQuestion question);

	// 회원 문의 검색
	List<ComQuestion> getQuestionById(String memberId);

	// 로그인 테이블 행의 갯수조회
	int getLoginHistoryCnt();

	// 로그인 이력 조회
	List<Map<String, Object>> getLoginHistory(int startRow, int rowPerPage);

	// 회원 검색 조회
	List<Member> getSearchList(Search search);
	
	// 판매자 현황
	List<Member> getSellerList();
	
	// 회원 탈퇴
	int removeMemberById(String memberId);
	
	// 회원 로그인 이력 삭제
	int removeLoginHistoryById(String memberId);
	
	// 회원수정
	int updateMember(Member member);
	
	// 특정회원정보조회
	Member getMemberInfoById(String memberId);
	
	// 회원가입
	int insertMember(Member member);
	
	// 아이디 중복체크
	boolean idCheck(String memberId);
	
	// 회원등급 조회
	List<MemberLevel> getMemberLevelList();

	// 회원목록 조회
	List<Member> getMemberList();
	
	// 회원의 반려동물 수 추가
	public void IncreasePetByMemberId(String memberId);
	
	//회원의 반려동물 수 감소
	public void DeclinePetByMemberId(String memberId);

	// 회원의 알림 확인
	void updateInformStatus(String informId);
	
	//회원 이름과 이메일 일치 확인
	String getMemberIdByNameEmail(Member member);
	
	//회원 아이디와 이메일 일치 확인
	String getMemberPwByNameEmail(Member member);
	
	//비밀번호 수정
	public int updateMemberPw(Member member);
}
