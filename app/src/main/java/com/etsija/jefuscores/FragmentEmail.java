package com.etsija.jefuscores;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentEmail extends Fragment {

    JEFUScores app;
    private static final String TAG = "FragmentEmail";
    private EditText etEmail1, etEmail2, etEmail3, etEmail4, etEmail5;
    private Button btnSend;
    private String[] recipients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the EditText elements from the layout
        etEmail1 = view.findViewById(R.id.etEmail1);
        etEmail2 = view.findViewById(R.id.etEmail2);
        etEmail3 = view.findViewById(R.id.etEmail3);
        etEmail4 = view.findViewById(R.id.etEmail4);
        etEmail5 = view.findViewById(R.id.etEmail5);
        btnSend = view.findViewById(R.id.btnSend);

        // Initialise them
        // Get access to global variables
        app = (JEFUScores) getActivity().getApplication();
        etEmail1.setText(app.getEmail1());
        etEmail2.setText(app.getEmail2());
        etEmail3.setText(app.getEmail3());
        etEmail4.setText(app.getEmail4());
        etEmail5.setText(app.getEmail5());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recipients
                String[] recipients = findRecipients();

                // Subject
                String subject = "Gamelogs from JEFUScores";

                // Compile the game log into email body
                String body = app.getEmailLogs();

                sendMail(recipients, subject, body);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Listen to changes in the settings EditText fields
        // and save the changed values to global variables
        etEmail1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail1(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etEmail2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail2(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etEmail3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail3(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etEmail4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail4(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
        etEmail5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
                // blank
            }
            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                app.setEmail5(c.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // blank
            }
        });
    }

    protected void sendMail(String[] to, String subject, String body) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] findRecipients() {
        List<String> mStrings = new ArrayList<String>();
        mStrings.add(app.getEmail());
        if (app.getEmail1().length() > 0) mStrings.add(app.getEmail1());
        if (app.getEmail2().length() > 0) mStrings.add(app.getEmail2());
        if (app.getEmail3().length() > 0) mStrings.add(app.getEmail3());
        if (app.getEmail4().length() > 0) mStrings.add(app.getEmail4());
        if (app.getEmail5().length() > 0) mStrings.add(app.getEmail5());

        Log.d(TAG, mStrings.toString());

        String[] ret = new String[mStrings.size()];
        ret = mStrings.toArray(ret);
        return ret;
    }
}
