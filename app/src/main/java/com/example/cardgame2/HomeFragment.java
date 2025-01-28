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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<CardItem> cards = new ArrayList<>();
        // Added HP, Attack, Rarity, and Gradient
        cards.add(new CardItem("Charizard", "Fire", 180, 120, R.drawable.charizard,
            R.drawable.fire_gradient, R.drawable.rarity_epic));
        cards.add(new CardItem("Blastoise", "Water", 160, 110, R.drawable.blastoise,
            R.drawable.water_gradient, R.drawable.rarity_rare));
        cards.add(new CardItem("Venusaur", "Grass", 170, 115, R.drawable.venusaur,
            R.drawable.grass_gradient, R.drawable.rarity_common));
        cards.add(new CardItem("Pikachu", "Electric", 140, 90, R.drawable.pikachu,
            R.drawable.electric_gradient, R.drawable.rarity_common));

        CardAdapter adapter = new CardAdapter(cards);
        recyclerView.setAdapter(adapter);
    }


    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        private List<CardItem> items;

        CardAdapter(List<CardItem> items) {
            this.items = items;
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
            CardItem item = items.get(position);

            // Basic Info
            holder.nameTextView.setText(item.getName());
            holder.typeTextView.setText(item.getType());
            holder.imageView.setImageResource(item.getImageRes());

            // Stats
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

                // Add card interaction logic here
                animateClick(holder.itemView); // Animate the click
                showPokemonDetails(item); // Show details

            });

            // Long press for special effect
            holder.itemView.setOnLongClickListener(v -> {
                // Trigger holographic effect
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
        private void animateClick(View view) {
            ScaleAnimation scale = new ScaleAnimation(1f, 0.9f, 1f, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(100);
            scale.setRepeatCount(1);
            scale.setRepeatMode(Animation.REVERSE);
            view.startAnimation(scale);
        }

        private void showPokemonDetails(CardItem item) {
            // Create DetailsFragment instance
            DetailsFragment detailsFragment = DetailsFragment.newInstance(item );

            // Use FragmentManager to replace the current fragment
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailsFragment) // Replace with your container ID
                .addToBackStack(null) // Add to back stack for navigation
                .commit();
        }

        // Method to animate holographic effect

        @Override
        public int getItemCount() {
            return items.size();
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