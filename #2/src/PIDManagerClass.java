import java.util.Vector;

public class PIDManagerClass implements PIDManager {
	private static boolean flag = true;
	private static PIDManagerClass instance = new PIDManagerClass();

	//I declare pid container as Vector.
	//Many thread objects want to connect pid container by getPID or getPIDWait.
	//By using vector, I can prevent duplicate pid when thread objects arrive container at same time.
	//And also, other class must not connect this container directly. So I set this as private.
	//The reason why declared as private next time is all the same reason, so I will not mention it after.
	private Vector<Integer> pids = new Vector<>();


	//And PIDManager must exist only one.
	//For prevent duplicate creating manager, I make PIDManagerClass constructor as private.
	private PIDManagerClass() {

	}


	//And make this instance at its global variable space as private.
	//Other class only connect this instance by getInstance() method.
	public static PIDManagerClass getInstance() {
		return instance;
	}

	//Before user want to test PIDManager, manager need some data about test.
	//User input thread number, thread running time, processTime, getPIDType.
	public int setPIDManager(int threadNum, int threadTime, int processTime, int getPIDType) {
		System.out.println("----------------PID MANAGER SET----------------");

		for(int i = MIN_PID; i <= MAX_PID; i++) {
			pids.add(i);

		}
		for(int i = 0; i <= threadNum; i++) {

			//And then, initiate pid container and make thread object.
			new MyThread(("thread"+(i)),threadTime,processTime, getPIDType);
		}

		return 1;
	}



	//getPID() : if pid container is empty,return -1.
	// If not, return available pid.
	@Override
	public int getPID() {
		if(pids.isEmpty()) {
			return -1;
		}else {
			int pd = pids.get(0);
			pids.remove(0);
			return pd;
		}

	}


	//getPIDWait() : I watch some error.
	//That is unexpected pid(ex 0,1,,, such that is smaller than MIN_PID declared at interface) is released to pid container.
	//So I declare flag as initiate true.
	@Override
	public int getPIDWait() {
		while(!flag) {
			//waiting
		}
		flag = false;

		//Critical Section
		int pidchild = getPID();

		//If getPID() return -1(no available pid), wait in the while.
		//And update pid every time in the while.
		while(pidchild==-1) {
			System.out.println("wait...");
			try {
				Thread.sleep(500);
			}catch(Exception e) {

			}
			pidchild= getPID();

		}

		//If available pid exist, get out the while and set flag as true.
		//And return available pid.
		flag = true;
		return pidchild;
	}



	//If process return pid to pid container, Just add that into container.
	@Override
	public void releasePID(int pid) {
		try {
        	pids.add(pid);

        }catch(IllegalArgumentException e) {
        	System.err.println(e);
        }

	}

}

