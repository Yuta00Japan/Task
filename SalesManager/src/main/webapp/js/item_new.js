/**
 * 商品管理画面の処理
 */
const controller = new AbortController();
const signal = controller.signal;

// タイムアウトを設定
const timeoutId = setTimeout(() => {
  console.log("Request timed out");
  controller.abort(); // リクエストを中止する
}, 60000); // 60秒

window.addEventListener('load',()=>{
	setTime(); 
	setCategory();
	decimalDelete();
})


/**
 * 集計期間の開始終了時間をセットする
 */
function setTime(){
	
	let time = new Date();
	let year = time.getFullYear();
	let month = time.getMonth();
	
	
	let start = document.getElementById('txtStartDate');
	let end = document.getElementById('txtEndDate');
	
	let lastDate = new Date(year,month+1,0);
	
	start.value=`${year}/${month+1}/1`;
	end.value=`${year}/${month+1}/${lastDate.getDate()}`;
	
	
}


/**
 * DBからカテゴリーを読み込みセットする
 */
function setCategory(){
	
	
	let category = '';
	
	let option ='<option value=""></option>';
	
	new Promise(resolve =>{
		
		category =  document.getElementById('category');
		
		resolve();
		
	}).then(()=>{
				
		fetch('http://localhost:3000/setCategory',{signal})
		.then(response => response.json())
		.then(data =>{
			console.log('load success');
			for(let i = 0; i < data.length; i++){
				option += '<option value="'+data[i].categoryID+'">'+data[i].category+'</option>';
			}
		
			category.innerHTML = option;
		
		})
	})

}

/**
 * 小数点以下の表示を削除する
 */
function decimalDelete(){
	
	let num = document.querySelectorAll('.number');
	
	let value = '';
	for(let i = 0; i < num.length; i++){
		value = num[i].textContent;
		value  = value.split('.');
		value = value[0];
		num[i].textContent = value;
	}
}


let submitBtn = document.getElementById('search');

submitBtn.addEventListener('click',function(event){formCheck(event)});


/**
 * 入力された日付が有効かどうかを判定する
 */
function isValidDate(dateString) {
  
  const pattern = /^\d{4}\/\d{1,2}\/\d{1,2}$/;
  if (!pattern.test(dateString)) {
    return false; // 日付の形式が正しくない場合はfalseを返す
  }
  
  const [year, month, day] = dateString.split('/');
  const date = new Date(year, month - 1, day);
  return date.getFullYear() == year && date.getMonth() == month - 1 && date.getDate() == day;
}

/**
 * 抽出ボタンをクリックした際に入力内容をチェックする
 */
function formCheck(event){
	
	let start = document.getElementById('txtStartDate');
	let end = document.getElementById('txtEndDate');
	
	//集計期間を使用しない検索の場合は検証を行わない
	if(start.value !="" && start.value != null && end.value !="" && end.value != null){
		console.log('検証開始');
		//入力された日付が有効かどうかを検証
		if(isValidDate(start.value)){
			console.log('集計開始日検証終了');
		}else{
			event.preventDefault();
			alert('正しい形式で入力してください');
			return ;
		}
		
		if(isValidDate(end.value)){
			cnosole.log('集計終了日検証終了');
		}else{
			event.preventDefault();
			alert('正しい形式で入力してください');
			return ;
		}
		
	}else{
		start.value="";
		end.value="";
	}
	
}




