/**
 *node expressのメイン部分 
 */

const express = require('express');
const mysql = require('mysql2');
const LocalStorage = require('node-localstorage').LocalStorage;
const localStorage = new LocalStorage('./scratch');
const net = require('net');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());
//8080へのアクセス許可を設定する
app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:8080');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});


const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '1013UmeAs5013TrueFalse',
  database: 'salesmanager'
});

const port= 3000; 

const isPortInUse = (port) => {
  return new Promise((resolve, reject) => {
     const tester = net.createServer((socket) => {})
      .on('error', (err) => {
        if (err.code === 'EADDRINUSE') {
          resolve(true);
        } else {
          reject(err);
        }
      })
      .on('listening', () => {
        tester.close();
        resolve(false);
      })
      .listen(port);
  });
  
}

// ポートが使用中でなければサーバーを起動
isPortInUse(port)
  .then(inUse => {
    if (inUse) {
      console.log(`Port ${port} is already in use.`);
    } else {
      const server= app.listen(port, () => {
        console.log(`Server is running on port ${port}`);
      });
      
      //タイムアウトしないように設定
      server.setTimeout(0);
    }
  })
  .catch(err => {
    console.error(err);
  });
  
 
/**
 * 所属を検索し返す
 */
app.get('/setBranch',(req,res)=>{
	connection.query('select BranchID,BranchName from MST_Branch',(err,result,field)=>{
		if (err) throw err;
		res.json(result);
	});
});
/**
 * 権限を検索し返す
 */
app.get('/setRole',(req,res)=>{
	connection.query('select roleNo,roleName from MST_Role ',(err,result,field)=>{
		if (err) throw err;
		console.log(result);
		res.json(result);
	});
});

/**
 *部署を検索し返す
 */
app.get('/setDepartment',(req,res)=>{
	connection.query('select DepartmentID,DepartmentName from MST_Department',(err,result,field)=>{
		if(err) throw err;
		res.json(result);
	});
});
/**
 * 従業員IDを格納する
 */
app.post('/setEmpId',(req,res)=>{
	let empId = req.body.empId;
	console.log("from setEmpId "+empId);
	localStorage.setItem('empId',empId);
});
/**
 * 現在編集中のパスワードを取得する
 */
app.get('/getPass',(req,res)=>{
	let empId = Number(localStorage.getItem('empId'));
	console.log("from getPass  "+empId);
	connection.query(`select password from MST_Employee where empId=${empId}`,(err,result,field)=>{
		if (err) throw err;
		console.log(result);
		res.json(result);
	})
});
/**
 * パスワードを変更する
 */
app.post('/getNewPass',(req,res)=>{
	console.log('now /getNewPass');
	let chPassword = req.body.changePass;
	let empId = Number(localStorage.getItem('empId'));
	console.log('new　password'+ chPassword + '->　empID '+ empId);
	connection.query(`update MST_Employee set password='${chPassword}' where empId=${empId}`,(err,result,field)=>{
		if (err) throw err;
		console.log("from getNewPass"+result);
		
	});
});
/**
 * empNoが重複してないかチェックする
 */
app.post('/judgeEmpNo',(req,res)=>{
	console.log('from / judgeEmpNo');
	let empNo = req.body.empNo;
	empNo = Number(empNo);
	connection.query(`select empId from MST_Employee where empNo=${empNo}`,(err,result,field)=>{
		if (err) throw err;
		console.log(result);
		res.json(result);
	});
	
});

/**
 * 上司IDと上司名が一致しているかどうかを検証する
 */
app.post('/checkBoss',(req,res)=>{
	console.log('from /checkBoss');
	let bossId = req.body.bossId;
	bossId=Number(bossId);
	console.log(bossId);
	connection.query(`select fullName from MST_Employee where empId=${bossId}`,(err,result,field)=>{
		if (err) throw err;
		console.log(" result "+result);
		res.json(result);
	})
})

/**
 * 編集中の従業員がシステム管理者かどうかを確認＋ほかにシステム管理者がいるかどうかをチェックする
 */
app.get('/allUserRole',(req,res)=>{
	
	connection.query('select userRole from MST_Employee',(err,result,field)=>{
		if (err) throw err;
		console.log(result);
		
	});
});

app.post('/getUserRole',(req,res)=>{
	console.log('from getUserRole');
	let empId = req.body.empId;
	empId=Number(empId);
	connection.query(`select userRole from MST_Employee where empId=${empId}`,(err,result,field)=>{
		if (err) throw err;
		console.log(result)
		res.json(result);
	});
});

