package ks51team03.board.dto;

import lombok.Data;

@Data
public class Criteria {
	
	private int pageNum;	//현재 페이지
	private int amount;		//한 페이지 당 보여질 게시물 횟수
	private String keyword;	//검색 키워드
	
	//기본 생성자
	public Criteria()
	{
		this(1,10);
	}
	
	//커스텀 생성자
	public Criteria(int pageNum,int amount)
	{
		this.pageNum=pageNum;
		this.amount=amount;
	}
}
