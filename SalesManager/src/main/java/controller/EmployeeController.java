package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.employee.Employee;
import model.employee.EmployeeList;
import model.employee.EmployeeLogic;
import model.node.NodeStart;
import model.util.AntiXss;
import model.util.LoginCheck;

/**
 * Servlet implementation class User
 */
@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		//sessionにユーザー情報が保持されていた場合破棄しログオフ状態にする
		if(session.getAttribute("user") != null) {
			session.removeAttribute("user");
		}
		
		//node express server 起動
		Thread node = new NodeStart();
		node.start();
		
		//ログイン回数記録用の値を保存
		session.setAttribute("tryCount", 0);
		
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String state = request.getParameter("state");
		
		//sessionがユーザー情報を保持しているかどうかを確認する
		
			try {
				switch(state) {
				//ログインフォームを表示する
				case "loginForm":
					proc_LoginForm(request,response);
					break;
					//ログイン試行
				case "try_Login":
					proc_Login(request,response,session);
					break;
					//ログアウト
				case "logout":
					proc_Logout(request,response,session);
					break;
					//従業員一覧
				case "list":
					if(LoginCheck.check(session)) {
						proc_List(request,response,session);
					}else {
						proc_SessionError(request,response,session);
					}
					break;
					//従業員検索
				case "search":
					if(LoginCheck.check(session)) {
						proc_Search(request,response,session);
					}else {
					    proc_SessionError(request,response,session);
					}
					break;
					//従業員登録
				case "new":
					if(LoginCheck.check(session)) {
						proc_New(request,response);
					}else {
						proc_SessionError(request,response,session);
					}
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	
	}
	/**
	 * session切れによりユーザー情報が欠損していた場合ログイン画面に戻す
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session ユーザー情報が欠損したsession
	 * @throws ServletException error
	 * @throws IOException error
	 */
	protected void proc_SessionError(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		System.out.println(getServletName()+"# session Error");
		session.invalidate();
		session = request.getSession();
		//ログイン回数記録用の値を保存
		session.setAttribute("tryCount", 0);
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	}
	
	/**
	 * ログインフォームへ遷移する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws ServletException error
	 * @throws IOException error
	 */
	protected void proc_LoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getServletName()+"# loginForm");
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	}
	
	/**
	 * ログイン処理を実行する
	 * @param request HTTP request
	 * @param response HTTP response 
	 * @param session ユーザー情報を入れるsession
	 * @throws Exception ログイン処理失敗
	 */
	protected void proc_Login(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# try_Login");
		
		String loginID = AntiXss.antiXss(request.getParameter("txtID"));
		String password = AntiXss.antiXss(request.getParameter("txtPASS"));
		//ログインID、パスワードでユーザー情報を取得
		Employee emp = EmployeeLogic.login(loginID, password);
			
		if(emp != null) {
			//ログイン成功
			System.out.println("login Success");
			session.setAttribute("user", emp);
			//メニュー画面へ遷移する
			getServletContext().getRequestDispatcher("/WEB-INF/menu/menu.jsp").forward(request, response);
			
		}else {
			//ログインに失敗した場合
			request.setAttribute("lbError", "入力に誤りがあります");
			
			//カウントを＋１
			int tryCount = (int)session.getAttribute("tryCount");
			tryCount++;
			
			System.out.println("試行回数"+(tryCount-1)+"->"+tryCount);
			
			session.setAttribute("tryCount", tryCount);
			
			if(!(tryCount <= 5)) {
				System.out.println("ログインエラー！");
				//ログインエラー画面を表示する
				getServletContext().getRequestDispatcher("/WEB-INF/login/loginError.jsp").forward(request, response);
			}else {
				//ログイン画面へ遷移する
				getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
			}
			
		}
	}
	
	/**
	 * ログアウト処理を行う
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session ユーザー情報を含むsession
	 * @throws IOException error
	 * @throws ServletException  error
	 */
	protected void proc_Logout(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		System.out.println(getServletName()+" # logout");
		session.invalidate();
	    session = request.getSession();
		//ログイン回数記録用の値を保存
		session.setAttribute("tryCount", 0);
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	}
	
	/**
	 * すべての従業員情報を取得し従業員一覧を表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws Exception ロード失敗
	 */
	protected void proc_List(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# list");
		EmployeeList empList = EmployeeLogic.loadAll();
		session.setAttribute("empList", empList);
		getServletContext().getRequestDispatcher("/WEB-INF/employee/list.jsp").forward(request, response);
	}
	/**
	 * 従業員情報を検索する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 従業員情報を入れるsession
	 * @throws Exception 検索失敗
	 */
	protected void proc_Search(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# search");
		
		String name = request.getParameter("name");
		String branch= request.getParameter("branch");
		String department = request.getParameter("department");
		String trueFalse = request.getParameter("enable");
		
		System.out.println("入力値ー＞ "+name+" "+branch+" "+department+" "+trueFalse);
		
		EmployeeList emplist = EmployeeLogic.search(name, branch, department, trueFalse);
		session.setAttribute("empList", emplist);
		getServletContext().getRequestDispatcher("/WEB-INF/employee/list.jsp").forward(request, response);
	}
	
	/**
	 * 従業員を新規登録する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws ServletException error
	 * @throws IOException error
	 */
	protected void proc_New(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getServletName()+"# new");
		getServletContext().getRequestDispatcher("/WEB-INF/employee/new.jsp").forward(request, response);
	}

}
