package com.koreait.app.board;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;

public class BoardFrontController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI(); // ????/board/boardmain.do
		String contextPath = req.getContextPath(); // ????(module명)
		String command = requestURI.substring(contextPath.length()); // /board/boardmain.do
		System.out.println(command);
		
		ActionTo transfer = null;
		switch(command) {
		case "/board/boardlist.bo":
			try {
				transfer = new BoardListAction().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/board/boardlist :"+e);
			}
			break;
		case "/board/boardwrite.bo":
			transfer = new ActionTo();
			transfer.setPath("/app/board/boardwrite.jsp");
			transfer.setRedirect(false);
			break;
		case "/board/boardview.bo":
			try {
				transfer = new BoardViewAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/board/boardview.bo"+e);
			}
			break;
		case "/board/boardwriteok.bo" :
			try {
				transfer = new BoardWriteOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/board/boardwriteok.bo"+e);
			}
			break;
		case "/board/boardremove.bo":
			try {
				transfer = new BoardRemoveAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/board/boardremove.bo"+e);
			}
			break;
		case "/board/filedownload.bo":
			try {
				new FileDownloadAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/board/filedownload.bo : "+e);
			}
			break;
			
		case "/board/boardmodify.bo":
			try {
				transfer=new BoardModifyAction().execute(req,resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("/board/boardmodify.bo : "+e);
			}
			break;
		case "/board/boardmodifyok.bo":
			try {
				transfer=new BoardModifyOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/board/boardmodifyok"+e);
			} 
			break;
		case "/reply/replywrite.bo":
			try {
				new ReplyWriteAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/reply/replywrite.bo"+e);
			}
			break;
		case "/reply/replymodify.bo":
			try {
				new ReplyModifyAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/reply/replymodify.bo"+e);
			}
			break;
		case "/reply/replyremove.bo":
			try {
				new ReplyRemoveAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/reply/replyremove.bo"+e);
			}
			break;
		}
		
		//전송 일괄처리
		if(transfer!=null) {
			if(transfer.isRedirect()) {
				resp.sendRedirect(transfer.getPath());
			}else {
				req.getRequestDispatcher(transfer.getPath()).forward(req, resp);
			}
		}
	}
}
