package controllers.ui.summary;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import model.OrderDetail;

public class UpdateSummaryRecycleView extends DiffUtil.Callback {

    private List<OrderDetail> oldList;
    private List<OrderDetail> newList;

    public UpdateSummaryRecycleView(List<OrderDetail> oldList, List<OrderDetail>newList){
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
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
