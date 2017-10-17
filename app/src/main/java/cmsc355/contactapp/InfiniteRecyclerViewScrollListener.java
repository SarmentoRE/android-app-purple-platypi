package cmsc355.contactapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class InfiniteRecyclerViewScrollListener extends RecyclerView.OnScrollListener
{
    //The minimum number of contacts below current position that triggers loading of more contact info
    private int loadingThreshold = 5;
    //The current offset index of data you have loaded
    private int currentPage = 0;
    //The total number of contacts after the last load
    private int prevContactCount = 0;
    //Boolean value that is marked true if the last set of data is still loading and false otherwise
    private boolean isLoading = true;
    //The current index of the starting page
    private int startingIndex = 0;

    //LayoutManager item to be used later in code
    RecyclerView.LayoutManager layoutManagerVar;

    //Section of code that sets up necessary constructors for the class based on different LayoutManager types
    public InfiniteRecyclerViewScrollListener(LinearLayoutManager linLayMan)
    {this.layoutManagerVar = linLayMan;}

    public InfiniteRecyclerViewScrollListener(GridLayoutManager gridLayMan)
    {
        this.layoutManagerVar = gridLayMan;
        loadingThreshold = loadingThreshold * gridLayMan.getSpanCount();
    }

    public InfiniteRecyclerViewScrollListener(StaggeredGridLayoutManager stagGridLayMan)
    {
        this.layoutManagerVar = stagGridLayMan;
        loadingThreshold = loadingThreshold * stagGridLayMan.getSpanCount();
    }

    //Helper method to be used in Override of onScrolled method
    public int getLastVisibleItem(int[] finalVisibleItemPosition)
    {
        int maxSize = 0;
        for(int i = 0; i < finalVisibleItemPosition.length; i++)
        {
            if (i == 0)
            {maxSize = finalVisibleItemPosition[i];}
            else if (finalVisibleItemPosition[i] > maxSize)
            {maxSize = finalVisibleItemPosition[i];}
        }
        return maxSize;
    }//getFinalVisibleItem method

    @Override
    public void onScrolled(RecyclerView view, int x, int y)
    {
        int finalVisibleItemPosition = 0;
        int completeContactCount = layoutManagerVar.getItemCount();

        if (layoutManagerVar instanceof StaggeredGridLayoutManager)
        {
            int[] finalVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManagerVar).findLastVisibleItemPositions(null);
            //returns the largest element in the list
            finalVisibleItemPosition = getLastVisibleItem(finalVisibleItemPositions);
        }
        else if (layoutManagerVar instanceof GridLayoutManager)
        {finalVisibleItemPosition = ((GridLayoutManager) layoutManagerVar).findLastVisibleItemPosition();}
        else if (layoutManagerVar instanceof LinearLayoutManager)
        {finalVisibleItemPosition = ((LinearLayoutManager)layoutManagerVar).findLastVisibleItemPosition();}

        //If the complete contact count is zero but the previous contact count isn't, assume this list
        //is somehow invalid and should be reset
        if (completeContactCount < prevContactCount)
        {
            this.currentPage = this.startingIndex;
            this.prevContactCount = completeContactCount;
            if (completeContactCount == 0)
            {this.isLoading = true;}
        }

        //If it is still loading, we check to see if the contact count has changed. If it has, we conclude
        //that the page has finished loading and update the current page number and total contact count
        if (isLoading && (completeContactCount >prevContactCount))
        {
            isLoading = false;
            prevContactCount = completeContactCount;
        }

        //If it isn't currently loading, we check to see if we have breached the loading threshold and need
        //to reload more data. If we need to reload more data, we execute the necessary onLoadMore method to
        //fetch the data. The threshold should reflect how many total columns there are
        if (!isLoading && (finalVisibleItemPosition + loadingThreshold) > completeContactCount)
        {
            currentPage++;
            onLoadMore(currentPage, completeContactCount, view);
            isLoading = true;
        }

    }//onScrolled method

    //Method to be called whenever a new search is being performed
    public void reset()
    {
        this.currentPage = this.startingIndex;
        this.prevContactCount = 0;
        this.isLoading = true;
    }

    //The process for loading contact information in from the SQL DB
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);


}//InfiniteRecyclerViewScrollListener class
