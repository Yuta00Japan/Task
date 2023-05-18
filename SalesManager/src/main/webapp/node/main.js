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
 * カテゴリーをセットする
 */
app.get('/setCategory',(req,res)=>{
	console.log('from setCategory');
	connection.query('select categoryID,category from MsCategory',(err,result,field)=>{
		if (err) throw err;
		res.json(result);
	});
	
});

/**
 * 現在編集中の従業員パスワードを取得する
 */
app.post('/getPass',(req,res)=>{
	let empId = Number(req.body.empId);
	let sql = `select password from mst_employee where empid=${empId}`;
	console.log(sql)
	connection.query(sql,(err,result,field)=>{
		if(err) throw err;
		res.json(result);
	});
	
});
/**
 * 現在編集中の従業員のパスワードを変更する
 */
app.post('/setNewPass',(req,res)=>{
	console.log('now /getNewPass');
	let chPassword = req.body.changePass;
	let empId = Number(req.body.empId);
	console.log('new　password'+ chPassword + '　empID '+ empId);
	connection.query(`update MST_Employee set password='${chPassword}' where empId=${empId}`,(err,result,field)=>{
		if (err) throw err;
		
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
		res.json(result);
	})
})

/**
 * 編集中のユーザーのほかにシステム管理者がいるかどうかをチェックする
 */
app.post('/allUserRole',(req,res)=>{
	console.log('form allUserRole');
	let empId= req.body.empId;
	empId=Number(empId);
	connection.query(`select userRole from MST_Employee where empId !=${empId} and enable=true`,(err,result,field)=>{
		if (err) throw err;
		res.json(result);
	});
});

/**
 * 受け取ったEMPIDをもとにそのIDの従業員権限を取得する
 */
app.post('/getUserRole',(req,res)=>{
	console.log('from getUserRole');
	let empId = req.body.empId;
	empId=Number(empId);
	connection.query(`select userRole from MST_Employee where empId=${empId} `,(err,result,field)=>{
		if (err) throw err;
		res.json(result);
	});
});

app.post('/setMajorName',(req,res)=>{
	console.log('from setMajorName');
	let parentId = req.body.parentId;
	console.log("parentID value : "+parentId);
	parentId = Number(parentId);
	console.log(parentId);
	connection.query(`select shouhin01Name from MST_shouhin01 where shouhin01ID=${parentId}`,(err,result,field)=>{
		if (err) throw err;
		res.json(result);
	});
});



