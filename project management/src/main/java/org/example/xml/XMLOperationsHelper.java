package org.example.xml;

import java.io.IOException;
import java.io.RandomAccessFile;

public class XMLOperationsHelper {
    public static final String START_SYMBOL = "<";
    public static final String END_SYMBOL = ">";
    public static final String CLOSE_SYMBOL = "</";
    public static final String OPEN_ID = "<id>";
    public static final String CLOSE_ID = "</id>";

    public static boolean checkEndsWithDocumentsTag(RandomAccessFile file, String endTag) throws IOException {
        if (file.length() < endTag.length() + 1) {
            return false;
        }
        // Skip empty lines
        long currentPosition = file.length() - endTag.length();

        // Skip empty lines before the endTag
        while (currentPosition >= 0) {
            file.seek(currentPosition);
            byte currentByte = file.readByte();

            // Skip newline characters
            while (currentByte == '\n' || currentByte == '\r') {
                currentPosition--;
                if (currentPosition < 0) {
                    // Reached the beginning of the file without finding the end tag
                    return false;
                }
                file.seek(currentPosition);
                currentByte = file.readByte();
            }

            // Check for the end tag
            file.seek(currentPosition);
            byte[] buffer = new byte[endTag.length()];
            file.readFully(buffer);
            String currentString = new String(buffer);

            if (currentString.equals(endTag)) {
                // Found the end tag
                return true;
            } else {
                // Move to the previous position for the next iteration
                currentPosition--;
            }
        }
        return false;
    }

    public static void moveToPreviousLine(RandomAccessFile file) throws IOException {
        long currentPosition = file.getFilePointer();
        if (currentPosition == 0) {
            return; // Already at the beginning of the file
        }

        // Move to the previous character until a newline character is found
        do {
            currentPosition--;
            file.seek(currentPosition);
        } while (file.readByte() != '\n');

        // Move one more character to start of the previous line
        file.seek(currentPosition);
    }
}
