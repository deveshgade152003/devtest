package test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Merge {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis1 = new FileInputStream (new File("F:\\test\\Sample_1.txt"));
			FileInputStream fis2 = new FileInputStream (new File("F:\\test\\Sample_2.txt"));
			FileInputStream fis3 = new FileInputStream (new File("F:\\test\\Sample_3.txt"));
			FileInputStream fis4 = new FileInputStream (new File("F:\\test\\Sample_4.txt"));
			FileInputStream[] arr = new FileInputStream[10];
			arr[0] = fis1;
			arr[1] = fis2;
			arr[2] = fis3;
			arr[3] = fis4;
			int i = 0;
			FileOutputStream fos = new FileOutputStream (new File("F:\\test\\Combined.txt"));
			
			byte[] buffer1 = new byte[1024];
			while (arr[i] != null) {
				int bytesread = 0;
				while ((bytesread = arr[i].read(buffer1)) > 0) {
					fos.write(buffer1);
				}
				i++;
				System.out.println(i);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
