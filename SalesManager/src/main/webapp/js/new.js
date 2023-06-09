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
	setRole(); 
	setBranch();
	setDepartment();
});

/**
 * 役割をセットする
 */
function setRole(){
	
	fetch('http://localhost:3000/setRole')
	.then(response => response.json())
	.then(data =>{
		let userRole = document.getElementById('role');
		//ユーザーロールの値を取得
		let role = document.getElementById('userRole').value;
		console.log(role.value);
		let roleNum = role.split("");
		roleNum = roleNum.reverse();
		//配列にして順番を反転させる 0000000001 -> 1000000000
		
		let roleIndex = [];
		let index = 0;
		//1のインデックスを保存する
		for(let i = 0; i < roleNum.length; i++){
			console.log(role[i]);
			if(roleNum[i]=="1"){
				console.log('match1');
				roleIndex[index]= i;
				index++;
			}
		}
		
		console.log('roleIndex length :'+roleIndex.length);
			
		let content = '';
		for(let i = 0; i < data.length; i++){
			//一つも権限がない場合
			if(roleIndex.length <= 0){
				content += data[i].roleName+'<input type="checkbox" class="checkbox" name="role" value="'+data[i].roleNo+'" id="'+data[i].roleNo+'">';
			}else{
					
				for(let k = 0; k < roleIndex.length; k++){
					console.log(roleIndex[k]+" == "+(i));
					if(roleIndex[k]==(i)){
						console.log("match2");
						content += data[i].roleName+'<input type="checkbox" class="checkbox" name="role" value="'+data[i].roleNo+'" id="'+data[i].roleNo+'" checked>';
						break;
					}
					//最後まで回り切った場合
					if((k+1) == roleIndex.length){
						content += data[i].roleName+'<input type="checkbox" class="checkbox" name="role" value="'+data[i].roleNo+'" id="'+data[i].roleNo+'">';
					}
				}
			}
		
			if((i +1) % 2 == 0 ){
				content += '<br>';
			}
		}
		userRole.innerHTML = content;
	});
}


 /*
  * 所属と従業員ごとに初期選択をセットする
  */
 function setBranch(){
	 
	 var branchValue = document.getElementById('branchValue').value;
	 console.log("branchValue "+branchValue);
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
	 console.log("departmentValue "+departmentValue);
	 
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
 
 const form = document.getElementById('Main');
 console.log("form tagName " +form.tagName);
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
	
	//チェックボックスを編集不可にする
	let checkbox = document.querySelectorAll('.checkbox');
	for(let i = 0; i < checkbox.length; i++){
		checkbox[i].disabled=true;
	}
	//リセットボタンを無効にする
	document.getElementById('reset').disabled=true;
	
	
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
	
	//チェックボックスを編集不可にする
	let checkbox = document.querySelectorAll('.checkbox');
	for(let i = 0; i < checkbox.length; i++){
		checkbox[i].disabled=false;
	}
	//リセットボタンを無効にする
	document.getElementById('reset').disabled=false;
	
	let table = document.getElementById('passTable');
	let button1 = document.getElementById('passButton1');
	let button2 = document.getElementById('passButton2');
	
	let error = document.getElementById('passwordError');
	
	table.innerHTML="";
	button1.innerHTML="";
	button2.innerHTML="";
	error.textContent ="";
}

