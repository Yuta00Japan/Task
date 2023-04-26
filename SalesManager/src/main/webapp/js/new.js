/**
 * 
 */

const controller = new AbortController();
const signal = controller.signal;

// タイムアウトを設定
const timeoutId = setTimeout(() => {
  console.log("Request timed out");
  controller.abort(); // リクエストを中止する
}, 60000); // 60秒


/**
 * 画面ロード時に所属と部署をセットする
 */
 window.addEventListener('load',()=>{
	setBranch();
	setDepartment();
});
  
 /*
  * 所属と従業員ごとに初期選択をセットする
  */
 function setBranch(){
	 
	 var branchValue = document.getElementById('branchValue').value;
	 
	 fetch('http://localhost:3000/setBranch',{signal})
	 .then(response => response.json())
	 .then(data =>{
		 let select = document.getElementById('branch');
		for(let i = 0; i < data.length; i++){
			console.log(data[i].BranchID+" "+data[i].BranchName);
			 var option = document.createElement("option");
      		 option.value = data[i].BranchID;
             option.text = data[i].BranchName;
     		 select.add(option);
		}
		 select.selectedIndex=(branchValue); 
	 }) 
	 .catch(error => {
  		console.error(error); // エラーを処理する
	 })
	 .finally(() => {
	 	 clearTimeout(timeoutId); // タイムアウトをクリアする
	 });
	
 }
 
 /**
  * 部署と従業員ごとに初期選択をセット
  */
 function setDepartment(){
	 
	 var departmentValue = document.getElementById('departmentValue').value;
	 
	  fetch('http://localhost:3000/setDepartment',{signal})
	 .then(response => response.json())
	 .then(data =>{
		 let select = document.getElementById('department');
		for(let i = 0; i < data.length; i++){
			console.log(data[i].DepartmentID+" "+data[i].DepartmentName);
			var option = document.createElement("option");
      		option.value =data[i].DepartmentID;
            option.text = data[i].DepartmentName;
     		select.add(option);
		}
		select.selectedIndex=(departmentValue) ;
	 })
	 .catch(error => {
 		 console.error(error); // エラーを処理する
	 })
	.finally(() => {
	 	 clearTimeout(timeoutId); // タイムアウトをクリアする
	});
 }
 

 
 