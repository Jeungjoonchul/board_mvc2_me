package com.koreait.app.board;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class ReplyModifyAction implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardnum = req.getParameter("boardnum");
		String page=req.getParameter("page");
		String keyword=req.getParameter("keyword");
		keyword = URLEncoder.encode(keyword,"utf-8");
		
		String i = req.getParameter("i");

		int replynum = Integer.parseInt(req.getParameter("replynum"));
		String replycontents=req.getParameter("reply"+i);

		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		String path = String.format("'%s/board/boardview.bo?boardnum=%s&page=%s&keyword=%s'", req.getContextPath(),boardnum,page,keyword);
		System.out.println(path);
		BoardDAO bdao = new BoardDAO();
		if(bdao.updateReply(replynum,replycontents)) {
			out.print("<script>");
			out.print("alert('댓글 수정 성공!');");
			out.print("location.href="+path);
			out.print("</script>");
		}else {
			out.print("<script>");
			out.print("alert('댓글 수정 실패!');");
			out.print("location.href="+path);
			out.print("</script>");
		}		
		out.close();
		return null;
	}

}
