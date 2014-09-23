package casestudy.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import casestudy.business.domain.Member;
import casestudy.business.service.DataDuplicatedException;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;

/**
 * Servlet implementation class MemberServlet
 */
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /* 
     * HTTP GET과 POST 방식의 요청을 모두 처리한다.
     * 요청파라미터 값을 확인하여 적절한 사용자의 요청을 처리한다.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
        
        try {
	        // action 요청파라미터 값을 확인한다.
	        String action = request.getParameter("action");
	
	        // action 값(select/register/update/login/logout)에 따라 적절한 메소드를 선택하여 호출한다.
	        if (action.equals("select")) {
	        	selectMember(request, response);
	        } else if (action.equals("register")) {
	        	registerMember(request, response);
	        } else if (action.equals("update")) {
	        	updateMember(request, response);           
	        } else if (action.equals("login")) {
	            login(request, response);
	        } else if (action.equals("logout")) {
	            logout(request, response);
	        }
        } catch (Exception ex) {
        	throw new ServletException(ex);
        }
    }
    
    /* 
     * 한 명의 회원 상세정보를 보여주는 요청을 처리한다.
     */
    private void selectMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataNotFoundException {
    	
        // 3. session scope 속성에서 회원 정보를 찾아 memberID를 확인한다.
        HttpSession session = request.getSession(false);        
        if (session == null) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN, "로그인이 필요합니다.");
        	return;
        }
        Member member = (Member) session.getAttribute("loginMember");
        if (member == null) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
        	return;
        }
        String memberID = member.getMemberID();
        
        // 비즈니스 로직을 수행할 MemberService 객체를 생성하여
        MemberService memberService = new MemberServiceImpl();
        
        // memberID로 회원를 검색한다.(MemberService의 findMember() 사용)
        Member selectedMember = memberService.findMember(memberID);

        // request scope 속성에 조회된 member를 저장하고
        request.setAttribute("selectedMember", selectedMember);
        
        // RequestDispatcher 객체를 통해 뷰 페이지(updateMember.jsp)로 요청을 전달한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher("updateMember.jsp");
        dispatcher.forward(request, response);
    }
    
    /* 
     * 새로운 회원을 등록하는 요청을 처리한다.
     */
    private void registerMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataDuplicatedException {
    	
        // 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
        String memberID = request.getParameter("memberID");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String zipcode = request.getParameter("zipcode");
        String address = request.getParameter("address");
        int point = 0;
        
        // 폼 데이터의 유효성을 검증하는 처리를 한다.
        // 에러 메시지들을 저장할 리스트
        List<String> errorMsgs = new ArrayList<String>();

        // 폼 데이터가 유효한 지 검증
        if ((memberID == null) || (memberID.length() == 0)) {
            errorMsgs.add("회원아이디를 입력해주세요.");
        }
        if ((password == null) || (password.length() == 0)) {
            errorMsgs.add("패스워드를 입력해주세요.");
        }        
        if ((name == null) || (name.length() == 0)) {
            errorMsgs.add("이름을 입력해주세요.");
        }        
        if ((email == null) || (email.length() == 0)) {
            errorMsgs.add("이메일주소를 입력해주세요.");
        }
                
        // 유효하지 않은 데이터가 있으면 
        if (!errorMsgs.isEmpty()) {
        	// 에러 내용을 request scope 속성에 저장하고
            request.setAttribute("errorMsgs", errorMsgs);
            // 에러 페이지 뷰(userError.jsp)로 요청을 전달한다.
            RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
            dispatcher.forward(request,response);
            return;
        }
        
        // 새로운 회원을 등록하는 처리를 한다.
        // 적절한 데이터를 가진 Member 객체를 생성하여
        Member member = new Member(memberID, password, name, email, tel, zipcode, address, point);
        
        // MemberService 객체에 위임하여 회원을 등록한다.
        MemberService memberService = new MemberServiceImpl();
        memberService.registerMember(member);
        
        // request scope 속성에 등록된 member를 저장하고 
        request.setAttribute("member", member);
        // RequestDispatcher 객체를 통해 뷰 페이지(thankYou.jsp)로 요청을 전달한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher("thankYou.jsp");
        dispatcher.forward(request,response);
    }
    
    /* 
     * 기존 회원 정보를 수정하는 요청을 처리한다.
     */
    private void updateMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataNotFoundException {
    	
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
        	return;
        }
        
        String memberID = ((Member) session.getAttribute("loginMember")).getMemberID();    	
        // 요청 파라미터를 통해 HTML 폼 데이터를 얻어낸다.
        //String memberID = request.getParameter("memberID");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String zipcode = request.getParameter("zipcode");
        String address = request.getParameter("address");
        
        // 폼 데이터의 유효성을 검증하는 처리를 한다.
        // 에러 메시지들을 저장할 리스트
        List<String> errorMsgs = new ArrayList<String>();

        // 폼 데이터가 유효한 지 검증
        if ((password == null) || (password.length() == 0)) {
            errorMsgs.add("패스워드를 입력해주세요.");
        }        
        if ((name == null) || (name.length() == 0)) {
            errorMsgs.add("이름을 입력해주세요.");
        }        
        if ((email == null) || (email.length() == 0)) {
            errorMsgs.add("이메일주소를 입력해주세요.");
        }
                
        // 유효하지 않은 데이터가 있으면 에러 내용을 request scope 속성에 저장하고
        // 에러 페이지 뷰(userError.jsp)로 요청을 전달한다.
        if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
            dispatcher.forward(request,response);
            return;
        }
        
        // 적절한 데이터를 가진 Member 객체를 생성하여
        Member member = new Member(memberID, password, name, email, tel, zipcode, address);
        
        // MemberService 객체에 위임하여 회원정보를 갱신한다.
        MemberService memberService = new MemberServiceImpl();
        memberService.updateMember(member);
        
        // request scope 속성에 등록된 member를 저장하고 
        // RequestDispatcher 객체를 통해 뷰 페이지(thankYou.jsp)로 요청을 전달한다.
        request.setAttribute("member", member);
        RequestDispatcher dispatcher = request.getRequestDispatcher("thankYou.jsp");
        dispatcher.forward(request,response);
    }
    
    /* 
     * 로그인 요청을 처리한다.
     */
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        // 요청 파라미터로부터 memberID와 password를 받아
        String memberID = request.getParameter("memberID");
        String password = request.getParameter("password");
        
        // MemberService 객체에 위임하여 로그인 가능 여부를 확인한다.
        MemberService service = new MemberServiceImpl();
        Member member = service.loginCheck(memberID, password);
        
        // check 값을 확인하여 
        int check = member.getCheck();
        
        if (check == Member.VALID_MEMBER) {
        	// 1. 유효한 회원일 경우 세션 객체를 얻어 session scope 속성에 회원 정보를 저장한다. 
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member);                
            // RequestDispatcher 객체를 통해 메인 페이지(index.jsp)로 요청을 전달한다.
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request,response);
            
        } else {
        	// 유효하지 않을 경우 request scope 속성에 에러 메시지를 저장하고 
            // RequestDispatcher 객체를 통해 메인 페이지(index.jsp)로 요청을 전달한다.
            String loginErrorMsg = null;
            
            if (check == Member.INVALID_ID) {
            	loginErrorMsg = "아이디가 존재하지 않습니다.";
            } else if (check == Member.INVALID_PASSWORD) {
            	loginErrorMsg = "패스워드가 일치하지 않습니다.";
            }
            
            request.setAttribute("loginErrorMsg", loginErrorMsg);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
 
    /* 
     * 로그아웃 요청을 처리한다.
     */
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 2. session scope 속성에 저장된 회원 정보를 제거하고 세션을 무효화 한다.
    	HttpSession session = request.getSession(false);
    	if (session != null) {
    		session.removeAttribute("loginMember");
    		session.invalidate();
    	}    		
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request,response);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
