package com.ifyou.nowincinema.presentation.view;

import com.ifyou.nowincinema.presentation.vo.MovieShow;

import java.util.List;

public interface MovieShowView extends BaseListView {

    void showResultsItemList(List<MovieShow> showingsList);
}
