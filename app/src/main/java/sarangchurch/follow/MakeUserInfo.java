package sarangchurch.follow;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MakeUserInfo {

    private static String name;
    private static String birth;
    private static String job;
    String str = Environment.getExternalStorageDirectory().getAbsolutePath()+"/follow/";

    MakeUserInfo(){
        File dir = makeDirectory(str);
        File savefile = makeFile(dir,str+"UserInfo.txt");
    }

    public void WriteFile(String name, String birth) {

        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/follow"); // 저장 경로
// 폴더 생성
        if(!saveFile.exists()){ // 폴더 없을 경우
            saveFile.mkdir(); // 폴더 생성
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/follow/UserInfo.txt");
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/follow/UserInfo.txt", false));
            buf.append(name); // 날짜 쓰기
            buf.newLine();
            buf.append(birth); // 파일 쓰기
            buf.newLine(); // 개행
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ReadFile();

    }

    public void ReadFile(){//파일 속 이름 생일을 변수에 저장

        String line = null; // 한줄씩 읽기
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/follow"); // 저장 경로
// 폴더 생성
        if(!saveFile.exists()){ // 폴더 없을 경우
            saveFile.mkdir(); // 폴더 생성
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader(saveFile+"/UserInfo.txt"));
            String[] temp = new String[2];
            for(int i=0;(line=buf.readLine())!=null;i++){
                temp[i]=line;
            }
            setName(temp[0]);
            setBirth(temp[1]);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getBirth() {
        return birth;
    }

    public static String getName() {
        return name;
    }

    public static String getJob() {
        return job;
    }

    public static void setJob(String job) {
        MakeUserInfo.job = job;
    }

    public static void setBirth(String birth) {
        MakeUserInfo.birth = birth;
    }

    public static void setName(String name) {
        MakeUserInfo.name = name;
    }

    private File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(file!=null&&!file.exists()){
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                }
            }else{
            }
        }
        return file;
    }
    private File makeDirectory(String dir_path){
        File dir = new File(dir_path);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
            } else {
            }
        } else {
        }
        return dir;
    }
}
