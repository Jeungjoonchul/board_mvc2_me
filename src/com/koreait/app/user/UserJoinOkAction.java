package com.koreait.app.user;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;
import com.koreait.dao.*;

public class UserJoinOkAction implements Action{

	// 메소드
	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//입력 -> 데이터 전송 -> [데이터 수집 -> 처리 -> 결과 전송] -> 출력
		UserDTO user = new UserDTO();
		UserDAO udao = new UserDAO();
		String userid = req.getParameter("userid");
		user.setUserid(userid);
		user.setUserpw(req.getParameter("userpw"));
		user.setUsername(req.getParameter("username"));
		user.setUsergender(req.getParameter("usergender"));
		user.setZipcode(req.getParameter("zipcode"));
		user.setAddr(req.getParameter("addr"));
		user.setAddrdetail(req.getParameter("addrdetail"));
		user.setAddretc(req.getParameter("addretc"));
		user.setUserhobby(req.getParameterValues("userhobby"));
		
		ActionTo transfer = new ActionTo();
		transfer.setRedirect(true);
		if(udao.join(user)) {
			transfer.setPath(req.getContextPath()+"/user/userlogin.us?user="+userid);
		}else {
			transfer.setPath(req.getContextPath());
		}
		return transfer;
	}
	
}
