package kr.insungjung.semifinalproject.datas;

import org.json.JSONException;
import org.json.JSONObject;

public class Bank {

    public int id;
    public String code;
    public String name;
    public String logo;

    // 생성자 아니고, 파싱해서 static 메소드 만들어야 한다.
    // JSON 을 가지고 Bank 클래스로 전환해주는 코드 작성
    public static Bank getBankFromJson(JSONObject bankJson) {

        // 리턴해주기 위한 Bank 객체 생성. 내부 데이터는 모두 빈 상태
        Bank bankObject = new Bank();

        // 빈 데이터를 bankJson으로 부터 추출해서 채운다.
        try {
            bankObject.id = bankJson.getInt("id");
            bankObject.code = bankJson.getString("code");
            bankObject.name = bankJson.getString("name");
            bankObject.logo = bankJson.getString("logo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bankObject;
    }

}
