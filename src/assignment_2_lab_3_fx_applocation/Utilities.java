/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_2_lab_3_fx_applocation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 * @author ektasharma
 */
public class Utilities {
    public static String readFixedLengthString(int size,
      DataInput in) throws IOException {
    char[] chars = new char[size];

    for (int i = 0; i < size; i++) 
      chars[i] = in.readChar();

    String returnStr = new String(chars);
    return returnStr;
  }

    public static void writeFixedLengthString(String s, int size,
      DataOutput out) throws IOException {
    char[] chars = new char[size];

    s.getChars(0, Math.min(s.length(), size), chars, 0);

    for (int i = Math.min(s.length(), size); i < chars.length; i++)
      chars[i] = ' ';

    out.writeChars(new String(chars));
  }
}
