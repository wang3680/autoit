package uftdemo.com.serviceImpl;

import uftdemo.com.service.UftInvestor;

public class TheadTrade extends Thread{

    private UftInvestor investor;
    private String a[];
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        try {
            investor.ReqFunction333002(a[0], a[1], a[2],
                    a[3], a[4], a[5], a[6], a[7],
                    a[8],a[9],a[10],a[11],a[12],a[13]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInvestor(UftInvestor investor) {
        this.investor = investor;
    }

    public void setA(String[] a) {
        this.a = a;
    }
}
