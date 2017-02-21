package com.lcw.herakles.platform.test;

import java.io.Serializable;

public class TfUtils implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int result;
    private String expr = ""; // 存放中缀表达式

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    /*
     * (操作符1) / \ (操作符2) (操作数4) / \ (操作符3) (操作数3) / \ (操作数1) (操作数2)
     */
    private int tree1[] = new int[7];; // 存放第一棵树
    // private int tree2[]; // 存放第二棵树
    private final int PLUS = 1; // 加
    private final int MINUS = 2; // 减
    private final int MULT = 3; // 乘
    private final int DIV = 4; // 除

    /**
     * 计算24点的主函数
     */
    public void calculate(int a, int b, int c, int d) {

        int data[] = {a, b, c, d};


        // 1.用数组构建一棵树，其中0,1,3处填操作符；2,4,5,6填充操作数
        // 2.按照参数a,b,c,d不同顺序填充树，+-*/也填充
        for (int h = 0; h < 4; h++) {
            for (int i = 0; i < 4; i++) {
                if (i == h) {
                    continue;
                }
                for (int j = 0; j < 4; j++) {
                    if (j == i || j == h) {
                        continue;
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k == h || k == i || k == j) {
                            continue;
                        }
                        tree1[2] = data[h];
                        tree1[4] = data[i];
                        tree1[5] = data[j];
                        tree1[6] = data[k];

                        // 填充操作符
                        for (int m = PLUS; m <= DIV; m++) {
                            for (int n = PLUS; n <= DIV; n++) {
                                for (int o = PLUS; o <= DIV; o++) {
                                    tree1[0] = m;
                                    tree1[1] = n;
                                    tree1[3] = o;
                                    String t[] = new String[4];
                                    for (int z = 0; z < 4; z++) {
                                        switch (tree1[z]) {
                                            case PLUS:
                                                t[z] = "+";
                                                break;
                                            case MINUS:
                                                t[z] = "-";
                                                break;
                                            case MULT:
                                                t[z] = "*";
                                                break;
                                            case DIV:
                                                t[z] = "/";
                                                break;
                                        }
                                    }

                                    // 目前为止tree数组全部已赋值
                                    String postexpr = tree1[5] + " " + tree1[6] + " " + t[3] + " "
                                            + tree1[4] + " " + t[1] + " " + tree1[2] + " " + t[0];
                                    String result =
                                            CalculatorUtils.calculateReversePolish(postexpr);
                                    if (Double.parseDouble((result)) == 24.0) {
                                        expr = "(((" + tree1[5] + t[3] + tree1[6] + ")" + t[1]
                                                + tree1[4] + ")" + t[0] + tree1[2] + ")";
                                        System.out.println(expr);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // tree2 = new int[7];
        for (int h = 0; h < 4; h++) {
            for (int i = 0; i < 4; i++) {
                if (i == h) {
                    continue;
                }
                for (int j = 0; j < 4; j++) {
                    if (j == i || j == h) {
                        continue;
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k == h || k == i || k == j) {
                            continue;
                        }
                        tree1[3] = data[h];
                        tree1[4] = data[i];
                        tree1[5] = data[j];
                        tree1[6] = data[k];

                        // 填充操作符
                        for (int m = PLUS; m <= DIV; m++) {
                            for (int n = PLUS; n <= DIV; n++) {
                                for (int o = PLUS; o <= DIV; o++) {
                                    tree1[0] = m;
                                    tree1[1] = n;
                                    tree1[2] = o;
                                    String t[] = new String[3];
                                    for (int z = 0; z < 3; z++) {
                                        switch (tree1[z]) {
                                            case PLUS:
                                                t[z] = "+";
                                                break;
                                            case MINUS:
                                                t[z] = "-";
                                                break;
                                            case MULT:
                                                t[z] = "*";
                                                break;
                                            case DIV:
                                                t[z] = "/";
                                                break;
                                        }
                                    }
                                    // 目前为止tree数组全部已赋值
                                    String postexpr = tree1[4] + " " + tree1[3] + " " + t[1] + " "
                                            + tree1[6] + " " + tree1[5] + " " + t[2] + " " + t[0];
                                    String result =
                                            CalculatorUtils.calculateReversePolish(postexpr);
                                    if (Double.parseDouble((result)) == 24.0) {
                                        expr = "((" + tree1[3] + t[1] + tree1[4] + ")" + t[0] + "("
                                                + tree1[5] + t[2] + tree1[6] + "))";
                                        System.out.println(expr);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        expr = "无解";
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static void main(String[] args) {
        TfUtils tf = new TfUtils();
        tf.calculate(1, 1, 1, 1);
        System.out.println(tf.getExpr());
    }

}
