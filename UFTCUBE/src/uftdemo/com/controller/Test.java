package uftdemo.com.controller;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
/**
          * 指定触发的时间      现在指定时间为   2013年10月27号  15点  43 分 1 秒时触发
          */
//        calendar.set(Calendar.DAY_OF_MONTH, 27);//设置日期为27号
//        calendar.set(Calendar.MONTH, 10);//设置日期为11月份   这里10表示11月份    11就表示12月份
        calendar.set(Calendar.HOUR_OF_DAY, 11); //设置15点的时候触发
        calendar.set(Calendar.MINUTE, 15); //设置43分钟的时候触发
        calendar.set(Calendar.SECOND, 0); //设置第一秒的时候触发
        calendar.set(Calendar.MILLISECOND,1);  //设置第一毫秒秒的时候触发

        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new RemindTask(), time);
    }

}


class RemindTask extends TimerTask {

    public void run() {
        System.out.println(LocalDateTime.now()+"-执行已经触发！");
    }
}
