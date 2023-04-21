package model.util;

/**
 * クロスサイトスクリプティング攻撃対策クラス
 * @author yuta
 *
 */
public class AntiXss {

	private AntiXss() {
		
	}
	/**
	 * ラジオボタンなどの入力しないものを除いて
	 * 入力内容はすべてこのメソッドを通します。
	 * @param content フォーム入力内容
	 * @return 無害化済み入力内容
	 */
	public static String antiXss(String content) {
		
		if(content == null) {
			return null;
		}else {
			content = content.replaceAll("&","xxxx" );
			content = content.replaceAll("<","xxxx");
			content = content.replaceAll(">","xxxx");
			content = content.replaceAll("\'","xxxx");
			content = content.replaceAll("\"","xxxx");
			content = content.replaceAll("`", "xxxx");
			
			return content;
		}
	}
}
