package com.koreait.app.board;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.koreait.action.*;

public class FileDownloadAction implements Action{

	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String systemname=req.getParameter("systemname");
		String orgname=req.getParameter("orgname");
		String saveFolder = req.getServletContext().getRealPath("file");
		//C:\0900_GB_JJC\file\instagram.png
		
		String filename=saveFolder+"\\"+systemname;
		
		InputStream is = null;
		OutputStream os = null;
		
		
		//다운로드 받으려는 파일을 자바의 객체로 가져옴(실제 파일X / 파일에 대한 정보들을 가지고 있고, 그 정보를 기반으로 파일을 쉽게 관리할 수 있게 도와주는 객체)
		File file = new File(filename);
		
		//그 파일을 향한 통로 개설(파일을 자바쪽으로 읽어오기 위한 스트림)
		is = new FileInputStream(file);
		
		//다운로드를 요청한 클라이언트의 로컬 정보가 담겨있는 헤더
		String client = req.getHeader("User-Agent");
		resp.reset();
		
		//파일을 응답해줄 준비, 세팅(응답 정보를 작성하는 과정, 파일 데이터를 응답할 것이라는 사전정보 작성)
		resp.setContentType("application/octet-stream"); // 이진 데이터
		resp.setHeader("Content-Description", "JSP Generated Data");
		
		String dwName = "";
		
		try {
			
			//파일 이름 인코딩
			try {
				//귀여운 고양이.png -> %1F%21%30+%2D%31%33 -> %1F%21%30%20%2D%31%33 -> 귀여운 고양이.png
				dwName = URLEncoder.encode(orgname,"UTF-8").replaceAll("\\+", "%20");
			} catch (UnsupportedEncodingException e) {
				dwName = URLEncoder.encode(file.getName(),"UTF-8").replaceAll("\\+", "%20");
			}
			
			//클라이언트의 로컬 정보를 담은 client 변수 안에 MSIE 문자열이 포함되어 있는지를 비교(있으면 인덱스 반환, 없으면 -1)
			//클라이언트가 이용하는 브라우저가 MSIE인지 아닌지를 구별해서 헤더를 작성
			if(client.indexOf("MSIE")!= -1) {
				resp.setHeader("Content-Disposition", "attachment; fileName="+dwName);
			}else {
				resp.setHeader("Content-Disposition", "attachment; fileName=\""+dwName+"\"");
			}
			//응답할 컨텐츠의 길이 헤더 작성
			resp.setHeader("Content-Length", file.length()+"");
			//응답 준비 완료
			
			//파일을 클라이언트에게 응답하기 위한 통로 개설 또는 설정(자바쪽으로 읽어온 파일 데이터를 사용자의 컴퓨터에 써주기 위한 통로)
			os=resp.getOutputStream();
			
			
			//파일을 바이트 단위로 쪼개서 담아줄 배열
			byte[] b = new byte[(int)file.length()];
			int len = 0;
			//while문 반복때마다 is.read() 수행해서 len 대입
			//len은 파일의 끝이라면(읽은 데이터가 없다면) -1이 담긴다.
			while((len=is.read(b,0,b.length))!=-1) {
				//무언가 파일 데이터를 읽어왔다면 os를 이용해서 사용자 컴퓨터에 write()로 써준다
				os.write(b,0,len);
			}
		} catch (Exception e) {
			System.out.println("FileDownloadAction : "+e);
		}
		finally {
			//is, os 반납
			if(is !=null) {
				is.close();
			}
			if(os !=null) {
				os.close();
			}
		}
		
		
		return null;
	}

}
