import java.util.Random;

public class MyThread extends Thread {

	//Thread global variable field
	private String threadName;
	private int createTime;
	private int threadTime;
	private int processTime;

	private int pid;
	private int getPIDType;

	//And I understand thread is run as process and get pid.
	//So I declare running thread as runningThreadasProcess.
	private Thread runningThreadasProcess;
	private Random random = new Random();
	private PIDManagerClass pidM = PIDManagerClass.getInstance();

	public MyThread(String threadName, int threadTime,int processTime, int getPIDType) {
		this.threadName = threadName;
		this.threadTime = threadTime;
		this.processTime = processTime;

		this.getPIDType = getPIDType;
		this.runningThreadasProcess = new Thread(this, this.threadName);

		runningThreadasProcess.start();
	}


	//I make getPIDtype method because code that is in run() is too long.
	@Override
    public void run() {
		getPIDtype(this.getPIDType);

	}


	public void getPIDtype(int gettype) {

		//Test 'getPID()' version PIDManager.
		if(gettype == 0) {
			try {

				//I implemented the time when thread was generated as a wake-up call as soon as thread was created.
				createTime = random.nextInt(processTime*1000);
				Thread.sleep(createTime);
				this.pid= pidM.getPID();
				if(this.pid !=-1) {

					System.out.println(threadName+" created at "+createTime+"ms "+"pid: "+this.pid);


				}else {
					//if getPID() return -1, this thread is covered under else part by return.
				}
			}catch(Exception e) {
				System.err.println(e);
			}

			try {

				if((createTime+threadTime*1000) > processTime*1000) {
					Thread.sleep(threadTime*1000);
					System.out.println(processTime+"sec has passed... Program ends");
					System.exit(0);
				}else {

					//If thread can not get pid, just drop it as return.
					if(this.pid == -1) {
						System.out.println("All pid are used now.");
						System.out.println("this thread can not get pid.");
						return;
					}

					//If not , thread sleep as running time and release its pid.
					Thread.sleep(threadTime*1000);
					pidM.releasePID(this.pid);
					System.out.println(threadName+" destroyed at "+(createTime+threadTime*1000)+"ms"+" pid: "+this.pid );
				}
			}catch(Exception e) {
				System.err.println(e);
			}

			//Test 'getPIDWait()' version.
		}else {
			try {

				createTime = random.nextInt(processTime*1000);
				Thread.sleep(createTime);
				this.pid= pidM.getPIDWait();
				if(this.pid !=-1) {

					System.out.println(threadName+" created at "+createTime+"ms "+"pid: "+this.pid);


				}else {
					//getPIDWait() cover this part.
					//Unlike getPID(), getPIDWait method handles the case that there is no available pid
					//So there were not codes for that part.
				}

			}catch(Exception e) {
				System.err.println(e);
			}

			try {

				if((createTime+threadTime*1000) > processTime*1000) {
					Thread.sleep(threadTime*1000);
					System.out.println(processTime+" sec passed... Program ends");
					System.exit(0);
				}else {
					Thread.sleep(threadTime*1000);
					pidM.releasePID(this.pid);
					System.out.println(threadName+" destroyed at "+(createTime+threadTime*1000)+"ms"+"pid: "+this.pid );
				}
			}catch(Exception e) {
				System.err.println(e);
			}
		}

	}

}

