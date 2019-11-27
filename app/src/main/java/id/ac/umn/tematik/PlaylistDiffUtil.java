package id.ac.umn.tematik;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class PlaylistDiffUtil extends DiffUtil.Callback {
    public static final String NAME = "NAME";

    private List<PlayList.Music> oldList;
    private List<PlayList.Music> newList;

    public PlaylistDiffUtil(List<PlayList.Music> oldList, List<PlayList.Music> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) == 0;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle bundle = new Bundle();
        if(oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) != 0)
            bundle.putString(NAME, newList.get(newItemPosition).getName());

        return bundle;
    }
}
