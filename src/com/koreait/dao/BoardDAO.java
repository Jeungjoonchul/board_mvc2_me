package com.koreait.dao;

import java.util.*;

import org.apache.ibatis.session.*;

import com.koreait.mybatis.*;

public class BoardDAO {

	SqlSession sqlsession;
	
	
	public BoardDAO() {
		sqlsession = SqlMapConfig.getFactory().openSession(true);
	}
	
	public int getBoardCnt() {
		return sqlsession.selectOne("Board.getBoardCnt");
	}

	public List<BoardDTO> getBoardList(int startRow, int pageSize) {
		HashMap<String, Integer> datas = new HashMap<String, Integer>();
		datas.put("startRow", startRow);
		datas.put("pageSize", pageSize);
		
		return sqlsession.selectList("Board.getBoardList",datas);
	}
	
	public BoardDTO getDetail(int boardnum) {
		return sqlsession.selectOne("Board.getDetail",boardnum);	
	}

	public boolean insertBoard(BoardDTO board) {
		return sqlsession.insert("Board.insertBoard",board)==1;
	}

	public int getLastNum(String userid) {
		return sqlsession.selectOne("Board.getLastNum",userid);
	}

	public boolean removeBoard(int boardnum) {
		return sqlsession.delete("Board.removeBoard", boardnum)==1;		
	}

	public void updateReadCount(int boardnum) {
		sqlsession.update("Board.updateReadCount",boardnum);		
	}
}
