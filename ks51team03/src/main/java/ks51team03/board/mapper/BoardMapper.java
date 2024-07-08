package ks51team03.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ks51team03.board.dto.NoticeBoard;

@Mapper
public interface BoardMapper {
	public List<NoticeBoard> getNoticeBoardList(String boardCateValue);
	
	//게시글 등록
	public int insertNBoard(NoticeBoard nboard);
	
	// 게시글 코드 도출
	int getNBoardCode();
}
