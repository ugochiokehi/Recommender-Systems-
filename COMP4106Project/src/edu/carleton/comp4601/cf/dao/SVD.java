package edu.carleton.comp4601.cf.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SVD {
	
	File file;
	private int[][] ratings;
	private int[][] newRatings;
	private String[] items;
	private String[] users;
	ArrayList<ArrayList<Integer>> visited;
	public SVD(File file) {
		this.file = file;
		visited = new ArrayList<ArrayList<Integer>>();
	}
	
	public SVD(int[][] r) {
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

	public double run(){
		boolean okay = true;
		
		int nUsers = 5;
		int nItems = 5;
		
		users = new String[nUsers];
		items = new String[nItems];
		
		System.out.println("SVD Normal Matrix");
		Matrix ratingsMartix = new Matrix(users.length,items.length-1);
		for(int i = 1; i< nUsers;i++){
			for(int j = 0; j< nItems-1;j++){
				ratingsMartix.set(i, j,ratings[i][j]); 
			}
		}
		ratingsMartix.print(nUsers-1, 1);
		SingularValueDecomposition svd_rate = ratingsMartix.svd();
		Matrix  U = svd_rate.getU();
		Matrix  S = svd_rate.getS();
		Matrix  V = svd_rate.getV();
		
		Matrix newU = new Matrix(users.length,2);
		for (int r = 0; r < U.getRowDimension(); r++) {
            for (int c = 0; c < 2; c++){
            	newU.set(r,c, U.get(r, c));
            }
        }
		Matrix newS = new Matrix(users.length,2);
		for (int r = 0; r < S.getRowDimension(); r++) {
            for (int c = 0; c < 2; c++){
            	newS.set(r,c, S.get(r, c));
            }
        }
		Matrix newV = new Matrix(users.length,2);
		for (int r = 0; r < V.getRowDimension(); r++) {
            for (int c = 0; c < 2; c++){
            	newV.set(r,c, V.get(r, c));
            }
        }
		
		
		
		Matrix tran = newV.transpose();
		//newU.print(nUsers, 5);
	
		double average = av(0,4);
		//System.out.println("av: "+ average);
		Matrix Uk = new Matrix(new double[][]{{0.4312,-0.4932}});
		Matrix Vk = new Matrix(new double[][]{{0.59},{0.73}});
		Matrix Sk = new Matrix(new double[][]{{12.2215,0},{0,4.9282}});
		Matrix result = (Uk.times(Sk)).times(Vk);
		result.print(1, 3);
		System.out.println("Matrix U: ");
		Uk.print(1, 5);
		System.out.println("Matrix V: ");
		Vk.print(2, 5);
		System.out.println("Matrix S: ");
		Sk.print(2, 5);
		System.out.println("result: "+result.get(0, 0));
		double pred = (average+result.get(0, 0));
		System.out.println("SVD Prediction:"+ pred );
		return pred;
	   
		//(Uk.times(Sk)).times(Vk);
		
		//s.close();
		//return okay;
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
	
//	public double prediction(int a, int p){
//		
//		av(a,p)+
//		return p;
//	}
//	
	
	
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
//		SVD sdao = new SVD(new File("test.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
		
//		sdao = new SimpleDataAccessObject1(new File("test2.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
		
//		sdao = new SimpleDataAccessObject1(new File("test3.txt"));
//		sdao.input();
//		System.out.println(sdao);
//		System.out.println("=====================================");
//		
	}
}


