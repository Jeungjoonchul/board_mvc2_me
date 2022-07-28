package com.koreait.app.user;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.ActionTo;

public class UserFrontController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doProcess(req,resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		
		//길나누는 코드
		String requestURI = req.getRequestURI(); // ????/user/userjoin.us
		String contextPath = req.getContextPath(); // ????(module명)
		String command = requestURI.substring(contextPath.length()); // /user/userjoin.us
		System.out.println(command);
		
		ActionTo transfer = null;
		
		switch(command) {
		case "/user/userjoin.us":
			transfer = new ActionTo();
			transfer.setPath("/app/user/joinview.jsp");
			transfer.setRedirect(false);
			break;
		case "/user/userlogin.us":
			transfer = new ActionTo();
			transfer.setPath("/app/user/loginview.jsp");
			transfer.setRedirect(false);
			break;
		case "/user/userjoinok.us":
			try {
				transfer = new UserJoinOkAction().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/user/userjoinok : "+e);
			}
			break;
		case "/user/checkidok.us":
			try {
				transfer = new CheckIdOkAction().execute(req,resp);
			} catch (Exception e) {
				System.out.println("/user/checkidok.us : "+e);
			}
			break;
		case "/user/userloginok.us":
			try {
				transfer = new UserLoginOkAction().execute(req, resp);
			} catch (Exception e) {
				System.out.println("/user/userloginok.us : "+e);
			}
			break;
		case "/user/userlogout.us":
			req.getSession().invalidate();
			PrintWriter out = resp.getWriter();
			out.print("<script>");
			out.print("alert('로그아웃 되었습니다!');");
			out.print("location.href = '" + req.getContextPath() + "/';");
			out.print("</script>");
			System.out.println("CP : "+req.getContextPath());
			break;
		}
		
		//전송 일괄처리(어디인지, 어떤 방식인지는 몰라도 transfer라는 객체에 담겨있는 정보를 해석해서 그대로 페이지를 이동시키는 코드)
		if(transfer !=null) {
			if(transfer.isRedirect()) {
				resp.sendRedirect(transfer.getPath());
			}else {
				req.getRequestDispatcher(transfer.getPath()).forward(req, resp);
			}	
		}
	}
}

