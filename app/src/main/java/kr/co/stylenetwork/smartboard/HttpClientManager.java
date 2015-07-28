/*
*  글 등록, 수정, 삭제, 리스트,상세보기 가져오기를 감당하는
*  Http 기반의 클라이언트 객체
* */
package kr.co.stylenetwork.smartboard;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpClientManager {
    String TAG;
    String path;
    URL url;
    HttpURLConnection con;
    BufferedWriter buffw;
    BufferedReader buffr;

    public HttpClientManager() {
        TAG=this.getClass().getName();
    }
    /*
      request("http://localhost:8080/android/board/write"
       , "?writer=sdfsdf&")
    */
    public Object request(String path, String params){
        Object resultValue=null;
        this.path=path;
        try {
            url = new URL(path);
            con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setReadTimeout(15000);
            con.setConnectTimeout(5000);
            con.setDoInput(true);
            con.setDoOutput(true);

            con.connect();

            /*서버에 요청시도*/
            buffw=new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            buffw.write(params + "\n");
            buffw.flush();

            /*서버가 요청을 제대로 처리한 경우만...*/
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                /*요청에 대한 응답을 받아온다!!*/
                buffr = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                String data=null;
                StringBuilder sb = new StringBuilder();
                while( (data=buffr.readLine()) !=null ){
                    sb.append(data);
                }

                /*서버측으로부터 받아온 텍스트 데이터를 제이슨으로 파싱해본다*/
                JSONObject jsonObject = null;
                jsonObject=new JSONObject(sb.toString());

                String resultType=jsonObject.getString("resultType");


                switch(resultType){
                    case "list":            resultValue=list(jsonObject);break;
                    case "write":       resultValue=write(jsonObject);break;
                    case "detail":      resultValue=detail(jsonObject);break;
                    case "edit":        resultValue=edit(jsonObject);break;
                    case "delete":      resultValue=delete(jsonObject);break;
                }
            }else{
                Log.d(TAG, "요청처리에 문제가 발생하였습니다.");
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(buffw!=null){
                try {
                    buffw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(con!=null){
                con.disconnect();
                con=null;
            }
        }
        return resultValue;
    }


    public List list(JSONObject jsonObject) throws JSONException{
        ArrayList<Board> list = new ArrayList<Board>();

        /*ListView가 볼수 있는 형태인 ArrayList로 가공해놓자~*/
        JSONArray array=jsonObject.getJSONArray("boardList");

        for(int i=0;i<array.length();i++){
            Board dto = new Board();
            JSONObject obj= (JSONObject)array.get(i);

            dto.setBoard_id(obj.getInt("board_id"));
            dto.setWriter(obj.getString("writer"));
            dto.setTitle(obj.getString("title"));
            dto.setContent(obj.getString("content"));
            dto.setRegdate(obj.getString("regdate"));
            dto.setHit(obj.getInt("hit"));

            list.add(dto);
        }
        return list;
    }

    public Integer write(JSONObject jsonObject) throws JSONException{
        int result=0;
        result=jsonObject.getInt("result");
        return result;
    }
    public Board detail(JSONObject obj) throws JSONException{
        Board dto = new Board();

        dto.setBoard_id(obj.getInt("board_id"));
        dto.setWriter(obj.getString("writer"));
        dto.setTitle(obj.getString("title"));
        dto.setContent(obj.getString("content"));
        dto.setRegdate(obj.getString("regdate"));
        dto.setHit(obj.getInt("hit"));

        return dto;
    }
    public Integer edit(JSONObject jsonObject) throws JSONException{
        int result=0;
        result=jsonObject.getInt("result");
        return result;
    }
    public Integer delete(JSONObject jsonObject) throws JSONException{
        int result=0;
        result=jsonObject.getInt("result");
        return result;
    }
}