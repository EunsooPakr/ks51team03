package ks51team03.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks51team03.board.dto.NBoardImg;
import ks51team03.board.dto.NBoardSearch;
import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.mapper.BoardMapper;
import ks51team03.company.dto.CompanyImg;
import ks51team03.files.dto.FileRequest;
import ks51team03.files.mapper.FileMapper;
import ks51team03.files.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("BoardService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {
	private final BoardMapper boardMapper;
	private final FileUtils fileUtils;
    private final FileMapper fileMapper;

	public Map<String, Object> getNoticeBoardList(String boardCateValue, int currentPage, String searchKey,
			String searchValue) {
		// 보여줄 행의 갯수
		int rowPerPage = 8;
		// 첫번째 인수값
		int startRow = (currentPage - 1) * rowPerPage;
		// 시작페이지 설정 초기화
		int startPageNum = 1;
		// 마지막페이지 설정 초기화
		int endPageNum = 10;

		log.info("getNoticeBoardList: {}",
				boardMapper.getNoticeBoardList(boardCateValue, startRow, rowPerPage, searchKey, searchValue));

		List<Map<String, Object>> NoticeBoardList = boardMapper.getNoticeBoardList(boardCateValue, startRow, rowPerPage,
				searchKey, searchValue);

		// 전체 행의 갯수 조회
		double cnt = boardMapper.getNoticeBoardListCnt(boardCateValue, searchKey, searchValue);

		// 마지막 페이지
		int lastPage = (int) Math.ceil(cnt / rowPerPage);

		// 마지막페이지 보다 작을 경우 마지막페이지로 설정
		endPageNum = lastPage < 10 ? lastPage : endPageNum;

		// 동적 페이지설정
		if (currentPage > 6 && endPageNum > 9) {
			startPageNum = currentPage - 5;
			endPageNum = currentPage + 4;
			// 마지막페이지번호가 마지막페이지수보다 클 경우에 페이지번호를 고정
			if (endPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("NoticeBoardList", NoticeBoardList);
		resultMap.put("endPageNum", endPageNum);
		resultMap.put("lastPage", lastPage);
		resultMap.put("startPageNum", startPageNum);

		return resultMap;
	}

	public void insertNBoard(NoticeBoard nboard) {
		String nbcode = "nb" + String.valueOf(boardMapper.getNBoardCode() + 1);

		nboard.setNboardCode(nbcode);

		nboard.setNboardView(0);
		nboard.setNboardRec(0);

		int result = boardMapper.insertNBoard(nboard);
	}

	public void updateNBoard(NoticeBoard nboard) {
		int result = boardMapper.updateNBoard(nboard);
	}

	public NoticeBoard getNBoardByNBCode(String nboardCode) {
		return boardMapper.getNBoardByNBCode(nboardCode);
	}

	public void increaseViewByNBCode(String nboardCode) {
		boardMapper.increaseViewByNBCode(nboardCode);
	}

	public void increaseRecByNBCode(String nboardCode) {
		boardMapper.increaseRecByNBCode(nboardCode);
	}

	public List<NoticeBoard> getBoardSearchList(NBoardSearch nbsearch) {
		String searchKey = nbsearch.getSearchKey();
		String columnName = "";
		switch (searchKey) {
		case "memberId" -> {
			columnName = "id";
		}
		case "nboardTitle" -> {
			columnName = "nb_title";
		}
		case "nboardContent" -> {
			columnName = "nb_content";
		}
		}
		nbsearch.setSearchKey(columnName);
		log.info("nbsearch: {}", nbsearch);

		return boardMapper.getBoardSearchList(nbsearch);
	}
	
	public String getBCTValueNameByBCTCode(NoticeBoard nboard)
	{
		return boardMapper.getBCTValueNameByBCTCode(nboard);
	}
	
	public String getBoardCateCodeByBCTValue(NoticeBoard nboard)
	{
		return boardMapper.getBoardCateCodeByBCTValue(nboard);
	}
	
	public String getBoardCodeByNBoard(NoticeBoard nboard)
	{
		return boardMapper.getBoardCodeByNBoard(nboard);
	}

	public String getBoardCodeByBCTValue(String bctCodeValue)
	{
		return boardMapper.getBoardCodeByBCTValue(bctCodeValue);
	}
	
	public String getBoardInfoByBCTValue(String bctCodeValue)
	{
		return boardMapper.getBoardInfoByBCTValue(bctCodeValue);
	}
	
	public String getBCTValueByNBCode(String nbCode)
	{
		return boardMapper.getBCTValueByNBCode(nbCode);
	}
	
	public List<NoticeBoard> getMainNoticeBoard(String bCode)
	{
		return boardMapper.getMainNoticeBoard(bCode);
	}
	
	public List<NoticeBoard> getMainViewBoard(String bCode)
	{
		return boardMapper.getMainViewBoard(bCode);
	}
	
	public List<NoticeBoard> getMainRecBoard(String bCode)
	{
		return boardMapper.getMainRecBoard(bCode);
	}
	
	public List<NoticeBoard> getMainLatestBoard(String bCode)
	{
		return boardMapper.getMainLatestBoard(bCode);
	}
	
	// 게시판 파일 업로드
    public void upLoadImgByNBCode(NBoardImg nboardimg, String nBoardCode) {

        FileRequest fileRequest =  fileUtils.uploadFile(nboardimg.getNBoardImgFile(), nBoardCode);
        if(fileRequest != null){
            fileRequest.setFileCate(nBoardCode);
            log.info("fileRequest: {}", fileRequest);
            fileMapper.addFile(fileRequest);
            nboardimg.setFileIdx(fileRequest.getFileIdx());
        }
        
        //이미지 등록
        boardMapper.insertnBoardImg(nboardimg);
    }
	
}
