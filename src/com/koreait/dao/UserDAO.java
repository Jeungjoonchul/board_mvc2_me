package com.koreait.dao;

import java.util.*;

import org.apache.ibatis.session.*;

import com.koreait.mybatis.*;

public class UserDAO {
	SqlSession sqlsession;
	
	
	public UserDAO() {
		sqlsession = SqlMapConfig.getFactory().openSession(true);
	}
	
	public boolean join(UserDTO user) {
		return sqlsession.insert("User.join", user)==1;
	}
	
	public boolean checkId(String userid) {
		return (Integer)sqlsession.selectOne("User.checkId", userid)==0;
	}

	public boolean login(String userid, String userpw) {
		HashMap<String, String> datas = new HashMap<String, String>();
		datas.put("userid", userid);
		datas.put("userpw", userpw);
		return (Integer)sqlsession.selectOne("User.login", datas) == 1;
//		return (UserDTO)sqlsession.selectOne("User.login", datas);
	}
}
