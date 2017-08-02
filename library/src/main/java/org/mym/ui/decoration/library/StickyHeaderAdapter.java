/*
 * Copyright 2014 Eduardo Barrenechea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mym.ui.decoration.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * The adapter to assist the {@link StickyHeaderDecoration} in creating and binding the header views.
 *
 * @param <T> the header view holder
 */
public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {

    /**
     * Indicate this item has no corresponding header.
     */
    long NO_HEADER = -1L;

    /**
     * Returns the header id for the item at the given position.
     *
     * @param childAdapterPosition the item adapter position
     * @return the header id, or {@link #NO_HEADER} if this item has no header.
     */
    long getHeaderId(int childAdapterPosition);

    /**
     * Creates a new header ViewHolder.
     *
     * @param parent the header's view parent, typically the RecyclerView
     * @return a view holder for the created view
     */
    @NonNull
    T onCreateHeaderViewHolder(ViewGroup parent);

    /**
     * Display the data at the specified position.
     * <p>
     *     PLEASE NOTE THE PARAM IS CHILD POSITION!
     *     IF YOU WANT TO USE HEADER ID, PLEASE CALL YOUR {@link #getHeaderId(int)}.
     * </p>
     * @param holder the header view holder
     * @param childAdapterPosition the child item position, can be used to retrieve header id.
     */
    void onBindHeaderViewHolder(@NonNull T holder, int childAdapterPosition);
}
