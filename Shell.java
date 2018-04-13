/*
Shell.java
@author: Pavel Samsonov

A shell class which can run other threads
-Use & to run the threads concurrenctly
-use ; to wait for prior threads to finish before running any new threads
*/

import java.util.*;

public class Shell extends Thread {

	public static void main(String[] args){}

	public Shell(){}

	public int executeCommand(String[] args){

		int nc = 0; //number of commands to run
		int i = 0;
		int start = 0;
		//parse the recieved string[] of commands into seperate commands and run them
		while(i < args.length){

				if(args[i].equals("&")){
					SysLib.exec(copySubSection(start, i - 1, args));
					start = i + 1;
					nc++;
				}

				if(i == (args.length - 1)){
					SysLib.exec(copySubSection(start, i, args));
					nc++;
				}
			i++;
		}

		return nc;

	}

	public void run(){
		int n = 1;
		//don't stop until program is closed
		while(true){
			String cd = "shell[" + n + "]%"; //display message
			StringBuffer params = new StringBuffer();
			SysLib.cout(cd);
			SysLib.cin(params);
			String[] pta = SysLib.stringToArgs(params.toString());
			
			if(params.toString().equals("exit")){
				break;
			}
			
			int i = 0;
			int start = 0;
			int nc = 0; // number of run commands
			//keep running until we find a ; or hit the end of the string
			while(i < pta.length){
				if(pta[i].equals(";")){
					nc += executeCommand(copySubSection(start, i - 1, pta));
					start = i + 1;
					//bad variable names
					for(int ohHiDoggie = 0; ohHiDoggie < nc; ohHiDoggie ++) SysLib.join();
					nc = 0;

				}
				if(i == pta.length - 1){
					nc += executeCommand(copySubSection(start, i, pta));
				}


				i++;
			}
			n++;
			//wait for all commands to finish
			for(int ohHiMark = 0; ohHiMark < nc; ohHiMark ++)			
				SysLib.join();
			nc = 0;		
		}

		SysLib.exit();

	}

	//copy a subsection of an array into a new array from start index to end index inclusive
	public String[] copySubSection(int start, int end, String[] orig){
		String[] temp = new String[end-start + 1];

		//SysLib.cout("Start: " + start + " End: " + end + " Length: " + orig.length);
		int n = 0;
		for(int i = start; i <= end; i++){
			//SysLib.cout(" I :" + i);
			temp[n] = orig[i];
			n++;
		}
	
		return temp;

	}


}
