package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.*;
import static java.util.Collections.sort;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        while (tests-- > 0) {
            int distance = 0;
            ArrayList<Integer> houses = new ArrayList<>();
            int nrOfhouses = scanner.nextInt();
            for (int house = 0; house < nrOfhouses; house++) {
                Integer address = scanner.nextInt();
                houses.add(address);
            }
            sort(houses);
            long median = houses.get(nrOfhouses/2);
            if(nrOfhouses%2==0){
                median=round((median+houses.get(nrOfhouses/2-1))/2.0);
            }
            for (Integer h : houses) {
                distance += abs(h - median);
            }
            System.out.println(distance);
        }
    }
}