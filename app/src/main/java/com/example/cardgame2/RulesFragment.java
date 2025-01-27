package com.example.cardgame2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class RulesFragment extends Fragment {

    private TextView rulesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules, container, false);

        // rulesText = view.findViewById(R.id.rules_text);
        // rulesText.setText(R.string.game_rules_content);

        return view;
    }
}