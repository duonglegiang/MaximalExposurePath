package M_ExposurePath;

import java.util.*;
import java.lang.*;
import java.io.*; 

public class SPHeuristic {
	int N;
	double speed;
	double limitTime;
	double wOfField = 100, hOfField = 100, x1 = wOfField, x2 = wOfField, deltaS = 0.5, s1 = 0, s2 = 0;
	ArrayList<Sensor> listSensors;
	double Rmax = 7;
	Point maxP;
	double maxEP = 0;
	int tmax = 0, Ep1 = 0, Ep2 = 0 ;
	double y1 = hOfField, y2 = hOfField;
	
	public SPHeuristic() {
		this.listSensors = new ArrayList<Sensor>();
	}
	
	public void init() {		
		speed = 5.0;
		limitTime = 100.0;

		String filename = "C:\\Users\\20161\\Desktop\\BTL_TTTH\\src\\M_ExposurePath\\10.txt";
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
		
		String line1 = lines.get(N+1);
		String[] parts1 = line1.split("\\s+");
		x1 = Double.parseDouble(parts1[0]);
		y1 = Double.parseDouble(parts1[1]);
		String line2 = lines.get(N+2);
		String[] parts2 = line2.split("\\s+");
		x2 = Double.parseDouble(parts2[0]);
		y2 = Double.parseDouble(parts2[1]);
		
//		System.out.println("x1 = " + x1 + ", y1 = " + y1);
//		System.out.println("x2 = " + x2 + ", y2 = " + y2);
//		
	}
	
	public double ShortestPath() {
		double res_;
		res_ = Math.abs(x1-x2) + Math.abs(y1-y2);
//		System.out.println("res = " + res_ );
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
	
	public void FindExposureMax() {
		
		maxP = new Point(0, 0);
		
		if( y1 == 0 || y2 == 0 ) {
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
			
//			System.out.println("tmax = " + tmax);
//			System.out.println("Pointmax = " + maxP.x3 + " " + maxP.y3 );
		}
		
		if( x1 == 0 || x2 == 0 ) {
			if(y2 >= y1) {
				double j = 0;
				while( j != wOfField ) {
			
					if( tmax < sumExposure(x2, j) ) {
						maxP = new Point(x2, j);
						tmax = sumExposure(x2, j);
					}
					
					j = j + deltaS;
				}
				
				double i = y1;
				while(i <= y2) {
					if( tmax < sumExposure(i, 0) ) {
						maxP = new Point(i, 0);
						tmax = sumExposure(i, 0);
					}
					
					i = i + deltaS;
				}
			}
			
			if(y2 < y1) {
				double j = 0;
				while( j != hOfField ) {
					
					if( tmax < sumExposure(x2, j) ) {
						maxP = new Point(x2, j);
						tmax = sumExposure(x2, j);
					}
					
					j = j + deltaS;
				}
				
				double i = x1;
				while(i <= x2) {
					
					if( tmax < sumExposure(i, 0) ) {
						maxP = new Point(i, 0);
						tmax = sumExposure(i, 0);
					}
					
					i = i - deltaS;
				}	
			}
			
//			System.out.println("tmax = " + tmax);
//			System.out.println("Pointmax = " + maxP.x3 + " " + maxP.y3 );
		}
	}
	
	public double Cal() {
		if( y1 == 0 || y2 == 0 ) {
			double resS =  ShortestPath();
			double t1 = resS/speed;
			double timeM = limitTime - t1;
			t1 = deltaS/speed;
			
			int Ep1_1 = 0;
			int Ep1_2 = 0;
			if(x2 >= x1) {
				double i = x1;
				while(i != x2) {
					Ep1_1 += sumExposure(i, y1);
					i = i + deltaS;
				}
				
				double j = 0;
				while( j <= hOfField ) {
					Ep1_1 += sumExposure(x2, j);
					j = j + deltaS;
				}
				Ep1 = Ep1_1 + Ep1_2 - tmax;	
				maxEP = Ep1*t1 + tmax*timeM;
			}
			
			if(x2 < x1) {
				double i = x1;
				while(i != x2) {
					Ep1_1 += sumExposure(i, y1);
					i = i - deltaS;
				}
				
				double j = 0;
				while( j <= hOfField ) {
					Ep1_1 += sumExposure(x2, j);
					j = j + deltaS;
				}
				Ep1 = Ep1_1 + Ep1_2 - tmax;	
				maxEP = Ep1*t1 + tmax*timeM;
			}
			
//			System.out.println("maxExposure = " + maxEP);
		}
		return maxEP;
	}
	
	public ArrayList<Point> listP(){
		ArrayList<Point> listPoint = new ArrayList<Point>();
		Point p = new Point(0, 0);
		if(x2 >= x1) {
			double i = x1;
			double j = 0;
			
			while(i != x2) {
				p = new Point(i, y1);
				listPoint.add(p);
				i = i + deltaS;		
			}
			
			if(y1 == 0) {
				while( j <= hOfField ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j + deltaS;
				}
			}
			else {
				j = hOfField;
				while( j >= 0 ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j - deltaS;
				}
			}
		}
		
		if(x2 < x1) {
			double i = x1;
			double j = 0;
			
			while(i != x2) {
				p = new Point(i, y1);
				listPoint.add(p);
				i = i - deltaS;	
			}
			
			if(y1 == 0) {
				while( j <= hOfField ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j + deltaS;
				}
			}
			else {
				j = hOfField;
				while( j >= 0 ) {
					p = new Point(x2, j);
					listPoint.add(p);
					j = j - deltaS;
				}
			}
		}
		return listPoint;
	}
		
	public static void main(String[] args) {
        //TODO
		SPHeuristic app = new SPHeuristic();
		app.init();
		app.ShortestPath();
		app.FindExposureMax();
		app.Cal();
    }	
}