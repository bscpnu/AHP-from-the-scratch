/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myahp;

/**
 *
 * @author Imam Mustafa Kamal
 * @email imamkamal@pusan.ac.kr
 */
public class AHP {

    public static double RI[] = {0.0, 0.0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};

    public double[][] initializeMatrix(double[] pairComp, int n) {
        //initialize the matrix a
        double matrix[][] = new double[n][n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else if (i < j) {
                    matrix[i][j] = pairComp[k];
                    k++;
                } else if (i > j) {
                    matrix[i][j] = 1 / matrix[j][i];
                }
            }
        }
        return matrix;
    }

    public double[] sumColMatrix(double[][] matrix) {

        double sum[] = new double[matrix.length];
        for (int i = 0; i < sum.length; i++) {
            sum[i] = 0;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sum[i] = sum[i] + matrix[j][i];
            }
        }
        return sum;
    }

    public double[][] devidedColBySum(double[][] matrix, double[] columnSum) {

        double[][] normMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                normMatrix[j][i] = matrix[j][i] / columnSum[i];
            }
        }
        return normMatrix;
    }

    public double[] normEigenVector(double[][] matrix) {

        double[] eigenVec = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            eigenVec[i] = 0;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                eigenVec[i] = eigenVec[i] + matrix[i][j];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            eigenVec[i] = eigenVec[i] / matrix.length;
        }
        return eigenVec;
    }

    public double principalEigenValue(double[] eigenVec, double[] sumColMatrix) {
        double eigenvalue = 0.0;
        for (int i = 0; i < eigenVec.length; i++) {
            eigenvalue = eigenvalue + eigenVec[i] * sumColMatrix[i];
        }
        return eigenvalue;
    }

    public double consistencyIndex(double eigenValue, int n) {
        return ((eigenValue - n)) / (n - 1);
    }

    public double consistencyRatio(double ci, int n) {
        if(RI[n-1] == 0)
            return 0;
        else
            return ci / RI[n - 1];
    }
    
    public double[] eigenVectorFinal(double[][] final_matrix, double[] eigenVec) {

        double[] eigenVecfinal = new double[final_matrix.length];
        for (int i = 0; i < eigenVecfinal.length; i++) {
            eigenVecfinal[i] = 0;
        }
        for (int i = 0; i < final_matrix.length; i++) {
            for (int j = 0; j < final_matrix[0].length; j++) {

                //final_matrix[i][j] = final_matrix[i][j]*eigenvector[j];
                eigenVecfinal[i] = eigenVecfinal[i] + final_matrix[i][j] * eigenVec[j];
            }
        }
        return eigenVecfinal;
    }
    
}
