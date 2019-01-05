/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myahp;

import java.util.Scanner;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */
public class Calculation {

    public int ncriteria;
    public int nalternatives;

    Scanner keyboard = new Scanner(System.in);

    public void setCriteriaLength(int ncriteria) {
        this.ncriteria = ncriteria;
    }

    public int getCriteriaLength() {
        return this.ncriteria;
    }

    public void setNalternatives(int nalternatives) {
        this.nalternatives = nalternatives;
    }

    public int getNalternatives() {
        return this.nalternatives;
    }

    public int getNumberComparison(int n) {
        return (n * n - n) / 2;
    }

    public int getN(String label) {
        int n;
        System.out.println("Enter the number of " + label + " (n)");
        System.out.print("n = ");
        n = keyboard.nextInt();
        return n;
    }

    public String[] getCriteria(int n, String label) {
        String[] criteria = new String[n];
        System.out.println("Enter the " + label + " name (without space) : ");
        for (int i = 0; i < n; i++) {
            System.out.print(label + " " + (i + 1) + ": ");
            criteria[i] = keyboard.next();
        }
        return criteria;
    }

    public double[] getComparison(int n, String[] criteria, int no_comparison, String label) {
        double[] pairComp = new double[no_comparison];
        System.out.println("\nEnter the comparison with respect to the " + label);
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                System.out.print("Compare " + criteria[i] + " with " + criteria[j] + ": ");
                pairComp[m] = keyboard.nextDouble();
                m++;
            }
        }
        return pairComp;
    }

    public void showMatrix(double[][] matrix, String label) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("\nThe matrix " + label + " is :");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(String.format("%,.4f", matrix[i][j]) + "\t\t");
            System.out.println();
        }
    }

    public void showEigenVect(String[] criteria, double[] eigenvec, String label) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Eigen Vector (wieght of each " + label + ") :");
        for (int i = 0; i < criteria.length; i++)
            System.out.println(label + " " + criteria[i] + " = " + eigenvec[i] + " (" + (Math.floor((eigenvec[i] * 100)) / 100) * 100 + "%)");
    }

    public void showSummarize(double eigenvalue, double ci, double cr) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Eigen Value (Alpha Max) = " + eigenvalue);
        System.out.println("CI (Consistency Index)  = " + ci);
        System.out.println("CR (Consistency Ratio)  = " + cr);
        if (cr < 0.1) {
            System.out.println("The preference is consistent (CR = " + cr + " < " + 0.1 + ")");
        } else {
            System.out.println("Warning! the preference is not consistent, (CR = " + cr + " > " + 0.1 + ") please make pair-wise comparison more consistent!");
        }
        System.out.println("---------------------------------------------------------------------------");
    }

}
