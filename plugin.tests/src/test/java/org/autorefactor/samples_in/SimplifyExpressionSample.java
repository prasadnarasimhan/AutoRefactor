/*
 * AutoRefactor - Eclipse plugin to automatically refactor Java code bases.
 *
 * Copyright (C) 2013-2014 Jean-Noël Rouvignac - initial API and implementation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program under LICENSE-GNUGPL.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution under LICENSE-ECLIPSE, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.autorefactor.samples_in;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SimplifyExpressionSample {

    private static final String NULL_CONSTANT = null;

    private boolean addedToMakeCodeFail(boolean b1, boolean b2, Object o) {
        return !b1 && b2 && o != null && addedToMakeCodeFail(b1, b2, o);
    }

    public void removeUselessNullCheck(String s) {
        {
            // Remove redundant null checks
            boolean b1 = s != null && "".equals(s);
            boolean b2 = s != null && "".equalsIgnoreCase(s);
            boolean b3 = s != null && s instanceof String;
        }
        {
            // Remove redundant null checks
            boolean b1 = null != s && "".equals(s);
            boolean b2 = null != s && "".equalsIgnoreCase(s);
            boolean b3 = null != s && s instanceof String;
        }
        {
            // Remove redundant left / write hand side operands
            boolean b3 = true && s != null;
            boolean b4 = false && s != null;
            boolean b5 = true || s != null;
            boolean b6 = false || s != null;
        }
    }

    public void doNotRemoveNullCheck(String s) {
        {
            // Do not remove non redundant null checks
            boolean b1 = s != null && s.equals(NULL_CONSTANT);
            boolean b2 = s != null && s.equalsIgnoreCase(NULL_CONSTANT);
        }
        {
            // Do not remove non redundant null checks
            boolean b1 = null != s && s.equals(NULL_CONSTANT);
            boolean b2 = null != s && s.equalsIgnoreCase(NULL_CONSTANT);
        }
        {
            // Right-hand-side left unchanged because left-hand-side can have
            // side effects
            boolean b3 = s != null && true;
            boolean b4 = s != null && false;
            boolean b5 = s != null || true;
            boolean b6 = s != null || false;
        }
        {
            // Right-hand-side left unchanged because left-hand-side can have
            // side effects
            boolean b3 = null != s && true;
            boolean b4 = null != s && false;
            boolean b5 = null != s || true;
            boolean b6 = null != s || false;
        }
    }

    public void fixCompareToUsage() {
        boolean b;
        final String s = "";

        // valid, do no change these ones
        b = s.compareTo("") < 0;
        b = s.compareTo("") <= 0;
        b = s.compareTo("") == 0;
        b = s.compareTo("") != 0;
        b = s.compareTo("") >= 0;
        b = s.compareTo("") > 0;
        b = s.compareToIgnoreCase("") == 0;

        // invalid, refactor them
        b = s.compareTo("") == -1;
        b = s.compareTo("") != -1;
        b = s.compareTo("") != 1;
        b = s.compareTo("") == 1;
        b = s.compareToIgnoreCase("") == 1;
    }

    public void borderLineParenthezisedExpressions(Integer i) throws Exception {
        // do not replace any because they are in a String concatenation
        String s1 = ((Number) i).doubleValue() + " ";
        String s2 = (i instanceof Number) + " ";
        String s3 = (i + 0) + " ";
        String s4 = (i == null ? null : "i") + " ";
        // do not replace
        long l1   = 2 + (i == null ? 0 : i);
        long l2   = (i != null && i == 0) ? 0 : i;

        // replace
        boolean b1 = ((Number) i).doubleValue() == 0;
        // replace
        boolean b2 = (i instanceof Number);
        // do not replace
        boolean b3 = (i + 0) == 0;
        // do not replace
        Collection<?> c = null;
        Object obj = ((List<?>) c).get(0);
        // do not replace
        boolean b4 = !(i instanceof Number);
        boolean b5 = !(b4 = false);
        // replace
        boolean b6 = (i != null);
        // replace
        boolean b7 = b5 && (i != null);
        // do not replace
        boolean b8 = b1 ? (b2 ? b3 : b4) : (b5 ? b6 : true);
        boolean b9 = b1 ? (b2 = true) : (b3 = true);
        boolean b10 = b1 ? (i instanceof Number) : (i instanceof Object);
        final Random rand = new Random();
        boolean b11 =  (i = rand.nextInt()) != i + 1;
        boolean b12 = ((i = rand.nextInt()) != i + 1) && ((i = rand.nextInt()) != i + 1);
    }

    public boolean doNotReplaceParenthesesAroundAssignmentInCondition(Reader reader, char[] cbuf, int c) throws IOException {
        // such expressions are used a lot in while conditions
        return -1 != (c = reader.read(cbuf));
    }

    public boolean removeUselessParentheses() throws Exception {
        boolean b = (true);
        int i;
        Collection<?> col = (null);
        i = (0);
        int[] ar = new int[(i)];
        ar = new int[] { (i) };
        ar[(i)] = (i);
        if ((b)) {
            throw (new Exception());
        }
        do {
        } while ((b));
        while ((b)) {
        }
        for (Object obj : (col)) {
        }
        for (i = 0; (b); i++) {
        }
        synchronized ((col)) {
        }
        switch ((i)) {
        case (0):
        }
        if ((col) instanceof Collection) {
        }
        return ((b));
    }

    public int removeUselessParenthesesInStatements(int i) {
        int j = (i);
        j = (i);
        if ((j == 0)) {
            removeUselessParenthesesInStatements((i));
        }
        do {
            i++;
        } while ((i == 0));
        while ((i == 0)) {
            i++;
        }
        return (i);
    }

    public void removeUselessParenthesesWithAssociativeOperators(boolean b1,
            boolean b2, boolean b3) {
        System.out.println(b1 && (b2 && b3));
        System.out.println(b1 || (b2 || b3));
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        System.out.println(i1 * (i2 * i3));
        System.out.println(i1 + (i2 + i3));
        System.out.println(i1 & (i2 & i3));
        System.out.println(i1 | (i2 | i3));
        System.out.println(i1 ^ (i2 ^ i3));
    }

    public void doNotRemoveParenthesesWithNonAssociativeOperators(int i1,
            int i2, int i3) {
        System.out.println(i1 - (i2 - i3));
        System.out.println(i1 / (i2 / i3));
    }

    public void doNotRemoveParenthesesDueToOperatorsPriority(int i1,
            int i2, int i3) {
        System.out.println((i1 + i2) / i3);
    }

    public void simplifyPrimitiveBooleanExpression(boolean b) {
        if (b == true) {
            int i = 0;
        }
        if (b != false) {
            int i = 0;
        }
        if (b == false) {
            int i = 0;
        }
        if (b != true) {
            int i = 0;
        }
        if (b == Boolean.TRUE) {
            int i = 0;
        }
        if (b != Boolean.FALSE) {
            int i = 0;
        }
        if (b == Boolean.FALSE) {
            int i = 0;
        }
        if (b != Boolean.TRUE) {
            int i = 0;
        }
    }

    public void simplifyBooleanWrapperExpression(Boolean b) {
        if (b == true) {
            int i = 0;
        }
        if (b != false) {
            int i = 0;
        }
        if (b == false) {
            int i = 0;
        }
        if (b != true) {
            int i = 0;
        }
    }

    public void doNotSimplifyBooleanWrapperExpression(Boolean b) {
        if (b == Boolean.TRUE) {
            int i = 0;
        }
        if (b != Boolean.FALSE) {
            int i = 0;
        }
        if (b == Boolean.FALSE) {
            int i = 0;
        }
        if (b != Boolean.TRUE) {
            int i = 0;
        }
    }

    public boolean addParenthesesToMixedAndOrBooleanOperators(int i, boolean b1, boolean b2, boolean b3) {
        if (i == 0) {
            return b1 && b2 || b3;
        }
        return b1 || b2 && b3;
    }

    public int addParenthesesToMixedBitwiseOperators(int b1, int b2, int b3) {
        int i = b1 & b2 | b3;
        int j = b1 | b2 & b3;
        int k = b1 << b2 | b3;
        int l = b1 | b2 << b3;
        int m = b1 >> b2 | b3;
        int n = b1 | b2 >> b3;
        int o = b1 >>> b2 | b3;
        int p = b1 | b2 >>> b3;
        return i + j + k + l + m + n + o + p;
    }
}
