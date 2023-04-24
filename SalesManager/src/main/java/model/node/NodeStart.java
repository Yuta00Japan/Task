package model.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * NODEサーバーを起動する為のクラス
 * <br>
 * 基本スレッドで実行する
 * @author yuta
 *
 */
public class NodeStart extends Thread{

	/**
	 * スレッド
	 */
	public void run() {
		try {
		    ProcessBuilder pb = new ProcessBuilder("node", "../../../Users/yuta/git/Task/SalesManager/src/main/webapp/node/main.js");
		    Process p = pb.start();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		    
		    int exitCode = p.waitFor();
		    System.out.println("node mysql :"+exitCode);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
}
