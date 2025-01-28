package com.example.cardgame2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.cardgame2.database.CardDatabaseHelper;
import com.example.cardgame2.models.CardItem;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView playerName, playerLevel, experienceText, winsCount, lossesCount, winRate;
    private ProgressBar experienceProgress;
    private ImageView profileImage;
    private PokemonAdapter adapter;
    private List<CardItem> cardList;
    private CardDatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        initializeViews(view);

        // Set player data
        setPlayerData("Player 1", 10, 500, 1000, 150, 50);

        // Database and RecyclerView setup
        setupDatabaseAndRecyclerView(view);

        return view;
    }

    private void initializeViews(View view) {
        playerName = view.findViewById(R.id.player_name);
        playerLevel = view.findViewById(R.id.player_level);
        experienceProgress = view.findViewById(R.id.experience_progress);
        experienceText = view.findViewById(R.id.experience_text);
        winsCount = view.findViewById(R.id.wins_count);
        lossesCount = view.findViewById(R.id.losses_count);
        winRate = view.findViewById(R.id.win_rate);
        profileImage = view.findViewById(R.id.profile_image);
    }

    private void setupDatabaseAndRecyclerView(View view) {
        dbHelper = new CardDatabaseHelper(requireContext());
        cardList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_pokemon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(cardList);
        recyclerView.setAdapter(adapter);

        loadCardsFromDatabase();
    }

    private void setPlayerData(String name, int level, int currentExp, int maxExp, int wins, int losses) {
        playerName.setText(name);
        playerLevel.setText(getString(R.string.level_format, level));
        experienceProgress.setMax(maxExp);
        experienceProgress.setProgress(currentExp);
        experienceText.setText(getString(R.string.xp_format, currentExp, maxExp));
        winsCount.setText(String.valueOf(wins));
        lossesCount.setText(String.valueOf(losses));

        calculateWinRate(wins, losses);
    }

    private void calculateWinRate(int wins, int losses) {
        int totalGames = wins + losses;
        double winRateValue = totalGames > 0 ? ((double) wins / totalGames) * 100 : 0;
        winRate.setText(getString(R.string.percentage_format, winRateValue));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCardsFromDatabase() {
        cardList.clear();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
            CardDatabaseHelper.TABLE_CARDS,
            null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CardItem cardItem = createCardItemFromCursor(cursor);
                    cardList.add(cardItem);
                } while (cursor.moveToNext());

                adapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("Range")
    private CardItem createCardItemFromCursor(Cursor cursor) {
        return new CardItem(
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_NAME)),
            cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_TYPE)),
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_HP)),
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_ATTACK)),
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_IMAGE_RES)),
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_BACKGROUND_RES)),
            cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_RARITY_RES))
        );
    }

    // Adapter class should be in a separate file, but included here for completeness

}