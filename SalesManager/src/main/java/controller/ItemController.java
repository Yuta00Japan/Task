package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.item.ItemLogic;
import model.item.TrSalesList;
import model.util.LoginCheck;

/**
 * Servlet implementation class Item
 */
@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String state = request.getParameter("state");
		
		HttpSession session = request.getSession();
		
		//sessionがユーザー情報を保持しているかどうかを確認する
		if(LoginCheck.check(session)) {
			try {
				switch(state) {
				case "search":
					proc_Search(request,response,session);
					break;
				//商品新規登録フォーム
				case "new":
					proc_New(request,response);
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			session.invalidate();
			session = request.getSession();
			//ログイン回数記録用の値を保存
			session.setAttribute("tryCount", 0);
			//ログイン画面へ遷移する
			getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
		}
	}
	/**
	 * 商品登録フォームを表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws IOException error
	 * @throws ServletException error
	 */
	protected void proc_New(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getServletName()+"# new");
		
		request.setAttribute("action", "商品名");
		getServletContext().getRequestDispatcher("/WEB-INF/item/new.jsp").forward(request, response);
	}
	
	/**
	 * 商品検索を実行し結果を表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 商品情報を含むsession
	 * @throws Exception 検索失敗
	 */
	protected void proc_Search(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# search");
		
		//セッションをリセット
		session.removeAttribute("trSalesList");
		
		String start = request.getParameter("txtStartDate");
		String end = request.getParameter("txtEndDate");
		String category = request.getParameter("category");
		String select = request.getParameter("rdoSelect");
		
		//カテゴリ検索の場合
		if(select.equals("categorySales")) {
			request.setAttribute("action","カテゴリ");
		}else {
		//それ以外	
			request.setAttribute("action","商品名");
		}
		
		System.out.println("入力値-> "+start +" ～ "+ end +" | "+category+" | "+ select);
		
		TrSalesList list = ItemLogic.search(start, end, category, select);
		
		session.setAttribute("trSalesList", list);
		
		getServletContext().getRequestDispatcher("/WEB-INF/item/new.jsp").forward(request, response);
	}

}
