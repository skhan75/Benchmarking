import java.util.Random;

public class CpuLoader implements Runnable {
	public static int noOfThreads;
	//private long flopsTotalTime = 0;
	//private long iopsTotalTime = 0;
	private float totalGFlops;
	private float totalIOps;

	@Override
	public void run() {
		
		this.noOfThreads = CpuLoader.noOfThreads;
		System.out.println("Threads #--->"+noOfThreads);
		long flopsTotalTime = flopsCalculator();
		long iopsTotalTime =  iopsCalculator();
		
		System.out.println("Thread #"+noOfThreads+" : Time for FLOPS	:"+ flopsTotalTime+" ms");
    	System.out.println("Thread #"+noOfThreads+" : Time for IOPS	:"+ iopsTotalTime+" ms");
    	
    	
    	totalGFlops = (operationsCalc(flopsTotalTime));
    	System.out.println("Thread #"+noOfThreads+" : Number of GFLOPS	:"+ getTotalGFlops());
    	totalIOps = (operationsCalc(iopsTotalTime));
    	System.out.println("Thread #"+noOfThreads+" : Number of GIOPS	:"+ getTotalIOps());
    	
    	System.out.println("Thread # "+noOfThreads+" Exiting");

    	
	}

	private float operationsCalc(float operations) {
		float ops = operations/1000;
		int max = 7*Integer.MAX_VALUE;
		float temp = (max/ops);
		float total = temp/1000000000;
		return total;
	}

	private synchronized static long iopsCalculator() {
		
			int minX = 5;
			int maxX = 100;
			int calc = 0;
			Random rand = new Random();

			int a = rand.nextInt() * (maxX - minX) + minX;
			int b = rand.nextInt() * (maxX - minX) + minX;
			int c = rand.nextInt() * (maxX - minX) + minX;
			int temp;
			
			long start = System.currentTimeMillis();
			for(long i=1; i<Integer.MAX_VALUE; i++){
				//i = (float)i;
				calc = (((a*b)+(b*c))/(a*b*c));
			}
			temp = calc;
			long end = System.currentTimeMillis();
			long difference = end - start;
			return difference;
				
	}

	private synchronized static long flopsCalculator() {
		
		int minX = 5;
		int maxX = 100;
		float calc = 0;
		float limiter = 100.0f;
		float temp;
		Random rand = new Random();
		

		float a = (float)rand.nextInt() * (maxX - minX) + minX;
		float b = (float)rand.nextInt() * (maxX - minX) + minX;
		float c = (float)rand.nextInt() * (maxX - minX) + minX;
		
		long start = System.currentTimeMillis();
		for(long i=1; i<Integer.MAX_VALUE; i++){
			calc = (((a*b)+(b*c))/(a*b*c));
		}
		
		temp = calc;
		long end = System.currentTimeMillis();
		long difference = end - start;
		return difference;
		
	}

	private float getTotalIOps() {
		return totalIOps;
	}

	private float getTotalGFlops() {
		return totalGFlops;
	}

	

	
	
	/*public static long emptyLoop_float_elapsedTime(){
		float a = (float)( new Random_Value().integer());
		long start = System.currentTimeMillis();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			float c = 123.123f;			
		}
		long end = System.currentTimeMillis();
		long difference = end - start;
		return difference;
	}
	
	public static long emptyLoop_int_elapsedTime(){
		int a = new Random_Value().integer();
		long start = System.currentTimeMillis();
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			int c = a;			
		}
		long end = System.currentTimeMillis();
		long difference = end - start;
		return difference;
	}*/
}
