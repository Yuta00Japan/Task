package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.item.Item01;
import model.item.Item01List;
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
		String []state = request.getParameter("state").split(",");
		
		HttpSession session = request.getSession();
		
		//sessionがユーザー情報を保持しているかどうかを確認する
		if(LoginCheck.check(session)) {
			try {
				switch(state[0]) {
				//商品登録フォーム
				case "new":
					proc_New(request,response);
					break;
				//商品販売実績ー検索
				case "achievementSearch":
					proc_AchievementSearch(request,response,session);
					break;
				//商品販売実績ー　一覧
				case "achievement":
					proc_Achievement(request,response);
					break;
				//大分類
				case "majorCategory":
					proc_MajorItem(request,response,session);
					break;
				//中分類
				case "minorCategory":
					
					break;
				//小分類
				case "detailedCategory":
					
					break;
				//分類登録
				case "newItem01":
					proc_Item01Add(request,response,session,state[1],state[2]);
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
				getServletContext().getRequestDispatcher("/WEB-INF/menu/menu.jsp").forward(request, response);
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
	 * @throws ServletException error
	 * @throws IOException error
	 */
	protected void proc_New(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getServletName()+"# new");
		getServletContext().getRequestDispatcher("/WEB-INF/item/new.jsp").forward(request, response);
	}
	
	/**
	 * 商品販売実績フォームを表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws IOException error
	 * @throws ServletException error
	 */
	protected void proc_Achievement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getServletName()+"# achievement");
		
		request.setAttribute("action", "商品名");
		getServletContext().getRequestDispatcher("/WEB-INF/item/achievement.jsp").forward(request, response);
	}
	
	/**
	 * 商品検索を実行し結果を表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 商品情報を含むsession
	 * @throws Exception 検索失敗
	 */
	protected void proc_AchievementSearch(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# achievement search");
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
		
		getServletContext().getRequestDispatcher("/WEB-INF/item/achievement.jsp").forward(request, response);
	}
	
	/**
	 * 大分類画面を表示する
	 * @param request HTTP request
	 * @param response HTTP response 
	 * @throws Exception error
	 */
	protected void proc_MajorItem(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# major item");
		Item01List list = ItemLogic.majorItem();
		session.setAttribute("majorItem", list);
		getServletContext().getRequestDispatcher("/WEB-INF/item/majorItem.jsp").forward(request, response);
	}
	
	/**
	 * 大分類、中分類、小分類を登録する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 分類を含むsession
	 * @param parentID 親ID
	 * @param from どこから登録したのか確認
	 * @throws Exception 登録失敗
	 */
	protected void proc_Item01Add(HttpServletRequest request, HttpServletResponse response,HttpSession session,String parentID,String from) throws Exception {
		System.out.println(getServletName()+" item01 add");
		ItemLogic.setItem01FromRequest(request, parentID);
		
		Item01 item = (Item01)session.getAttribute("item01");
		ItemLogic.addItem01(item);
		
		//登録内容を反映しつつ登録した分類の画面へ遷移する
		switch(from) {
		//大分類
		case "major":
			proc_MajorItem(request,response,session);
			break;
		//中分類、
		case "minor":
			break;
		//小分類
		case "detailed":
			break;
		}
		
	}
	
	

}
