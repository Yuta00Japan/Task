/**
 * 所属と部署をデータベースから取りだしセットする
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
 
 /**
  * 所属をセットする
  */
 function setBranch(){
	 fetch('http://localhost:3000/setBranch',{signal})
	 .then(response => response.json())
	 .then(data =>{
		  console.log(data);
		 let select = '';
		for(let i = 0; i < data.length; i++){
			select += '<option value'+data[i].BranchID+'>'+data[i].BranchName+'</option>';
		} 
		console.log(select);
		document.getElementById('branch').innerHTML = select;
	 })
	 .catch(error => {
  		console.error(error); // エラーを処理する
	 })
	 .finally(() => {
	 	 clearTimeout(timeoutId); // タイムアウトをクリアする
	 });
 }
 
 /**
  * 部署をセットする
  */
 function setDepartment(){
	  fetch('http://localhost:3000/setDepartment',{signal})
	 .then(response => response.json())
	 .then(data =>{
		 console.log(data);
		 let select = '';
		for(let i = 0; i < data.length; i++){
			select += '<option value'+data[i].DepartmentID+'>'+data[i].DepartmentName+'</option>';
		} 
		console.log(select);
		document.getElementById('department').innerHTML = select;
	 })
	 .catch(error => {
 		 console.error(error); // エラーを処理する
	 })
	.finally(() => {
	 	 clearTimeout(timeoutId); // タイムアウトをクリアする
	});
 }
 