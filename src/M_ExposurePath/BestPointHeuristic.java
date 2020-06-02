package M_ExposurePath;

import java.util.*;
import java.lang.*;
import java.io.*;

public class BestPointHeuristic {
	int N;
	double speed;
	double limitTime, limitS;
	double wOfField = 100, hOfField = 100, x1 = wOfField, x2 = wOfField, deltaS = 0.5, s1 = 0, s2 = 0;
	ArrayList<Sensor> listSensors;
	double Rmax = 7;
	Point maxP;
	static double maxEP = 0;
	int tmax = 0, Ep1 = 0, Ep2 = 0 ;
	static String filename;
	
	public BestPointHeuristic() {
		this.listSensors = new ArrayList<Sensor>();

	}
	
	public void init() {

		speed = 5;
		limitTime = 100;
		limitS = speed * limitTime;
		
//		String filename = "C:\\Users\\20161\\Desktop\\BTL_TTTH\\src\\M_ExposurePath\\10.txt";
		List<String> lines = new ArrayList<String>();

		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
		String[] firstLine = lines.get(0).split("\\s+");
		N = Integer.parseInt(firstLine[0]); 
		double x11 = 100, y11 = 100, r11 = Rmax;
		for (int i = 0; i < N; i++) {
			String line = lines.get(i+1);
			String[] parts = line.split("\\s+");
			
			x11 = Double.parseDouble(parts[0]);
			y11 = Double.parseDouble(parts[1]);
			r11 = Double.parseDouble(parts[2]);
			Sensor sensor1 = new Sensor(x11, y11, r11);
			listSensors.add(sensor1);
			
//			System.out.println("Sensor "+ (i+1) + ": " + sensor1.x + " " + sensor1.y + " " + sensor1.r );
		}
		
		double y1, y2;
		
		String line1 = lines.get(N+1);
		String[] parts1 = line1.split("\\s+");
		x1 = Double.parseDouble(parts1[0]);
		y1 = Double.parseDouble(parts1[1]);
		String line2 = lines.get(N+2);
		String[] parts2 = line2.split("\\s+");
		x2 = Double.parseDouble(parts2[0]);
		y2 = Double.parseDouble(parts2[1]);
		
		System.out.println("x1 = " + x1 + ", y1 = " + y1);
		System.out.println("x2 = " + x2 + ", y2 = " + y2);
	
	}
		
	public double ShortestPath(double i) {
		double res_;
		res_ = Math.abs(x1-i) + hOfField + Math.abs(x2-i);
		//System.out.println("res = " + res_ );
		return res_;
	}
	
	public int exposure(Sensor sensor, double a, double b) {
		double distance = Math.sqrt((sensor.x - a)*(sensor.x - a) + ( sensor.y - b )*( sensor.y - b ));
		return distance>sensor.r?0:1;
	}
	
	public int sumExposure(double x, double y) {
		int res = 0;
	
		for (Sensor sensor: listSensors) {
			res += exposure(sensor, x, y);
		}
		
		return res;
	}
	
	public void FindExposureMax(double k) {
		
		maxP = new Point(0, 0);
		
		if( k > x1 && k < x2 ) {
			double i = x1;
			while(i != k) {
				
				if( tmax < sumExposure(i, 0) ) {
					maxP = new Point(i, 0);
					tmax = sumExposure(i, 0);
				}
				
				i = i + deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				
				if( tmax < sumExposure(k, j) ) {
					maxP = new Point(k, j);
					tmax = sumExposure(k, j);
				}
				
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				
				if( tmax < sumExposure(t, hOfField) ) {
					maxP = new Point(t, hOfField);
					tmax = sumExposure(t, hOfField);
				}
				
				t = t + deltaS;
			}
		}
		
		if( k < x1 && k > x2 ) {
			double i = x1;
			while(i != k) {
				
				if( tmax < sumExposure(i, 0) ) {
					maxP = new Point(i, 0);
					tmax = sumExposure(i, 0);
				}
				
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				
				if( tmax < sumExposure(k, j) ) {
					maxP = new Point(k, j);
					tmax = sumExposure(k, j);
				}
				
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				
				if( tmax < sumExposure(t, hOfField) ) {
					maxP = new Point(t, hOfField);
					tmax = sumExposure(t, hOfField);
				}
				
				t = t - deltaS;
			}
		}
		
		if(k < x1 && k < x2 ) {
			double i = x1;
			while(i != k) {
				
				if( tmax < sumExposure(i, 0) ) {
					maxP = new Point(i, 0);
					tmax = sumExposure(i, 0);
				}
				
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				
				if( tmax < sumExposure(k, j) ) {
					maxP = new Point(k, j);
					tmax = sumExposure(k, j);
				}
				
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				
				if( tmax < sumExposure(t, hOfField) ) {
					maxP = new Point(t, hOfField);
					tmax = sumExposure(t, hOfField);
				}
				
				t = t + deltaS;
			}
		}
		
		if(k > x1 && k > x2 ) {
			double i = x1;
			while(i != k) {
				
				if( tmax < sumExposure(i, 0) ) {
					maxP = new Point(i, 0);
					tmax = sumExposure(i, 0);
				}
				
				i = i + deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				
				if( tmax < sumExposure(k, j) ) {
					maxP = new Point(k, j);
					tmax = sumExposure(k, j);
				}
				
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				
				if( tmax < sumExposure(t, hOfField) ) {
					maxP = new Point(t, hOfField);
					tmax = sumExposure(t, hOfField);
				}
				
				t = t - deltaS;
			}
		}
		
		if( k == x1 || k == x2 ) {
			if(x2 >= x1) {
				double i = x1;
				while(i != x2) {
					
					if( tmax < sumExposure(i, 0) ) {
						maxP = new Point(i, 0);
						tmax = sumExposure(i, 0);
					}
					
					i = i + deltaS;
					
				}
				
				double j = 0;
				while( j <= hOfField ) {
					
					if( tmax < sumExposure(x2, j) ) {
						maxP = new Point(x2, j);
						tmax = sumExposure(x2, j);
					}
					
					j = j + deltaS;
				}
			}
			
			if(x2 < x1) {
				double i = x1;
				while(i != x2) {
					
					if( tmax < sumExposure(i, 0) ) {
						maxP = new Point(i, 0);
						tmax = sumExposure(i, 0);
					}
					
					i = i - deltaS;
				}
				
				double j = 0;
				while( j <= hOfField ) {
					
					if( tmax < sumExposure(x2, j) ) {
						maxP = new Point(x2, j);
						tmax = sumExposure(x2, j);
					}
					
					j = j + deltaS;
				}
			}
		}
		
	}
	
