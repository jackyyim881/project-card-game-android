package com.example.cardgame2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView playerName, playerLevel, experienceText, winsCount, lossesCount, winRate;
    private ProgressBar experienceProgress;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        playerName = view.findViewById(R.id.player_name);
        playerLevel = view.findViewById(R.id.player_level);
        experienceProgress = view.findViewById(R.id.experience_progress);
        experienceText = view.findViewById(R.id.experience_text);
        winsCount = view.findViewById(R.id.wins_count);
        lossesCount = view.findViewById(R.id.losses_count);
        winRate = view.findViewById(R.id.win_rate);
        profileImage = view.findViewById(R.id.profile_image);

        // Set player data
        setPlayerData("Player 1", 10, 500, 1000, 150, 50);

        return view;
    }

    private void setPlayerData(String name, int level, int currentExp, int maxExp, int wins, int losses) {
        playerName.setText(name);
        playerLevel.setText("Level " + level);
        experienceProgress.setProgress((currentExp * 100) / maxExp);
        experienceText.setText(currentExp + "/" + maxExp + " XP");
        winsCount.setText(String.valueOf(wins));
        lossesCount.setText(String.valueOf(losses));

        // Calculate win rate
        int totalGames = wins + losses;
        double winRateValue = (totalGames > 0) ? ((double) wins / totalGames) * 100 : 0;
        winRate.setText(String.format("%.0f%%", winRateValue));
    }
}