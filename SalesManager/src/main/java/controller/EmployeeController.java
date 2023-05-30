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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String state[] = request.getParameter("state").split(",");
		
			try {
				//sessionがユーザー情報を保持しているかどうかを確認する
				if(LoginCheck.check(session)) {
					switch(state[0]) {
					//従業員登録フォームの内容をリセットし表示する
					case "formReset":
						proc_formReset(request,response,session);
						break;
					//従業員一覧
					case "list":
						proc_List(request,response,session,state[1]);
						break;
					//従業員検索
					case "search":
						proc_Search(request,response,session);
						break;
					//従業員登録フォーム
					case "new":
						proc_New(request,response,session);
						break;
					//従業員登録
					case "add":
						proc_Add(request,response,session);
						break;
					//従業員詳細
					case "detail":
						proc_Detail(request,response,session,state[1]);
						break;
					//既存従業員更新
					case "update":
						proc_Update(request,response,session);
						break;
					//上司選択
					case "selectBoss":
						proc_BossSelect(request,response,session,state[1]);
						break;
					//従業員削除		
					case "deleteEmployee":
						proc_Delete(request,response,session,state[1]);
						break;
					default:
						response.sendRedirect("SessionController?state=logout");
					}
					return ;
				}
				//ユーザ情報のsessionが保持されていなかった場合ログイン画面へ戻す
				response.sendRedirect("SessionController?state=logout");
				
			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("SessionController?state=logout");
			}
	
	}
	
	/**
	 * 不要なsessionをリセットする
	 * @param session 不要なsessionデータ
	 */
	public void sessionReset(HttpSession session) {
		System.out.println(getServletName()+"# sessionReset ");
		
		session.removeAttribute("boss");
		session.removeAttribute("employee");
		session.removeAttribute("empList");
		session.removeAttribute("method");
		session.removeAttribute("boss");
	}
	
	/**
	 * 従業員登録画面の情報をリセットする
	 * @param request HTTP request
	 * @param response HTTP response
	 * @throws ServletException error
	 * @throws IOException error
	 */
	protected void proc_formReset(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		System.out.println(getServletName()+"#  resetForm");
		//session削除
		sessionReset(session);
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/employee/new.jsp").forward(request, response);
	}
	
	/**
	 * すべての従業員情報を取得し従業員一覧を表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param method 用途　例）上司検索、従業員検索、閲覧
	 * @throws Exception ロード失敗
	 */
	protected void proc_List(HttpServletRequest request, HttpServletResponse response,HttpSession session,String method) throws Exception {
		System.out.println(getServletName()+"# list");
		
		//登録フォームの値を保持する
		EmployeeLogic.setEmployeeFromRequest(request);
		
		//通常の閲覧であれば何もしない
		if(method.equals("normal")) {
			System.out.println("通常検索");	
		}//従業員検索
		else if(method.equals("employee")) {
			request.setAttribute("from","new");
			System.out.println("従業員検索開始");
			session.setAttribute("method",method);
		}//上司検索
		else {
			System.out.println("上司検索開始");
			request.setAttribute("from","new");
			session.setAttribute("method", method);
		}
		
		//全従業員情報を取りだす
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
		
		String name = AntiXss.antiXss(request.getParameter("name"));
		String branch= request.getParameter("branch");
		String department = request.getParameter("department");
		String trueFalse = request.getParameter("enable");
		
		System.out.println("入力値ー＞ 氏名："+name+" | 支社ID: "+branch+" | 部署ID: "+department+" | 削除者検索 "+trueFalse);
		//検索を行う
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
	protected void proc_New(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		System.out.println(getServletName()+"# new");
		//システム管理者かどうかチェックする
		Employee emp = (Employee) session.getAttribute("user");
		
		if(emp != null) {
			if(Character.toString(emp.getUserRole().charAt(9)).equals("1")) {
				//管理者の場合
				getServletContext().getRequestDispatcher("/WEB-INF/employee/new.jsp").forward(request, response);
				return ;
			}
		}
		response.sendRedirect("SessionController?state=menu");
		
	}
	/**
	 * 従業員を新規登録する
	 * @param request HTTP requst
	 * @param response HTTP response
	 * @param session 従業員登録情報を含むsession
	 * @throws Exception 登録失敗
	 */
	protected void proc_Add(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# add");
		EmployeeLogic.setEmployeeFromRequest(request);
		Employee emp = (Employee)session.getAttribute("employee");
		EmployeeLogic.add(emp);
		response.sendRedirect("SessionController?state=menu");
	}
	
	/**
	 * 従業員情報を取得し画面に表示する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 従業員情報を含むsession
	 * @param employeeId 従業員ID
	 * @throws Exception ロード失敗
	 */
	protected void proc_Detail(HttpServletRequest request, HttpServletResponse response,HttpSession session,String employeeId) throws Exception {
		System.out.println(getServletName()+"# detail");
		System.out.println("従業員ID:"+employeeId);
		//従業員情報
		Employee emp = EmployeeLogic.loadSingle(employeeId);
		//従業員の上司情報
		Employee boss = EmployeeLogic.searchBoss(emp.getBossId()+"");
		session.setAttribute("employee", emp);
		session.setAttribute("boss", boss);
		getServletContext().getRequestDispatcher("/WEB-INF/employee/new.jsp").forward(request, response);
	}
	
	/**
	 * 既存従業員情報を更新する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 既存従業員情報を含むsession
	 * @throws Exception 更新失敗
	 */
	protected void proc_Update(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println(getServletName()+"# update");
		EmployeeLogic.setEmployeeFromRequest(request);
		Employee emp = (Employee)session.getAttribute("employee");
		EmployeeLogic.update(emp);
		//ログインユーザ情報を再度取得する
		Employee user = (Employee)session.getAttribute("user");
		session.setAttribute("user",EmployeeLogic.loadSingle(user.getEmpId()+""));
		response.sendRedirect("SessionController?state=menu");
	}
	
	/**
	 * 従業員IDをもとに上司を検索しセットする。
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 従業員情報を含むsession
	 * @param employeeId 上司ID
	 * @throws Exception ロード失敗
	 */
	protected void proc_BossSelect(HttpServletRequest request, HttpServletResponse response,HttpSession session,String employeeId) throws Exception {
		System.out.println(getServletName()+"# bossSelect");
		System.out.println( "bossID :"+employeeId);
		//上司を検索
		Employee boss = EmployeeLogic.searchBoss(employeeId);
		session.setAttribute("boss", boss);
		getServletContext().getRequestDispatcher("/WEB-INF/employee/new.jsp").forward(request, response);
	}
	
	/**
	 * 従業員を削除する
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session 従業員情報を含むsession
	 * @param employeeId 削除対象従業員ID
	 * @throws Exception 削除失敗
	 */
	protected void proc_Delete(HttpServletRequest request, HttpServletResponse response,HttpSession session,String employeeId) throws Exception {
		System.out.println(getServletName()+"# delete");
		
		EmployeeLogic.delete(employeeId);
		
		//ログインユーザが有効化どうかチェックする
		Employee user = (Employee)session.getAttribute("user");
		//もしログインユーザが更新された場合sessionの情報が古いままなので最新にする
		user = EmployeeLogic.loadSingle(user.getEmpId()+"");
		if(user.isEnable()) {
			response.sendRedirect("SessionController?state=menu");
			return ;
		}
		//有効でない場合ログイン画面へ遷移する
		response.sendRedirect("SessionController?state=menu");
	}

}
