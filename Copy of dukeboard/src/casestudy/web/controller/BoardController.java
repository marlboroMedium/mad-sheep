package casestudy.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardService;
import casestudy.business.service.BoardServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.util.PageHandler;

/**
 * Servlet implementation class BoardController
 */
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * HTTP GET과 POST 방식의 요청을 모두 처리한다. 요청파라미터 값을 확인하여 적절한 사용자의 요청을 처리한다.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// action 요청파라미터 값을 확인한다.
			String action = request.getPathInfo();

			// action 값에 따라 적절한 메소드를 선택하여 호출한다.
			if (action.equals("/list")) {
				selectBoardList(request, response);
			} else if (action.equals("/read")) {
				readBoard(request, response);
			} else if (action.equals("/writeForm")) {
				writeBoardForm(request, response);
			} else if (action.equals("/write")) {
				writeBoard(request, response);
			} else if (action.equals("/updateForm")) {
				updateBoardForm(request, response);
			} else if (action.equals("/update")) {
				updateBoard(request, response);
			} else if (action.equals("/remove")) {
				removeBoard(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/*
	 * 조건에 맞는 모든 게시물 목록을 보여주는 요청을 처리한다.
	 */
	private void selectBoardList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// searchType, searchText 요청 파라미터 값을 구한다.
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		// pageNumber 요청 파라미터 값을 구한다.
		String pageNumber = request.getParameter("pageNumber");
		
		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if(pageNumber != null && pageNumber.length()!=0){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		// 검색 옵션을 담고 있는 Map 객체를 생성하여 searchType, searchText값을 저장한다.
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
				
		// BoardService 객체로부터 모든 게시글 리스트를 구해온다.
		BoardService boardService = new BoardServiceImpl();

		// 전체 개시글 개수(boardService로부터 구한다.)
		int totalBoardCount = boardService.getBoardCount(searchInfo);
		
		// 전체 페이지 개수
		int totalPageCount = PageHandler.getTotalPageCount(totalBoardCount);
		//(int)Math.ceil(totalBoardCount / (float)PAGE_LIST_SIZE);
		
		// (6) 페이지 선택 바에 표시될 시작 페이지 번호
		int startPageNumber = PageHandler.getStartPageNumber(currentPageNumber);
		//(currentPageNumber -1) / PAGE_GROUP_SIZE * PAGE_GROUP_SIZE + 1;
		            
		// (7) 페이지 선택 바에 표시될 끝 페이지 번호
		int endPageNumber = PageHandler.getEndPageNumber(currentPageNumber, totalBoardCount);
		/*startPageNumber + PAGE_GROUP_SIZE - 1;
		if(endPageNumber > totalPageCount){
			endPageNumber = totalPageCount;
		}*/
		
		// (8) 현재 페이지의 게시글 목록에서 처음 보여질 게시글의 행 번호
		int startRow = PageHandler.getStartRow(currentPageNumber);
		//(currentPageNumber -1) * PAGE_LIST_SIZE + 1;
		
		// (9) 현재 페이지의 게시글 목록에서 마지막에 보여질 게시글의 행 번호
		int endRow = PageHandler.getEndRow(currentPageNumber);
		//currentPageNumber * PAGE_LIST_SIZE;
		
		// Map 에 startRow, endRow값 저장
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		Board[] boardList = boardService.getBoardList(searchInfo);
		
		// request scope 속성(boardList)에 게시글 리스트를 저장한다.
		request.setAttribute("boardList", boardList);
		// request scope 속성으로 searchType, searchText를 저장한다.
//		request.setAttribute(searchType, searchType);
//		request.setAttribute(searchType, searchType);

		request.setAttribute("currentPageNumber", currentPageNumber);
		request.setAttribute("startPageNumber", startPageNumber);
		request.setAttribute("endPageNumber", endPageNumber);
		request.setAttribute("totalPageCount", totalPageCount);

		// RequestDispatcher 객체를 통해 뷰 페이지(list.jsp)로 요청을 전달한다.
		// RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 선택된 게시글을 읽어와서 보여주는 요청을 처리한다.
	 */
	private void readBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터(num)로 부터 글 번호를 구한다.
		String num = request.getParameter("num");

		String pageNumber = request.getParameter("pageNumber");
		
		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if(pageNumber != null && pageNumber.length()!=0){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		// searchType, searchText 요청 파라미터 값을 구한다.
//		String searchType = request.getParameter("searchType");
//		String searchText = request.getParameter("searchText");

		// 2. BoardService 객체로부터 해당 글 번호의 게시글을 구해온다.
		BoardService boardService = new BoardServiceImpl();
		Board board = boardService.readBoard(Integer.parseInt(num));

		// 3. request scope 속성(board)에 게시글을 저장한다.
		request.setAttribute("board", board);
		
		request.setAttribute("currentPageNumber",currentPageNumber );
//		request.setAttribute("searchType", searchType);
//		request.setAttribute("searchText", searchText);

		// 4. RequestDispatcher 객체를 통해 뷰 페이지(read.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/read.jsp");
		dispatcher.forward(request, response);

	}

	/*
	 * 게시글 등록을 위한 폼을 응답한다.
	 */
	private void writeBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {

		// RequestDispatcher 객체를 통해 뷰 페이지(writeForm.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글을 등록하는 요청을 처리한다.
	 */
	private void writeBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터로 부터 작성자(writer), 제목(title), 내용(contents)를 구한다.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String ip = request.getRemoteAddr();

		// 2. 구해 온 요청 파라미터 값와 ip 값을 지닌 Board 객체를 생성한다.
		Board board = new Board(writer, title, contents, ip);

		// 3. BoardService 객체를 통해 해당 게시글을 등록한다.
		BoardService boardService = new BoardServiceImpl();
		boardService.writeBoard(board);

		// 4. RequestDispatcher 객체를 통해 목록 보기(board?action=list)로 요청을 전달한다.
		RequestDispatcher dispatcher = request.getRequestDispatcher("list");
		dispatcher.forward(request, response);

	}

	/*
	 * 게시글 수정을 위해 적절한 내용이 채워진 폼을 응답한다.
	 */
	private void updateBoardForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 요청 파라미터로 부터 글 번호(num)를 구한다.
		String num = request.getParameter("num");
		// searchType, searchText 요청 파라미터 값을 구한다.
//		String searchType = request.getParameter("searchType");
//		String searchText = request.getParameter("searchText");
		String pageNumber = request.getParameter("pageNumber");
		
		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if(pageNumber != null && pageNumber.length()!=0){
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		// BoardService 객체를 통해 해당 번호의 게시글을 검색한다.
		BoardService boardService = new BoardServiceImpl();
		Board board = boardService.findBoard(Integer.parseInt(num));

		// request scope 속성(board)에 검색한 게시글을 저장한다.
		request.setAttribute("board", board);

		request.setAttribute("currentPageNumber",currentPageNumber );
//		request.setAttribute("searchType", searchType);
//		request.setAttribute("searchText", searchText);

		// RequestDispatcher 객체를 통해 뷰 페이지(updateForm.jsp)로 요청을 전달한다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/views/board/updateForm.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * 게시글을 수정하는 요청을 처리한다.
	 */
	private void updateBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터로 부터 글 번호(num), 작성자(writer), 제목(title), 내용(contents)을 구한다.
		String num = request.getParameter("num");
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		String ip = request.getRemoteAddr();

		// 2. 구해 온 요청 파라미터 값와 ip 값을 지닌 Board 객체를 생성한다.
		Board board = new Board(Integer.parseInt(num), writer, title, contents,
				ip);

		// 3. BoardService 객체를 통해 해당 게시글을 갱신한다.
		BoardService boardService = new BoardServiceImpl();
		boardService.updateBoard(board);

		// 4. request scope 속성(board)에 게시글을 저장한다.
		request.setAttribute("board", board);

		// 5. RequestDispatcher 객체를 통해 게시물 보기(board?action=read)로 요청을 전달한다.
		RequestDispatcher dispatcher = request.getRequestDispatcher("read");
		dispatcher.forward(request, response);

	}

	/*
	 * 게시글을 삭제하는 요청을 처리한다.
	 */
	private void removeBoard(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DataNotFoundException {
		// 1. 요청 파라미터로 부터 글 번호(num)를 구한다.
		String num = request.getParameter("num");
		// 2. BoardService 객체를 통해 해당 번호의 게시글을 삭제한다.
		String pageNumber = request.getParameter("pageNumber");
		
		// (1) 현재 페이지 번호
		int currentPageNumber = 1;
		if(pageNumber != null && pageNumber.length()!=0){
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		BoardService boardService = new BoardServiceImpl();
		boardService.removeBoard(Integer.parseInt(num));
		
		request.setAttribute("currentPageNumber",currentPageNumber);
		// 3. RequestDispatcher 객체를 통해 목록 보기(board?action=list)로 요청을 전달한다.
		RequestDispatcher dispatcher = request.getRequestDispatcher("list");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
