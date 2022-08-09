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
		//title,contents,userid,readcount = 47
		
		
		boolean flag=true;
		if(!board.getUserid().equals(loginUser)) {
			Cookie[] cookies = req.getCookies();
			for(Cookie c :cookies) {
				if(c.getName().equalsIgnoreCase("hit-"+boardnum+"-"+loginUser)) {
					flag=false;
				}
			}
			if(flag) {
				board.setReadcount(board.getReadcount()+1);
				bdao.updateReadCount(boardnum);
				Cookie hits = new Cookie("hit-"+boardnum+"-"+loginUser,"hit");
				hits.setMaxAge(60*60*24);
				resp.addCookie(hits);
			}
		}
		
		FileDAO fdao = new FileDAO();
		
		req.setAttribute("board", board);
		req.setAttribute("files", fdao.getFiles(boardnum));
		req.setAttribute("replies", bdao.getReplies(boardnum));
		
		ActionTo transfer = new ActionTo();
		transfer.setRedirect(false);
		transfer.setPath("/app/board/boardview.jsp");
		return transfer;
	}
}
