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
		console.log(result);
		res.json(result);
	});
});



