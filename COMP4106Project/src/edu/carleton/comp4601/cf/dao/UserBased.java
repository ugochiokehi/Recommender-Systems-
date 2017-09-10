package edu.carleton.comp4601.cf.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;

public class UserBased {
	
	File file;
	private int[][] ratings;
	private int[][] newRatings;
	private String[] items;
	private String[] users;
	ArrayList<ArrayList<Integer>> visited;
	public UserBased(File file) {
		this.file = file;
		visited = new ArrayList<ArrayList<Integer>>();
	}
	public UserBased(int[][] r) {
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

	public boolean input() throws FileNotFoundException {
		boolean okay = true;
		
		Scanner s = new Scanner(file);
		int nUsers = s.nextInt();
		int nItems = s.nextInt();
		
		users = new String[nUsers];
		for (int i = 0; i < nUsers; i++)
			users[i] = s.next();
		items = new String[nItems];
		for (int j = 0; j < nItems; j++)
			items[j] = s.next();
		
		ratings = new int[nUsers][nItems];
		for (int i = 0; i < nUsers; i++) {
			for (int j = 0; j < nItems; j++) {
				ratings[i][j] = s.nextInt();
			}
		}
		System.out.println("Similarity matrix");
		Matrix similarityMatrix = new Matrix(users.length,users.length);
		for(int i = 0; i< nUsers;i++){
			for(int j = 0; j< nUsers;j++){
				similarityMatrix.set(i, j,similarity(i,j,4)); 
			}
		}
		similarityMatrix.print(nUsers, 3);
	
		System.out.println("item Similarity matrix");
		Matrix itemSimilrity = new Matrix(items.length,users.length);
		for(int i = 0; i< nItems;i++){
			for(int j = 0; j< nUsers;j++){
				itemSimilrity.set(i, j,similarity2(i,j,0,4)); 
			}
		}
		itemSimilrity.print(nUsers, 3);
		
		for(ArrayList<Integer>t: findNegatives()){
			System.out.println("prediction users: "+prediction(t.get(0),t.get(1)));
		}

		s.close();
		return okay;
	}
	
	public double run(){
		boolean okay = true;
		int nUsers = 5;
		int nItems = 5;
		users = new String[nUsers];
		items = new String[nItems];
		System.out.println("User Based Normal matrix");
		Matrix normalMatrix = new Matrix(5,5);
		for(int i = 0; i< nUsers;i++){
			for(int j = 0; j< nUsers;j++){
				normalMatrix.set(i, j,ratings[i][j]); 
			}
		}
		normalMatrix.print(nUsers, 3);
		System.out.println("User Based Similarity matrix");
		Matrix similarityMatrix = new Matrix(5,5);
		for(int i = 0; i< nUsers;i++){
			for(int j = 0; j< nUsers;j++){
				similarityMatrix.set(i, j,similarity(i,j,4)); 
			}
		}
		similarityMatrix.print(nUsers, 3);

		//int pred = 
		for(ArrayList<Integer>t: findNegatives()){
			double pred = prediction(t.get(0),t.get(1));
			System.out.println("User Based Prediction: "+prediction(t.get(0),t.get(1)));
			return pred;
			
		}
		return nItems;

		
	}
	
	
	public double similarity(int a, int b,int p){
		return top( a, b, p)/normalization(a, b, p);
	}
	public double similarity2(int a, int b,int x,int y){
		return top2( a, b, x,y)/normalization2(a, b, x,y);
	}
	
	public float difference(float ratings2 , float averager){
		return ratings2-averager;
	}	
	
	public double normalization(int a, int b,int p){
		float sumA = 0;
		float sumB = 0;
		float avA = average(a,p);
		float avB = average(b,p);
		for (int j = 0; j < items.length; j++) {
			if(j!=p){
				if(ratings[a][j] != -1){
					sumA+= square(difference(ratings[a][j], avA));
				}
				if(ratings[b][j] != -1){
					sumB+= square(difference(ratings[b][j], avB));	
				}
			}
		}		
		double newA = Math.sqrt(sumA);
		double newB = Math.sqrt(sumB);		
		return newA*newB;
	}
	public double normalization2(int a, int b,int x,int y){
		float sumA = 0;
		float sumB = 0;
		//float avA = averageforUser(a,p);
		//float avB = averageforUser(b,p);
		for (int j = 0; j < users.length; j++) {
			if(j!=x){
				if(ratings[j][a] != -1){
					sumA+= square(difference(ratings[j][a], averageforUser(j,y)));
				}
				if(ratings[j][b] != -1){
					sumB+= square(difference(ratings[j][b], averageforUser(j,y)));	
				}
			}
		}		
		double newA = Math.sqrt(sumA);
		double newB = Math.sqrt(sumB);		
		return newA*newB;
	}
	public float square(float a){
		return a*a;
	}
	
	public float top(int a, int b,int p){
		float avA =  average(a,p);
		float avB =  average(b,p);
		float sum = 0;
		float first = 1;
		float second = 1;
		for (int j = 0; j < items.length; j++) {
			if(j != p){
				if(ratings[a][j] != -1){
					 first = difference(ratings[a][j], avA);
				}
				if(ratings[b][j] != -1){
					 second = difference(ratings[b][j], avB);
				}
				sum+= first*second;
			}
		}
		return sum;
	}
	public float top2(int a, int b,int x,int y){
		//float avA =  averageforUser(a,p);
		//float avB =  averageforUser(b,p);
		float sum = 0;
		float first = 1;
		float second = 1;
		for (int j = 0; j < users.length; j++) {
			if(j != x){
				if(ratings[j][a] != -1){
					 first = difference(ratings[j][a], averageforUser(j,y));
				}
				if(ratings[j][b] != -1){
					 second = difference(ratings[j][b], averageforUser(j,y));
				}
				sum+= first*second;
			}
		}
		return sum;
	}
	//item
	public float average(int index,int y){//ArrayList<Integer> t){
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
	//user
	public float averageforUser(int index,int y){//ArrayList<Integer> t){
		float sum = 0;
		int counter = 0;
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
	
	
	public double prediction(int a, int p){
//		int x = pred.get(0);
//		int p = pred.get(1);
		double avgA = average(a,p);
		double sumUp = 0;
		double sumDown = 0;
		for(int b=0; b <users.length;b++){
			if(b != a){
				float res = average(b,p);
				float diff = difference(ratings[b][p], res);
				double simi = similarity(a, b,p);
				if(!(diff < 0 || simi < 0)){
					sumUp += simi* diff;
				}
			}
		}
		for(int b=0; b<users.length;b++){
			if(similarity(a, b,p )>0){
				sumDown += similarity(a, b,p);
			}
		}
//		System.out.println("sumup "+ sumUp);
//		System.out.println("sumDOWN "+ sumDown);
		double result = avgA + (sumUp/sumDown);
		return result;
	}
	
	public double prediction2(int a, int x){
//		int x = pred.get(0);
//		int p = pred.get(1);
		//double avgA = average(a,p);
		double sumUp = 0;
		double sumDown = 0;
		for(int b=0; b <items.length;b++){
			if(b != x){
				double simi = similarity2(b, x,a,x);
				if(!(ratings[a][b]<0 || simi < 0)){
					sumUp += simi* ratings[a][b];
				}
			}
		}
		for(int b=0; b<items.length;b++){
			if(similarity2(b, x,a,x )>0){
				sumDown += similarity2(b, x,a,x);
			}
		}
//		System.out.println("sumup "+ sumUp);
//		System.out.println("sumDOWN "+ sumDown);
		double result =  (sumUp/sumDown);
		return result;
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
		UserBased sdao = new UserBased(new File("test.txt"));
		sdao.input();
		System.out.println(sdao);
		System.out.println("=====================================");
		
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


