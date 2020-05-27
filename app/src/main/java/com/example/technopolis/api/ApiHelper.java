package com.example.technopolis.api;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.user.model.User;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ApiHelper {

    private Queue<Integer> messages = new LinkedBlockingQueue<>();

    public Integer getMessage() {
        return messages.poll();
    }

    public void setMessage(Integer message) {
        messages.offer(message);
    }

    public int size() {
        return messages.size();
    }

    public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean showMessageIfExist(Activity activity, MailApi api, ScreenNavigator screenNavigator, Runnable load) {
        Integer message = getMessage();
        if (message != null) {
            if (message == R.string.networkError) {
                if (!isOnline(activity)) {
                    activity.runOnUiThread(() -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
                }
            } else if (message == R.string.reloadRequest) {
                clear();
                reloadAuthToken(api);
                load.run();
            } else if (message == R.string.authFailed) {
                activity.runOnUiThread(() -> screenNavigator.changeAuthorized(false));
            }
            return true;
        }
        return false;
    }

    public static void reloadAuthToken(MailApi api) {
        User user = api.getUser();
        AuthDto authDto = api.requestAuthDto(user.getLogin(), user.getPassword());
        user.setAuth_token(authDto.getAuth_token());
    }

    public void clear() {
        messages.clear();
    }
}
