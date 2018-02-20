package acodexm.cleanweather.view.fragments;


import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import acodexm.cleanweather.R;
import acodexm.cleanweather.data.model.LocationData;
import acodexm.cleanweather.view.viewmodel.ModelViewControl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SidebarAdapter extends RecyclerView.Adapter<SidebarAdapter.SidebarViewHolder> {
    private List<LocationData> mSidebarListItems = new ArrayList<>();
    private SidebarUserClickAction mSidebarUserClickAction;
    private ModelViewControl viewControl;

    public void setSidebarClickListener(SidebarUserClickAction sidebarUserClickAction, ModelViewControl viewControl) {
        this.mSidebarUserClickAction = sidebarUserClickAction;
        this.viewControl = viewControl;
    }

    public void addSidebarListItem(LocationData location) {
        int i = 0;
        for (LocationData city : mSidebarListItems) {
            if (city.equals(location)) i++;
        }
        if (i == 0) {
            mSidebarListItems.add(location);
        }
        notifyDataSetChanged();
    }

    public void setSidebarListItems(List<LocationData> mList) {
        if (mList != null) {
            this.mSidebarListItems.clear();
            this.mSidebarListItems.addAll(mList);
        }
        notifyDataSetChanged();
    }


    public void deleteItem(LocationData location) {
        mSidebarListItems.remove(mSidebarListItems.indexOf(location));
        viewControl.deleteLocation(location);
    }

    @Override
    public SidebarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_location, parent, false);
        return new SidebarViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(SidebarViewHolder holder, int position) {
        LocationData location = mSidebarListItems.get(position);
        holder.mTextView.setText(location.getLocation());
        holder.mLocation = location;
    }

    @Override
    public int getItemCount() {
        return mSidebarListItems.size();
    }

    public interface SidebarUserClickAction {
        void onSidebarListItemClick(LocationData locationData);

    }

    class SidebarViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nav_text_location)
        TextView mTextView;
        @BindView(R.id.row_delete_btn)
        AppCompatImageView mDeleteBtn;
        LocationData mLocation;

        SidebarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick
        void onSidebarViewClick() {
            mSidebarUserClickAction.onSidebarListItemClick(mLocation);
            notifyDataSetChanged();
        }

        @OnClick(R.id.row_delete_btn)
        void deleteLocation() {
            deleteItem(mLocation);
            notifyDataSetChanged();
        }

    }

}
