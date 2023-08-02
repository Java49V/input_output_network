package telran.io;

public abstract class PerformanceTest {
    protected String testName;
    protected int nRuns;

    public PerformanceTest(String testName, int nRuns) {
        this.testName = testName;
        this.nRuns = nRuns;
    }

    protected abstract void runTest();

    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nRuns; i++) {
            runTest();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Test: " + testName);
        System.out.println("Number of runs: " + nRuns);
        System.out.println("Total time (ms): " + totalTime);
        System.out.println("Average time per run (ms): " + (double) totalTime / nRuns);
    }
}