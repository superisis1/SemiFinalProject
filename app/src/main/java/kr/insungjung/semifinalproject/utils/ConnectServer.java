package kr.insungjung.semifinalproject.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectServer {

    // 서버의 근본 주소. => 프로젝트마다 서버를 확인, 주소만 변경
    private final static String BASE_URL = "http://delivery-dev-389146667.ap-northeast-2.elb.amazonaws.com";

    // 서버의 응답을 처리하는 역할을 담당하는 인터페이스
    public interface JsonResponseHandler {

        void onResponse(JSONObject json);
    }

    /**
     * 사용자 로그인 정보 - 아이디, 비번
     * (Post 방식)
     *
     * @param context
     * @param user_id
     * @param password
     * @param handler
     */
    public static void postRequestSignIn(Context context, String user_id, String password, final JsonResponseHandler handler) {

        // 클라이언트 역할임은 무슨 메쏘드이든 동일. 항상 복붙
        OkHttpClient client = new OkHttpClient();

        // POST 메쏘드는 urlBuilder 가 아니라, RequestBody 를 Build.
        // formData 에 파라미터를 첨부하는 코드.
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", user_id)
                .add("password", password)
                .build();

        // 실제 Request 를 생성, 서버로 떠날 준비.
        Request request = new Request.Builder()
                .url(BASE_URL + "/auth")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();

                Log.d("서버응답내용(로그인정보)", responseContent);

                try {
                    // 받아온 응답을 JSON 객체로 변환
                    JSONObject json = new JSONObject(responseContent);

                    if (handler != null) {
                        // 화면에서 처리하는 코드가 있으면 실행시켜줌.
                        handler.onResponse(json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 사용자 상세정보
     * (Get 방식)
     *
     * @param context
     * @param token
     * @param handler
     */
    public static void getRequestMeInfo(Context context, String token, final JsonResponseHandler handler) {

        // 서버 - 클라이언트 (앱)
        OkHttpClient client = new OkHttpClient();

        // URL 설정 => 목적지 설정
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/v2/me_info").newBuilder();
        // ※ GET, DELETE 메쏘드는 필요 파리미터를 URL 에 담아줘야함.
        // 이 담는과정을 쉽게 하려고 urlBuilder 를 사용
        // 실제로 서버에 접근하는 완성된 url
        String requestURL = urlBuilder.build().toString();
        // 완성된 url로 접근하는 request를 생성.
        Request request = new Request.Builder()
                .header("X-Http-Token", token)
                .url(requestURL) // post등의 메쏘드를 안쓰면, 기본적으로 GET
                .build();
        // 만들어진 Request를 실제로 서버에 요청.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseContent = response.body().string();

                Log.d("서버응답내용(사용자상세정보)", responseContent);

                try {
//                    받아온 응답을 JSON 객체로 변환
                    JSONObject json = new JSONObject(responseContent);

                    if (handler != null) {
//                        화면에서 처리하는 코드가 있으면 실행시켜줌.
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 은행 리스트
     * (Get 방식)
     *
     * @param context
     * @param handler
     */
    public static void getRequestInfoBank(Context context, /* 필요 변수들, */ final JsonResponseHandler handler) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/info/bank").newBuilder();

        String requestURL = urlBuilder.build().toString();
        Request request = new Request.Builder().url(requestURL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseContent = response.body().string();

                Log.d("서버응답내용(은행리스트)", responseContent);

                try {
                    // 받아온 응답을 JSON 객체로 변환
                    // 제일 큰 덩어리를 여기서 파싱하고 그다음 내용은 핸들러에서 처리하도록 미룬다. - 인터페이스 구현
                    JSONObject json = new JSONObject(responseContent);

                    if (handler != null) {
                        // 화면에서 처리하는 코드가 있으면 실행시켜줌
                        handler.onResponse(json);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });

    }

}









