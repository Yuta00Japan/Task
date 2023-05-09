/**
 * 登録、削除、変更時のmodal処理を担当する
 */


const newBtn = document.getElementById('newBtn');


//処理内容に応じて変化させる
var okBtn = document.getElementById('ok');

const modal = document.getElementById('easyModal');
const cancel = document.getElementById('cancel');
const buttonClose = document.getElementsByClassName('modalClose')[0];

//更新ボタン削除ボタンにイベントを付与する
window.addEventListener('load',()=>{
	
		var updateBtn=document.querySelectorAll('.update');
		var deleteBtn= document.querySelectorAll('.delete');
	
		console.log(updateBtn.length);

		for(let i = 0; i < updateBtn.length; i++){
			console.log('event 付与')
			updateBtn[i].addEventListener('click',function(event){updateAndDelete(event,'update',i)});
			deleteBtn[i].addEventListener('click',function(event){updateAndDelete(event,'delete',i)});
		}
	
})

/**
 * 更新時と削除時の動作を付与する
 */
function updateAndDelete(event,method,index){
	
	event.preventDefault();
	var form = document.querySelectorAll('.form');
	
	var updateBtn=document.querySelectorAll('.update');
	var deleteBtn= document.querySelectorAll('.delete');
	
	modal.style.display='block';
	
	let select = '';
	console.log(index);
	
	//更新処理の場合
	if(method=='update'){
		 document.getElementById('msg').textContent='更新してよろしいですか？';
		 select =  updateBtn[index];
	}else{
	//削除処理の場合
		 document.getElementById('msg').textContent='削除してよろしいですか？';
		 select = deleteBtn[index];
	}
	
     okBtn.addEventListener('click',()=>{
		form[index].requestSubmit(select);
	})
}


newBtn.addEventListener('click', (event) => {
	 console.log('モーダル起動');
	 event.preventDefault();
	 //画面遷移を停止
	 document.getElementById('msg').textContent='登録してよろしいですか？';
     modal.style.display = 'block';
     
     okBtn.addEventListener('click',()=>{
		document.querySelector('#newForm').requestSubmit(newBtn);
	})
    
});

// バツ印がクリックされた時
buttonClose.addEventListener('click', modalClose);

//Noボタンが押された場合
cancel.addEventListener('click',modalClose);

function modalClose() {
  modal.style.display = 'none';
}
