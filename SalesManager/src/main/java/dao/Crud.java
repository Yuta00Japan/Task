package dao;

/**
 * データベース関連の処理を実装します
 * @author yuta
 *
 */
public interface Crud {

	//登録
	void add(Object o);
	//更新
	void update(Object o);
	//削除
	void delete(Object o);
}
