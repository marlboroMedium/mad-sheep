package casestudy.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import casestudy.business.domain.Basket;
import casestudy.business.domain.BasketCart;
import casestudy.business.domain.Member;
import casestudy.business.domain.Product;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;
import casestudy.business.service.BasketPurchaserService;
import casestudy.business.service.BasketPurchaserServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.business.service.MemberService;
import casestudy.business.service.MemberServiceImpl;
import casestudy.business.service.ProductService;
import casestudy.business.service.ProductServiceImpl;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /* 
     * HTTP GET과 POST 방식의 요청을 모두 처리한다.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
        
        try {
			// action 요청파라미터 값을 확인한다.
			String action = request.getParameter("action");
			
			/* action 값(select/select-all)에 따라 적절한 메소드를 선택하여 호출한다.
			 * (select 이면 selectProduct(), select-all 이면 selectAllProduct() 메소드 호출) */
			if (action.equals("select")) {
				selectProduct(request, response);
			} else if (action.equals("select-all")) {
				selectAllProducts(request, response);
			} else if (action.equals("putOne-basket")) {
                putOneBasket(request,response);
            } else if (action.equals("emptyOne-basket")) {
                emptyOneBasket(request,response);
            } else if (action.equals("emptyAll-basket")) {
                emptyAllBasket(request,response);
            } else if (action.equals("select-memberPurchaser")) {
                selectMember(request,response);
            } else if (action.equals("insert-basketPurchaser")) {
                insertBasketPurchaser(request,response);
            }
			
        } catch (DataNotFoundException ex) {
        	throw new ServletException(ex);
        }
    }
    
    /* 
     * 하나의 상품 상세정보를 보여주는 요청을 처리한다.
     */
    private void selectProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DataNotFoundException {
        
        // 요청파라미터 값을 확인하고 검증
        String productID = request.getParameter("productID");
        if ((productID == null) || (productID.length() == 0)) {
            productID = "sams110";
        }
        
        // 서비스 객체를 통해 적절한 비즈니스 로직을 수행
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProduct(productID);

        // request scope 속성에 조회된 product를 저장하고
        request.setAttribute("selectedProduct", product);
        
        // RequestDispatcher 객체를 통해 뷰 페이지(selectProduct.jsp)로 요청을 전달한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher("selectProduct.jsp");
        dispatcher.forward(request, response);
    } 
    
    /* 
     * 모든 상품 리스트를 보여주는 요청을 처리한다.
     */
    private void selectAllProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductService productService = new ProductServiceImpl();
        Product[] productList = productService.getAllProducts();

        // request scope 속성에 조회된 productList를 저장하고
        request.setAttribute("productList", productList);
        
        // RequestDispatcher 객체를 통해 뷰 페이지(selectAllProducts.jsp)로 요청을 전달한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher("selectAllProducts.jsp");
        dispatcher.forward(request, response);
    }
    
    /** 구매정보 등록하기    */
    private void insertBasketPurchaser(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            List<String> errorMsgs = new ArrayList<String>();
            errorMsgs.add("로그인을 먼저하세요!!");
            request.setAttribute("errorMsgs", errorMsgs);

            RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
            dispatcher.forward(request,response);
            return;
        }

        BasketPurchaserService basketPurchaserService = new BasketPurchaserServiceImpl();

        String memberID =  ((Member) session.getAttribute("loginMember")).getMemberID();
        BasketCart basketCart = (BasketCart) session.getAttribute("basketCart");
        Collection<Basket> collection = basketCart.getItems();
        Iterator<Basket> iterator = collection.iterator();
        Basket basket = null;
        
        while (iterator.hasNext()) {
            basket = iterator.next();
            Purchaser purchaser = new Purchaser();

            int orderNo = basketPurchaserService.orderMaxNo();
            String cardNumber = request.getParameter("cardnumber");
            String cardType = request.getParameter("cardtype");
            
            if (cardNumber.equals("") | cardType.equals("")) {
                cardNumber = "N";
                cardType = "N";
            }
            
            basket.setOrderNum(orderNo);
            
            purchaser.setOrderNum(orderNo);
            purchaser.setMemberID(memberID);
            purchaser.setAddress(request.getParameter("address"));
            purchaser.setName(request.getParameter("name"));
            purchaser.setEmail(request.getParameter("email"));
            purchaser.setTel(request.getParameter("tel"));
            purchaser.setPlace(request.getParameter("place"));
            purchaser.setCardNumber(cardNumber);
            purchaser.setCardType(cardType);
            purchaser.setPayStatus("N");
            purchaser.setPayType(request.getParameter("paytype"));
            purchaser.setAmount(basket.getTotalPrice());
            
            basketPurchaserService.insertBasketPurchaser(basket, purchaser, memberID);
        }
        
        Collection<TheOrder> orderCollection = basketPurchaserService.listBasketPurchaser(memberID);
        request.setAttribute("theOrderCollection", orderCollection);

        //쇼핑 카트 초기화
        basketCart.clear();
        basketPurchaserService.deleteBasket(memberID);
        
        RequestDispatcher dispatcher=request.getRequestDispatcher("orderReport.jsp");
        dispatcher.forward(request,response);
    }
    
    /** 구매정보 등록하기전에 회원정보 조회하기    */
    protected void selectMember(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException, DataNotFoundException {

        HttpSession session = request.getSession(false);
        /* 로그인인 상태가 아닌 경우(loginMember 속성이 없는 경우) 다른 페이지로 이동  */
        if (session == null || session.getAttribute("loginMember") == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }        
        // 세션 속성의 Member 객체에서 memberID를 구해냄
        String memberID = ((Member) session.getAttribute("loginMember")).getMemberID();
        
        MemberService service = new MemberServiceImpl();
        Member selectedMember = service.findMember(memberID);

        request.setAttribute("member", selectedMember);
        RequestDispatcher dispatcher = request.getRequestDispatcher("purchaseProduct.jsp");
        dispatcher.forward(request,response);
    }
    
    /** Basket(장바구니)에서 상품 모두를 삭제    */
    protected void emptyAllBasket(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        if (session != null) {
            //쇼핑카트 clear
            BasketCart basketCart = (BasketCart) session.getAttribute("basketCart");
            basketCart.clear();
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("basketProduct.jsp");
        dispatcher.forward(request,response);
    }
    
    /** Basket(장바구니)에서 상품 하나를 삭제     */
    protected void emptyOneBasket(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        if (session != null) {
            BasketCart basketCart = (BasketCart) session.getAttribute("basketCart");
            String productID = request.getParameter("productID");
            basketCart.remove(productID);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("basketProduct.jsp");
        dispatcher.forward(request,response);
    }
    
    /** Basket(장바구니)에 선택한 상품을 담기    */
    protected void putOneBasket(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException, DataNotFoundException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            List<String> errorMsgs = new ArrayList<String>();
            errorMsgs.add("로그인을 먼저하세요!!");
            request.setAttribute("errorMsgs", errorMsgs);

            RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
            dispatcher.forward(request,response);
            return;
        }

        String productID = request.getParameter("productID");
        String quantity = request.getParameter("quantity");
        int iQuantity = Integer.parseInt(quantity);
        
        BasketCart basketCart = (BasketCart) session.getAttribute("basketCart");
        Basket basket = listProduct(request, response, productID);
        
        if (basketCart == null) {
            basketCart = new BasketCart();
        }

        basketCart.add(productID, basket,iQuantity);
        
        session.setAttribute("basketCart", basketCart);
        
        RequestDispatcher dispatcher=request.getRequestDispatcher("basketProduct.jsp");
        dispatcher.forward(request,response);
    }
    
    /** 제품명을 parameter로 입력 받아 그 제품의 장바구니 정보를 가져오기    */
    private Basket listProduct(HttpServletRequest request, HttpServletResponse response, String productID) 
    		throws DataNotFoundException {

        ProductService productService = new ProductServiceImpl();
        
        Product product = (Product) productService.findProduct(productID);
        
        String quantity = request.getParameter("quantity");
        System.out.println("quantity: " + quantity);
        Basket basket = new Basket();
        basket.setCompany(product.getCompany());
        basket.setMallID(product.getMallID());
        
        basket.setOrderNum(1);
        basket.setPrice(product.getPrice2());
        basket.setProductID(product.getProductID());
        basket.setProductName(product.getProductName());
        basket.setQuantity(Integer.parseInt(quantity));
        
        return basket;
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
