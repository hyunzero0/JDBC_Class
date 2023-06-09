package com.web.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardWriter;
//	private Member boardWriter; 이 참조관계라서 원래 자바 설계상에서는 맞지만 DB 따라서 String 씀
	private String boardContent;
	private String boardOriFilename;
	private String boardRnFilename;
	private Date boardDate;
	private int boardReadcount;
	
}
