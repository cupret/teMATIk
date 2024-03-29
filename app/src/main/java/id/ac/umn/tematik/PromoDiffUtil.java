package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class PromoDiffUtil extends DiffUtil.Callback {
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DATE = "DATE";
    public static final String IMG = "IMG";


    private List<Promo> oldList;
    private List<Promo> newList;

    public PromoDiffUtil(List<Promo> oldList, List<Promo> newList){
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
        Boolean isSame = oldList.get(oldItemPosition).getId().intValue() == newList.get(newItemPosition).getId().intValue();
        Log.d("DEBUG", "areItemsTheSame: " + isSame.toString());
        return isSame;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Boolean isSame = oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) == 0
                || oldList.get(oldItemPosition).getImages_url().get(0).compareTo(newList.get(newItemPosition).getImages_url().get(0)) == 0
                || oldList.get(oldItemPosition).getDescription().compareTo(newList.get(newItemPosition).getDescription()) == 0
                || oldList.get(oldItemPosition).getStart_date().compareTo(newList.get(newItemPosition).getStart_date()) == 0
                || oldList.get(oldItemPosition).getEnd_date().compareTo(newList.get(newItemPosition).getEnd_date()) == 0;

        if(oldList.get(oldItemPosition).getImages_url() != null && newList.get(newItemPosition).getImages_url() != null){
            if(isSame){
                if(oldList.get(oldItemPosition).getImages_url().size() != newList.get(newItemPosition).getImages_url().size())
                    isSame = false;
                else{
                    for(int i = 0; i < oldList.get(oldItemPosition).getImages_url().size(); i++){
                        if(oldList.get(oldItemPosition).getImages_url().get(i).compareTo(newList.get(newItemPosition).getImages_url().get(i)) != 0){
                            isSame = false;
                            break;
                        }
                    }
                }
            }
        }

        Log.d("DEBUG", "areContentsTheSame: " + isSame.toString());
        return isSame;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle bundle = new Bundle();
        if(oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) != 0)
            bundle.putString(NAME, newList.get(newItemPosition).getName());

        if(oldList.get(oldItemPosition).getStart_date().compareTo(newList.get(newItemPosition).getStart_date()) != 0
                || oldList.get(oldItemPosition).getEnd_date().compareTo(newList.get(newItemPosition).getEnd_date()) != 0){
            String date = newList.get(newItemPosition).getStart_date() + " - " + newList.get(newItemPosition).getEnd_date();
            bundle.putString(DATE, date);
        }

        if(oldList.get(oldItemPosition).getDescription().compareTo(newList.get(newItemPosition).getDescription()) != 0)
            bundle.putString(DESCRIPTION, newList.get(newItemPosition).getDescription());

        if(oldList.get(oldItemPosition).getImages_url() != null && newList.get(newItemPosition).getImages_url() != null){
            if(oldList.get(oldItemPosition).getImages_url().size() != newList.get(newItemPosition).getImages_url().size())
                bundle.putStringArrayList(IMG, newList.get(newItemPosition).getImages_url());
            else{
                for(int i = 0; i < oldList.get(oldItemPosition).getImages_url().size(); i++){
                    if(oldList.get(oldItemPosition).getImages_url().get(i).compareTo(newList.get(newItemPosition).getImages_url().get(i)) != 0){
                        bundle.putStringArrayList(IMG, newList.get(newItemPosition).getImages_url());
                        break;
                    }
                }
            }
        }

        return bundle;
    }
}
