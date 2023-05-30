package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.employee.Employee;
import model.employee.EmployeeLogic;
import model.node.NodeStart;
import model.util.AntiXss;
import model.util.LoginCheck;

/**
 * Servlet implementation class SessionController
 * ログイン、ログアウトなどsessionの管理を担当する
 */
@WebServlet("/SessionController")
public class SessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String state = request.getParameter("state");
		HttpSession session = request.getSession();
		
		//EmployeeControllerなどからメニュー画面に戻りたいとき利用する
		if(state != null) {
			
			switch(state) {
			case "menu":
				proc_Menu(request, response,session);
				break;
			case "logout":
				proc_Logout(request,response,session);
				break;
			}
			return  ;
		}
		
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
		
		String state = request.getParameter("state");
		HttpSession session = request.getSession();
		
		try {
			switch(state) {
			//ログイン試行
			case "try_Login":
				proc_Login(request,response,session);
				break;
			//ログアウト処理
			case "logout":
				proc_Logout(request,response,session);
				break;
			//メニュー画面
			case "menu":
				if(LoginCheck.check(session)) {
					proc_Menu(request,response,session);
					return ;
				}
				proc_Logout(request,response,session);
				
				break;
			default:
				proc_Logout(request,response,session);
			}
		}catch(Exception e) {
			e.printStackTrace();
			proc_Logout(request,response,session);
		}
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
		
		//ログインIDとパスワードで取りだせてかつ従業員が有効ならばログイン成功とみなす
		if(emp != null && emp.isEnable()==true) {
			//ログイン成功
			System.out.println("login Success");
			session.setAttribute("user", emp);
			//メニュー画面へ遷移する
			getServletContext().getRequestDispatcher("/WEB-INF/menu/menu.jsp").forward(request, response);
			return ;
		}
		//ログインに失敗した場合
		
		request.setAttribute("lbError", "入力に誤りがあります");
		//カウントを＋１
		session.setAttribute("tryCount",( (int)session.getAttribute("tryCount") + 1 ));	
		System.out.println("試行回数"+((int)session.getAttribute("tryCount")-1)+"->"+(int)session.getAttribute("tryCount"));
		
		if(!((int)session.getAttribute("tryCount") <= 5)) {
			System.out.println("ログインエラー");
			//ログインエラー画面を表示する
			getServletContext().getRequestDispatcher("/WEB-INF/login/loginError.jsp").forward(request, response);
			return ;
		}
		//ログイン画面へ遷移する
		getServletContext().getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
			
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
	 * メニュー画面に遷移しsessionをログインユーザ情報以外のsessionをリセットする
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param session ユーザ情報含むsession
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected void proc_Menu(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException {
		System.out.println(getServletName()+"# menu");
		
		System.out.println("sessionをリセットしました。（ログインユーザ情報を除く）");
		Employee user = (Employee)session.getAttribute("user");
		session.invalidate();
		session = request.getSession();
		session.setAttribute("user", user);
		getServletContext().getRequestDispatcher("/WEB-INF/menu/menu.jsp").forward(request, response);
	}

}
