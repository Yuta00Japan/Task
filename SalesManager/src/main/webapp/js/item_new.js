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

let submitBtn = document.getElementById('search');

submitBtn.addEventListener('click',function(event){formCheck(event)});

function formCheck(event){
	
	let start = document.getElementById('txtStartDate');
	let end = document.getElementById('txtEndDate');
	
	//入力されたデータが時間に関するものか検証
	
	/** 日付を数字のみで入力する場合　20230502　or 2023502 202352*/
	let timePattern1 = /[1-9][0-9]{5,6,7}/;
	/**文字で区切って入力する場合  2023/05/02  2023/5/2*/
	let timePattern2 = /[1-9][0-9]{3}\/[0-9]{1,2}\/[0-9]{1,2}/;
	
	if(timePattern1.test(start.value) && timePattern1.test(end.value)){
		
	}else if(timePattern2.test(start.value) && timePattern2.test(end.value)){
		
	}else{
		event.preventDefault();
		alert('正しい形式で入力してください　例）2023年5月2日ー＞　20230502 or 202352  or 2023/05/02 or 2023/5/2');
	}
	
}


