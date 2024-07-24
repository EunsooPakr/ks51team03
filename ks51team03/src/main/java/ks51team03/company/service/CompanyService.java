package ks51team03.company.service;


import ks51team03.company.dto.*;
import ks51team03.company.mapper.CompanyMapper;
import ks51team03.files.dto.FileRequest;
import ks51team03.files.mapper.FileMapper;
import ks51team03.files.util.FileUtils;
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
    private final FileUtils fileUtils;
    private final FileMapper fileMapper;

    // 업체 이미지 조회 지도용
    public List<String> getCompanyImgByCcodeForMap(String cCode){
        return companyMapper.getCompanyImgByCcodeForMap(cCode);
    }

    // 업체 운영시간 수정
    public int updateOperTime(ComOperTime comOperTime){
        return companyMapper.updateOperTime(comOperTime);
    }

    // 업체 운영시간 등록
    public int insertOperTime(ComOperTime comOperTime){
        return companyMapper.insertOperTime(comOperTime);
    }

    // 모든 리뷰 조회
    public List<ComReview> getAllReview(){
        return companyMapper.getAllReview();
    }

    // 업체 대표 이미지 리스트 조회
    public List<CompanyImg> getCompanyImgByCcode(String cCode){
        return companyMapper.getCompanyImgByCcode(cCode);
    }

    // 업체 알림 수신자 등록
    public int insertComInformReciPient(ComInformReciPient comInformReciPient){
        return companyMapper.insertComInformReciPient(comInformReciPient);
    }

    // 업체 알림 내용 저장
    public int insertComInform(ComInform comInform){
        return companyMapper.insertComInform(comInform);
    }

    // 업체 구독자 리스트
    public List<ComLike> getCompanyLikeMemberByCcode(String cCode){
        return companyMapper.getCompanyLikeMemberByCcode(cCode);
    }

    // 업체 리뷰 삭제, memberService에서 하기로함

    // 리뷰 파일 업로드
    public void addReviewWithFile(ComReview comReview) {

        FileRequest fileRequest =  fileUtils.uploadFile(comReview.getRevImgFile(), "리뷰");
        if(fileRequest != null){
            fileRequest.setFileCate("리뷰");

            fileMapper.addFile(fileRequest);
            comReview.setFileIdx(fileRequest.getFileIdx());
        }
        addReview(comReview);

    }

    // 업체 대표 이미지 업로드
    public void addCompanyWithFile(CompanyImg companyimg, String companyCode) {

        FileRequest fileRequest =  fileUtils.uploadFile(companyimg.getRevImgFile(), companyCode+"대표이미지");
        if(fileRequest != null){
            fileRequest.setFileCate(companyCode);
            fileMapper.addFile(fileRequest);
            companyimg.setFileIdx(fileRequest.getFileIdx());
        }
        companyMapper.insertCompanyImg(companyimg);

    }


    // 리뷰 등록하기
    public void addReview(ComReview comReview) {
        companyMapper.insertReview(comReview);
    }

    // 문의 등록하기
    public void addQuestion(ComQuestion comQuestion) {
        companyMapper.insertQuestion(comQuestion);
    }


    // 직원 신청 전 이력 확인
    public Boolean getStaffSignLog(String memberId){

        return companyMapper.getStaffSignLog(memberId); // null일 경우 기본값 0 반환
    }

    // 직원 신청 insert 작업
    public void insertStaff(ComStaff comStaff) {
        companyMapper.insertStaff(comStaff);
    }

    // 직원 신청 취소 로직
    public String signStaffCancel(String memberId){
        return companyMapper.signStaffCancel(memberId);
    }

    // 특정 문의 조회
    public ComQuestion getCompanyQuestionById(String quesnum) {
        return companyMapper.getCompanyQuestionById(quesnum);
    }

    // 특정 문의 답변 조회
    public ComQuestionAnswer getAnswerByQuesNum(String quesnum) {
        return companyMapper.getAnswerByQuesNum(quesnum);
    }

    // 문의 답변 수정 로직
    public void updateAnswer(ComQuestionAnswer comQuestionAnswer) {
        companyMapper.updateAnswer(comQuestionAnswer);
    }

    // 업체 수정
    public int modifyCompany(Company company) {
        return companyMapper.modifyCompany(company);
    }

    // 업체 이미지 등록
    public void addCompanyImage(CompanyImg companyImg) {
        // 파일 업로드 처리 로직 추가
        FileUtils fileUtils = new FileUtils();
        FileRequest fileRequest = fileUtils.uploadFile(companyImg.getRevImgFile(), "업체대표이미지");
        fileRequest.setFileCate(companyImg.getCCode());
        companyImg.setFileIdx(fileRequest.getFileIdx());
        fileMapper.addFile(fileRequest);
        companyMapper.insertCompanyImg(companyImg);

    }

    // 업체 이미지 삭제
    public void deleteCompanyImage(String imageId) {
        FileRequest fileRequest = fileMapper.getFileByFileIdx(imageId);
        if (fileRequest != null) {
            FileUtils fileUtils = new FileUtils();
            fileUtils.deleteFile(fileRequest);
            companyMapper.deleteCompanyImage(imageId);
            fileMapper.deleteReview(imageId);

        }
    }

    // 문의 답변 등록
    public void addAnswer(ComQuestionAnswer comQuestionAnswer) {
        companyMapper.insertAnswer(comQuestionAnswer);
    }

    // 업체 문의 답변 리스트 반환
    public List<ComQuestionAnswer> getCompanyQuestionAnswer(String cCode){

        return companyMapper.getCompanyQuestionAnswer(cCode);
    }

    // 업체 문의 리스트 반환
    public List<ComQuestion> getCompanyQuestion(String cCode){

        return companyMapper.getCompanyQuestion(cCode);

    }

    // 업체 문의 삭제
    public void deleteQuestion(String comQuestion) {
        companyMapper.deleteQuestion(comQuestion);
    }

    // 문의 답변 먼저 삭제
    public void deleteAnswersByQuesNum(String quesNum) {
        companyMapper.deleteAnswersByQuesNum(quesNum);
    }

    // 전체 업체 리스트 반환
    public List<Company> getCompanyList(){

        return companyMapper.getCompanyList();
    }

    // 업체 코드로 업체 운영시간 리스트 반환
    public List<ComOperTime> getCompanyOperTime(String cCode){

        return companyMapper.getCompanyOperTime(cCode);
    }

    // 세션의 아이디를 통해 업체 리스트 반환
    public List<Company> getCompanyInfoById(String memberId){

        return companyMapper.getCompanyInfoById(memberId);
    }

    // 회원 아이디로 업체 코드 검색(직원용)
    public String getCompanyCodeByMemberId(String memberId) {
        return companyMapper.getCompanyCodeByMemberId(memberId);
    }

    // 업체 코드로 업체 정보 조회
    public List<Company> getCompanyInfoByCcode(String companyCode) {
        return companyMapper.getCompanyInfoByCcode(companyCode);
    }

    // 업체 리스트 키워드로 반환
    public List<Company> getCompanyListByKeyWord(String keyword) {
        return companyMapper.getCompanyListByKeyWord(keyword);

    }

    // 좌표 반환
    public ComMap getComMapByCCode(String companyCode) {

        return companyMapper.getComMapByCCode(companyCode);
    }


    // 별점 평균 반환
    public Double getAvgReviewScore(String companyCode) {
        Double avgScore = companyMapper.avgReviewScore(companyCode);
        return (avgScore != null) ? avgScore : 0.0;
    }


    // 업체 리뷰 반환
    public List<ComReview> getCompanyReview(String companyCode) {

        return companyMapper.getCompanyReview(companyCode);
    }

    // 업체 리뷰수 반환
    public int getCompanyReviewCount(String companyCode) {
        int reviewCount = companyMapper.getCompanyReviewCount(companyCode);

        return reviewCount;
    }

    // 직원 신청 회원 조회
    public List<ComStaff> getStaffSingList(String cCode){

        return companyMapper.getStaffSignList(cCode);
    }

    // 직원 리스트 반환
    public List<ComStaff> getStaffList(String cCode){

        return companyMapper.getStaffList(cCode);
    }

    // 직원 승인 전 회원 레벨 변경
    public int updateLevel(String memberId){

        return companyMapper.updateLevel(memberId);
    }

    // 직원 승인
    public int acceptStaff(String requestId, String memberId){

        return companyMapper.acceptStaff(requestId,memberId);
    }

    // 직원 해고 전 회원 레벨 변경
    public int updateLevelBeforeDelete(String memberId){
        return companyMapper.updateLevelBeforeDelete(memberId);
    }

    // 직원 해고
    public int deleteStaff(String requestId){

        return companyMapper.deleteStaff(requestId);
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
