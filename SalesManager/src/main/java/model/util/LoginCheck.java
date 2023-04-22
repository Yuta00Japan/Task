package model.util;

import javax.servlet.http.HttpSession;

/**
 * ユーザーが正常にログインしているかどうかをチェックします
 * @author yuta
 *
 */
public class LoginCheck {

	private LoginCheck() {
		
	}
	/**
	 * ユーザー情報が保持されているかどうかをチェックする
	 * @param session ユーザー情報を含むsession
	 */
	public static boolean check(HttpSession session) {
		
		if(session.getAttribute("user")!=null) {
			return true;
		}
		return false;
	}
}
