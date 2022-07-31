package top.whitecola.promodule.utils;


import java.util.concurrent.CopyOnWriteArrayList;

public class HiThread {
    public Thread th;
    public String hiThreadId;
    public int waitingSpace;
    private final CopyOnWriteArrayList<Runnable> tasks=new CopyOnWriteArrayList<Runnable>();

    public HiThread(final String hiThreadId, final int waitingSpace){
        this.hiThreadId = hiThreadId;
        this.waitingSpace = waitingSpace;
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (this){
                        try {
                            for (Runnable th : tasks) {
                                th.run();
                            }
                            tasks.clear();
                            Thread.sleep(waitingSpace);
                        }catch (Throwable e){
                            if(e instanceof ThreadDeath){
                                return;
                            }
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    public String getThisThreadId(){
        return hiThreadId;
    }

    public void addTask(Runnable th){
        tasks.add(th);
    }


    public void cleanTask(){
        tasks.clear();
    }

    public void start(){
        th.start();
    }

    public void stop(){
        th.stop();
    }

    public void remove(Runnable th){
        tasks.remove(th);
    }

}
