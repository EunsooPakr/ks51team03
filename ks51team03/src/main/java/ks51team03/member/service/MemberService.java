package ks51team03.member.service;

import java.util.List;
import java.util.Map;

import ks51team03.company.dto.ComInform;
import ks51team03.company.dto.ComInformReciPient;
import ks51team03.company.dto.ComQuestion;
import ks51team03.company.dto.ComReview;
import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.dto.MemberLike;
import ks51team03.member.dto.Search;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

	// 회원 구독 상태 0일때 1로 바꾸기
	int updateMemberLikeStateOn(String lkCone);

	// 회원 구독 추가 전 이미 있는지 체크
	MemberLike memberLikeCheckFirst(String cCode, String memberId);

	// 회원 구독 했는지 체크
	MemberLike memberLikeCheck(String cCode, String memberId);

	// 회원 구독 취소 업데이트
	int updateMemberLikeState(String lkCode);

	// 회원 알림 조작
	int updateMemberLikeAlarm(String lkCode, int lkAlarm);

	// 회원 구독 업체리스트 조회
	List<MemberLike> memberGetLikeCompany(String memberId);

	// 회원 구독
    int memberAddLike(String memberId, String cCode);

    // 회원 알림 내용 조회
	List<ComInformReciPient> getInform(String memberId);

	// 회원 알림 수 조회
	int getInformCount(String memberId);

	// 회원 리뷰 삭제
	int memberReviewDelete(ComReview review);

	// 이미지 삭제
	int memberReviewModify(ComReview review, boolean deleteImage, boolean newImage, MultipartFile revImgFile);

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
	int updateMember(Member member);
	
	// 회원 정보 조회
	Member getMemberInfoById(String memberId);
	
	// 회원가입
	void insertMember(Member member);
	
	// 회원등급 조회
	List<MemberLevel> getMemberLevelList();

	// 회원목록 조회
	List<Member> getMemberList();

	String getCompanyCodeByMemberId(String memberId);
	
	void IncreasePetByMemberId(String memberId);
	
	void DeclinePetByMemberId(String memberId);

	// 알림 상태 수정
	void disableNotification(String informId);
	
	//이름과 이메일 정보 일치 확인
	String getMemberIdByNameEmail(Member member);
	
	//아이디와 이메일 정보 일치 확인
	String getMemberPwByNameEmail(Member member);
	
	//비밀번호 변경
	boolean updateMemberPw(Member member);
}
