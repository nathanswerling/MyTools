package com.nathan.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class IO {
	private static Logger logger = Logger.getLogger(IO.class.getName());

	public static Boolean dump(String text, String fileName) {

		Boolean success = write(text, fileName, false);
		return success;
	}

	/**
	 * write(String text, String fileName, Boolean append) is used by dump() and append() to do the
	 * actual work. If the Boolean parameter "append" is true, the text will be appended to the given
	 * file. If "append" is false the file will be overwritten with text. 
	 */
	private static Boolean write(String text, String fileName, Boolean append) {
		Boolean success = false;
		File directory = new File(fileName).getParentFile();
		if (directory != null) {
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (directory.exists()) {
				try {
					BufferedWriter bfwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName), append)));
					bfwriter.write(text);
					if (append) {
						bfwriter.write("\n");
					}
					bfwriter.close();
				}
				catch (FileNotFoundException exception) {
					logger.severe("Couldn't open File " + fileName);
				}
				catch (IOException exception) {
					logger.severe("Could not write to File " + fileName);
				}
			} else {
				logger.fine("Could not create directory: " + directory.getAbsolutePath());
			}
		}
		return success;
	}

	public static String fileToString(File file) {

		// Stream to read file
		FileInputStream fin = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// Open an input stream
			fin = new FileInputStream(file);

			// Read a line of text
			BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
			while (reader.ready()) {
				buffer.append(reader.readLine());
				if (reader.ready()) {
					buffer.append("\n");
				}
			}
		}
		catch (IOException e) {
			logger.severe("Unable to read from file" + file.getAbsolutePath());
		}
		finally {
			// Close our input stream
				try {
					fin.close();
				} catch (IOException e) {
					logger.fine(e.getMessage());
				}
		}
		return buffer.toString();
	}

	public static File getFile (String text) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);
		dlg.setText(text);
		String fileName = dlg.open();
		display.dispose();
		return new File(fileName);
	}

	public static File getDirectory (String text) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		DirectoryDialog dlg = new DirectoryDialog(shell, SWT.OPEN);
		dlg.setText(text);
		String fileName = dlg.open();
		display.dispose();
		return new File(fileName);
	}

}
