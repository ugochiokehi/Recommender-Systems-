package edu.carleton.comp4601.cf.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SlopeOne {
	
	File file;
	private int[][] ratings;
	private int[][] newRatings;
	private String[] items;
	private String[] users;
	ArrayList<ArrayList<Integer>> visited;
	public SlopeOne(File file) {
		this.file = file;
		visited = new ArrayList<ArrayList<Integer>>();
	}
	public SlopeOne(int[][] r) {
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

	public boolean run() throws FileNotFoundException {
		boolean okay = true;
		
		int nUsers = 5;
		int nItems = 5;
		
		users = new String[nUsers];
		items = new String[nItems];
		

		System.out.println("pred: "+pred(2, 0));
		//s.close();
		return okay;
	}
		
	public double av(int index,int y){//ArrayList<Integer> t){
		float sum = 0;
		int counter = 0;
		//int y = t.get(1);
		for (int j = 0; j < items.length; j++) {
			if(j != y){
				if(ratings[index][j]!=-1){
					sum += ratings[index][j];
					counter++;
				}
			}
		}
		return sum/counter;
	}
	

	public double[] findAvg(int a, int b){
		double sum = 0;
		int count=0;
		for(int i = 0; i<users.length; i++){
			if(ratings[i][a]>0 & ratings[i][b]>0){
				System.out.println(" "+ratings[i][a]+" - "+ratings[i][b]);
				sum+=(ratings[i][a]-ratings[i][b]);
				count+=1;
			}
		}
		double result = sum/count;
		System.out.println("sum: "+sum);
		double[] n =new double[]{result,count};
		return n;
		
	}
	
	public double pred(int x, int y){
		double below = 0;
		double above = 0;
		for(int i=0;i<items.length;i++){
			if(y!=i){
				double[] res = findAvg(y,i);
				System.out.println("res: "+res[0]);
				System.out.println("count: "+res[1]);
				above += res[1]*(ratings[x][i]+res[0]);
				below+= res[1];
				
			}
		}
		double result = above/below;
		
		return result;
		
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
//		SlopeOne sdao = new SlopeOne(new File("test.txt"));
////		sdao.input();
////		System.out.println(sdao);
////		System.out.println("=====================================");
//		
//		sdao = new SlopeOne(new File("test2.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
////		
//		sdao = new SimpleDataAccessObject1(new File("test3.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
//		
	}
}


