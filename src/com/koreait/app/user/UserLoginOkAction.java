package com.koreait.app.user;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class UserLoginOkAction extends ActionTo implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		// MIME 타입
		resp.setContentType("text/html; charset=utf-8");

		UserDAO udao = new UserDAO();
		String userid = req.getParameter("userid");
		String userpw = req.getParameter("userpw");

		// UserDTO loginUser = udao.login(userid, userpw);

		PrintWriter out = resp.getWriter();
//		ActionTo transfer = new ActionTo();
//		transfer.setRedirect(false);
		if (udao.login(userid, userpw)) {
			req.getSession().setAttribute("loginUser", userid);
			req.getSession().setAttribute("loginUserWelcome", false);
//			transfer.setPath(req.getContextPath()+"/board/boardlist.bo");
			out.print("<script>");
			out.print("alert('" + userid + "님 어서오세요~!');");
			out.print("location.href = '" + req.getContextPath() + "/board/boardlist.bo';");
			out.print("</script>");
		} else {
//			transfer.setPath("/");
			out.print("<script>");
			out.print("location.href = '" + req.getContextPath() + "/';");
			out.print("</script>");
		}
//		return transfer;
		 out.close();
		 return null;
	}
}
