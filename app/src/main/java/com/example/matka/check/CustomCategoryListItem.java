package com.example.matka.check;

import android.graphics.Color;
import android.view.View;

/**
 * Created by matka on 15/03/17.
 */
public class CustomCategoryListItem {
    private String categoryText;

    public CustomCategoryListItem(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    @Override
    public String toString() {
        return categoryText;
    }

}
