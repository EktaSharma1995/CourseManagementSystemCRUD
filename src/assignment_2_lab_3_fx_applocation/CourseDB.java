/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_2_lab_3_fx_applocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.scene.control.Alert;

/**
 *
 * @author ektasharma
 */
public class CourseDB {

    private static String fileName = "Course.dat";
    public static final int RECORDSIZE = 76;

    /**
     * add Course 
     * @param rec
     * @throws Exception 
     */
    public static void writeCourseToFile(Course rec) throws Exception {
        try {
            RandomAccessFile outFile = new RandomAccessFile(fileName, "rw");
            int numberOfRecords = (int) outFile.length() / RECORDSIZE;

            outFile.seek(RECORDSIZE * numberOfRecords);

            Utilities.writeFixedLengthString(rec.getTitle(), 30, outFile);
            outFile.writeInt(rec.getCredits());
            outFile.writeDouble(rec.getFee());
            outFile.writeInt(rec.getCourseNumber());

            outFile.close();

        } catch (FileNotFoundException fnex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(fnex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        } catch (IOException ioex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(ioex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        } catch (ArithmeticException ae) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(ae.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }
    }

    /**
     * find course
     * @param recordNumber
     * @return
     * @throws Exception 
     */
    public Course readCourseFromFile(int recordNumber) throws Exception {
        try {

            RandomAccessFile readFromFile = new RandomAccessFile(fileName, "r");
            int numberOfRecords = (int) readFromFile.length() / RECORDSIZE;        
            
            int current = 0;
            Course course = null;
            
            while (current < readFromFile.length()) {
                readFromFile.seek(current);
                String title = Utilities.readFixedLengthString(30, readFromFile);
                int credits = readFromFile.readInt();
                double fee = readFromFile.readDouble();
                int courseNum = readFromFile.readInt();

                current += RECORDSIZE;

                if (recordNumber == courseNum) {
                   course = new Course(courseNum, title, credits, fee);
                   break;
                } 
            }
            return course;
        } catch (IllegalArgumentException Iex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(Iex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();

        } catch (Exception ex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(ex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }

        return null;
    }

    /**
     * update course
     * @param course
     * @param numberOfRecords 
     */
    public void updateToFileAsFixedLength(Course course, int numberOfRecords) {
        try {
            RandomAccessFile readWriteFile = new RandomAccessFile(fileName, "rw");
            if ((numberOfRecords) <= readWriteFile.length() / RECORDSIZE) {
                readWriteFile.seek(RECORDSIZE * (numberOfRecords - 1));
                Utilities.writeFixedLengthString(course.getTitle(), 30, readWriteFile);
                readWriteFile.writeInt(course.getCredits());
                readWriteFile.writeDouble(course.getFee());

                readWriteFile.close();
            }

        } catch (FileNotFoundException fnfex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(fnfex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();

        } catch (IOException ioex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(ioex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }
    }

    /**
     * read next course
     * @param recordNo
     * @return
     * @throws Exception 
     */
    public static Course readNextCourseFromFile(int recordNo) throws Exception {
        try {

            RandomAccessFile readNextFromFile = new RandomAccessFile(fileName, "r");
            int numberOfRecords = (int) readNextFromFile.length() / RECORDSIZE;
            if (recordNo < numberOfRecords) {
                readNextFromFile.seek(RECORDSIZE * (recordNo));

                String title = Utilities.readFixedLengthString(30, readNextFromFile);
                int credits = readNextFromFile.readInt();
                double fee = readNextFromFile.readDouble();
                int courseNumber = readNextFromFile.readInt();

                Course course = new Course(courseNumber, title, credits, fee);

                return course;

            } else {
                throw new IllegalArgumentException("This is the last record");
            }
        } catch (IllegalArgumentException Iex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(Iex.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(e.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }

        return null;

    }
    
    /**
     * read previous course
     * @param recordNo
     * @return
     * @throws Exception 
     */
    public static Course readPreviousCourseFromFile(int recordNo) throws Exception {
        try {

            RandomAccessFile readPreviousFromFile = new RandomAccessFile(fileName, "r");
            int numberOfRecords = (int) readPreviousFromFile.length() / RECORDSIZE;

            recordNo = recordNo - 1;
            if (recordNo < numberOfRecords) {
                readPreviousFromFile.seek(RECORDSIZE * (recordNo - 1));

                String title = Utilities.readFixedLengthString(30, readPreviousFromFile);
                int credits = readPreviousFromFile.readInt();
                double fee = readPreviousFromFile.readDouble();
                int courseNumber = readPreviousFromFile.readInt();;

                Course course = new Course(courseNumber, title, credits, fee);

                return course;

            } else {
                throw new IOException("This is the first record");
            }
        } catch (IOException Iex) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText("This is the first record");
            msg.setHeaderText("Error Box");
            msg.showAndWait();

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(e.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }

        return null;

    }
    
    /**
     * read first course
     * @return
     * @throws Exception 
     */
    public static Course readFirstCourseFromFile() throws Exception {
        try {

            RandomAccessFile readFirstFromFile = new RandomAccessFile(fileName, "r");

            readFirstFromFile.seek(0);

            String title = Utilities.readFixedLengthString(30, readFirstFromFile);

            int credits = readFirstFromFile.readInt();
            double fee = readFirstFromFile.readDouble();
            int courseNumber = readFirstFromFile.readInt();

            Course course = new Course(courseNumber, title, credits, fee);

            return course;

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(e.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }

        return null;

    }
    
    /**
     * read last course
     * @return
     * @throws Exception 
     */
    public static Course readLastCourseFromFile() throws Exception {
        try {

            RandomAccessFile readLastFromFile = new RandomAccessFile(fileName, "r");
            int numberOfRecords = (int) readLastFromFile.length() / RECORDSIZE;

            readLastFromFile.seek(RECORDSIZE * (numberOfRecords - 1));

            String title = Utilities.readFixedLengthString(30, readLastFromFile);
            int credits = readLastFromFile.readInt();
            double fee = readLastFromFile.readDouble();
            int courseNumber = readLastFromFile.readInt();

            Course course = new Course(courseNumber, title, credits, fee);

            return course;

        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText(e.getMessage());
            msg.setHeaderText("Error Box");
            msg.showAndWait();
        }

        return null;

    }
}
