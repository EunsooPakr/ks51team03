package ks51team03.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("BoardService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {
	private final BoardMapper boardMapper;
	
	public List<NoticeBoard> getNoticeBoardList(String boardCateValue)
	{
		log.info("getNoticeBoardList: {}", boardMapper.getNoticeBoardList(boardCateValue));
		return boardMapper.getNoticeBoardList(boardCateValue);
	}
	
	public void insertNBoard(NoticeBoard nboard)
	{
		String nbcode="nb"+String.valueOf(boardMapper.getNBoardCode()+1);
    	
		nboard.setNboardCode(nbcode);
		
		/*여기 알아서 읽어넣을 수 있도록 수정*/
		nboard.setBoardCode("b3");
		nboard.setBoardCateCode("bct3");
		
		nboard.setNboardView(0);
		nboard.setNboardRec(0);
    	
		int result = boardMapper.insertNBoard(nboard);
	}
}
