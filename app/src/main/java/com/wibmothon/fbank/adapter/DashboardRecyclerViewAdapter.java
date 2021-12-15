package com.wibmothon.fbank.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wibmothon.fbank.R;
import com.wibmothon.fbank.model.DashboardModel;

import java.util.List;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DashboardModel> dashboardModelList;
    Context context;

    public DashboardRecyclerViewAdapter(List<DashboardModel> dashboardModelList, Context context) {
        this.dashboardModelList = dashboardModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
                return new DiscoverType1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DashboardModel discoverServices = dashboardModelList.get(position);
        if (discoverServices != null) {
            ((DiscoverType1ViewHolder) holder).txt_title.setText(discoverServices.getTitleText());
            ((DiscoverType1ViewHolder) holder).txt_subtitle.setText(discoverServices.getSubTitle());
            ((DiscoverType1ViewHolder) holder).item_image.setImageResource(discoverServices.getImageSrc());
            ((DiscoverType1ViewHolder) holder).titleSubText1.setText(discoverServices.getTitleSubText1());
            ((DiscoverType1ViewHolder) holder).titleSubText2.setText(discoverServices.getTitleSubText2());

            if (position == 1) {
                SpannableString content = new SpannableString(discoverServices.getTitleSubText1());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                ((DiscoverType1ViewHolder) holder).titleSubText1.setText(content);
            }

            if (discoverServices.getTitleSubText2().equals("0")) {
                ((DiscoverType1ViewHolder) holder).titleSubText2.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dashboardModelList.size();
    }

    private class DiscoverType1ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;
        TextView txt_subtitle;
        ImageView item_image;
        TextView btn_perform_action;
        TextView titleSubText1;
        TextView titleSubText2;

        public DiscoverType1ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_subtitle = itemView.findViewById(R.id.txt_subtitle);
            item_image = itemView.findViewById(R.id.item_image);
            btn_perform_action = itemView.findViewById(R.id.btn_perform_action);
            titleSubText1 = itemView.findViewById(R.id.titleSubText1);
            titleSubText2 = itemView.findViewById(R.id.titleSubText2);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private DashboardRecyclerViewAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DashboardRecyclerViewAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
