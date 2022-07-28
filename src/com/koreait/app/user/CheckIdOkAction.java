package com.koreait.app.user;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class CheckIdOkAction implements Action {

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userid = req.getParameter("userid");
		UserDAO udao = new UserDAO();
		PrintWriter out= resp.getWriter(); //응답을 작성하기 위한 객체
		if(udao.checkId(userid)) {
			out.write("O");
		}else {			
			out.write("X");
		}
		out.close();
		return null;
	}

}
