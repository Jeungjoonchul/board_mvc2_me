package com.koreait.app.board;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class ReplyWriteAction implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int boardnum = Integer.parseInt(req.getParameter("boardnum"));
		String userid = (String)req.getSession().getAttribute("loginUser");
		String replycontents = req.getParameter("replycontents");
		String page=req.getParameter("page");
		String keyword=req.getParameter("keyword");
		keyword=URLEncoder.encode(keyword,"utf-8");
		
		BoardDAO bdao = new BoardDAO();
		ReplyDTO reply = new ReplyDTO();
		
		reply.setUserid(userid);
		reply.setBoardnum(boardnum);
		reply.setReplycontents(replycontents);
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		String path = String.format("'%s/board/boardview.bo?boardnum=%d&page=%s&keyword=%s'", req.getContextPath(),boardnum,page,keyword);
		
		if(bdao.insertReply(reply)) {
			out.print("<script>");
			out.print("alert('댓글 등록 성공!');");
			out.print("location.href="+path);
			out.print("</script>");
		}else {
			out.print("<script>");
			out.print("alert('댓글 등록 실패!');");
			out.print("location.href="+path);
			out.print("</script>");
		}		
		out.close();
		return null;
	}

}
