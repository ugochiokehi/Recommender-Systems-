package edu.carleton.comp4601.cf.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;

public class ItemBased {
	
	File file;
	private int[][] ratings;
	private int[][] newRatings;
	private String[] items;
	private String[] users;
	ArrayList<ArrayList<Integer>> visited;
	public ItemBased(File file) {
		this.file = file;
		visited = new ArrayList<ArrayList<Integer>>();
	}
	public ItemBased(int[][] r) {
		ratings = r;
	}
	
//	public  int[][] getRatings() {
//		return ratings;
//	}
	public  int[][] getNewRatings() {
		return newRatings;
	}
	public String[] getItems() {
		return items;
	}

	public String[] getUsers() {
		return users;
	}
//	
//	public void setRatings(double[][] r){
//		ratings = r.;
//	}
	public double run(){
		boolean okay = true;
		int nUsers = 5;
		int nItems = 5;
		
		users = new String[nUsers];
		items = new String[nItems];
		
		System.out.println("Item Based Normal matrix");
		Matrix normalMatrix = new Matrix(5,5);
		for(int i = 0; i< nUsers;i++){
			for(int j = 0; j< nUsers;j++){
				normalMatrix.set(i, j,ratings[i][j]); 
			}
		}
		normalMatrix.print(nUsers, 3);

		System.out.println("Item Based Similarity matrix");
		Matrix itemSimilrity = new Matrix(items.length,users.length);
		for(int i = 0; i< nUsers;i++){
			for(int j = 0; j< nItems;j++){
				itemSimilrity.set(i, j,similarity(0,4,i,j)); 
			}
		}
		itemSimilrity.print(nUsers, 3);
		double pred = prediction(4,2);
		System.out.println("Item Based Prediction: "+ pred);
		return pred;
	}
	
	
	
	public double similarity(int user, int item,int a,int b){
		return top( user, item,a ,b)/normalization(user,item,a,b);
	}
	
	public double difference(double ratings2 , float averager){
		return ratings2-averager;
	}	
	
	

	public double square(double first){
		return first*first;
	}
	
	
	
	public double top(int user,int item, int a,int b){//alice,4,0,3
		double sum = 0;
		double first = 0;
		double second = 0;
		for(int i=0;i<users.length;i++){
			if(user != i){
				//System.out.println(ratings[i][a]+" - "+averageforUsers(i,item));
				first = difference(ratings[i][a],averageforUsers(i,item));
				
				//System.out.println(ratings[i][b]+" - "+averageforUsers(i,item));
				second = difference(ratings[i][b],averageforUsers(i,item));	
				sum+= (first*second);
			}
		}
		return sum;
	}
	public double normalization(int user, int item,int a,int b){
		double sumfirst = 0;
		double sumsecond = 0;
		double first =0;
		double second = 0;
		for(int i = 0;i<users.length;i++){
			if(user!=i){
				if(ratings[i][a]!=-1 &ratings[i][b]!=-1){	
					//System.out.println(ratings[i][a]+" - "+averageforUsers(i,item));
					first = difference(ratings[i][a],averageforUsers(i,item));	
					sumfirst+=square(first);
					//System.out.println(ratings[i][b]+" - "+averageforUsers(i,item));
					second = difference(ratings[i][b],averageforUsers(i,item));	
					sumsecond+=square(second);
				}
			}
		}
		//System.out.println(sumfirst+" * "+sumsecond);
		double result = Math.sqrt(sumfirst)*Math.sqrt(sumsecond);
		
		return result;
		
	}
	//item
	public float averageforItems(int user,int item){
		float sum = 0;
		int counter = 0;
		//int y = t.get(1);
		for (int j = 0; j < users.length; j++) {
			if(j != user){
				if(ratings[j][item]!=-1){
					//System.out.println(ratings[j][item]);
					sum += ratings[j][item];
					counter++;
				}
			}
		}
		//System.out.println("sum "+sum +"count  "+counter);
		return sum/counter;
	}
	
//	public float averageforUsers(int user,int item){
//		float sum = 0;
//		int counter = 0;
//		for (int j = 0; j < items.length; j++) {
//			if(j != item){
//				if(ratings[user][j]!=-1){
//					//System.out.println(ratings[item][j]);
//					sum += ratings[user][j];
//					counter++;
//				}
//			}
//		}
//		//System.out.println("sum "+sum +"count  "+counter);
//		return sum/counter;
//	}

	public float averageforUsers(int user,int item){
		float sum = 0;
		int counter = 0;
		for (int j = 0; j < items.length; j++) {
			//if(j != item){
			if(j != item){
				if(ratings[user][j]!=-1){
					//System.out.println(ratings[item][j]);
					sum += ratings[user][j];
					counter++;
				}
			}
		}
		//System.out.println("sum "+sum +"count  "+counter);
		return sum/counter;
	}

	public ArrayList<ArrayList<Integer>> findNegatives(){
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> save;// = new ArrayList<Integer>();
		for(int i=0;i<users.length;i++){
			for(int j=0;j<items.length;j++){
				if(ratings[i][j]==-1)
				{
					save = new ArrayList<Integer>();
					save.add(i);
					save.add(j);
					result.add(save);
				}
			}
		}
		return result;
	}
	
	
	public double prediction(int user,int item){
		double above = 0;
		double below=0;
		for(int i=0;i<items.length;i++){
			if(ratings[user][i]!=-1){
				double sim = similarity(user,item,i,item);
				if(sim>0){
					System.out.println(sim+" * "+ratings[user][i]);
					above+= sim*ratings[user][i];
					below+= similarity(user,item,i,item);
				}		
			}
			
		}
		System.out.println(above+" "+below);
		
		return above/below;

	}
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("SimpleDataAccessObject\n\n");
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
//		for (int i = 0; i < items.length; i++) {
//			for (int j = 0; j < users.length; j++) {
//				if (newRatings[i][j] == -1)
//					buf.append("?");
//				else
//					buf.append(newRatings[i][j]);
//				buf.append(" ");
//			}
//			buf.append("\n");
//		}
		return buf.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {
		ItemBased sdao = new ItemBased(new File("test.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
		
//		sdao = new SimpleDataAccessObject(new File("test2.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
//		
//		sdao = new SimpleDataAccessObject(new File("test3.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
//		
	}
}


