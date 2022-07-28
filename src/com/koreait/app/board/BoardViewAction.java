package com.koreait.app.board;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class BoardViewAction implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// select, forward, boardview.jsp, BoardDTO
		// 들어와서 제일 먼저 해야할 것? 데이터 수집

		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		
		BoardDAO bdao = new BoardDAO();
		String loginUser=(String)req.getSession().getAttribute("loginUser");
		
		BoardDTO board = bdao.getDetail(boardnum);
		
		if(!board.getUserid().equals(loginUser)) {
			board.setReadcount(board.getReadcount()+1);
			bdao.updateReadCount(boardnum);
		}
		
		FileDAO fdao = new FileDAO();
		
		req.setAttribute("board", board);
		req.setAttribute("files", fdao.getFiles(boardnum));

		ActionTo transfer = new ActionTo();
		transfer.setRedirect(false);
		transfer.setPath("/app/board/boardview.jsp");
		return transfer;
	}
}
