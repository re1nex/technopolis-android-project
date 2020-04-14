package com.example.technopark;

import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements View.OnLongClickListener {

    private ClipboardManager myClipboard;
    private ClipData myClip;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        ((BaseActivity)getActivity()).setBarVisible(View.VISIBLE);
        myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            showBackButton(v);
        }

//        Copy links
        v.findViewById(R.id.phone_number).setOnLongClickListener(this);
        v.findViewById(R.id.email).setOnLongClickListener(this);
        v.findViewById(R.id.odnoklassniki).setOnLongClickListener(this);
        v.findViewById(R.id.github).setOnLongClickListener(this);
        v.findViewById(R.id.vkontakte).setOnLongClickListener(this);

//        Exit
        v.findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog exitDialog = new Dialog(getActivity(), R.style.ExitDialogAnimation);
                exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
                exitDialog.setContentView(R.layout.exit_popup_view);
                exitDialog.setCancelable(true);
                exitDialog.show();

                exitDialog.findViewById(R.id.exitButtonApprove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                      TODO: make log out
                        Toast.makeText(getActivity(), "log out in progress", Toast.LENGTH_SHORT).show();
                    }
                });
                exitDialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
            }
        });

//        Show group members
        v.findViewById(R.id.group_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupListFragment groupListFragment = new GroupListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_content, groupListFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        return v;
    }

    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        String text = textView.getText().toString();

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(getActivity(), R.string.copied, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showBackButton(View v) {
        View toolBar = v.findViewById(R.id.toolbar);

    }
}
