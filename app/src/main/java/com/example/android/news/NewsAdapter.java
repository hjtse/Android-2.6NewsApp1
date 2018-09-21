package com.example.android.news;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    private static final String DATETIME_SEPARATOR = "T";
    private static final String Title_SEPARATOR = "|";

    String CurrentDate;

    public NewsAdapter(Context context, ArrayList<News> New) {
        super(context, 0, New);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < getCount()) {
            News currentNews = getItem(position);

            holder.SectionView.setText(currentNews.getSectionName());


            String DateTime = currentNews.getDateTime();
            if (DateTime.contains(DATETIME_SEPARATOR)) {
                String[] parts = DateTime.split(DATETIME_SEPARATOR);
                CurrentDate = parts[0];
            }
            holder.DateView.setText(CurrentDate);

            String contributor = currentNews.getAuthor();
            holder.ContributorView.setText(contributor);

            String Title = currentNews.getTitle();
            if (Title.contains(currentNews.getAuthor())) {
                if (Title.contains(Title_SEPARATOR)) {
                    Title = Title.replace(Title_SEPARATOR, "");
                    Title = Title.replace(currentNews.getAuthor(), "");
                }
            }
            holder.TitleView.setText(Title);
        }
        return convertView;
    }

    public static class ViewHolder {
        private TextView SectionView;
        private TextView ContributorView;
        private TextView DateView;
        private TextView TitleView;

        public ViewHolder(View itemView) {
            SectionView = (TextView) itemView.findViewById(R.id.section_name);
            ContributorView = (TextView) itemView.findViewById(R.id.author);
            DateView = (TextView) itemView.findViewById(R.id.date);
            TitleView = (TextView) itemView.findViewById(R.id.title);
        }
    }
}