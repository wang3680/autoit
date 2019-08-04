package util;


import bean.Account;

import java.io.*;
import java.util.ArrayList;

public class ObjectDatUtil {
    /**
     * 把java的可序列化的对象(实现Serializable接口)序列化保存到XML文件里面,如果想一次保存多个可序列化对象请用集合进行封装
     * 保存时将会用现在的对象原来的XML文件内容
     * @param obj 要序列化的可序列化的对象
     * @param fileName 带完全的保存路径的文件名
     * @throws FileNotFoundException 指定位置的文件不存在
     * @throws IOException 输出时发生异常
     * @throws Exception 其他运行时异常
     */
    public static void objectXmlEncoder(ArrayList<Account> list, String fileName)
            throws FileNotFoundException,IOException,Exception
    {
        //创建输出文件
        File fo = new File(fileName);
        //文件不存在,就创建该文件
        if(!fo.exists())
        {
            //先创建文件的目录
            String path = fileName.substring(0,fileName.lastIndexOf('.'));
            File pFile = new File(path);
            pFile.mkdirs();
        }
        ObjectOutputStream os = new ObjectOutputStream(
                new FileOutputStream(fileName));
//        for(Account a:list){
            os.writeObject(list);
//        }
        os.close();
    }
    /**
     * 把java的可序列化的对象(实现Serializable接口)序列化保存到XML文件里面,如果想一次保存多个可序列化对象请用集合进行封装
     * 保存时将会用现在的对象原来的XML文件内容
     * @param obj 要序列化的可序列化的对象
     * @param fileName 带完全的保存路径的文件名
     * @throws FileNotFoundException 指定位置的文件不存在
     * @throws IOException 输出时发生异常
     * @throws Exception 其他运行时异常
     */
    public static void objectXmlEncoder(String str, String fileName)
            throws FileNotFoundException,IOException,Exception
    {
        //创建输出文件
        File fo = new File(fileName);
        //文件不存在,就创建该文件
        if(!fo.exists())
        {
            //先创建文件的目录
            String path = fileName.substring(0,fileName.lastIndexOf('.'));
            File pFile = new File(path);
            pFile.mkdirs();
        }
        ObjectOutputStream os = new ObjectOutputStream(
                new FileOutputStream(fileName));
//        for(Account a:list){
        os.writeObject(str);
//        }
        os.close();
    }
    /**
     * 读取由objSource指定的XML文件中的序列化保存的对象,返回的结果经过了List封装
     * @param objSource 带全部文件路径的文件全名
     * @return 由XML文件里面保存的对象构成的List列表(可能是一个或者多个的序列化保存的对象)
     * @throws FileNotFoundException 指定的对象读取资源不存在
     * @throws IOException 读取发生错误
     * @throws Exception 其他运行时异常发生
     */
    public static ArrayList<Account> objectXmlDecoder(String objSource)
            throws FileNotFoundException,IOException,Exception
    {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                objSource));
        ArrayList<Account> a = (ArrayList<Account>) is.readObject();
        return a;
    }
}
