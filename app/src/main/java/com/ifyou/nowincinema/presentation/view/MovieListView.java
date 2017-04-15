package com.ifyou.nowincinema.presentation.view;

import com.ifyou.nowincinema.presentation.vo.Movies;

import java.util.List;

public interface MovieListView extends BaseListView {

    void showResultsItemList(List<Movies> moviesList);

}
