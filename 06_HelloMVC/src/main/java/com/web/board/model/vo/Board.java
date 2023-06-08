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
	private String boardContent;
	private String boardOriFilename;
	private String boardRnFilename;
	private Date boardDate;
	private int boardReadcount;
	
}
