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
				//既存分類更新
				case "updateItem01":
					proc_UpdateItem01(request,response,session,state[1],state[2]);
					break;
				//既存分類削除
				case "deleteItem01":
					proc_DeleteItem01(request,response,session,state[1],state[2]);
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
		ItemLogic.setItem01FromRequest(request);
		
		Item01 item = (Item01)session.getAttribute("item01");
		item.setParentID(Integer.parseInt(parentID));
		ItemLogic.addItem01(item);
		
		//登録情報を反映
		proc_ReflectUpdate(request,response,session,from);
		
	}
	
	/**
	 * 
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 商品０１情報を含むsession
	 * @param shouhin01ID 商品０１ID
	 * @param from 遷移元
	 * @throws Exception 更新失敗
	 */
	protected void proc_UpdateItem01(HttpServletRequest request, HttpServletResponse response,HttpSession session,String shouhin01ID,String from) throws Exception {
		System.out.println(getServletName()+" update item01");
		//更新対象の商品IDをもとにロード
		Item01 item = ItemLogic.loadSingle(shouhin01ID);
		session.setAttribute("item01", item);
		//更新情報を加えてsessionにセットしなおす
		ItemLogic.setItem01FromRequest(request);
		//更新処理を実行する
		ItemLogic.updateItem01(item);
		//更新情報をsessionに反映
		proc_ReflectUpdate(request,response,session,from);
	}
	
	/**
	 * 対象IDの商品０１を削除する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 商品０１を含むsession
	 * @param shouhin01ID 商品０１ID
	 * @param from 遷移元
	 * @throws Exception 削除失敗
	 */
	protected void proc_DeleteItem01(HttpServletRequest request, HttpServletResponse response,HttpSession session,String shouhin01ID,String from) throws Exception {
		System.out.println(getServletName()+"# delete item01");
		ItemLogic.deleteItem01(shouhin01ID);
		//削除をsessionに反映
		proc_ReflectUpdate(request,response,session,from);
	}
	
	
	protected void proc_Item01Detail(HttpServletRequest request, HttpServletResponse response,HttpSession session,String shouhin01ID,String from) throws Exception {
		System.err.println(getServletName()+" # item01 detail");
		
	}
	
	/**
	 * 登録、更新、削除後に情報を更新し遷移もとに画面を遷移させる
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 更新情報を含むsession
	 * @param from 遷移元
	 * @throws Exception 情報更新失敗
	 */
	protected void proc_ReflectUpdate(HttpServletRequest request, HttpServletResponse response,HttpSession session,String from) throws Exception {
		System.out.println(getServletName()+"# reflectUpdate");
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
		default:
			getServletContext().getRequestDispatcher("/WEB-INF/menu/menu.jsp").forward(request, response);
			break;
		}
	}

}
