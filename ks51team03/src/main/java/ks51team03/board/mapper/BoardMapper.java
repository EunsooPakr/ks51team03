package ks51team03.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks51team03.board.dto.NBoardImg;
import ks51team03.board.dto.NBoardSearch;
import ks51team03.board.dto.NoticeBoard;

@Mapper
public interface BoardMapper {
	//게시글 목록
	//public List<NoticeBoard> getNoticeBoardList(String boardCateValue);
	int getNoticeBoardListCnt(String boardCateValue,String searchKey,String searchValue);
	List<Map<String, Object>> getNoticeBoardList(String boardCateValue,int startRow, int rowPerPage,String searchKey,String searchValue);
	
	
	//게시글 등록
	public int insertNBoard(NoticeBoard nboard);
	
	// 게시글 코드 도출
	int getNBoardCode();
	
	//게시글 검색
	List<NoticeBoard> getBoardSearchList(NBoardSearch nbsearch);
	
	// 게시글코드로 열람
	NoticeBoard getNBoardByNBCode(String nboardCode);
	
	// 게시글코드로 조회수 증가
	void increaseViewByNBCode(String nboardCode);
	
	// 게시글코드로 추천수 증가
	void increaseRecByNBCode(String nboardCode);
	
	// 게시글 수정
	public int updateNBoard(NoticeBoard nboard);
	
	// 게시글로 게시글 카테고리 이름 도출
	public String getBCTValueNameByBCTCode(NoticeBoard nboard);
	
	// 게시글로 게시글 카테고리 코드 도출
	public String getBoardCateCodeByBCTValue(NoticeBoard nboard);
		
	// 게시글로 게시글 보드 코드 도출
	public String getBoardCodeByNBoard(NoticeBoard nboard);
	
	//게시글코드로 게시판 이름 도출
	public String getBCTValueByNBCode(String nbCode);
	
	// 게시글 게시글 카테고리 이름으로 게시글 보드 코드 도출
	public String getBoardCodeByBCTValue(String bctCodeValue);
	
	// 게시글 게시글 카테고리 이름으로 게시글 설명 도출
	public String getBoardInfoByBCTValue(String bctCodeValue);

	//보드코드로 최신순으로 가져오기
	public List<NoticeBoard> getMainNoticeBoard(String bCode);
	
	//조회순으로 가져오기
	public List<NoticeBoard> getMainViewBoard(String bCode);
	
	//추천순으로 가져오기
	public List<NoticeBoard> getMainRecBoard(String bCode);
	
	//추천순으로 가져오기
	public List<NoticeBoard> getMainLatestBoard(String bCode);
	
	// 게시글 이미지 등록
    void insertnBoardImg(NBoardImg nboardimg);
    
    // 게시글 코드로 게시글 이미지 경로 가져오기
    public String getNBoardImgByNBCode(String nbcode);
}
