package com.example.cardgame2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cardgame2.models.CardItem;
import java.util.ArrayList;
import java.util.List;

public class PlayFragment extends Fragment {

    private RecyclerView playerHandRecyclerView;
    private RecyclerView opponentHandRecyclerView;
    private RecyclerView playAreaRecyclerView;
    private final List<CardItem> playerHand = new ArrayList<>();
    private final List<CardItem> opponentHand = new ArrayList<>();
    private final List<CardItem> playArea = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        // Initialize RecyclerViews
        playerHandRecyclerView = view.findViewById(R.id.player_area);
        opponentHandRecyclerView = view.findViewById(R.id.opponent_area);
        playAreaRecyclerView = view.findViewById(R.id.play_area);

        // Set up RecyclerViews
        setupRecyclerView(playerHandRecyclerView, playerHand, true); // Player's hand
        setupRecyclerView(opponentHandRecyclerView, opponentHand, false); // Opponent's hand
        setupRecyclerView(playAreaRecyclerView, playArea, false); // Play area

        // Load sample Pokémon cards
        loadSampleCards();

        // Display cards
        displayPlayerHand();
        displayOpponentHand();

        return view;
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<CardItem> cards, boolean isPlayer) {
        // Use a GridLayoutManager with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Create and set the adapter
        CardAdapter adapter = new CardAdapter(cards, isPlayer);
        recyclerView.setAdapter(adapter);
    }

    private void loadSampleCards() {
        // Add sample Pokémon cards to player and opponent hands
        playerHand.add(new CardItem("Pikachu", "Electric", 70, 40, R.drawable.pikachu, R.drawable.pokemon_card_bg_yellow, R.drawable.rarity_common));
        playerHand.add(new CardItem("Charizard", "Fire", 120, 80, R.drawable.charizard, R.drawable.pokemon_card_bg_red, R.drawable.rarity_rare));
        opponentHand.add(new CardItem("Blastoise", "Water", 130, 70, R.drawable.blastoise, R.drawable.pokemon_card_bg_blue, R.drawable.rarity_rare));
    }

    private void displayPlayerHand() {
        // Notify the adapter that data has changed
        if (playerHandRecyclerView.getAdapter() != null) {
            playerHandRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void displayOpponentHand() {
        // Notify the adapter that data has changed
        if (opponentHandRecyclerView.getAdapter() != null) {
            opponentHandRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void onCardClick(CardItem card) {
        try {
            playCard(card);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
        }
    }

    private void playCard(CardItem card) {
        // Move the card from the player's hand to the play area
        playerHand.remove(card);
        playArea.add(card);

        // Notify adapters of data changes
        displayPlayerHand();
        if (playAreaRecyclerView.getAdapter() != null) {
            playAreaRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    // RecyclerView Adapter
    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
        private final List<CardItem> cards;
        private final boolean isPlayer;

        CardAdapter(List<CardItem> cards, boolean isPlayer) {
            this.cards = cards;
            this.isPlayer = isPlayer;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CardItem item = cards.get(position);

            // Bind card data to the view
            holder.nameTextView.setText(item.getName());
            holder.typeTextView.setText(item.getType());
            holder.imageView.setImageResource(item.getImageRes());
            holder.hpTextView.setText("HP: " + item.getHp());
            holder.attackTextView.setText("ATK: " + item.getAttack());

            // Visual Enhancements
            holder.cardView.setBackgroundResource(item.getBackgroundRes());
            holder.rarityBadge.setImageResource(item.getRarityRes());

            // Add elevation shadow
            holder.cardView.setCardElevation(8f);

            // Click Animation
            holder.itemView.setOnClickListener(v -> {
                ScaleAnimation scale = new ScaleAnimation(1f, 0.9f, 1f, 0.9f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
                scale.setDuration(100);
                scale.setRepeatCount(1);
                scale.setRepeatMode(Animation.REVERSE);
                holder.itemView.startAnimation(scale);

                // Handle card click (e.g., play the card)
                if (isPlayer) {
                    onCardClick(item);
                }
            });

            // Long press for special effect
            holder.itemView.setOnLongClickListener(v -> {
                animateHolographicEffect(holder.cardView);
                return true;
            });
        }

        private void animateHolographicEffect(View view) {
            // Implement shimmer animation here
            ScaleAnimation shimmer = new ScaleAnimation(1f, 1.05f, 1f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
            shimmer.setDuration(300);
            shimmer.setRepeatCount(1);
            shimmer.setRepeatMode(Animation.REVERSE);
            view.startAnimation(shimmer);
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView nameTextView;
            TextView typeTextView;
            TextView hpTextView;
            TextView attackTextView;
            ImageView imageView;
            ImageView rarityBadge;

            ViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardView);
                nameTextView = itemView.findViewById(R.id.tv_name);
                typeTextView = itemView.findViewById(R.id.tv_type);
                hpTextView = itemView.findViewById(R.id.tv_hp);
                attackTextView = itemView.findViewById(R.id.tv_attack);
                imageView = itemView.findViewById(R.id.iv_image);
                rarityBadge = itemView.findViewById(R.id.iv_rarity);
            }
        }
    }
}