	public double Cal(double k) {
		tmax = 0;
		FindExposureMax(k);
		double resS =  ShortestPath(k);
		double t1 = resS/speed;
		double timeM = limitTime - t1;
		t1 = deltaS/speed;
//		System.out.println("it max la " + tmax);
		
		if(resS > limitS) return 0;
		
		if( k > x1 && k < x2 ) {
			int Ep1_1 = 0;
			int Ep1_2 = 0;
			int Ep1_3 = 0;
			double i = x1;
			
			while(i != k) {
				Ep1_1 += sumExposure(i, 0);
				i = i + deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				Ep1_2 += sumExposure(k, j);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				Ep1_3 += sumExposure(t, hOfField);
				t = t + deltaS;
			}
			
			Ep1 = Ep1_1 + Ep1_2 + Ep1_3 - tmax;
			maxEP = Ep1*t1 + tmax*timeM;
		}
		
		if( k < x1 && k > x2 ) {
			int Ep1_1 = 0;
			int Ep1_2 = 0;
			int Ep1_3 = 0;
			double i = x1;
			while(i != k) {
				Ep1_1 += sumExposure(i, 0);
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				Ep1_2 += sumExposure(k, j);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				Ep1_3 += sumExposure(t, hOfField);
				t = t - deltaS;
			}
			
			Ep1 = Ep1_1 + Ep1_2 + Ep1_3 - tmax;
			maxEP = Ep1*t1 + tmax*timeM;
		}
		
		if(k < x1 && k < x2 ) {
			int Ep1_1 = 0;
			int Ep1_2 = 0;
			int Ep1_3 = 0;
			double i = x1;
			while(i != k) {
				Ep1_1 += sumExposure(i, 0);
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				Ep1_2 += sumExposure(k, j);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				Ep1_3 += sumExposure(t, hOfField);
				t = t + deltaS;
			}
			
			Ep1 = Ep1_1 + Ep1_2 + Ep1_3 - tmax;
			maxEP = Ep1*t1 + tmax*timeM;
		}
		
		if(k > x1 && k > x2 ) {
			int Ep1_1 = 0;
			int Ep1_2 = 0;
			int Ep1_3 = 0;
			double i = x1;
			while(i != k) {
				Ep1_1 += sumExposure(i, 0);
				i = i + deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				Ep1_2 += sumExposure(k, j);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				Ep1_3 += sumExposure(t, hOfField);
				t = t - deltaS;
			}
			
			Ep1 = Ep1_1 + Ep1_2 + Ep1_3 - tmax;
			maxEP = Ep1*t1 + tmax*timeM;
		}
		
		if( k == x1 || k == x2 ) {
			if(x2 >= x1) {
				int Ep1_1 = 0;
				int Ep1_2 = 0;
				double i = x1;
				while(i != x2) {
					Ep1_1 += sumExposure(i, 0);
					i = i + deltaS;
					
				}
				
				double j = 0;
				while( j <= hOfField ) {
					Ep1_2 += sumExposure(x2, j);
					j = j + deltaS;
				}
				
				Ep1 = Ep1_1 + Ep1_2 - tmax;
				maxEP = Ep1*t1 + tmax*timeM;
			}
			
			if(x2 < x1) {
				int Ep1_1 = 0;
				int Ep1_2 = 0;
				double i = x1;
				while(i != x2) {
					Ep1_1 += sumExposure(i, 0);
					i = i - deltaS;
				}
				
				double j = 0;
				while( j <= hOfField ) {
					Ep1_2 += sumExposure(x2, j);
					j = j + deltaS;
				}
				
				Ep1 = Ep1_1 + Ep1_2 - tmax;
				maxEP = Ep1*t1 + tmax*timeM;
			}
//			System.out.println("bangroinhe");
		}

		return maxEP;
	}
	
