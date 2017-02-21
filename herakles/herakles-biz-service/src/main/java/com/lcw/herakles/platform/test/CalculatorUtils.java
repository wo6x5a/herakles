package com.lcw.herakles.platform.test;

import java.util.Stack;

public class CalculatorUtils {

    /**
     * 计算后缀表达式
     */
    public static String calculateReversePolish(String str) {

        String[] splitStr = str.split(" ");
        Stack<String> s = new Stack<String>();
        for (int i = 0; i < splitStr.length; i++) {
            String ch = splitStr[i];
            if (ch.matches("\\d+.\\d+") || ch.matches("\\d+")) {
                s.push(ch);
            } else {
                if (s.size() >= 2) {
                    String c1 = s.pop();
                    String c2 = s.pop();
                    if (ch.equals("+")) {
                        if (c1.contains(".") || c2.contains(".")) {
                            s.push(String.valueOf(
                                    (Double.parseDouble(c2 + "") + Double.parseDouble(c1 + ""))));
                        } else {
                            s.push(String.valueOf(
                                    (Integer.parseInt(c2 + "") + Integer.parseInt(c1 + ""))));
                        }

                    } else if ("-".equals(ch)) {
                        if (c1.contains(".") || c2.contains(".")) {
                            s.push(String.valueOf(
                                    (Double.parseDouble(c2 + "") - Double.parseDouble(c1 + ""))));
                        } else {
                            s.push(String.valueOf(
                                    (Integer.parseInt(c2 + "") - Integer.parseInt(c1 + ""))));
                        }
                    } else if ("*".equals(ch)) {
                        if (c1.contains(".") || c2.contains(".")) {
                            s.push(String.valueOf(
                                    (Double.parseDouble(c2 + "") * Double.parseDouble(c1 + ""))));
                        } else {
                            s.push(String.valueOf(
                                    (Integer.parseInt(c2 + "") * Integer.parseInt(c1 + ""))));
                        }
                    } else if ("/".equals(ch)) {
                        s.push(String.valueOf(
                                (Double.parseDouble(c2 + "") / Double.parseDouble(c1 + ""))));
                    }

                } else {
                    System.out.println("式子有问题!");
                    return null;
                }
            }
        }
        return s.pop();
    }
}
