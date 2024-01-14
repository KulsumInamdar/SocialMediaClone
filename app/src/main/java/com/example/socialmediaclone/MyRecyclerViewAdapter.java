package com.example.socialmediaclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
    //adding the data to the adapter
    private ArrayList<ProfileObject> mCourses;
    private LayoutInflater mlayoutInflater;
    private ItemCLickedInterfaced mitemCLickedInterfaced;
    private  ArrayList<String> mCurrentFollowers;

    MyRecyclerViewAdapter(Context context, ArrayList<ProfileObject> data, ItemCLickedInterfaced itemCLickedInterfaced, ArrayList<String> currentFollowers ) {
        this.mlayoutInflater= LayoutInflater.from(context);
        this.mCourses = data;
        this.mitemCLickedInterfaced = itemCLickedInterfaced;
        this.mCurrentFollowers = currentFollowers;
    }

    @NonNull
    @Override
    //here we pass the layout of individual row
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the view which one
        View view = mlayoutInflater.inflate(R.layout.recycler_view_items,parent,false);

        //passed it in viewholder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //adding data
        ProfileObject courses =  mCourses.get(position);
        String text = courses.getName();
        holder.courseeTextView.setText(text);

        if(mCurrentFollowers != null)
        {
            for(int i =0; i<mCurrentFollowers.size(); i++)
            {
                if(mCurrentFollowers.get(i).equalsIgnoreCase(courses.getName()))
                {
                    holder.courseeTextView.setChecked(true);
                    holder.courseeTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public String getName(int position)
    {
        return mCourses.get(position).getName();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CheckedTextView courseeTextView;
        public ViewHolder(@NonNull View itemView) {
            //here we have only the text view so we have only passed the text view
            super(itemView);
            courseeTextView = itemView.findViewById(R.id.textviewUsers);
            courseeTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //unfollow
           if(courseeTextView.isChecked())
            {
                courseeTextView.setChecked(false);
                courseeTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                if (mitemCLickedInterfaced!=null)
                {
                    mitemCLickedInterfaced.onItemCLicked(false, getAdapterPosition());
                }
            }
            //follow
            else {
                courseeTextView.setChecked(true);
                courseeTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                if (mitemCLickedInterfaced!=null) {
                    mitemCLickedInterfaced.onItemCLicked(true, getAdapterPosition());
                }
            }

        }
    }

    //the person using this interface will define what it will do,interface do not have an object and implementation
    public interface ItemCLickedInterfaced
    {
        void onItemCLicked(boolean isChecked, int position);
    }
}
