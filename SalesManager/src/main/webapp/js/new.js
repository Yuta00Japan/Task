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
	setRole();
});

/**
 * 役割をセットする
 */
function setRole(){
	
	fetch('http://localhost:3000/setRole')
	.then(response => response.json())
	.then(data =>{
		let role = document.getElementById('role');
		let content = '';
		for(let i = 0; i < data.length; i++){
			content += data[i].roleName+'<input type="checkbox" name="role" value='+data[i].roleNo+'">　';
			if((i +1) % 2 == 0 ){
				content += '<br>';
			}
		}
		role.innerHTML = content;
	});
}

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
 
 document.getElementById('password').addEventListener('click',appearForm);
 
 var form = document.getElementById('Main');
 
 const inputs = form.querySelectorAll('input, select, textarea');
/**
 * パスワード設定フォームを表示させる
 */
function appearForm(){
	//フォームを編集不可にする
	form.disabled = true;
	inputs.forEach((input) => {
  	input.disabled = true;
	});
	
	let table = document.getElementById('passTable');
	let button1 = document.getElementById('passButton1');
	let button2 = document.getElementById('passButton2');
	table.innerHTML = 
	'<table>'
	+'<tr><td class="header2">新パスワード</td><td class="content"><input type="text" id="chPassword"></td></tr>'
	+'<tr><td class="header">確認</td><td class="content"><input type="text" id="confirmPass"></td></tr>'
	+'</table>';
	
	button1.innerHTML='<button class="button2" type="button" id="updatePassword">登録</button>';
	button2.innerHTML='<button class="button2" type="button" id="cancelPass">閉じる</button>';
	
	//フォーム削除のリスナーを設置
	document.getElementById('cancelPass').addEventListener('click',deleteForm);
	//パスワード更新のリスナーを設置
	document.getElementById('updatePassword').addEventListener('click',changePassword);
	
}

/**
 * パスワード設定フォームを消して入力できるようにする
 */
function deleteForm(){
	//編集可能にする
	form.disabled=false;
	inputs.forEach((input) => {
 	 input.disabled = false;
	});
	
	let table = document.getElementById('passTable');
	let button1 = document.getElementById('passButton1');
	let button2 = document.getElementById('passButton2');
	
	let error = document.getElementById('passwordError');
	
	table.innerHTML="";
	button1.innerHTML="";
	button2.innerHTML="";
	error.textContent ="";
}
/**現在編集中の従業員パスワード　変更前 */
var truePass = 0;

 function changePassword(){
	/**変更後パスワード */
	let changePass = document.getElementById('chPassword').value;
	/**確認パスワード */
	let confirmPass = document.getElementById('confirmPass').value;
	let error = document.getElementById('passwordError');
	
	//パスワードと確認が同じかどうかを確認
	if(changePass != confirmPass){
		error.textContent = '新規パスワードと確認が不一致';
		return ;
	}
	let pattern = /^[a-zA-Z0-9]+$/;
	//半角英数字出ない場合
	if(!(pattern.test(changePass)) &&!(pattern.test(confirmPass))){
		error.textContent='半角英数字で入力してください';
		return ;
	}
	//現在編集中の従業員IDを送信する
	return new Promise(resolve =>{
		let empId = document.getElementById('empId').textContent;
	
		fetch('http://localhost:3000/setEmpId',{
	  		method: 'POST',
	  		headers: { 'Content-Type': 'application/json' },
	  		signal:signal,
	  		body: JSON.stringify({empId:empId})
		})
		.then(response => response.json())
		.then(data => {
  			console.log('Success:', data);
		})
			clearTimeout(timeoutId); 
			console.log("OK");
			resolve();
	}).then(()=>{
		//送信した従業員IDから現在のパスワードを取得する
            return new Promise(resolve => {

               fetch('http://localhost:3000/getPass')
                  .then(response => response.json())
                  .then(data => {
                       truePass = data[0].password;
                       console.log(truePass);
                       resolve(truePass);
                  });
                  clearTimeout(timeoutId);
                });
	}).then((truePass)=>{
		return new Promise(resolve=>{
				console.log('from checkPassword '+truePass+" "+changePass);
  			if(truePass == changePass) {
    			error.textContent = '前回と同じパスワードは設定できません';
    			return ;
  			}
  			resolve();
		})
	}).then(()=>{
		return new Promise(resolve =>{
			//新しい従業員パスワードに更新する
		fetch('http://localhost:3000/getNewPass',{
	 	 	method: 'POST',
	  		headers: { 'Content-Type': 'application/json' },
	  		signal:signal,
	  		body: JSON.stringify({changePass:changePass})
		})
		.then(response => response.json())
		.then(data => {
  			console.log('Success:', data);
  			//成功した場合
  			resolve();
		})
		.catch((error) => {
  			console.error('Error:', error);
  			//失敗した場合
		})
			clearTimeout(timeoutId); 
			deleteForm();
			//変更後のパスに変更する
			let pass = document.getElementById('passValue');
			pass.value = changePass;
		})
		
	})
}