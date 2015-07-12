package com.tk.jogl;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SplitTask2 implements Callable {
	public static final String LS = System.getProperty("line.separator");

	// private static String path = "C:/Users/fei.wang/Desktop/logFile/OC_20150626_121548_multithread_test.log";
	private static String path = "C:/Users/fei.wang/Desktop/logFile/OC_20150626_145920_100.log";

	private static ExecutorService pool = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future future = SplitTask2.pool.submit(new SplitTask2());
		Object obj = future.get();
		Object[] objs = (Object[])obj;
		List cntList = (List)objs[0];
		List skipList = (List)objs[1];
		System.out.println(cntList);
		System.out.println(skipList);

		if(cntList.size() != skipList.size()) {
			System.out.println("*******ERROR*******");
			return;
		}
		int size = cntList.size();

		CyclicBarrier cb = new CyclicBarrier(size+1, new Runnable() {
			@Override
			public void run() {
				System.out.println(">>>>主任务执行了！<<<<");
			}
		});

		List futureList = new ArrayList();
		for(int i=0; i<size; i++) {
			futureList.add(SplitTask2.pool.submit(new SubTask(cb, SplitTask2.path, cntList, skipList, i)));
		}

		//  for(int i=0; i<futureList.size(); i++) {
		//   Future f = (Future)futureList.get(i);
		//   List li = (List)f.get();
		////   System.out.println(li.size());
		//  }

		Future f = (Future)futureList.get(1);
		List li = (List)f.get();
		System.out.println(li.size());
	}

	public SplitTask2() {
	}

	public SplitTask2(String path) {
		SplitTask2.path = path;
	}

	@Override
	public Object call() throws Exception {
		List<Long> skipList = new ArrayList<Long>(1024);
		List<Integer> li = new ArrayList<Integer>(1024);
		LineNumberReader lnr = null;
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(SplitTask2.path)));
			lnr = new LineNumberReader(br);

			long skip = 0;
			skipList.add(skip);

			int cnt = 0;

			long lo1 = System.currentTimeMillis();
			String line = lnr.readLine();
			while(line != null) {
				if(line.startsWith("||")) {
					if(cnt > 220000) {
						li.add(cnt);
						skipList.add(skip);
						cnt = 0;
					}
				}

				cnt++;

				skip += line.length() + SplitTask2.LS.length();

				line = lnr.readLine();
			}
			long lo2 = System.currentTimeMillis();
			//   System.out.println(lo2 - lo1);

			li.add(cnt);
			/**
  [220046, 220001, 220005, 220008, 220009, 220049, 220021, 220006, 220006, 220005, 220007, 220012, 220001, 92880]
  [     0, 12440682, 24898292, 37370379, 49842922, 62315512, 74850589, 87301024, 99749987, 112199057, 124648159, 137097421, 149616962, 162059363]
			 */
		} finally {
			if(lnr != null) {
				lnr.close();
			}
		}
		return new Object[]{li, skipList};
	}

}

class SubTask implements Callable {

	public static final String LS = System.getProperty("line.separator");

	List<String[]> resultList = new ArrayList<String[]>(1024 * 50);

	private String path;
	private List<Integer> cntList;
	private List<Long> skipList;
	private int idx;

	private CyclicBarrier cb;

	public SubTask(CyclicBarrier cb, String path, List cntList, List skipList, int idx) {
		this.cb = cb;
		this.path = path;
		this.cntList = cntList;
		this.skipList = skipList;
		this.idx = idx;
	}

	@Override
	public Object call() throws Exception {

		int maxcnt = cntList.get(idx);
		long skipCnt = skipList.get(idx);

		int lineCnt = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

		br.skip(skipCnt);

		StringBuilder sb = null;
		String line = br.readLine();
		while (line != null) {
			lineCnt++;
			if(220046 == lineCnt) {
				System.out.println(lineCnt);
			}

			if(lineCnt > maxcnt) {
				sb = null;
				break;
			}

			if (line.startsWith("#")) {
				line = br.readLine();
				continue;
			}

			if (line.startsWith("||")) {
				if (sb != null) {
					resultList.add(convertToStrArr(sb.toString()));
				}

				sb = new StringBuilder(100);
				sb.append(line);
			} else {
				sb.append(SubTask.LS + line);
			}

			line = br.readLine();
		} // end while

		if (sb != null) {
			resultList.add(convertToStrArr(sb.toString()));
		}

		//  try {
		//         cb.await();
		//     } catch (InterruptedException e) {
		//      e.printStackTrace();
		//     } catch (BrokenBarrierException e) {
		//      e.printStackTrace();
		//     }

		return resultList;
	}

	private String[] convertToStrArr(String str) {
		String[] strArr = str.substring(2).split("(?m)\\|");
		return strArr;
	}
}