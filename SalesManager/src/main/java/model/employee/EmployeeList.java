package model.employee;

import java.util.List;
/**
 * 従業員検索結果を格納する
 * @author yuta
 *
 */
public class EmployeeList {
	
	/**従業員検索結果*/
	private List<Employee> list;
	/**従業員検索結果詳細*/
	private List<EmployeeDetail> detail;
	
	public EmployeeList() {
		
	}

	public List<Employee> getList() {
		return list;
	}

	public void setList(List<Employee> list) {
		this.list = list;
	}

	public List<EmployeeDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<EmployeeDetail> detail) {
		this.detail = detail;
	}
}
