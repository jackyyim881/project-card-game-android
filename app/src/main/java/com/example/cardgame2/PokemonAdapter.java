package com.example.cardgame2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cardgame2.models.CardItem;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<CardItem> cardList;

    public PokemonAdapter(List<CardItem> cardList) {
        this.cardList = cardList;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.pokemon_card_item, parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        CardItem card = cardList.get(position);
        holder.name.setText(card.getName());
        holder.attack.setText("Attack: " + card.getAttack());
        holder.hp.setText("HP: " + card.getHp());
        holder.cardImage.setImageResource(card.getImageRes());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView name, attack, hp;
        ImageView cardImage;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_name);
            attack = itemView.findViewById(R.id.card_attack);
            hp = itemView.findViewById(R.id.card_hp);
            cardImage = itemView.findViewById(R.id.card_image);
        }
    }
}
