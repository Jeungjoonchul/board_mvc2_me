package com.koreait.app.board;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class BoardModifyAction implements Action{

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO bdao = new BoardDAO();
		FileDAO fdao = new FileDAO();
		
		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		
		req.setAttribute("board", bdao.getDetail(boardnum));
		req.setAttribute("files", fdao.getFiles(boardnum));
		
		ActionTo transfer = new ActionTo();
		transfer.setRedirect(false);
		transfer.setPath("/app/board/boardmodify.jsp");
		return transfer;
	}

}
