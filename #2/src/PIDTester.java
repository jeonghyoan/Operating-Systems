import java.util.Scanner;

public class PIDTester {
	public static void main(String[] args) {
		PIDManagerClass pidM = PIDManagerClass.getInstance();
		Scanner scan = new Scanner(System.in);
		System.out.println("--------------INPUT-POSITIVE-INTEGER-NUMBER--------------");
		System.out.println("----------ThreadNum, ThreadTime, ProgramTime----------");
		try {

			//ThreadNum = number of threads created.
			//ThreadTime = life time of a thread while the program is running
			//ProcessTime = life time of the program
			int ThreadNum = scan.nextInt();
			int ThreadTime = scan.nextInt();
			int ProcessTime = scan.nextInt();

			//If user input unexpected type number, (float or negative int),
			//program notice this num is not available. And recommend restart program.
			if(ThreadNum<0||ThreadTime<0||ProcessTime <0) {
				System.out.println("<<<<Please input 'POSITIVE' 'INTEGER' number>>>>");
				System.out.println("<<<<Restart Program please>>>>");
				System.exit(0);
			}

			//User can test getPID() version.
			//PIDManager as input 0.
			//If want to test getPIDWait(), just input positive number except 0.
			System.out.println("-----Select Mode : 0.getPID(), OtherNum.getPIDWait()------");
			int pidMode = scan.nextInt();
			pidM.setPIDManager(ThreadNum, ThreadTime, ProcessTime, pidMode);


			//If user input unexpected type number, (float or negative int),
			//program notice this num is not available. And recommend restart program.
		}catch(Exception e) {
			System.out.println("<<<<Please input 'POSITIVE' 'INTEGER' number>>>>");
			System.out.println("<<<<Restart Program please>>>>");
		}finally {
			scan.close();
		}

	}

}

