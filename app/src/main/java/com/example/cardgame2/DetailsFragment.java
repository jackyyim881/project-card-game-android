package com.example.cardgame2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cardgame2.models.CardItem;

public class DetailsFragment extends Fragment {

    private static final String ARG_CARD_ITEM = "card_item";

    private CardItem cardItem;

    public static DetailsFragment newInstance(CardItem cardItem) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_ITEM, cardItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardItem = getArguments().getParcelable(ARG_CARD_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize views
        ImageView imageView = view.findViewById(R.id.iv_detail_image);
        TextView nameTextView = view.findViewById(R.id.tv_detail_name);
        TextView typeTextView = view.findViewById(R.id.tv_detail_type);
        TextView hpTextView = view.findViewById(R.id.tv_detail_hp);
        TextView attackTextView = view.findViewById(R.id.tv_detail_attack);

        // Set data
        if (cardItem != null) {
            imageView.setImageResource(cardItem.getImageRes());
            nameTextView.setText(cardItem.getName());
            typeTextView.setText("Type: " + cardItem.getType());
            hpTextView.setText("HP: " + cardItem.getHp());
            attackTextView.setText("Attack: " + cardItem.getAttack());
        }

        return view;
    }
}