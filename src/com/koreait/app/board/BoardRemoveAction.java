package com.koreait.app.board;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class BoardRemoveAction implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		
		ActionTo transfer = new ActionTo();
		transfer.setRedirect(true);
		
		BoardDAO bdao=new BoardDAO();
		FileDAO fdao = new FileDAO();
		
		String saveFolder = req.getServletContext().getRealPath("file");
		List<FileDTO> files = fdao.getFiles(boardnum);
		
		if(bdao.removeBoard(boardnum)) {
			for(int i=0;i<files.size();i++) {
				File file = new File(saveFolder,files.get(i).getSystemname());
				if(file.exists()) {
					file.delete();
					fdao.deleteByName(files.get(i).getSystemname());
				}
			}
			
			transfer.setPath(req.getContextPath()+"/board/boardlist.bo");
		}else {
			transfer.setPath(req.getContextPath()+"/board/boardview.bo?boardnum="+boardnum);
		}
		return transfer;
	}
}
