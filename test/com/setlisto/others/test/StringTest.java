package com.setlisto.others.test;

public class StringTest {

	public static void main(String[] args) {
		int times = 50000;
		String a = "";
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < times; i++) {
			a = a + " Hola que tal";
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(times + " [+]-> " + (end - start) + " ms");
		
		
		StringBuilder sb = new StringBuilder();
		start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			sb = sb.append(" Hola que tal");
		}
		end = System.currentTimeMillis();
		System.out.println(times + " [StringBuilder] -> " + (end - start) + " ms");

	}

}

