package com.koreait.dao;

import java.util.*;

import org.apache.ibatis.session.*;

import com.koreait.mybatis.*;

public class FileDAO {

	SqlSession sqlsession;
	public FileDAO() {
		sqlsession = SqlMapConfig.getFactory().openSession(true);
	}
	
	public boolean insertFile(FileDTO file) {
		return sqlsession.insert("File.insertFile",file)==1;
	}

	public List<FileDTO> getFiles(int boardnum) {
		return sqlsession.selectList("File.getFiles",boardnum);
	}

	public void deleteByName(String systemname) {
		sqlsession.delete("File.deleteByName",systemname);		
	}

}
