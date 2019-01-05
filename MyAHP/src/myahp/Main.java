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
public class Main {

    public static void main(String[] args) {

        Calculation calc = new Calculation();
        AHP ahp = new AHP();

        int ncriteria;

        ncriteria = calc.getN("Criteria");

        double[][] matrix = new double[ncriteria][ncriteria];
        String[] criteria = new String[ncriteria];

        double[] pairComp = new double[calc.getNumberComparison(ncriteria)]; // used to hold the values of comparisons

        criteria = calc.getCriteria(ncriteria, "Criteria");

        pairComp = calc.getComparison(ncriteria, criteria, calc.getNumberComparison(ncriteria), "goal");
        matrix = ahp.initializeMatrix(pairComp, ncriteria);
        calc.showMatrix(matrix, "comparison");

        double[] summatrixcol = new double[ncriteria];
        summatrixcol = ahp.sumColMatrix(matrix);

        double[][] matrixnorm = new double[ncriteria][ncriteria];
        matrixnorm = ahp.devidedColBySum(matrix, summatrixcol);

        double[] eigenvector = new double[ncriteria];
        eigenvector = ahp.normEigenVector(matrixnorm);

        calc.showEigenVect(criteria, eigenvector, "Criteria");

        double eigenvalue = ahp.principalEigenValue(eigenvector, summatrixcol);
        double ci = ahp.consistencyIndex(eigenvalue, ncriteria);
        double cr = ahp.consistencyRatio(ci, ncriteria);

        calc.showSummarize(eigenvalue, ci, cr);

        int nalternatives;
        nalternatives = calc.getN("Alternatives");
        double[][] matrixAlternatives = new double[nalternatives][nalternatives];

        String[] alternatives = new String[nalternatives];
        double[] pairCompAlternatives = new double[calc.getNumberComparison(nalternatives)]; // used to hold the values of comparisons

        alternatives = calc.getCriteria(nalternatives, "Alternatives");

        double[] ci_all = new double[criteria.length];
        double[] ri_all = new double[criteria.length];
        double[][] final_matrix = new double[alternatives.length][criteria.length];

        for (int i = 0; i < criteria.length; i++) {

            pairCompAlternatives = calc.getComparison(nalternatives, alternatives, calc.getNumberComparison(nalternatives), criteria[i]);
            matrixAlternatives = ahp.initializeMatrix(pairCompAlternatives, nalternatives);
            System.out.println("Matrix alternatives correspond to criteria " + criteria[i] + " : ");

            calc.showMatrix(matrixAlternatives, "comparison");

            double[] summatrixcol2 = new double[nalternatives];
            summatrixcol2 = ahp.sumColMatrix(matrixAlternatives);

            double[][] matrixnorm2 = new double[nalternatives][nalternatives];
            matrixnorm2 = ahp.devidedColBySum(matrixAlternatives, summatrixcol2);

            double[] eigenvector2 = new double[nalternatives];
            eigenvector2 = ahp.normEigenVector(matrixnorm2);
            for (int j = 0; j < eigenvector2.length; j++) {
                final_matrix[j][i] = eigenvector2[j];
            }

            calc.showEigenVect(alternatives, eigenvector2, "Alternatives");

            double eigenvalue2 = ahp.principalEigenValue(eigenvector2, summatrixcol2);
            double ci2 = ahp.consistencyIndex(eigenvalue2, nalternatives);
            double ri2 = ahp.consistencyRatio(ci2, nalternatives);

            ci_all[i] = ci2;
            ri_all[i] = ri2;
            calc.showSummarize(eigenvalue2, ci2, ri2);
        }
        double ci_total = 0.0;
        double ri_total = 0.0;
        for (int i = 0; i < ci_all.length; i++) {
            ci_total = ci_total + eigenvector[i] * ci_all[i];
            ri_total = ri_total + eigenvector[i] * ri_all[i];
        }
        calc.showMatrix(final_matrix, "final matrix comparison");

        double[] eigenvectorfinal = new double[alternatives.length];
        eigenvectorfinal = ahp.eigenVectorFinal(final_matrix, eigenvector);

        calc.showEigenVect(alternatives, eigenvectorfinal, "alternatives");

        double final_consitency;
        if (ri_total == 0) {
            final_consitency = 0;
        } else {
            final_consitency = ci_total / ri_total;
        }
        System.out.println("CR (Consistency Ratio) Total = " + final_consitency);
        if (final_consitency < 0.1) {
            System.out.println("The preference is consistent (CR total = " + final_consitency + " < " + 0.1 + ")");
        } else {
            System.out.println("Warning! the preference is not consistent, (CR total = " + final_consitency + " > " + 0.1 + "), please make pair-wise comparison more consistent!");
        }

    }
}
