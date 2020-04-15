package M_ExposurePath;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Init {
	int N = 20;
	double wOfField = 100;
	double hOfField = 100;
	double Rmax = 14;
	double deltaS = 0.5;
	double x1 = 100, x2 = 100;
	
	public void InitRandom() throws IOException {
		final Random R = new Random();
		PrintWriter writer1 = null;
		// double c = 1.2;
		writer1 = new PrintWriter(new File("C:\\Users\\20161\\Desktop\\BTL_TTTH\\src\\M_ExposurePath\\20.txt"));
		writer1.write(N + "\n");
		for (int i = 0; i < N; i++) {
			double x11 = 200, y11 = 100, r11 = Rmax;

			while (x11 >= wOfField && y11 >= hOfField) {
				x11 = R.nextDouble() + R.nextInt((int) wOfField);
				y11 = R.nextDouble() + R.nextInt((int) hOfField);
				r11 = R.nextDouble() + R.nextInt((int) Rmax);
			}

			Sensor sensor1 = new Sensor(x11, y11, r11);
			writer1.write(x11 + " " + y11 + " " + r11 + "\n");
			
		}

		double temp1 = wOfField, temp2 = wOfField;

		while (temp1 >= wOfField && temp2 >= wOfField) {
			temp1 = R.nextDouble() + R.nextInt((int) wOfField);
			temp2 = R.nextDouble() + R.nextInt((int) wOfField);

		}

		double a_temp = 0, b_temp = a_temp + deltaS;
		int check1 = 1, check2 = 1;
		for (int i = 0; i < wOfField; i++) {
			if (temp1 >= a_temp && temp1 <= b_temp) {
				x1 = (Math.abs(temp1 - a_temp) >= Math.abs(temp1 - b_temp)) ? b_temp : a_temp;
				check1 = 0;
			}
			if (temp2 >= a_temp && temp2 <= b_temp) {
				x2 = (Math.abs(temp2 - a_temp) >= Math.abs(temp2 - b_temp)) ? b_temp : a_temp;
				check2 = 0;
			}
			a_temp = b_temp;
			b_temp = b_temp + deltaS;
			if (check1 == 0 && check2 == 0)
				break;
		}

		double x8 = 0;
		writer1.write(x1 + " " + x8 + "\n");
		writer1.write(x2 + " " + hOfField);

		writer1.flush();
		writer1.close();

	}

	public static void main(final String[] args) {
		final Init app = new Init();
		try {
			app.InitRandom();
		} catch (final Exception e) {
			System.out.println("Exception duoc xu ly");
		}
	}
}