	static double bestMEP = 0;
	double tempk = 0;
	
	public void BestExposure() {
		double tempEP = 0;
		
		for(double i = 0; i <= wOfField; i = i + deltaS) {
			tempEP = Cal(i);
			//bestMEP = Math.max(bestMEP, tempEP);
			if(bestMEP < tempEP) {
				bestMEP = tempEP;
				tempk = i;
			}
		}
		
		System.out.println("BestMaxExposure = " + bestMEP);
		System.out.println("BestPoint = " + tempk);
		
		double k = tempk;
		ArrayList<Point> listPoint = new ArrayList<Point>();
		Point p = new Point(0, 0);
		
		if( k > x1 && k < x2 ) {
			double i = x1;
			
			while(i != k) {
				p = new Point(i, 0);
				listPoint.add(p);
				i = i + deltaS;
				
			}
			
			double j = 0;
			while( j != hOfField ) {
				p = new Point(k, j);
				listPoint.add(p);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				p = new Point(t, hOfField);
				listPoint.add(p);
				t = t + deltaS;
			}
		}
		
		if( k < x1 && k > x2 ) {
			double i = x1;
			while(i != k) {
				p = new Point(i, 0);
				listPoint.add(p);
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				p = new Point(k, j);
				listPoint.add(p);
				j = j + deltaS;
			}
			
			double t = k;
			while( t != x2 + deltaS ) {
				p = new Point(t, hOfField);
				listPoint.add(p);
				t = t - deltaS;
			}
		}
		
		if(k < x1 && k < x2 ) {
			double i = x1;
			while(i != k) {
				p = new Point(i, 0);
				listPoint.add(p);
				//System.out.println("dc neeeeeeeeee = " + tempk);
				i = i - deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				p = new Point(k, j);
				listPoint.add(p);
				j = j + deltaS;
			}
			
			double t = k;
			while( t <= x2 ) {
				p = new Point(t, hOfField);
				listPoint.add(p);
				t = t + deltaS;
			}
		}
		
		if(k > x1 && k > x2 ) {
			double i = x1;
			while(i != k) {
				p = new Point(i, 0);
				listPoint.add(p);
				i = i + deltaS;
			}
			
			double j = 0;
			while( j != hOfField ) {
				p = new Point(k, j);
				listPoint.add(p);
				j = j + deltaS;
			}
			
			double t = k;
			while( t != x2 + 1 ) {
				p = new Point(t, hOfField);
				listPoint.add(p);
				t = t - deltaS;
			}
		}
		
		if( k == x1 || k == x2 ) {
			if(x2 > x1) {
				double i = x1;
				while(i != x2) {
					p = new Point(i, 0);
					listPoint.add(p);
					i = i + deltaS;
					
				}
				
				double j = 0;
				while( j <= hOfField ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j + deltaS;
				}
			}
			
			if(x2 < x1) {
				double i = x1;
				while(i != x2) {
					p = new Point(i, 0);
					listPoint.add(p);
					i = i - deltaS;
				}
				
				double j = 0;
				while( j <= hOfField ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j + deltaS;
				}
			}
		}
		
		int sizelistPoint = listPoint.size(), it = 0;
		System.out.print("MaximalExposurePath (BestPointHeuristic): ");
		for(Point point: listPoint) {
			System.out.print("("+point.x3+", "+point.y3+")");
			it++;
			if( it == sizelistPoint ) break;
			System.out.print(" , ");
		}
	}
	
	public static void main(String[] args) throws IOException {
//		BestPointHeuristic app = new BestPointHeuristic();
//		app.init();
//		app.BestExposure();
		
		PrintWriter writer2 = null;
		String filekq = "/home/giang/Documents/MEP_PSO/ketquaBestPointHeuristic.txt";
		writer2 = new PrintWriter(new File(filekq));
		writer2.write("filename                  result            time " + "\n");
		String[] datas = { "10", "20", "50", "100", "200" };
		
		for(int i = 0; i < datas.length; i++) {
			for(int j = 1; j <= 10; j++) {
				filename = "/home/giang/Documents/MEP_PSO/data/data_" + datas[i] + "_" + String.valueOf(j) + ".txt";
				System.out.println(filename);
				bestMEP = 0;
				BestPointHeuristic app = new BestPointHeuristic();
				app.init();
				app.BestExposure();
//				app.PrintFile();
				System.out.println("\n -----------------------------");
				writer2.write("data_" + datas[i] + "_" + String.valueOf(j) + ".txt            " + bestMEP + "          "  + "\n");
				
//				writer2.flush();
			}
		}
		
		writer2.flush();
		writer2.close();
    }
}