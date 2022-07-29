package com.koreait.app.board;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionTo;
import com.koreait.dao.BoardDAO;
import com.koreait.dao.BoardDTO;
import com.koreait.dao.FileDAO;
import com.koreait.dao.FileDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardModifyOkAction implements Action{
	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveFolder = req.getServletContext().getRealPath("file");
		int size = 1024*1024*5;
		
		MultipartRequest multi = new MultipartRequest(req, saveFolder, size, "UTF-8",
				new DefaultFileRenamePolicy());
		
		int boardnum = Integer.parseInt(multi.getParameter("boardnum"));
		String boardtitle = multi.getParameter("boardtitle");
		String boardcontents = multi.getParameter("boardcontents");
		
		BoardDTO board = new BoardDTO();
		board.setBoardtitle(boardtitle);
		board.setBoardcontents(boardcontents);
		board.setBoardnum(boardnum);
		
		ActionTo transfer = new ActionTo();
		transfer.setRedirect(true);
		
		BoardDAO bdao = new BoardDAO();
		FileDAO fdao = new FileDAO();
		if(bdao.updateBoard(board)) {
			//기존의 파일이 수정되었다면						기존의 파일을 수정X, 새로운 파일 추가			파일에 관련된 수정 X
			//1. 기존 파일 삭제								1. 새로운 파일에 대한 데이터를 db에 추가		...
			//2. 기존 파일에 대한 데이터를 db에서 삭제				
			//3. 새로운 파일에 대한 데이터를 db에 추가
			//filename : 이 게시글에 올라와있는 파일들
			String[] newFilenames = multi.getParameterValues("filename");//{"bare.png","wifi.png"}
			String[] systemname = {multi.getFilesystemName("file1"),multi.getFilesystemName("file2")};//{null,null}
			String[] orgname = {multi.getOriginalFileName("file1"),multi.getOriginalFileName("file2")};//{null,null}
			List<FileDTO> files = fdao.getFiles(boardnum);
			
			int cnt = 0;
			//수정되면 안될 파일의 번호를 담아줄 리스트
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int i = 0; i < newFilenames.length; i++) {
				if(newFilenames[i] != null && !newFilenames[i].equals("")) {
					cnt++;
					if(files.size()>i) {
						//기존에 존재하던 파일들을 검사
						if(files.get(i).getOrgname().equals(newFilenames[i])) {
							//기존에 있던 파일의 이름과 수정 이후의 파일 이름이 같을 때
							//파일이 수정되지 않았다는 뜻이므로 그때의 i번을 temp 리스트에 담아준다.
							temp.add(i);
						}
					}
					
				}
			}
			//temp 리스트는 수정되면 안될 파일의 번호들을 담고 있을것이다.
			//cnt : 2
			if(files.size() > cnt) {
				//기존보다 개수가 줄어들음
				for(int i=0;i<files.size();i++) {
					//기존의 파일들을 하나씩 꺼내오면서 반복
					if(temp.contains(i)) {
						//그때의 i가 temp에 속해있다는 뜻은 files.get(i)는 변화되지 않았다는 뜻
					}
					else {
						//이때의 i는 변화되었다는 뜻(삭제되었거나 수정)
						//기존의 파일을 삭제하는 과정
						File file = new File(saveFolder,files.get(i).getSystemname());
						if(file.exists()) {
							file.delete();
							fdao.deleteByName(files.get(i).getSystemname());
						}
						//수정되었을 수도 있으므로 새롭게 올라온 파일이 있다면 추가
						if(systemname[i] != null) {
							FileDTO fdto = new FileDTO();
							fdto.setBoardnum(boardnum);
							fdto.setSystemname(systemname[i]);
							fdto.setOrgname(orgname[i]);
							fdao.insertFile(fdto);
						}
					}
				}
				
			}
			else {
				//기존보다 개수가 늘어나거나 변화없음
				for(int i=0;i<cnt;i++) {
					if(systemname[i] == null) {
					}
					else {
						if(files.size() > i) {
							File file = new File(saveFolder,files.get(i).getSystemname());
							if(file.exists()) {
								file.delete();
								fdao.deleteByName(files.get(i).getSystemname());
							}
						}
						FileDTO fdto = new FileDTO();
						fdto.setBoardnum(boardnum);
						fdto.setSystemname(systemname[i]);
						fdto.setOrgname(orgname[i]);
						fdao.insertFile(fdto);
					}
				}
			}
			transfer.setPath(req.getContextPath()+"/board/boardview.bo?boardnum="+boardnum);
		}
		else {
			transfer.setPath(req.getContextPath()+"/board/boardview.bo?boardnum="+boardnum);
		}
		return transfer;
	}
}













