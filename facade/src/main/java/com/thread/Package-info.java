/**
 * Java中，每个对象都有唯一一个与之对应的内部锁，JVM会为每个对象维护2个队列
 * <p/>
 * Entry Set （锁池）：存储所有等待获取对象内部锁的线程
 * <p/>
 * Wait Set （等待池）：存储所有执行了wait()/wait(long)方法的线程
 * <p/>
 * notify：会在Wait Set中随机获取一个线程唤醒进入锁池竞争内部锁
 * <p/>
 * notifyAll：将Wait Set中的所有线程唤醒进入锁池竞争内部锁
 * <p/>
 * ReentrantLock中是Blocking队列和Waiting队列
 */
