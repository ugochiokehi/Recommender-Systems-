package edu.carleton.comp4601.cf.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class RfPredictor {
	
	File file;
	private int[][] ratings;
	private int[][] newRatings;
	private String[] items;
	private String[] users;
	ArrayList<ArrayList<Integer>> visited;
	public RfPredictor(File file) {
		this.file = file;
		visited = new ArrayList<ArrayList<Integer>>();
	}
	
	public RfPredictor(int[][] r) {
		ratings = r;
	}
	
	public  int[][] getRatings() {
		return ratings;
	}
	public  int[][] getNewRatings() {
		return newRatings;
	}
	public String[] getItems() {
		return items;
	}

	public String[] getUsers() {
		return users;
	}

	public boolean run(){
		boolean okay = true;
		
//		Scanner s = new Scanner(file);
//		int nUsers = s.nextInt();
//		int nItems = s.nextInt();
//		
//		users = new String[nUsers];
//		for (int i = 0; i < nUsers; i++)
//			users[i] = s.next();
//		items = new String[nItems];
//		for (int j = 0; j < nItems; j++)
//			items[j] = s.next();
//		
//		ratings = new int[nUsers][nItems];
//		for (int i = 0; i < nUsers; i++) {
//			for (int j = 0; j < nItems; j++) {
//				ratings[i][j] = s.nextInt();
//			}
//		}

		int nUsers = 5;
		int nItems = 5;
		
		users = new String[nUsers];
		items = new String[nItems];
		
//		System.out.println("popus: "+getPopularbyuser(0));
//		System.out.println("popit: "+getPopularbyitems(2));
		System.out.println("freq: "+freq(0,2));
		
		return okay;
	}
		
	public int freq(int user,int item){
		double max = 0;
		int result = 0;
		for(int i =1;i<=5;i++){
			double currmax =(frequser(user,i)*freqitem(item,i));
			if(currmax>max){
				max=currmax;
				result = i;
			}
		}
		return result;
		
	}
	
	public double freqitem(int item,int v){
		int count = 0;
		for(int i = 0;i<users.length;i++){
			if(ratings[i][item] == v){
				count+=1;
			}
		}
		return count;
		
	}
	public double frequser(int user,int v){
		int count = 0;
		for(int i = 0;i<items.length;i++){
			if(ratings[user][i]==v){
				count+=1;
			}
		}
		return count;
		
	}
	public double newFreq(int user,int item){
		double max = 0;
		for(int i =1;i<=5;i++){
			double currmax =(frequser(user,i)*freqitem(item,i));
			if(currmax>max){
				max=currmax;
			}
		}
		return max;
		
	}
	public int getPopularbyuser(int user)
	{
	  int count = 1, tempCount;
	  int popular = ratings[user][0];
	  int temp = 0;
	  for (int i = 0; i < (items.length ); i++)
	  {
	    temp = ratings[user][i];
	    tempCount = 0;
	    for (int j = 1; j < items.length; j++)
	    {
	      if (temp == ratings[user][j])
	        tempCount++;
	    }
	    if (tempCount > count)
	    {
	      popular = temp;
	      count = tempCount;
	    }
	  }
	  return popular;
	}
	public int getPopularbyitems(int item)
	{
	  int count = 1, tempCount;
	  int popular = ratings[0][item];
	  int temp = 0;
	  for (int i = 0; i < (users.length ); i++)
	  {
	    temp = ratings[i][item];
	    tempCount = 0;
	    for (int j = 1; j < users.length; j++)
	    {
	      if (temp == ratings[j][item])
	        tempCount++;
	    }
	    if (tempCount > count)
	    {
	      popular = temp;
	      count = tempCount;
	    }
	  }
	  return popular;
	}
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("SimpleDataAccessObject1\n\n");
		for (String u : users) {
			buf.append(u);
			buf.append(" ");
		}
		buf.append("\n");
		for (String i : items) {
			buf.append(i);
			buf.append(" ");
		}		
		buf.append("\n");
		for (int i = 0; i < users.length; i++) {
			for (int j = 0; j < items.length; j++) {
				if (ratings[i][j] == -1)
					buf.append("?");
				else
					buf.append(ratings[i][j]);
				buf.append(" ");
			}
			buf.append("\n");
		}
		buf.append("\n");
//		System.out.println("users length: "+users.length);
//		System.out.println("items length: "+items.length);
//		System.out.println("newRatings length: "+ newRatings.length);
		
		return buf.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {
//		RfPredictor sdao = new RfPredictor(new File("test.txt"));
////		sdao.input();
////		System.out.println(sdao);
////		System.out.println("=====================================");
//		
////		sdao = new SimpleDataAccessObject3(new File("test2.txt"));
////		sdao.input();
////		System.out.println(sdao);
////		System.out.println("=====================================");
//////		
//		sdao = new RfPredictor(new File("test3.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
//		
	}
}