/**現在編集中の従業員のパスワードを変更する 
 * 
*/
 function changePassword(){
	/**変更後パスワード */
	let changePass = document.getElementById('chPassword').value;
	/**確認パスワード */
	let confirmPass = document.getElementById('confirmPass').value;
	/**エラーメッセージ表示要素 */
	let error = document.getElementById('passwordError');
	
	let empId = document.getElementById('empId').textContent;
	
	//パスワードと確認が同じかどうかを確認
	if(changePass != confirmPass){
		error.textContent = '新規パスワードと確認が不一致';
		return ;
	}
	let pattern = /^[a-zA-Z0-9]+$/;
	//半角英数字出ない場合
	if(!(pattern.test(changePass)) || !(pattern.test(confirmPass))){
		error.textContent='半角英数字で入力してください';
		return ;
	}
	
	//現在編集中の従業員IDを送信しパスワードを取得する
	return new Promise(resolve =>{
		
		fetch('http://localhost:3000/getPass',{
	  		method: 'POST',
	  		headers: { 'Content-Type': 'application/json' },
	  		signal:signal,
	  		body: JSON.stringify({empId:empId})
		})
		.then(response => response.json())
		.then(data => {
  			resolve(data[0].password);
		})
	}).then((truePass)=>{
		return new Promise(resolve=>{
				console.log('from checkPassword '+truePass+" -> "+changePass);
  			if(truePass == changePass) {
    			error.textContent = '前回と同じパスワードは設定できません';
    			return ;
  			}else{
				resolve();	  
			}
		})
	}).then(()=>{
		return new Promise(resolve =>{
			//新しい従業員パスワードに更新する
		fetch('http://localhost:3000/setNewPass',{
	 	 	method: 'POST',
	  		headers: { 'Content-Type': 'application/json' },
	  		signal:signal,
	  		body: JSON.stringify({changePass:changePass,empId:empId})
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
			deleteForm();
			//変更後のパスに変更する
			let pass = document.getElementById('passValue');
			pass.value = changePass;
		})
		
	})
}

let submitButton= document.getElementById('submit');

submitButton.addEventListener('click',function(event){formCheck(event)});

let error = document.getElementById('formError');

/**
 * フォームの入力内容を確認する。
 */
function formCheck(event){
	//一度画面遷移を停止
	event.preventDefault();
	
	console.log("from form check empID: "+document.getElementById('empId').textContent);
	
	let pattern =  /^[a-zA-Z0-9]+$/;
	
	let loginId = document.getElementById('loginId').value;
	let password = document.getElementById('passValue').value;
	let mail = document.getElementById('mail').value;
	
	let empId = document.getElementById('empId').textContent;
	let empNo = document.getElementById('empNo').value;
	
	//上司IDと上司名が一致するかどうかをチェックする
	let bossId = document.getElementById('bossId').textContent;
	let bossName = document.getElementById('bossName').value;
	
	let userRole = '';
	
	//新規登録
		if(empId==0 || empId== null || empId==""){
			console.log('process # add')
			document.getElementById('submit').value="add"
		}else{
	//編集
			console.log('process # update');
			document.getElementById('submit').value="update";
		}
	
	//もしどちらか入力されていなければ
	if(loginId=="" || password==""){
		event.preventDefault();
		error.textContent ='ログインID・パスワードは必須です';
		return ;
	}
	//半角英数字出なければ
	if(!(pattern.test(loginId)) || !(pattern.test(password))){
		event.preventDefault();
		error.textContent = '半角英数字で入力してください';
		return ;
	}
	//mailが半角英数字と記号でない場合
	let pattern2 = /^[!-~]*$/;
	if(!pattern2.test(mail)){
		event.preventDefault();
		error.textContent='mailは半角英数字・記号で入力してください';
		return ;
	}
	//上司名が入力されていなければBOSSIDを削除する
	if(bossName=="" || bossName ==null){
		bossId ="";
	}else{
		document.getElementById('bossName').value=bossId;
	}
	console.log("bossId value: "+bossId);
	//入力されたEMPNOを送信し重複してないかを判定する
	return new Promise(resolve =>{

		fetch('http://localhost:3000/judgeEmpNo',{
			method: 'POST',
	  		headers: { 'Content-Type': 'application/json' },
	  		signal:signal,
	  		body: JSON.stringify({empNo:empNo})
		})
		.then(response => response.json())
		.then(data =>{
			//empNoで検索し得たempIDが一致しなければ登録、一致すれば更新、もし被った場合エラーを出す
			if(!data.length <= 0){
				
				if(!(Number(data[0].empId) == Number(empId))){
					error.textContent='そのempNoはすでに登録されています';
				}else{
					error.textContent='';
					resolve();
				}
			}else{
				error.textContent='';
				resolve();
			}
		})
		
	}).then(()=>{
		/**入力されている上司名が正しいかチェックする */
		return new Promise(resolve =>{
			
		 	fetch('http://localhost:3000/checkBoss',{
				method :'POST',
				headers: { 'Content-Type': 'application/json' },
	  			signal:signal,
	  			body: JSON.stringify({bossId:bossId})
			})
			.then(response => response.json())
			.then(data =>{
				//上司名が違っていた場合
				if(data.length != 0){
					console.log("bossName value: "+data[0].fullName);
					if(!(data[0].fullName == bossName)){
						error.textContent='上司名が不一致です';
					}else{
						error.textContent='';
						resolve();
					}
				}else{
					resolve();
				}
			})	
		})
		
	}).then(()=>{
		//新規登録の場合ここからさきは不要
		if(document.getElementById('submit').value == 'add'){
			return ;
		}
		//システム管理者の情報を変更する場合　システム管理者がほかにいるかどうかをチェック
		
		//まず編集中の従業員権限情報を取得する
		return new Promise(resolve =>{
			
			fetch('http://localhost:3000/getUserRole',{
				method :'POST',
				headers: { 'Content-Type': 'application/json' },
	  			signal:signal,
	  			body: JSON.stringify({empId:empId})
			})
			.then(response => response.json())
			.then(data =>{
				userRole = data[0].userRole;
				//システム管理者かどうかをチェックする
				console.log(userRole.charAt(9));
				if(userRole.charAt(9)== '1'){
					console.log('システム管理者');
					resolve();
				}else{
					console.log('非システム管理者');
					//フォーム送信を再開させる
					document.querySelector('#Main').requestSubmit(submitButton);
				}
			})
		})
	}).then(()=>{
		//checkboxのIDがチェックされているか確認
		let system = document.getElementById('1');
		
		//チェックされていなければほかにシステム管理者がいるかどうかを確認する
		if(!system.checked){
			
			fetch('http://localhost:3000/allUserRole',{
				method:'POST',
				headers: { 'Content-Type': 'application/json' },
	  			signal:signal,
	  			body: JSON.stringify({empId:empId})
			})
			.then(response => response.json())
			.then(data =>{
				
				console.log(data.length);
				for(let i = 0; i < data.length; i++){
					
					let output = data[i].userRole;
					console.log(output);
					if(output.charAt(9)=="1"){
						//フォーム送信を再開させる
						document.querySelector('#Main').requestSubmit(submitButton);
						break;
					}
					//最後までシステム管理者と一致しなかった場合エラーを出す
					if(i== (data.length-1)){
						error.textContent='他システム管理者が存在しないため変更不可';
					}
				}
				
			})
		}else{
			
			//フォーム送信を再開させる
			document.querySelector('#Main').requestSubmit(submitButton);
		}
		
	});
}
/**
--削除チェック　モーダル処理---
*/
const deleteBtn = document.getElementById('delete');

const modal = document.getElementById('easyModal');

const cancel = document.getElementById('cancel');

const buttonClose = document.getElementsByClassName('modalClose')[0];
/**
 * 削除ボタンをクリックしたときにモーダルを作成する
 */
 deleteBtn.addEventListener('click', () => {
	 console.log('モーダル起動');
     modal.style.display = 'block';
    
 });

// バツ印がクリックされた時
buttonClose.addEventListener('click', modalClose);

//Noボタンが押された場合
cancel.addEventListener('click',modalClose);

function modalClose() {
  modal.style.display = 'none';
}

/**削除モーダルのOKボタン要素 */
let deleteOKBtn = document.getElementById('exeDelete');

/**
 * 削除モーダルのOKボタンがクリックされたら
 */

deleteOKBtn.addEventListener('click',function(event){deleteCheck(event)});

/**
 * 削除確認を行う
 */
function deleteCheck(event){
	
	console.log('削除確認を開始');
	
	let empId = document.getElementById('empId').textContent;
	
	//画面遷移を停止
	event.preventDefault();
	
	let userRole='';
	
	//システム管理者の情報を変更する場合　システム管理者がほかにいるかどうかをチェック
		
		//まず編集中の従業員権限情報を取得する
		return new Promise(resolve =>{
			
			fetch('http://localhost:3000/getUserRole',{
				method :'POST',
				headers: { 'Content-Type': 'application/json' },
	  			signal:signal,
	  			body: JSON.stringify({empId:empId})
			})
			.then(response => response.json())
			.then(data =>{
				userRole = data[0].userRole;
				//システム管理者かどうかをチェックする
				console.log(userRole.charAt(9));
				if(userRole.charAt(9)== '1'){
					console.log('システム管理者');
					resolve();
				}else{
					console.log('非システム管理者');
					//フォーム送信を再開させる
					document.querySelector('#Main').requestSubmit(deleteOKBtn);
				}
			})
		}).then(()=>{
			//システム管理者の場合はほかにシステム管理者がいるかどうかをチェック
			
			fetch('http://localhost:3000/allUserRole',{
				method:'POST',
				headers: { 'Content-Type': 'application/json' },
	  			signal:signal,
	  			body: JSON.stringify({empId:empId})
			})
			.then(response => response.json())
			.then(data =>{
				
				for(let i = 0; i < data.length; i++){
					
					let output = data[i].userRole;
					if(output.charAt(9)=="1"){
						//フォーム送信を再開させる
						document.querySelector('#Main').requestSubmit(deleteOKBtn);
						break;
					}
					//最後までシステム管理者と一致しなかった場合エラーを出す
					if(i== (data.length-1)){
						error.textContent='他システム管理者が存在しないため削除不可';
						modalClose();
					}
				}
				
			})
		
	});
}



