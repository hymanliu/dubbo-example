package com.hyman.kafka;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DateFormatTest extends Thread {
	

	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    	
    private String name;
    private String dateStr;
    private boolean sleep;

    public DateFormatTest(String name, String dateStr, boolean sleep) {
        this.name = name;
        this.dateStr = dateStr;
        this.sleep = sleep;
    }

    @Override
    public void run() {

        Date date = null;

        if (sleep) {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            date = sdf.parse(dateStr);
            System.out.println(name + " : date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String datestr = MyUtil.formatDate(date);//sdf.format(date);
        
        System.out.println(name + " : datestr: " + datestr);
    }

    public static void main(String[] args) throws InterruptedException {

//        ExecutorService executor = Executors.newCachedThreadPool();
//
//        // A 会sleep 2s 后开始执行sdf.parse()
//        executor.execute(new DateFormatTest("A", "1991-08-12", true));
//        // B 打了断点,会卡在方法中间
//        executor.execute(new DateFormatTest("B", "2013-09-13", false));
//
//        executor.shutdown();
        
        
        
        Thread[] runs = new Thread[3];
        for(int i=0;i<runs.length;i++){
            runs[i]=new DateFormatTest(""+i, "1991-08-1"+i, i%2==0);
        }
        for(int i=0;i<runs.length;i++){
            runs[i].start();
        }
        
    }
    
    
    
}

class MyUtil{
	
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){
		@Override
        protected DateFormat initialValue() {
            System.out.println(Thread.currentThread().getName()+"initialValue");
            return new SimpleDateFormat("yyyy-MM-dd");
        } 
    };
	public static String formatDate(Date date){
		return threadLocal.get().format(date);
	}
}

