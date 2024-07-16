package ks51team03.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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
}
